package panneau.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import panneau.model.DetailConsommation;
import panneau.model.Salle;
import panneau.model.PuissanceSolaire;
import panneau.model.Resultat;
import panneau.model.SourceEnergie;
import panneau.model.connection.DBConnect;
import panneau.model.enumeration.Jour;
import panneau.model.util.Util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Prevision", value = "/Prevision")
public class Prevision extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String str_date = request.getParameter("date");
        List<Resultat> resultats = new ArrayList<>();
        try {
            Connection connection = new DBConnect().getConnection();

            Date date = Util.parseToDate(str_date);
            List<SourceEnergie> ls = SourceEnergie.findAll(connection);
            List<PuissanceSolaire> ps = PuissanceSolaire.findAllByDate(date, connection);
            double conso;
            List<DetailConsommation> detailConsommations;
            Time coupure; 

            for (SourceEnergie s : ls) {
                int nbr_matin = Salle.countAvgEleve(s.getId(), Jour.MATIN.getCode(), Util.getDayOfWeek(date), connection);
                int nbr_apre_midi = Salle.countAvgEleve(s.getId(), Jour.APRES_MIDI.getCode(), Util.getDayOfWeek(date), connection);
                conso = s.calculAvgConsommation(connection);
                detailConsommations = s.voirDetail(conso, nbr_matin, nbr_apre_midi, ps);
                coupure = s.getHeureCoupure(conso, nbr_matin, nbr_apre_midi, ps);
                resultats.add(new Resultat(conso, s, detailConsommations, coupure));
            }
            connection.close();
        } catch (ClassNotFoundException | SQLException | ParseException e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("resultat", resultats);
        request.setAttribute("date", str_date);
        request.getRequestDispatcher("prevision.jsp").forward(request, response);

    }
}
