package com.ecommerce.dao;

import com.ecommerce.model.Produit;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class ProduitDao extends GenericDao<Produit> {
    public ProduitDao(EntityManagerFactory emf) { super(emf, Produit.class); }

    public List<Produit> findByVitrine(Long vitrineId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM Produit p WHERE p.vitrine.id = :id", Produit.class)
                    .setParameter("id", vitrineId).getResultList();
        } finally {
            em.close();
        }
    }
}
