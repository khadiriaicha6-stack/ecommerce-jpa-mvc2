package com.ecommerce.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class LignePanier implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantite;
    @ManyToOne
    private Panier panier;
    @ManyToOne
    private Produit produit;

    public LignePanier() {}

    public double getSousTotal() { return produit.getPrix() * quantite; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
    public Panier getPanier() { return panier; }
    public void setPanier(Panier panier) { this.panier = panier; }
    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }
}