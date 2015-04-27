package com.github.collabeng.sandboxen;

import com.github.collabeng.api.error.ScriptException;
import com.github.collabeng.api.error.UnknownParameterException;
import com.github.collabeng.constants.ContextAttributes;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by paul.smout on 23/04/2015.
 */
public class PlanContext {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private MongoDatabase mongoDatabase;

    private Long contextId;

    public PlanContext(MongoDatabase mongoDatabase, Long contextId) {
        this.mongoDatabase = mongoDatabase;
        this.contextId = contextId;
    }

    public void saveToList(String listName, Map<String, Object> objectMap){
        MongoCollection<Document> contexts = mongoDatabase.getCollection(ContextAttributes.CONTEXTS_COLLECTION);

        Document objectToSave = new Document();
        for (String key : objectMap.keySet()){
            objectToSave.put(key, objectMap.get(key));
        }

        contexts.updateOne(eq(ContextAttributes.ID,contextId),
                           new Document("$addToSet", new Document(listName,objectToSave )));

//        db.test.update(
//                { _id: 1 },
//        { $addToSet: {letters: [ "c", "d" ] } }
//        )
    }


    public List<Map<String,Object>> getList(String name){
        List<Map<String,Object>> ret = null;
        MongoCollection<Document> contexts = mongoDatabase.getCollection(ContextAttributes.CONTEXTS_COLLECTION);
        Document doc = contexts.find(eq(ContextAttributes.ID,contextId)).first();

        if (doc.containsKey(name)){
            Object field = doc.get(name);
            LOG.info("field is of class " + field.getClass());
            ret = handleField(field);
        } else {
            throw new ScriptException("Unknown List " + name, null);
        }

        return ret;
    }


    private List handleField(Object field){
        return (List)field;
    }

    private List handleField(List field){
        throw new ScriptException("Field is not a list", null);
    }
}
