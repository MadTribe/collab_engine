package com.github.collabeng.api.error;

import static java.lang.String.format;

/**
 * Created by paul.smout on 09/03/2015.
 */
public class ItemNotFoundException extends BusinessException{
    public ItemNotFoundException(String item, Long id) {
        super(format("%s Not Found for with %d",item, id));
    }

    public ItemNotFoundException(String item, String name) {
        super(format("%s Not Found for with name %d",item, name ));
    }
}
