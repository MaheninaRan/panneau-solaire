package panneau;

import panneau.model.DetailConsommation;
import panneau.model.PuissanceSolaire;
import panneau.model.Salle;
import panneau.model.SourceEnergie;
import panneau.model.connection.DBConnect;
import panneau.model.enumeration.Jour;
import panneau.model.util.Util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection connection = new DBConnect().getConnection();

        Date date = Date.valueOf("2023-12-13");

        List<SourceEnergie> ls = SourceEnergie.findAll(connection);
        List<PuissanceSolaire> ps = PuissanceSolaire.findAllByDate(date, connection);
        double conso;

        for (SourceEnergie s : ls) {
            int nbr_matin = Salle.countAvgEleve(
                    s.getId(),
                    Jour.MATIN.getCode(),
                    Util.getDayOfWeek(date),
                    connection);
            int nbr_apre_midi = Salle.countAvgEleve(
                    s.getId(),
                    Jour.APRES_MIDI.getCode(),
                    Util.getDayOfWeek(date),
                    connection);

            conso = s.calculAvgConsommation(connection);
            List<DetailConsommation> detailConsommations = s.voirDetail(conso, nbr_matin, nbr_apre_midi, ps);

            System.out.println("debit    | fin      | panneau | batt | nbr_ | consommation ");
            for (DetailConsommation d : detailConsommations) {
                System.out.println(d.getDebut() +" | "+ d.getFin() +" | " +(int) d.getPuissance_panneau() +" | "+ (int)d.getBatterie()+" | "+d.getNbr_eleve()+" | "+d.getConsommation());
                if (d.getBatterie() == 0) {
                    System.out.println("Coupure "+s.getHeureCoupure(conso, nbr_matin, nbr_apre_midi, ps));
                    System.out.println("Consommation/eleve :"+ conso+"W");
                    break;
                }
            }
        }


        connection.close();
    }
}
