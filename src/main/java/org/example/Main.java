package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.example.dto.CountedEnrollmentForStudent;
import org.example.dto.CountedEnrollmentForStudentName;
import org.example.dto.EnrolledStudent;
import org.example.entities.Student;
import org.example.persistence.CustomPersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        Map<String, String> props = new HashMap<>();
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "none");  // create, none, update

        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(), props);
        EntityManager em = emf.createEntityManager(); // represents the context

        try {
            em.getTransaction().begin();

            /*
            String jpql = """
                    SELECT NEW org.example.dto.CountedEnrollmentForStudent(s, COUNT(s))
                    FROM Student s
                    GROUP BY s
                    """;

            TypedQuery<CountedEnrollmentForStudent> q = em.createQuery(jpql, CountedEnrollmentForStudent.class);
            q.getResultList().forEach(o -> System.out.println(o.s() + " " + o.count()));

             */

            /*
            String jpql = """
                    SELECT NEW org.example.dto.CountedEnrollmentForStudentName(s.name, COUNT(s))
                    FROM Student s
                    GROUP BY s.name
                    HAVING s.name LIKE '%e'
                    ORDER BY s.name DESC
                    """;

            TypedQuery<CountedEnrollmentForStudentName> q = em.createQuery(jpql, CountedEnrollmentForStudentName.class);
            q.getResultList().forEach(o -> System.out.println(o.s() + " " + o.count()));

             */

            TypedQuery<Student> q = em.createNamedQuery("getAllEnrolledStudents", Student.class);
            q.getResultList().forEach(System.out::println);

            em.getTransaction().commit(); // end of transaction
        } finally {
            em.close();
        }
    }
}