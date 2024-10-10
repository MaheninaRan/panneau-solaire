<%@ page import="panneau.model.Resultat" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="panneau.model.DetailConsommation" %><%--
  Created by IntelliJ IDEA.
  User: Mahenina
  Date: 15/12/2023
  Time: 09:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Resultat> lr = (List<Resultat>) request.getAttribute("resultat");
    if (lr == null) lr=new ArrayList<>();
    String date = (String) request.getAttribute("date");
%>
<html>
<head>
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


    <title>Title</title>
</head>
<style>
    .container{
        width: 95%;

    }
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
    .daty{
        margin: auto;
        width: 200px;
    }
    .coupure{
        margin: auto;
        width: 270px;
    }
</style>


<body>
<div class="row bar">
    <div class="col-lg-3 prev"><p>Prevision coupure</p></div>
    <div class="col-lg-2"><h6 class="menu"> <a href="index.jsp"><div>Secteur</div> </a> </h6></div>
    <div class="col-lg-2"><h6 class="menu"> <div>Prevision</div> </h6></div>
    <div class="col-lg-3"><h6 class="menu"><div>PresenceJournee</div></h6></div>
    <div class="col-lg-2"><h6 class="menu"><div>Meteo</div></h6></div>
</div> <br> <br>
<% for (Resultat r : lr) { %>
<div class="row daty"> <% out.print(date); %> </div>
<div class="row" style="margin-top: 50px;">
    <div class="col-md-12">
        <div class="table-wrap">
            <table class="table table-bordered">
                <thead class="thead-primary">
                <tr>
                    <th>Heure debut</th>
                    <th>Heure Fin</th>
                    <th>Puissance Panneau</th>
                    <th>Capacite Batterie</th>
                    <th>Nombre Eleve</th>
                    <th>Consommation totale</th>
                </tr>
                </thead>
                <tbody>
                <% for (DetailConsommation d : r.getDetailConsommations()) { %>
                <tr>
                    <td><%=d.getDebut()%></td>
                    <td><%=d.getFin()%></td>
                    <td class="nbr" style="text-align: center"><%=d.getPuissance_panneau()%></td>
                    <td class="nbr" style="text-align: center"><%=d.getBatterie()%></td>
                    <td class="nbr" style="text-align: center"><%=d.getNbr_eleve()%></td>
                    <td class="nbr" style="text-align: center"><%=d.getConsommation()%></td>
                </tr>

                <% if(d.getBatterie() == 0)  {%>
                <div class="row coupure">
                    <p><b>Coupure : </b> <%=r.getHeure_coupure()%><br>
                    <p><b>Consommation : </b> <%=r.getConso()%><br>
                </div>
<%--                <tr>--%>
<%--                    <td><b>Coupure</b></td>--%>
<%--                    <td><b><%=r.getHeure_coupure()%></b></td>--%>
<%--                </tr>--%>
<%--                <tr>--%>
<%--                    <td><b>conso/eleve</b></td>--%>
<%--                    <td><b class="nbr"><%=r.getConso()%></b></td>--%>
<%--                </tr>--%>
                <% break; } } %>
                </tbody>
            </table>
        </div>
    </div>
</div>
<hr>
<% } %>
</div>


<script>
    const elements = document.querySelectorAll(".nbr");
    elements.forEach(function (element) {
        let number = parseFloat(element.textContent.trim());
        if (!isNaN(number)) {
            element.textContent = number.toLocaleString();
        }
    });
</script>

</body>
</html>
