<%@ page import="br.jus.tredf.evoting.test.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

Esta é a página index.jsp
<%
//   TesteHE t = new TesteHE();
//   t.testar();
//   TesteRSA rsa = new TesteRSA();
//   rsa.testar();
  TestePaillier paillier = new TestePaillier();
  paillier.testar();
%>

</body>
</html>