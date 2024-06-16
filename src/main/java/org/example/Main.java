package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.entities.Employee;
import org.example.entities.Product;
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

            var e1 = new Employee();
            e1.setName("John Doe");
            e1.setAddress("123 Main St");

            em.persist(e1);

            /*
            // find vs getReference
//            var e1 = em.find(Employee.class, 1);
            var e1 = em.getReference(Employee.class, 1); // Creates a shell around the entity and execute any command if the entity is used
            System.out.println(e1);

            e1.setName("Anne");

            em.refresh(e1);  // Undo any changes to the object from the last commit

             */


//            em.persist();     -> Adding an entity in the context
//            em.find();        -> Find by PK. Get from DB and add it to the context if it doesn't already exist
//            em.remove();      -> Marking entity for removal
//            em.merge();       -> Merges an entity from outside the context to the context
//            em.refresh();     -> Mirror the context from the database
//            em.detach();      -> Taking the entity out of the context
//            em.getReference();

            em.getTransaction().commit(); // end of transaction
        } finally {
            em.close();
        }
    }
}