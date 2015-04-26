package com.github.collabeng.sandboxen;

import com.github.collabeng.constants.ContextAttributes;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by paul.smout on 23/04/2015.
 */
public class PlanContext {

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

}
