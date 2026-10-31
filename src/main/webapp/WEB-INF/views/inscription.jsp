<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Inscription</title>
<style>
  body { font-family: sans-serif; margin: 20px; }
  input { margin: 4px 0; }
</style>
</head>
<body>
<h2>Inscription</h2>
<c:if test="${not empty erreur}"><p style="color:red">${erreur}</p></c:if>
<form method="post" action="InternauteServlet">
  <input type="hidden" name="action" value="inscrire">
  Nom : <input name="nom" required><br>
  Prénom : <input name="prenom" required><br>
  Email : <input type="email" name="email" required><br>
  Mot de passe : <input type="password" name="motDePasse" required><br>
  <button type="submit">S&#39;inscrire</button>
  <a href="InternauteServlet?action=afficherConnexion">Déjà un compte ?</a>
</form>
</body>
</html>