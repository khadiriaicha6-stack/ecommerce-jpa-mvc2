package com.ecommerce.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Produit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String designation;
    private String description;
    private double prix;
    private int stock;
    @ManyToOne
    private Vitrine vitrine;

    public Produit() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public Vitrine getVitrine() { return vitrine; }
    public void setVitrine(Vitrine vitrine) { this.vitrine = vitrine; }
}
