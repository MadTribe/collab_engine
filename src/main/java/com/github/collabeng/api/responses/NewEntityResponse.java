package com.github.collabeng.api.responses;

import java.util.Date;

/**
 * Created by paul.smout on 22/03/2015.
 */
public class NewEntityResponse extends BaseResponse {
    private final long id;


    public NewEntityResponse(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }


}
