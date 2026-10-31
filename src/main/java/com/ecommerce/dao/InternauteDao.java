package com.ecommerce.dao;

import com.ecommerce.model.Internaute;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class InternauteDao extends GenericDao<Internaute> {
    public InternauteDao(EntityManagerFactory emf) { super(emf, Internaute.class); }

    public Internaute findByEmail(String email) {
        EntityManager em = emf.createEntityManager();
        try {
            List<Internaute> l = em.createQuery("SELECT i FROM Internaute i WHERE i.email = :e", Internaute.class)
                    .setParameter("e", email).getResultList();
            return l.isEmpty() ? null : l.get(0);
        } finally {
            em.close();
        }
    }
}
