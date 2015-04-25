package com.github.collabeng.api.responses;

import com.github.collabeng.api.dto.NamedEntityDto;

import java.util.List;

/**
 * Created by paul.smout on 20/04/2015.
 */
public class NamedEntityListResponse {
    private List<NamedEntityDto> items;


    public NamedEntityListResponse() {
    }

    public NamedEntityListResponse(List<NamedEntityDto> items) {
        this.items = items;
    }

    public List<NamedEntityDto> getItems() {
        return items;
    }
}
