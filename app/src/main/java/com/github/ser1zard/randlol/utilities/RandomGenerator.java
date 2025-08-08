package com.github.ser1zard.randlol.utilities;

import java.util.Random;

public class RandomGenerator {
    private Random first, second;

    public RandomGenerator() {
        first = new Random();
        second = new Random();
    }

    public Random getRandom() {

        if(first != second) {
            return first;
        } else {
            do {
                second = new Random();
            } while (first == second);
            first = second;

            return second;
        }
    }
}
