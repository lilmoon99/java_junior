package org.example;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> car = Class.forName("org.example.Car");
        Constructor<?>[] constructors = car.getConstructors();
        Object gaz = constructors[0].newInstance("ГАЗ");
        System.out.println(gaz);

        Field[] fields = gaz.getClass().getFields();
        int temp  = fields[fields.length -1].getInt(gaz);

        fields[fields.length - 1].setInt(gaz,temp * 2);
        System.out.println(gaz);

        Method[] methods = gaz.getClass().getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            System.out.println(methods[i]);
        }

        //TODO 1:32:21
    }
}