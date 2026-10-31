<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mon panier</title>
<style>
  body { font-family: sans-serif; margin: 20px; }
  table { border-collapse: collapse; }
  td, th { border: 1px solid #ccc; padding: 6px; }
</style>
</head>
<body>
<h2>Mon panier</h2>
<table>
  <tr><th>Produit</th><th>Prix</th><th>Qté</th><th>Sous-total</th><th></th></tr>
  <c:forEach items="${panier.lignes}" var="l">
    <tr>
      <td>${l.produit.designation}</td>
      <td><fmt:formatNumber value="${l.produit.prix}" minFractionDigits="2"/></td>
      <td>${l.quantite}</td>
      <td><fmt:formatNumber value="${l.sousTotal}" minFractionDigits="2"/></td>
      <td><a href="PanierServlet?action=retirer&ligneId=${l.id}">Retirer</a></td>
    </tr>
  </c:forEach>
  <tr>
    <td colspan="3"><b>Total</b></td>
    <td colspan="2"><b><fmt:formatNumber value="${panier.total}" minFractionDigits="2"/> DH</b></td>
  </tr>
</table>
<c:if test="${empty panier.lignes}"><p>Votre panier est vide.</p></c:if>
<p>
  <a href="PanierServlet?action=vider">Vider le panier</a> |
  <a href="VitrineServlet?action=lister">Continuer mes achats</a>
</p>
</body>
</html>