package org.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class Homework {
    public static void main(String[] args) {

        Example example = new Example();
        RandomDateProcessor.processRandomLocalDate(example);
        System.out.println(example);
    }

    private static class Example {
        @RandomDate(min = "2024-12-01", max = "2024-12-10")
        LocalDate Localdate;
        @RandomDate(min = "2024-12-01", max = "2024-12-10")
        Date date;

        @RandomDate(min = "2024-12-01", max = "2024-12-10")
        LocalDateTime localDateTime;

        @RandomDate(min = "2024-12-01", max = "2024-12-10")
        Instant instant;


        public Example() {
        }


        @Override
        public String toString() {
            return "Example{" +
                    "\nLocalDate=" + Localdate +
                    "\ndate=" + date +
                    ",\nlocalDateTime=" + localDateTime +
                    ",\ninstant=" + instant +
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
                if (annotation != null) {
                    declaredField.setAccessible(true);
                    switch (declaredField.getGenericType().getTypeName()) {
                        case "java.time.LocalDate":
                            try {
                                declaredField.set(object, processLocalDate(annotation));
                            } catch (IllegalAccessException e) {
                                System.err.println("Не удалось подставить рандомное значение: " + e);
                            }
                            break;
                        case "java.util.Date":
                            try {
                                declaredField.set(object, processDate(annotation));
                            } catch (IllegalAccessException e) {
                                System.err.println("Не удалось подставить рандомное значение: " + e);
                            }
                            break;
                        case "java.time.LocalDateTime":
                            try {
                                declaredField.set(object, processLocalDateTime(annotation));
                            } catch (IllegalAccessException e) {
                                System.err.println("Не удалось подставить рандомное значение: " + e);
                            }
                            break;
                        case "java.time.Instant":
                            try {
                                declaredField.set(object,processInstant(annotation));
                            } catch (IllegalAccessException e) {
                                System.err.println("Не удалось подставить рандомное значение: " + e);
                            }
                            break;
                    }
                }


            }
        }
    }

    private static LocalDate processLocalDate(RandomDate annotation) {
        LocalDate min = LocalDate.parse(annotation.min());
        LocalDate max = LocalDate.parse(annotation.max());
        long unixStartTime = min.toEpochDay();
        long unixEndTime = max.toEpochDay();
        long randomDate = ThreadLocalRandom.current().nextLong(unixStartTime, unixEndTime);
        return LocalDate.ofEpochDay(randomDate);
    }

    private static Date processDate(RandomDate annotation) {
        LocalDate randomLocalDate = processLocalDate(annotation);
        return new Date(randomLocalDate.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli());
    }

    private static LocalDateTime processLocalDateTime(RandomDate annotation) {
        return processLocalDate(annotation).atStartOfDay();
    }

    private static Instant processInstant(RandomDate annotation){
        LocalDate randomLocalDate = processLocalDate(annotation);
        return  Instant.ofEpochSecond(randomLocalDate.toEpochSecond(randomLocalDate.atStartOfDay().toLocalTime(),ZoneOffset.UTC));
    }
}

