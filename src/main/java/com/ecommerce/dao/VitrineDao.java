package com.ecommerce.dao;

import com.ecommerce.model.Vitrine;
import javax.persistence.EntityManagerFactory;

public class VitrineDao extends GenericDao<Vitrine> {
    public VitrineDao(EntityManagerFactory emf) { super(emf, Vitrine.class); }
}