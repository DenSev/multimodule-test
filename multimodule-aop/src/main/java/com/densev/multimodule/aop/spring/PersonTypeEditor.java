package com.densev.multimodule.aop.spring;

import com.densev.multimodule.aop.PersonType;

import java.beans.PropertyEditorSupport;

/**
 * Created by Dzianis_Sevastseyenk on 03.16.2018.
 */
public class PersonTypeEditor extends PropertyEditorSupport {

    public void setValue(Object value) {


        setValue(new PersonType(((String)value).toUpperCase()));
    }
}
