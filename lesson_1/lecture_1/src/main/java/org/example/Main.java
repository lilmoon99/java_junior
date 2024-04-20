package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

//
//        PlainInterface plainInterface = Integer::sum;
//
//        System.out.println(plainInterface.action(5,3));

//        List<String> list = Arrays.asList("Привет","мир","!","я","родился","!");
//
////        list.stream().filter(str -> str.length() > 4).forEach(System.out::println);
//        list.stream().filter(str -> str.length() > 4).filter(str -> str.contains("о")).forEach(System.out::println);
//
//        Arrays.asList(1,2,3,4,5).stream().map(x ->"Число: " + x +" Квадрат числа: " + x * x).forEach(System.out::println);
//
//        Arrays.asList(1,10,0,5,7,5).stream().sorted().forEach(System.out::println);
    List<User> users = Arrays.asList(new User("Pavel",25),new User("Maxim",32),new User("Arkadiy",74));
    users.stream().map(user -> new User(user.name,user.age - 5)).filter(user -> user.age <= 25).forEach(System.out::println);
    }
}