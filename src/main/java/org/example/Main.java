package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.entities.Employee;
import org.example.entities.Product;
import org.example.persistence.CustomPersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(), new HashMap<>());
        EntityManager em = emf.createEntityManager(); // represents the context

        try {
            em.getTransaction().begin();

            Employee e1 = em.find(Employee.class, 3);
            e1.setName("Peter Brown");

            System.out.println(e1);

            em.getTransaction().commit(); // end of transaction
        } finally {
            em.close();
        }
    }
}