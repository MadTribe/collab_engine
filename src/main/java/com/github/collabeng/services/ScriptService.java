package com.github.collabeng.services;

import com.github.collabeng.api.dto.NamedEntityDto;
import com.github.collabeng.api.dto.ScriptDto;
import com.github.collabeng.api.error.ItemNotFoundException;
import com.github.collabeng.api.error.ScriptException;
import com.github.collabeng.api.requests.EventMessage;
import com.github.collabeng.api.requests.NewScriptRequest;
import com.github.collabeng.api.requests.UpdateScriptRequest;
import com.github.collabeng.api.responses.NamedEntityListResponse;
import com.github.collabeng.api.responses.NewEntityResponse;
import com.github.collabeng.dao.ScriptDao;
import com.github.collabeng.domain.*;
import com.github.collabeng.sandboxen.BasicSandbox;
import com.github.collabeng.sandboxen.GroovySandbox;
import com.github.collabeng.sandboxen.TaskEventHandlerAPI;
import com.github.collabeng.sandboxen.TaskEventHandlerSandbox;
import com.google.inject.persist.Transactional;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.lang.invoke.MethodHandles;
import java.util.*;

/**
 * Created by paul.smout on 14/04/2015.
 */
public class ScriptService {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Inject
    private GroovySandbox sandbox;

    @Inject
    private ScriptDao scriptDao;

    @Inject
    private ContextService contextService;

    @Transactional
    public NewEntityResponse createNewScript(NewScriptRequest newScriptRequest){
        Script newScript = constructScriptFromRequest(newScriptRequest);
        newScript = scriptDao.persist(newScript);
        return new NewEntityResponse(newScript.getId());
    }

    public String testScript(String script){
        BasicSandbox basicSandbox = new BasicSandbox();
        // basicSandbox.getAllowedTypes().add(ScriptService.class);
        return sandbox.scriptRunner(script,this, basicSandbox );
    }

    @Transactional
    public void updateScript(NewScriptRequest updateScriptRequest) {
        LOG.info("updating script with name {}",updateScriptRequest.getName());
        Optional<Script> scriptOptional = scriptDao.findByName(updateScriptRequest.getName());
        Script script = scriptOptional.orElseThrow(() -> new ItemNotFoundException("Script",updateScriptRequest.getName()));
        script = script.withScriptContent(updateScriptRequest.getSource());

        scriptDao.merge(script);

    }

    private Set<ScriptParameter> convertParam(Map<String, String> params) {
        return null;
    }

    private Script constructScriptFromRequest(NewScriptRequest newScriptRequest){
        Script script = new Script(newScriptRequest.getName(), newScriptRequest.getSource(), ScriptType.valueOf(newScriptRequest.getType()), convertParam(newScriptRequest.getParams()));

        return script;
    }

    public NamedEntityListResponse listMatching(String match) {
        List<NamedEntityDto> results = new ArrayList<>();
        scriptDao.findByNamePrefix(match).forEach((Script script) -> {
            results.add(new NamedEntityDto(script.getId(), script.getVersion(), script.getCreatedAt(), script.getLastModifiedAt(), script.getName()));
        });

        LOG.info("" + results);
        return new NamedEntityListResponse(results);
    }

    public ScriptDto getScriptByName(String name) {
        Script script = scriptDao.findByName(name).orElseThrow(() -> new ItemNotFoundException("Script",name));
        return new ScriptDto(script);
    }

    public String runTaskEventHandler(Script eventHandler, Task task, PlanStepEventEntity eventDefinintion, EventMessage eventMessage) {

        contextService.getContextAPI(task);

        TaskEventHandlerSandbox basicSandbox = new TaskEventHandlerSandbox();
        TaskEventHandlerAPI api = new TaskEventHandlerAPI(eventDefinintion, task, eventMessage  );

        String resp = "";

        try {
            resp = sandbox.scriptRunner(eventHandler.getScriptContent(), api, basicSandbox );
        } catch (RuntimeException rte){
            LOG.error("error running script", rte);
            throw new ScriptException(rte.getMessage(),rte);
        }

        return resp;
    }
}
