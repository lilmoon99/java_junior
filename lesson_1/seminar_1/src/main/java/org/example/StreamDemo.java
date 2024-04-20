package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class StreamDemo {
    public static void main(String[] args) {
//        List<Integer> ints = new ArrayList<>();
//        for (int i = 0; i < 30; i++) {
//            int randomInt = ThreadLocalRandom.current().nextInt(0,100);
//            if (ThreadLocalRandom.current().nextBoolean()){
//                randomInt = -randomInt;
//            }
//            ints.add(randomInt);
//        }
//
//        ints.stream()
//                .filter(it -> it > 0)
//                .filter(it -> it % 2 ==0)
//                .map(it -> it *4)
//                .forEach(System.out::println);

        List<Department> departments = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            departments.add(new Department("Department #" + i));
        }
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            int randomDepartmentIndex = ThreadLocalRandom.current().nextInt(departments.size());
            Department department = departments.get(randomDepartmentIndex);

            Person person = new Person();
            person.setName("Person #" + i);
            person.setAge(ThreadLocalRandom.current().nextInt(20,65));
            person.setSalary(ThreadLocalRandom.current().nextDouble(20000,100000));
            person.setDepartment(department);
            persons.add(person);
        }
        System.out.println(findBestPerson(persons).get());

    }

    // Вывести имена сотрудников старше 40 лет
    static void printPersons(List<Person> persons){
        persons.stream()
                .filter(x -> x.getAge() > 40)
                .map(x -> x.getName())
                .forEach(System.out::println);
    }

    //Найти сотрудника с самой высокой зарплатой в департаменте №5
    static Optional<Person> findBestPerson(List<Person> persons){
        return persons.stream()
                .filter(x -> x.getDepartment().getName().equals("Department #5"))
                .max((o1, o2) -> Double.compare(o1.getSalary(),o2.getSalary()));
    }

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

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", salary=" + salary +
                    ", department=" + department +
                    '}';
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

        public void setName(String name) {
            this.name = name;
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

        @Override
        public String toString() {
            return "Department{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
