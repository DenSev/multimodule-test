package com.densev.chess.util;

import java.util.Random;

/**
 * Random bag, creates {@link Random} with {@link System}.currentTimeMillis() at startup
 *
 * Created on: 10/24/18
 */
public class RandomBag {

    private RandomBag(){
    }

    private static final Random random = new Random(System.currentTimeMillis());

    public static int getInt(){
        return random.nextInt();
    }

    public static int getInt(int max) {
        return random.nextInt(max);
    }
}
