package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.entities.Student;
import org.example.entities.keys.StudentKey;
import org.example.persistence.CustomPersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        Map<String, String> props = new HashMap<>();
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "update");  // create, none, update

        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(), props);
        EntityManager em = emf.createEntityManager(); // represents the context

        try {
            em.getTransaction().begin();

//            Product p1 = new Product();
//            p1.setCode("ABC");
//            p1.setNumber(10);
//            p1.setColor("Red");

            StudentKey id = new StudentKey();
            id.setCode("ABC");
            id.setNumber(10);

            Student s = new Student();
            s.setId(id);
            s.setName("John");

            em.persist(s);

            em.getTransaction().commit(); // end of transaction
        } finally {
            em.close();
        }
    }
}