package com.github.collabeng.services;

import com.github.collabeng.constants.ContextAttributes;
import com.github.collabeng.dao.TaskContextDao;
import com.github.collabeng.domain.Task;
import com.github.collabeng.domain.TaskContext;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.inject.Inject;

/**
 * Created by paul.smout on 24/04/2015.
 */
public class ContextService {

    @Inject
    private MongoDatabase mongoDatabase;

    @Inject
    private TaskContextDao taskContextDao;

    public void getContextAPI(Task task) {

        //TaskContext context = task.getContext();
        //context.getParameters();


    }

    public TaskContext createNewContext(){
        MongoCollection<Document> contexts = mongoDatabase.getCollection(ContextAttributes.CONTEXTS_COLLECTION);

        TaskContext taskContextEntity = new TaskContext();
        taskContextEntity = taskContextDao.persist(taskContextEntity);

        Document newContext = new Document();
        newContext.put(ContextAttributes.ID,taskContextEntity.getId());
        newContext.put(ContextAttributes.CREATION_DATE, taskContextEntity.getCreatedAt());
        newContext.put(ContextAttributes.OWNER,taskContextEntity.getOwner().getId());


        contexts.insertOne(newContext);

        return taskContextEntity;
    }
}
