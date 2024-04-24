package org.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class Homework {
    public static void main(String[] args) {

        Example example = new Example();
        RandomDateProcessor.processRandomLocalDate(example);
        Instant instant = Instant.parse("2018-11-30T18:35:24.00Z");
        System.out.println(instant);
    }

    private static class Example {
        @RandomDate(min = "2024-12-01", max = "2024-12-10")
        LocalDate Localdate;

        Date date;

        LocalDateTime localDateTime;

        Instant instant;


        public Example() {
        }



        @Override
        public String toString() {
            return "Example{" +
                    "date=" + date +
                    '}';
        }
    }

    /**
     * 1. Создать аннотацию RandomDate со следующими возможностями:
     * 1.1 Если параметры не заданы, то в поле должна вставляться рандомная дата в диапазоне min, max.
     * 1.2 Аннотация должна работать с полем типа java.util.Date.
     * 1.3 Должна генерить дату в диапазоне [min, max)
     * 1.4 ** Научиться работать с полями LocalDateTime, LocalDate, Instant, ... (классы java.time.*)
     * <p>
     * Реализовать класс RandomDateProcessor по аналогии с RandomIntegerProcessor, который обрабатывает аннотацию.
     */

    // TODO
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.LOCAL_VARIABLE, ElementType.FIELD})
    public static @interface RandomDate {

        // UNIX timestamp - количество милисекунд, прошедших с 1 января 1970 года по UTC-0
        String min() default "2024-01-01"; // 1 января 2024 GMT

        String max() default "2024-12-31";

    }

    private static class RandomDateProcessor {
        public static void processRandomLocalDate(Object object) {
            for (Field declaredField : object.getClass().getDeclaredFields()) {
                RandomDate annotation = declaredField.getAnnotation(RandomDate.class);
//                System.out.println(declaredField.getGenericType().getTypeName());
                switch (declaredField.getGenericType().getTypeName()){
                    case "java.time.LocalDate":
                        break;
                    case "java.util.Date":
                        break;
                    case "java.time.LocalDateTime":
                        break;
                    case "java.time.Instant":
                        break;
                }
                if (annotation != null) {
                    LocalDate min = LocalDate.parse(annotation.min());
                    LocalDate max = LocalDate.parse(annotation.max());
                    declaredField.setAccessible(true);

                    try {
                        long unixStartTime = min.toEpochDay();
                        long unixEndTime = max.toEpochDay();
                        long randomDate = ThreadLocalRandom.current().nextLong(unixStartTime, unixEndTime);
                        LocalDate result = LocalDate.ofEpochDay(randomDate);
                        declaredField.set(object, result);
                    } catch (IllegalAccessException e) {
                        System.err.println("Не удалось подставить рандомное значение: " + e);
                    }
                }
            }
        }

        private void processLocalDate(){

        }
    }

}