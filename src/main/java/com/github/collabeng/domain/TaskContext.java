package com.github.collabeng.domain;

import org.hibernate.annotations.BatchSize;

import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by paul.smout on 22/03/2015.
 */
public class TaskContext extends OwnedEntity {
    @OneToMany(mappedBy = "owningContext")
    private List<TaskContextParameter> parameters = new ArrayList<>();


}
