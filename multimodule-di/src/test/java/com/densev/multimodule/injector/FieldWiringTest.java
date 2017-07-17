package com.densev.multimodule.injector;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created by Dzianis_Sevastseyenk on 07/17/2017.
 */
public class FieldWiringTest {

    private TestApp testApp;

    @BeforeTest
    public void init() {
        testApp = Injector.INSTANCE.getBean(TestApp.class);
    }

    @Test
    public void testFieldWiring() {
        String expectedResult = "test";
        String actualResult = testApp.run(expectedResult);

        assertEquals(actualResult, expectedResult);
    }
}
