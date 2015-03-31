package com.github.collabeng.services;

import com.github.collabeng.api.error.EventNotFoundException;
import com.github.collabeng.api.error.TaskEventValidationException;
import com.github.collabeng.api.error.TaskNotFoundException;
import com.github.collabeng.api.requests.EventMessage;
import com.github.collabeng.dao.PlanStepEventDao;
import com.github.collabeng.dao.TaskDao;
import com.github.collabeng.domain.PlanStepEntity;
import com.github.collabeng.domain.PlanStepEventEntity;
import com.github.collabeng.domain.Task;
import com.github.collabeng.domain.TaskStatus;
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

    @Transactional
    public void handleTaskEvent(EventMessage eventMessage) {
        String name = eventMessage.getEventName();
        Long taskId = eventMessage.getTaskId();

        Task task = taskDao.find(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));

        List<PlanStepEventEntity> events = task.getPlanStep().getKnownPossibleEvents();

        Optional<PlanStepEventEntity> matchingEvets = events.stream().filter((PlanStepEventEntity event) -> { return event.getEventName().equalsIgnoreCase(name);}).findFirst();

        PlanStepEventEntity event = matchingEvets.orElseThrow(() -> {
            return new EventNotFoundException(name);
        });
        LOG.info("Found Matching Event " + event);

        try {
            if (validateEvent(event)){
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


    }

    private boolean validateEvent(PlanStepEventEntity event) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        String validatorName = event.getEventValidator();
        Class<?> validatorClass = this.getClass().getClassLoader().loadClass(validatorName);
        EventValidator validator = (EventValidator) validatorClass.newInstance();

        return validator.isValid(event);
    }
}
