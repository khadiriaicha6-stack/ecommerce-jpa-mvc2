package com.ecommerce.controller;

import com.ecommerce.dao.ProduitDao;
import com.ecommerce.dao.VitrineDao;
import com.ecommerce.model.Produit;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/VitrineServlet")
public class VitrineServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProduitDao produitDao;
    private VitrineDao vitrineDao;

    @Override
    public void init() throws ServletException {
        // injection: the EntityManagerFactory created by JpaListener
        EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        produitDao = new ProduitDao(emf);
        vitrineDao = new VitrineDao(emf);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) action = "lister";

        if ("lister".equals(action)) {
            String vid = request.getParameter("vitrineId");
            List<Produit> produits = (vid == null || vid.isEmpty())
                    ? produitDao.findAll()
                    : produitDao.findByVitrine(Long.parseLong(vid));
            request.setAttribute("produits", produits);
            request.setAttribute("vitrines", vitrineDao.findAll());
            request.getRequestDispatcher("/WEB-INF/views/vitrine.jsp").forward(request, response);

        } else if ("formulaire".equals(action)) {
            String id = request.getParameter("id");
            if (id != null && !id.isEmpty()) {
                request.setAttribute("produit", produitDao.findById(Long.parseLong(id)));
            }
            request.setAttribute("vitrines", vitrineDao.findAll());
            request.getRequestDispatcher("/WEB-INF/views/produitForm.jsp").forward(request, response);

        } else if ("ajouter".equals(action)) {
            Produit p = fill(request, new Produit());
            produitDao.save(p);
            response.sendRedirect("VitrineServlet?action=lister");

        } else if ("modifier".equals(action)) {
            Produit p = produitDao.findById(Long.parseLong(request.getParameter("id")));
            produitDao.update(fill(request, p));
            response.sendRedirect("VitrineServlet?action=lister");

        } else if ("supprimer".equals(action)) {
            produitDao.delete(Long.parseLong(request.getParameter("id")));
            response.sendRedirect("VitrineServlet?action=lister");

        } else {
            response.sendRedirect("VitrineServlet?action=lister");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private Produit fill(HttpServletRequest r, Produit p) {
        p.setDesignation(r.getParameter("designation"));
        p.setDescription(r.getParameter("description"));
        p.setPrix(Double.parseDouble(r.getParameter("prix")));
        p.setStock(Integer.parseInt(r.getParameter("stock")));
        p.setVitrine(vitrineDao.findById(Long.parseLong(r.getParameter("vitrineId"))));
        return p;
    }
}