package com.ecommerce.controller;

import com.ecommerce.dao.PanierDao;
import com.ecommerce.model.Internaute;
import com.ecommerce.model.Panier;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/PanierServlet")
public class PanierServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PanierDao panierDao;

    @Override
    public void init() throws ServletException {
        EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        panierDao = new PanierDao(emf);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Internaute user = (Internaute) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("InternauteServlet?action=afficherConnexion");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) action = "voir";
        Panier panier = panierDao.findOrCreate(user);

        if ("ajouter".equals(action)) {
            int qte = Integer.parseInt(request.getParameter("quantite"));
            panierDao.addProduit(panier.getId(), Long.parseLong(request.getParameter("produitId")), qte);
            response.sendRedirect("PanierServlet?action=voir");

        } else if ("retirer".equals(action)) {
            panierDao.removeLigne(panier.getId(), Long.parseLong(request.getParameter("ligneId")));
            response.sendRedirect("PanierServlet?action=voir");

        } else if ("vider".equals(action)) {
            panierDao.vider(panier.getId());
            response.sendRedirect("PanierServlet?action=voir");

        } else { // voir
            request.setAttribute("panier", panierDao.findById(panier.getId()));
            request.getRequestDispatcher("/WEB-INF/views/panier.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
