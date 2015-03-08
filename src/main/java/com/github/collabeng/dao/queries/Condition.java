package com.github.collabeng.dao.queries;

import java.util.Set;

/**
 * Created by paul.smout on 07/03/2015.
 */
public interface Condition {

    Set<QueryParam<?>> params();

    String conditionString();

}
