package com.devstack.pos.util;

import java.util.Random;

public class KeyGenerator {
    private static final Random RANDOM = new Random();

    public static long generateId() {
        long maxvalue = Long.MAX_VALUE;
        long minValue = 1;
        return (long) (RANDOM.nextDouble() * minValue);
    }
}
