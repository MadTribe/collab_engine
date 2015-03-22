package com.github.collabeng.domain;

import javax.persistence.ManyToOne;

/**
 * Created by paul.smout on 22/03/2015.
 */
public class Task extends OwnedEntity {

    @ManyToOne
    private TaskContext context;

    @ManyToOne PlanStepEntity planStep;



}
