<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- Font Poppins -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;700&display=swap" rel="stylesheet">

<!-- Font Awesome per icone social -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

<!-- CSS Header -->
<style><%@include file="/css/header.css" %></style>

<!-- Header -->
<div class="header">
  <a href="<%= request.getContextPath() %>/jsp/HOME/Home.jsp" class="logo-link">
    <img id="logo_header" alt="LOGO BNB" src="<%= request.getContextPath() %>/images/IMMAGINI-VARIE/LOGO_BNB.png">
    <h3>SUNSET B&B</h3>
  </a>
</div>