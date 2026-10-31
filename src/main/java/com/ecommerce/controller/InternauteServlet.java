package com.ecommerce.controller;

import com.ecommerce.dao.InternauteDao;
import com.ecommerce.model.Internaute;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/InternauteServlet")
public class InternauteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private InternauteDao dao;

    @Override
    public void init() throws ServletException {
        EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        dao = new InternauteDao(emf);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) action = "afficherConnexion";

        if ("afficherConnexion".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);

        } else if ("afficherInscription".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/views/inscription.jsp").forward(request, response);

        } else if ("inscrire".equals(action)) {
            if (dao.findByEmail(request.getParameter("email")) != null) {
                request.setAttribute("erreur", "Email déjà utilisé");
                request.getRequestDispatcher("/WEB-INF/views/inscription.jsp").forward(request, response);
                return;
            }
            Internaute i = new Internaute();
            i.setNom(request.getParameter("nom"));
            i.setPrenom(request.getParameter("prenom"));
            i.setEmail(request.getParameter("email"));
            i.setMotDePasse(request.getParameter("motDePasse"));
            dao.save(i);
            response.sendRedirect("InternauteServlet?action=afficherConnexion");

        } else if ("connecter".equals(action)) {
            Internaute i = dao.findByEmail(request.getParameter("email"));
            if (i != null && i.getMotDePasse().equals(request.getParameter("motDePasse"))) {
                request.getSession().setAttribute("user", i);
                response.sendRedirect("VitrineServlet?action=lister");
            } else {
                request.setAttribute("erreur", "Email ou mot de passe incorrect");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            }

        } else if ("deconnecter".equals(action)) {
            request.getSession().invalidate();
            response.sendRedirect("InternauteServlet?action=afficherConnexion");

        } else {
            response.sendRedirect("InternauteServlet?action=afficherConnexion");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}