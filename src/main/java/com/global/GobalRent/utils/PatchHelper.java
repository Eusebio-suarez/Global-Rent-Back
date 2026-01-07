package com.global.GobalRent.utils;

import org.openapitools.jackson.nullable.JsonNullable;

import java.util.function.Consumer;

public final class PatchHelper {

    public static <T> void updateIfPresent(JsonNullable<T> field, Consumer<T> setter){

        if(field != null && field.isPresent()){
            setter.accept(field.get());
        }

    }
}
