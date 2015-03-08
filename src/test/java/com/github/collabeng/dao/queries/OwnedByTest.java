package com.github.collabeng.dao.queries;

import com.github.collabeng.domain.UserEntity;
import org.junit.Test;

/**
 * Created by paul.smout on 07/03/2015.
 */
public class OwnedByTest {
    @Test
    public void testConditionString() throws Exception {
        UserEntity user1 = new UserEntity("paul1","ppp","qqq",true);
        UserEntity user2 = new UserEntity("paul2","ppp","qqq",true);

        OwnedBy query = new OwnedBy(new Or(new OwnedBy(user1)), user2);

        System.err.println(query.conditionString());
    }
}
