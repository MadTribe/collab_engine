package com.github.collabeng.dao.queries;

import com.github.collabeng.domain.UserEntity;

import java.util.Set;

/**
 * Created by paul.smout on 07/03/2015.
 */
public class OwnedBy extends AbstractCondition implements Condition {

    private final UserEntity owner;
    private String prefix = "";

    public OwnedBy(UserEntity owner){
        super();
        this.owner = owner;
    }

    public OwnedBy(Condition condition, UserEntity owner, String prefix){
        super(condition);
        this.owner = owner;
        this.prefix = prefix;
    }

    public OwnedBy(Condition condition, UserEntity owner){
        super(condition);
        this.owner = owner;
    }

    @Override
    public Set<QueryParam<?>> params() {
         condition.params().add(new QueryParam(prefix + "owner", owner));
        return condition.params();
    }

    @Override
    public String conditionString() {
        return condition.conditionString() + " ownedBy = :owner";
    }
}
