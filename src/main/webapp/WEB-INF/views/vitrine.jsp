<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Vitrine</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>
  body { font-family: sans-serif; margin: 20px; }
  table { border-collapse: collapse; width: 100%; margin-top: 10px; }
  td, th { border: 1px solid #ccc; padding: 6px; }
  nav a { margin-right: 12px; }
</style>
</head>
<body>
<nav>
  <a href="VitrineServlet?action=lister">Vitrine</a>
  <a href="PanierServlet?action=voir">Mon panier</a>
  <c:choose>
    <c:when test="${not empty sessionScope.user}">
      Bonjour ${sessionScope.user.prenom}
      <a href="InternauteServlet?action=deconnecter">Déconnexion</a>
    </c:when>
    <c:otherwise>
      <a href="InternauteServlet?action=afficherConnexion">Connexion</a>
      <a href="InternauteServlet?action=afficherInscription">Inscription</a>
    </c:otherwise>
  </c:choose>
</nav>

<h2>Vitrine</h2>

<form method="get" action="VitrineServlet">
  <input type="hidden" name="action" value="lister">
  <select name="vitrineId" onchange="this.form.submit()">
    <option value="">-- Toutes --</option>
    <c:forEach items="${vitrines}" var="v">
      <option value="${v.id}" ${param.vitrineId == v.id ? 'selected' : ''}>${v.titre}</option>
    </c:forEach>
  </select>
  <input type="text" id="search" placeholder="Rechercher...">
  <a href="VitrineServlet?action=formulaire">+ Nouveau produit</a>
</form>

<table id="tbl">
  <tr><th>Désignation</th><th>Vitrine</th><th>Prix</th><th>Stock</th><th>Actions</th></tr>
  <c:forEach items="${produits}" var="p">
    <tr>
      <td>${p.designation}</td>
      <td>${p.vitrine.titre}</td>
      <td><fmt:formatNumber value="${p.prix}" type="number" minFractionDigits="2"/> DH</td>
      <td>${p.stock}</td>
      <td>
        <form method="post" action="PanierServlet" style="display:inline">
          <input type="hidden" name="action" value="ajouter">
          <input type="hidden" name="produitId" value="${p.id}">
          <input type="number" name="quantite" value="1" min="1" max="${p.stock}" style="width:50px">
          <button>Ajouter au panier</button>
        </form>
        <a href="VitrineServlet?action=formulaire&id=${p.id}">Modifier</a>
        <a class="del" href="VitrineServlet?action=supprimer&id=${p.id}">Supprimer</a>
      </td>
    </tr>
  </c:forEach>
</table>

<script>
$(function () {
  // live search (jQuery)
  $('#search').on('keyup', function () {
    var q = $(this).val().toLowerCase();
    $('#tbl tr:gt(0)').each(function () {
      $(this).toggle($(this).text().toLowerCase().indexOf(q) > -1);
    });
  });
  // delete confirmation (jQuery)
  $('.del').on('click', function () {
    return confirm('Supprimer ce produit ?');
  });
});
</script>
</body>
</html>