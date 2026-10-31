package com.ecommerce.listener;

import com.ecommerce.model.Vitrine;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class JpaListener implements ServletContextListener {
    private EntityManagerFactory emf;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        emf = Persistence.createEntityManagerFactory("ecommercePU");
        sce.getServletContext().setAttribute("emf", emf);

        EntityManager em = emf.createEntityManager();
        try {
            Long n = em.createQuery("SELECT COUNT(v) FROM Vitrine v", Long.class).getSingleResult();
            if (n == 0) {
                em.getTransaction().begin();
                Vitrine v1 = new Vitrine(); v1.setTitre("Informatique"); v1.setDescription("PC, accessoires");
                Vitrine v2 = new Vitrine(); v2.setTitre("Mode"); v2.setDescription("Vêtements");
                em.persist(v1);
                em.persist(v2);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (emf != null && emf.isOpen()) emf.close();
    }
}
