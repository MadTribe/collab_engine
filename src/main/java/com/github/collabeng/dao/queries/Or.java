package com.github.collabeng.dao.queries;

import java.util.Collections;
import java.util.Set;

/**
 * Created by paul.smout on 07/03/2015.
 */
public class Or extends AbstractCondition {
    public Or(Condition condition) {
        super(condition);
    }

    @Override
    public Set<QueryParam<?>> params() {
        return condition.params();
    }

    @Override
    public String conditionString() {
        return  condition.conditionString() +  " or " ;
    }
}
