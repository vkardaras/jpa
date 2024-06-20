package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.example.dto.CountedEnrollmentForStudent;
import org.example.dto.CountedEnrollmentForStudentName;
import org.example.dto.EnrolledStudent;
import org.example.entities.DistinctStudent;
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
            String sql = """
                    SELECT * FROM student
                    """;

            Query q = em.createNativeQuery(sql, Student.class);
            q.getResultList().forEach(System.out::println);

             */

            // How to create a view
            // CREATE VIEW view_unique_students AS
            // SELECT DISTINCT name FROM student

            String sql = "SELECT s FROM DistinctStudent s";
            TypedQuery<DistinctStudent> q = em.createQuery(sql, DistinctStudent.class);
            q.getResultList().forEach(System.out::println);

            em.getTransaction().commit(); // end of transaction
        } finally {
            em.close();
        }
    }
}