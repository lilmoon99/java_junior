package org.moon;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.SQLException;
import java.util.List;


/**
 * Перенести структуру дз третьего урока на JPA:
 * 1. Описать сущности Student и Group
 * 2. Написать запросы: Find, Persist, Remove
 * 3. ... поупражняться с разными запросами ...
 */
public class Homework {
    private static final SessionFactory sessionFactory = Connector.getSessionFactory();

    public static void main(String[] args) throws SQLException {
        insertInitData();
        getAllStudents();
        getAllGroups();
        getStudentById(1L);

    }

    public static void insertInitData() {
        try (Session session = sessionFactory.openSession()) {
            StudyGroup studyGroupJava = new StudyGroup();
            studyGroupJava.setName("Java");
            StudyGroup studyGroupPython = new StudyGroup();
            studyGroupPython.setName("Python");

            Student student1 = createStudent("Aynur", "Shamsullin", studyGroupJava);
            Student student2 = createStudent("Elisa", "Vienne", studyGroupPython);
            session.beginTransaction();
            session.persist(student1);
            session.persist(student2);
            session.persist(studyGroupJava);
            session.persist(studyGroupPython);
            session.getTransaction().commit();
        }
    }

    public static void getAllStudents() {
        try (Session session = sessionFactory.openSession()) {
            List<Student> selectAFromStudentA = session.createQuery("SELECT a FROM Student a", Student.class).getResultList();
            selectAFromStudentA.forEach(System.out::println);
        }
    }

    public static void getAllGroups() {
        try (Session session = sessionFactory.openSession()) {
            List<StudyGroup> selectAFromStudyGroupA = session.createQuery("SELECT a FROM StudyGroup a", StudyGroup.class).getResultList();
            selectAFromStudyGroupA.forEach(x -> System.out.println(x.getName() + ":\n" + x.getStudents()));
        }
    }


    public static void getStudentById(long id){
        try (Session session = sessionFactory.openSession()){
            Student singleResult = session.createQuery("From Student as s Where s.id = " + id, Student.class).getSingleResult();
            System.out.println(singleResult);
        }
    }

    private static Student createStudent(String firstName, String secondName, StudyGroup studyGroup) {
        Student student = new Student();
        student.setFirstName(firstName);
        student.setSecondName(secondName);
        student.setStudyGroup(studyGroup);
        return student;
    }
}
