package org.example;

import java.util.Random;
import java.util.function.*;

public class Lambdas {
    public static void main(String[] args) {

        Runnable obj = () -> {
            System.out.println("Hello,world!");
        };
        obj.run();

        Consumer<String> consumer = str -> System.out.println(str);
        consumer.accept("Hello from consumer!");

        IntSupplier supplier = () -> new Random().nextInt(0, 10);

        System.out.println(supplier.getAsInt());

        Function<String, Integer> stringLengthExtractor = x -> x.length();
        System.out.println(stringLengthExtractor.apply("Java"));

        BiFunction<Integer, Integer, Integer> function = (x, y) -> x + y;
        System.out.println(function.apply(10, 4));

        Predicate<Integer> isEvenTester = x -> x % 2 == 0;
        System.out.println(isEvenTester.test(0));
        System.out.println(isEvenTester.test(2));
        System.out.println(isEvenTester.test(1));
//        Runnable object = new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Hello,world");
//            }
//        };
//
//        object.run();
    }

}
