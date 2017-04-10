package com.densev.multimodule.visitor;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dzianis_Sevastseyenk on 01/05/2017.
 */
public class App {

    public static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String... args) {

        Stopwatch stopwatch = Stopwatch.createStarted();

        Converter converter = new Converter();
        Convertible convertible = new Convertible();
        convertible.setNumber(1);

        converter.convert(convertible);

        logger.info("elapsed {} {}", stopwatch.elapsed(TimeUnit.MICROSECONDS), TimeUnit.MICROSECONDS.toString());


       /* List<ArrayList<String>> lists = new ArrayList<>();
        ArrayList<String> list1 = new ArrayList<>();
        list1.addAll(Arrays.asList("Asd", " bcdd "));
        ArrayList<String> list2 = null;

        lists.add(list1);
        lists.add(list2);

        lists.stream()
            .filter(CollectionUtils::isNotEmpty)
            .forEach(innerlist -> innerlist
                .stream()
                .filter(Objects::nonNull)
                .forEach(logger::info));*/

        List<Obj> lobj = new ArrayList<>();
        lobj.add(new Obj(1, 3));
        lobj.add(new Obj(1, 1));
        lobj.add(new Obj(1, 2));

        lobj.add(new Obj(2, 1));
        lobj.add(new Obj(2, 2));

        Random r = new Random();


        TreeMap<Double, Obj> doubles = new TreeMap<>();


        long time = 0;

        for (int i = 0; i < 100000; i++) {
            List<Integer> lint = new ArrayList<>();
            for (int j = 0; j < 100000; j++) {
                lint.add(r.nextInt());
            }
            Stopwatch st = Stopwatch.createStarted();
            lint.parallelStream().filter(o -> o % 5 == 0);
            long t = st.elapsed(TimeUnit.NANOSECONDS);
            if (i > 0) {
                time += t;
            }
        }

        logger.info("total time: {}", time);
        logger.info("average time: {}", (double) time / 9d);

        /*for (Obj o : lobj) {
            doubles.put(sortObj(o), o);
        }*/
            /*lobj
            .stream()
            .collect(Collectors.groupingBy(
                ()->new TreeMap<Double, Obj>(App::sortObj),
                i -> i
            ));*/

        for (Double key : doubles.keySet()) {
            System.out.println(key + " " + doubles.get(key));
        }

    }

    static Set<Integer> integers = new HashSet<>();
    static double mutiplier = 0.5;
    static double counter = 0;
    static double otherCounter = 0;

    public static double sortObj(Obj obj) {
        if (integers.contains(obj.a)) {
            return otherCounter--;
        } else {
            integers.add(obj.a);
            counter++;
            return 1 / counter;
        }
    }

    public static class Obj {

        public Integer a;
        public Integer b;

        public Obj(Integer a, Integer b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public String toString() {
            return "Obj{" +
                "a=" + a +
                ", b=" + b +
                '}';
        }
    }
}
