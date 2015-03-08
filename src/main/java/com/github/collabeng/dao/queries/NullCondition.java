package com.github.collabeng.dao.queries;

import java.util.Set;

import static java.util.Collections.emptySet;

/**
* Created by paul.smout on 07/03/2015.
*/
class NullCondition implements Condition{

    public NullCondition() {
    }

    @Override
    public Set<QueryParam<?>> params() {
        return emptySet();
    }

    @Override
    public String conditionString() {
        return "";
    }

}
