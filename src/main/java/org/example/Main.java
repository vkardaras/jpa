package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.entities.Passport;
import org.example.entities.Person;
import org.example.persistence.CustomPersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        Map<String, String> props = new HashMap<>();
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "create");  // create, none, update

        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(), props);
        EntityManager em = emf.createEntityManager(); // represents the context

        try {
            em.getTransaction().begin();

            Person person = new Person();
            person.setName("John");

            Passport passport = new Passport();
            passport.setNumber("ABC123");

            person.setPassport(passport);
            passport.setPerson(person);

            em.persist(person);
//            em.persist(passport); // When use cascade = CascadeType.PERSIST in the Person entity this line is not needed

//            TypedQuery<Person> q = em.createQuery("SELECT p FROM Person p WHERE p.passport.number = :number", Person.class);
//            q.setParameter("number", "ABC123");
//            System.out.println(q.getResultList());

            em.getTransaction().commit(); // end of transaction
        } finally {
            em.close();
        }
    }
}