package com.github.collabeng.domain;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * Created by paul.smout on 22/03/2015.
 */
public class TaskContextParameter extends OwnedEntity{
    @NotNull
    @ManyToOne
    @JoinColumn(name = "CONTEXT_ID")
    private TaskContext owningContext;

}
