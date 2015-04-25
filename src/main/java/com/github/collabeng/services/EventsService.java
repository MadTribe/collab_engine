package com.github.collabeng.services;

import com.github.collabeng.api.error.EventNotFoundException;
import com.github.collabeng.api.error.TaskEventValidationException;
import com.github.collabeng.api.error.TaskNotFoundException;
import com.github.collabeng.api.requests.EventMessage;
import com.github.collabeng.api.requests.NewEventParameter;
import com.github.collabeng.api.responses.BaseResponse;
import com.github.collabeng.api.responses.NewEntityResponse;
import com.github.collabeng.api.responses.ScriptResponse;
import com.github.collabeng.dao.PlanStepEventDao;
import com.github.collabeng.dao.PlanStepEventParameterDao;
import com.github.collabeng.dao.TaskDao;
import com.github.collabeng.domain.*;
import com.github.collabeng.eventvalidators.EventValidator;
import com.google.inject.persist.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static java.lang.String.format;

/**
 * Created by paul.smout on 28/03/2015.
 */
public class EventsService {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Inject
    private TaskDao taskDao;

    @Inject
    private PlanStepEventDao planStepEventDao;

    @Inject
    private PlanStepEventParameterDao planStepEventParameterDao;

    @Inject
    private ScriptService scriptService;

    @Transactional
    public BaseResponse handleTaskEvent(EventMessage eventMessage) {
        LOG.info("Received Event: " + eventMessage);

        String name = eventMessage.getEventName();
        Long taskId = eventMessage.getTaskId();

        Task task = taskDao.find(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));

        List<PlanStepEventEntity> events = task.getPlanStep().getKnownPossibleEvents();

        Optional<PlanStepEventEntity> matchingEvents = events.stream().filter((PlanStepEventEntity event) -> { return event.getEventName().equalsIgnoreCase(name);}).findFirst();

        PlanStepEventEntity event = matchingEvents.orElseThrow(() -> new EventNotFoundException(name));
        LOG.info("Found Matching Event " + event);

        try {
            if (isValidEvent(event, eventMessage)){

                Script eventHandler = event.getEventHandler();
                if (event.getEventHandler() != null ) {
                    runEventHandler(eventHandler, task, event);
                }

                PlanStepEntity step = event.getNextStep();
                if (step != null){

                    Task next = new Task(null, step, TaskStatus.IN_PROGRESS);
                    LOG.info("Creating new task " + next);
                    taskDao.persist(next);
                } else {
                    LOG.info("No Next Step ");
                }

                task = task.withStatus(event.getNextStatus());
                taskDao.merge(task);
            } else {
                throw new TaskEventValidationException("Error Validating Event");
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {

            LOG.error("Error validating event",e);
            throw new TaskEventValidationException("Error Validating Event");
        }

        return new BaseResponse();

    }

    private ScriptResponse runEventHandler(Script eventHandler, Task task, PlanStepEventEntity event) {
        String resp = scriptService.runTaskEventHandler(eventHandler, task, event);
        return new ScriptResponse(resp);
    }

    private boolean isValidEvent(PlanStepEventEntity event, EventMessage eventMessage) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        String validatorName = event.getEventValidator();
        Class<?> validatorClass = this.getClass().getClassLoader().loadClass(validatorName);
        EventValidator validator = (EventValidator) validatorClass.newInstance();

        return validator.isValid(event, eventMessage);
    }

    @Transactional
    public NewEntityResponse createEventParameter(long eventId, NewEventParameter newEventParameter) {
        PlanStepEventEntity event = planStepEventDao.find(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        PlanStepEventParameter param = new PlanStepEventParameter(event, newEventParameter.getParamName(), newEventParameter.getType());

        param = planStepEventParameterDao.persist(param);

        event.getParameters().add(param);
        planStepEventDao.merge(event);

        return new NewEntityResponse(param.getId());
    }
}
