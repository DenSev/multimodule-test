package com.densev.multimodule.aop;

import org.springframework.stereotype.Component;

/**
 * Created by Dzianis_Sevastseyenk on 06/27/2017.
 */
@Component
public class RepositoryTest {

    public String search(String s) {

        return s + " - it's a test!";
    }

    public String wrapperSearch(String s) {
        return search(s);
    }


}
