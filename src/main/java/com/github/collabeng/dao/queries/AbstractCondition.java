package com.github.collabeng.dao.queries;

import java.util.Set;

import static java.util.Collections.emptySet;

/**
 * Created by paul.smout on 07/03/2015.
 */
public abstract class AbstractCondition implements Condition {


    protected Condition condition = new NullCondition();

    protected AbstractCondition() {
    }

    protected AbstractCondition(Condition condition) {
        this.condition = condition;
    }

}
