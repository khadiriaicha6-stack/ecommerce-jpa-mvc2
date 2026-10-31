<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Produit</title>
<style>body { font-family: sans-serif; margin: 20px; } input, select { margin: 4px 0; }</style>
</head>
<body>
<h2>${empty produit ? 'Ajouter' : 'Modifier'} un produit</h2>
<form method="post" action="VitrineServlet">
  <input type="hidden" name="action" value="${empty produit ? 'ajouter' : 'modifier'}">
  <input type="hidden" name="id" value="${produit.id}">
  Désignation : <input name="designation" value="${produit.designation}" required><br>
  Description : <input name="description" value="${produit.description}"><br>
  Prix : <input type="number" step="0.01" name="prix" value="${produit.prix}" required><br>
  Stock : <input type="number" name="stock" value="${produit.stock}" required><br>
  Vitrine :
  <select name="vitrineId">
    <c:forEach items="${vitrines}" var="v">
      <option value="${v.id}" ${produit.vitrine.id == v.id ? 'selected' : ''}>${v.titre}</option>
    </c:forEach>
  </select><br>
  <button>Enregistrer</button>
  <a href="VitrineServlet?action=lister">Annuler</a>
</form>
</body>
</html>