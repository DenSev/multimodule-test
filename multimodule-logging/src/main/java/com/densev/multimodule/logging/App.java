package com.densev.multimodule.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Dzianis_Sevastseyenk on 01/17/2017.
 */
public class App {

    public static void main(String... args){
        Logger logger = LoggerFactory.getLogger(App.class);

        String string[] = new String[]{"[asd, asd, asd]"};

        Arrays.stream(string).forEach((str)->{
            logger.error(str);
        });
        logger.info("text {}", 1);
        logger.warn("text {}", 2);
        logger.debug("text {}", 3);
        logger.error("text {}", 4);
        logger.trace("text {}", 5);

        String[] arr = new String[]{"1","2","3","4","5","6"};

        Integer in = lambdaTest("asdf", (str)->{
            try {
                System.out.println(str);
                return new Integer(str);
            } catch (NumberFormatException nfe){
                logger.error("Cannot convert str to integer", nfe);
                return null;
            }
        });
        System.out.println(in);

        Map<String, Integer> map = Arrays.stream(arr)
            .collect(Collectors.toMap(s -> s, s->1));

        System.out.println(map);
        /*try{
            EnumSingleton.THIS.test();
        } catch (Exception e){
            logger.error("error ", e);
        }*/

    }

    public static Integer lambdaTest(String t, Function<String, Integer> function){
        return function.apply(t);
    }

}
