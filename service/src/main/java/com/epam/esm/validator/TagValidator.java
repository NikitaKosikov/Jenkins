package com.epam.esm.validator;

import com.epam.esm.exception.InvalidNameOfTag;
import org.springframework.stereotype.Component;

@Component
public class TagValidator {

    private static final String CERTIFICATE_ERROR_CODE = "998";
    private static final String TAG_NAME_IS_EMPTY = "tag.name.empty";

    public void validateName(String tagName){
        if ("".equals(tagName) || tagName == null){
            throw new InvalidNameOfTag(TAG_NAME_IS_EMPTY, CERTIFICATE_ERROR_CODE);
        }
    }
}
