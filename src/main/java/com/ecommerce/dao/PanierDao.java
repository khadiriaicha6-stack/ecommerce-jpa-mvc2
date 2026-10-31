package com.ecommerce.dao;

import com.ecommerce.model.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class PanierDao extends GenericDao<Panier> {
    public PanierDao(EntityManagerFactory emf) { super(emf, Panier.class); }

    public Panier findOrCreate(Internaute user) {
        EntityManager em = emf.createEntityManager();
        try {
            List<Panier> l = em.createQuery("SELECT p FROM Panier p WHERE p.internaute.id = :id", Panier.class)
                    .setParameter("id", user.getId()).getResultList();
            if (!l.isEmpty()) return l.get(0);
            em.getTransaction().begin();
            Panier p = new Panier();
            p.setInternaute(em.find(Internaute.class, user.getId()));
            em.persist(p);
            em.getTransaction().commit();
            return p;
        } finally {
            em.close();
        }
    }

    public void addProduit(Long panierId, Long produitId, int qte) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Panier p = em.find(Panier.class, panierId);
            Produit prod = em.find(Produit.class, produitId);
            LignePanier ligne = null;
            for (LignePanier l : p.getLignes()) {
                if (l.getProduit().getId().equals(produitId)) ligne = l;
            }
            if (ligne == null) {
                ligne = new LignePanier();
                ligne.setPanier(p);
                ligne.setProduit(prod);
                ligne.setQuantite(qte);
                p.getLignes().add(ligne);
            } else {
                ligne.setQuantite(ligne.getQuantite() + qte);
            }
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public void removeLigne(Long panierId, Long ligneId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Panier p = em.find(Panier.class, panierId);
            p.getLignes().removeIf(l -> l.getId().equals(ligneId));
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public void vider(Long panierId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.find(Panier.class, panierId).getLignes().clear();
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}
