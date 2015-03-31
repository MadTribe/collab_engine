package com.github.collabeng.dao;

import com.github.collabeng.domain.PlanEntity;
import com.github.collabeng.domain.Task;
import com.github.collabeng.domain.TaskStatus;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by paul.smout on 26/01/2015.
 */
public class TaskDao extends AccessControlDao<Task> {

    public List<Task> allOpen(){
        return queryMany(ALIAS + ".status = :status",param("status", TaskStatus.IN_PROGRESS));
    }

}
