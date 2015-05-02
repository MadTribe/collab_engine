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

    public void saveToField(String name, Object value){
        LOG.info("Saving value " + value + " to key " + name );
        MongoCollection<Document> contexts = mongoDatabase.getCollection(ContextAttributes.CONTEXTS_COLLECTION);


        contexts.updateOne(eq(ContextAttributes.ID,contextId),
                new Document("$set", new Document(name, value )));

    }


    public Object getValue(String name){
        LOG.info("Getting Context Value for name " + name);
        Document doc = getContextDocument();
        Object ret = null;
        if (doc.containsKey(name)){
            ret = doc.get(name);
            LOG.info("field is of class " + ret.getClass());
        } else {
            throw new ScriptException("Unknown List " + name, null);
        }
        return ret;
    }


    public List<Map<String,Object>> getList(String name){
        Document doc = getContextDocument();
        List<Map<String, Object>> ret;

        // TODO end game will be a hierarchical (tree probably, network maybe) of Contexts e.g. Global, Project, Plan, maybe task. OR whatever the user needs
        // If a property is not found in the current context it will chain upwards. There will probably also be a syntax for absolute paths to  values from a root context
        // A permissions model will be implemented.
        if (doc.containsKey(name)){
            Object field = doc.get(name);
            LOG.info("field is of class " + field.getClass());
            ret = handleField(field);
        } else {
            throw new ScriptException("Unknown Field " + name, null);
        }

        return ret;
    }

    private Document getContextDocument() {
        List<Map<String,Object>> ret = null;
        MongoCollection<Document> contexts = mongoDatabase.getCollection(ContextAttributes.CONTEXTS_COLLECTION);
        return contexts.find(eq(ContextAttributes.ID,contextId)).first();
    }


    private List handleField(Object field){
        // TODO verify object is a list of Documents

        return (List)field;
    }


}
