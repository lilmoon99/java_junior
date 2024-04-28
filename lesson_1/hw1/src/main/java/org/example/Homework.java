package org.example;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class Homework {
    public static void main(String[] args) {
        List<Department> departments = new ArrayList<>();
        for (int i = 0; i <= 3; i++) {
            departments.add(new Department("Department #" + i));
        }
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            int randomDepartmentIndex = ThreadLocalRandom.current().nextInt(departments.size());
            Department department = departments.get(randomDepartmentIndex);

            Person person = new Person();
            person.setName("Person #" + i);
            person.setAge(ThreadLocalRandom.current().nextInt(20, 65));
            person.setSalary(ThreadLocalRandom.current().nextDouble(20000, 100000));
            person.setDepartment(department);
            persons.add(person);
        }

        //Вывод всех сотрудников
        persons.forEach(System.out::println);

//        //Task 1
//        System.out.println(countPersons(persons,35,80000));
//
        //Task 2
//        System.out.println(averageSalary(persons,2));
//        //Task 3
//        Map<Department,List<Person>> groupedByDepartment = groupByDepartment(persons);
//        for (Map.Entry<Department, List<Person>> departmentListEntry : groupedByDepartment.entrySet()) {
//            System.out.println(departmentListEntry);
//        }

//        //Task 4
//        Map<Department,Double> maxSalaryByDepartment = maxSalaryByDepartment(persons);
//        for (Map.Entry<Department, Double> departmentDoubleEntry : maxSalaryByDepartment.entrySet()) {
//            System.out.println(departmentDoubleEntry);
//        }

//        //Task 5
//        Map<Department,List<String>> groupPersonNamesByDepartment = groupPersonNamesByDepartment(persons);
//        for (Map.Entry<Department, List<String>> departmentListEntry : groupPersonNamesByDepartment.entrySet()) {
//            System.out.println(departmentListEntry);
//        }

//        //Task 6
//        List<Person> minSalaryPersons = minSalaryPersons(persons);
//        System.out.println();
//        for (Person minSalaryPerson : minSalaryPersons) {
//            System.out.println(minSalaryPerson);
//        }

    }

    /**
     * Используя классы Person и Department, реализовать методы ниже:
     */

    private static class Person {
        private String name;
        private int age;
        private double salary;
        private Department department;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public double getSalary() {
            return salary;
        }

        public void setSalary(double salary) {
            this.salary = salary;
        }

        public Department getDepartment() {
            return department;
        }

        public void setDepartment(Department department) {
            this.department = department;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", salary=" + salary +
                    ", department=" + department +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return age == person.age && Double.compare(salary, person.salary) == 0 && Objects.equals(name, person.name) && Objects.equals(department, person.department);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age, salary, department);
        }
    }

    private static class Department {
        private String name;

        public Department(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Department{" +
                    "name='" + name + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Department that = (Department) o;
            return Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    /**
     * Найти количество сотрудников, старше x лет с зарплатой больше, чем d
     */
    static int countPersons(List<Person> persons, int x, double d) {
        // TODO: Реализовать метод
        return (int) persons.stream()
                .filter(p -> p.age > x && p.getSalary() > d)
                .count();
    }

    /**
     * Найти среднюю зарплату сотрудников, которые работают в департаменте X
     */
    static double averageSalary(List<Person> persons, int x) {
        // TODO: Реализовать метод
        return persons.stream()
                .filter(person -> person.getDepartment().getName().contains(String.valueOf(x)))
                .mapToDouble(Person::getSalary)
                .average().getAsDouble();
    }

    ;

    /**
     * Сгруппировать сотрудников по департаментам
     */
    static Map<Department, List<Person>> groupByDepartment(List<Person> persons) {
        // TODO: Реализовать метод
//        Map<Department, List<Person>> result = new HashMap<>();
//        List<Department> departments = new ArrayList<>();
//        persons.stream().map(Person::getDepartment)
//                .distinct()
//                .forEach(departments::add);
//        for (Department department : departments) {
//            result.put(department, persons.stream()
//                            .filter(x->x.getDepartment().equals(department))
//                    .toList());
//        }
//        return result;
        return persons.stream().collect(groupingBy(Person::getDepartment));
    }

    /**
     * Найти максимальные зарплаты по отделам
     */
    static Map<Department, Double> maxSalaryByDepartment(List<Person> persons) {
        // TODO: Реализовать метод
        Map<Department,Double> result = new HashMap<>();
        List<Department> departments = new ArrayList<>();
        persons.stream().map(Person::getDepartment)
                .distinct()
                .forEach(departments::add);
        for (Department department : departments) {
            result.put(department,persons.stream()
                    .filter(p -> p.getDepartment().equals(department))
                    .mapToDouble(Person::getSalary)
                    .max().getAsDouble());
        }
        return result;

    }

    /**
     * ** Сгруппировать имена сотрудников по департаментам
     */
    static Map<Department, List<String>> groupPersonNamesByDepartment(List<Person> persons) {
        // TODO: Реализовать метод
        Map<Department, List<String>> result = new HashMap<>();
        List<Department> departments = new ArrayList<>();
        persons.stream().map(Person::getDepartment)
                .distinct()
                .forEach(departments::add);
        for (Department department : departments) {
            result.put(department, persons.stream()
                    .filter(x -> x.getDepartment().equals(department))
                    .map(Person::getName)
                    .toList());
        }


        return result;
    }

    /**
     * ** Найти сотрудников с минимальными зарплатами в своем отделе
     */
    static List<Person> minSalaryPersons(List<Person> persons) {
        // TODO: Реализовать метод
        // В каждом департаменте ищем сотрудника с минимальной зарплатой.
        // Всех таких сотрудников собираем в список и возвращаем из метода.
        List<Department> departments = new ArrayList<>();
        persons.stream().map(Person::getDepartment)
                .distinct()
                .forEach(departments::add);

        List<Person> result = new ArrayList<>();
        for (Department department : departments) {
            result.add(persons.stream().filter(person -> person.getDepartment().equals(department))
                    .min(Comparator.comparingDouble(Person::getSalary))
                    .get());
        }
        return result;
    }

}
