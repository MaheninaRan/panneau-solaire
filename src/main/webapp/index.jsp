
<%@ page import="java.util.List" %>
<%@ page import="panneau.model.Resultat" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="panneau.model.DetailConsommation" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
	List<Resultat> lr = (List<Resultat>) request.getAttribute("resultat");
	if (lr == null) lr=new ArrayList<>();
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta charset="utf-8">
	<title>Gestion panneau</title>
	<meta content="width=device-width, initial-scale=1.0" name="viewport">
	<meta content="" name="keywords">
	<meta content="" name="description">

	<!-- Favicon -->
	<link href="img/favicon.ico" rel="icon">

	<!-- Google Web Fonts -->
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600&family=Inter:wght@700;800&display=swap" rel="stylesheet">

	<!-- Icon Font Stylesheet -->
	<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

	<!-- Libraries Stylesheet -->
	<link href="assets/lib/animate/animate.min.css" rel="stylesheet">
	<link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
	<link rel="stylesheet" href="css/all.min.css">
	<link rel="stylesheet" href="css/all.css">
	<!-- Customized Bootstrap Stylesheet -->
	<link href="assets/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" href="assets/css/font-awesome.min.css">
	<!-- Template Stylesheet -->
	<link href="assets/css/style.css" rel="stylesheet">
</head>
	<body>

	<style>
		.container{
			width: 95%;
			margin-top: 10%;
		}
		.divInput{
			text-align: center;
			background-size: cover;
			background-repeat: no-repeat;
			background-position-x: right;
			background-position-y: bottom;
		}
		[class*="col"]{
			margin-bottom: 20px ;
		}
		img{width : 100%;}
		.menu{
			color: red;
			font-family: 'Times New Roman', Times, serif;
			font-size: 20px;
		}
		.bar{
			border-bottom: solid 1px red;
			background-color: rgb(255, 208, 208);
			height: 70px;
		}
		.menu div{
			width: 100px;
			margin-top: 20px;
			color: red;
			font-weight: bold;
		}
		.prev{
			color:red; background-color:white;
			height: 70px;
			border-bottom: solid 1px red;
		}
		.prev p{
			font-size: 40px;
			font-family: 'Times New Roman', Times, serif;
		}
	</style>
	<div class="row bar">
		<div class="col-lg-3 prev">  <p>Prevision coupure</p></div>
		<div class="col-lg-2"><h6 class="menu"> <a href="secteur.jsp"><div>Prevision</div> </a> </h6></div>
		<div class="col-lg-2"><h6 class="menu"> <a href="index.jsp"><div>Secteur</div> </a> </h6></div>
		<div class="col-lg-3"><h6 class="menu"><a href="presence.jsp"><div>PresenceJournee</div> </a></h6></div>
		<div class="col-lg-2"><h6 class="menu"><a href="meteo.jsp"><div>Meteo</div> </a></h6></div>
	</div> <br> <br>
	<div class="ftco-section">
		<div class="row">
		<div class="col-lg-3 col-sm-1 col-md-3"></div>
		<form class ="form-horizontal col-xs-12 col-lg-6 col-sm-10 col-md-7 divInput" action="Prevision" method="POST">
			<div class ="form-group ">
				<legend >Date prevision </legend>
			</div>
			<div class ="row">
				<div class ="form-group">
					<div class ="col-lg-1 col-xs-1"></div>
					<label for="text" class ="col-lg-2 col-xs-2"> Date </label >
					<div class ="col-lg-6 col-xs-6"><input type ="date" class ="form-control" name="date" id="categorie"></div>
				</div>
			</div>
			<button class ="submit"> Envoyer </button >
		</form >
		<div class="col-lg-3 col-sm-1 col-md-2"></div>
		</div>


	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/popper.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/main.js"></script>
	</body>
</html>
