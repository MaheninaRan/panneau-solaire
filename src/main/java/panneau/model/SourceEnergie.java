package panneau.model;

import panneau.model.connection.DBConnect;
import panneau.model.enumeration.Jour;
import panneau.model.util.Util;

import java.sql.SQLException;
import java.sql.Date;
import java.sql.Connection;
import java.sql.Time;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SourceEnergie {
    private int id;
    private Batterie batterie;
    private PanneauSolaire panneauSolaire;

    public double calculAvgConsommation(Connection connection) throws SQLException, ClassNotFoundException {
        List<Date> dates = Util.getAllDate(connection);
        double[] consommation_array = new double[dates.size()];
        for (int i=0; i < dates.size(); i++) {
            consommation_array[i] = this.calculConsommation(dates.get(i), connection);
        }
        return Util.average(consommation_array);
    }

    public Time getHeureCoupure(double consommation, int nbr_matin, int nbr_apres_midi, List<PuissanceSolaire> puissanceSolaires) throws SQLException, ClassNotFoundException {
        double nbr_eleve = nbr_matin;
        double panneau_reel, consommation_reel, batterie = this.getBatterie().getCapacite()/2;
        double diff_puissance, t;
        for (PuissanceSolaire ps : puissanceSolaires) {
            if (ps.getDebut().after(Time.valueOf("11:59:59"))) nbr_eleve = nbr_apres_midi;

            consommation_reel = consommation * nbr_eleve;
            panneau_reel = this.getPanneauSolaire().getPuissance() * (ps.getNiveau() * 0.1);

            if (panneau_reel < consommation_reel) {
                diff_puissance = consommation_reel - panneau_reel;

                t = (batterie / diff_puissance);
                if (t >= 1) batterie = batterie - diff_puissance;
                else {
                    return Util.addTime(ps.getDebut(), t);
                }
            } else {
                diff_puissance = panneau_reel - consommation_reel;
                batterie = Math.min(this.getBatterie().getCapacite()/2, diff_puissance + batterie);
            }
        }

        return null;
    }

    public Time getHeureCoupure(double consommation, Date date, Connection connection) throws SQLException, ClassNotFoundException {
        int nbr_matin = Salle.countEleve(this.getId(), Jour.MATIN.getCode(), date, connection);
        int nbr_apres_midi = Salle.countEleve(this.getId(), Jour.APRES_MIDI.getCode(), date, connection);
        List<PuissanceSolaire> puissanceSolaires = PuissanceSolaire.findAllByDate(date, connection);
        return this.getHeureCoupure(consommation, nbr_matin, nbr_apres_midi, puissanceSolaires);
    }

    public List<DetailConsommation> voirDetail(double consommation,  int nbr_matin, int nbr_apres_midi, List<PuissanceSolaire> puissanceSolaires) {
        List<DetailConsommation> detailConsommations = new ArrayList<>();
        DetailConsommation temp = null;
        int nbr_eleve = nbr_matin;
        double panneau_reel, consommation_reel, batterie = this.getBatterie().getCapacite()/2;
        double diff_puissance, t;
        for (PuissanceSolaire ps : puissanceSolaires) {
            if (ps.getDebut().after(Time.valueOf("11:59:59"))) nbr_eleve = nbr_apres_midi;
            consommation_reel = consommation * nbr_eleve;
            panneau_reel = this.getPanneauSolaire().getPuissance() * (ps.getNiveau() * 0.1);

            if (panneau_reel < consommation_reel) {
                diff_puissance = consommation_reel - panneau_reel;

                t = (batterie / diff_puissance);
                if (t >= 1)  {
                    batterie = batterie - diff_puissance;
                }
                else {
                    batterie = 0;
                }
            } else {
                diff_puissance = panneau_reel - consommation_reel;
                batterie = Math.min(this.getBatterie().getCapacite()/2, diff_puissance + batterie);
            }
            temp = new DetailConsommation(ps.getDebut(), ps.getFin(), batterie, panneau_reel, ps.getNiveau(), consommation_reel, nbr_eleve);
            detailConsommations.add(temp);
        }

        return detailConsommations;
    }

    public Time getCoupureByDate(Date date, Connection connection) throws SQLException, ClassNotFoundException {
        boolean isOpen = false;
        if (connection == null) {
            connection = new DBConnect().getConnection();
            isOpen = true;
        }

        String sql = "SELECT heure FROM coupure WHERE date = ? AND idsourceenegie = ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setDate(1, date);
        preparedStatement.setInt(2, this.getId());

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getTime(1);
        }

        resultSet.close();
        preparedStatement.close();
        if (isOpen) connection.commit();

        return null;
    }

    private double calculConsommation(Date date, Connection connection) throws SQLException, ClassNotFoundException {
        double min = 0, discretion = 1, max = min + discretion;
        Time coupure_vraie = this.getCoupureByDate(date, connection);
        Time coupure_temp = this.getHeureCoupure(max, date, connection);
        double last_max;
        int nbr_zero = 1;
        if (coupure_vraie != null ) {
            while (true) {
                last_max = max;
                if (coupure_temp == null) {
                    min = max;
                }else {
                    if (coupure_temp.getTime() > coupure_vraie.getTime()) {
                        min = max;
                    } else {
                        discretion = Util.generateNumberWithZeros(nbr_zero);
                        nbr_zero++;
                    }
                    if (coupure_temp.getTime() == coupure_vraie.getTime()) {
                        return max;
                    }

                }
                max = min + discretion;
                coupure_temp = this.getHeureCoupure(max, date, connection);
                if (last_max == max) {
                    return max;
                }
            }
        }
        return 0.0;
    }

    public static List<SourceEnergie> findAll(Connection connection) throws SQLException, ClassNotFoundException {
        boolean isOpen = false;
        List<SourceEnergie> sourceEnergies = new ArrayList<>();
        if (connection == null) {
            connection = new DBConnect().getConnection();
            isOpen = true;
        }

        String sql = "SELECT * FROM sourceenergie";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        SourceEnergie temp;
        while (resultSet.next()) {
            temp = new SourceEnergie();
            temp.setId(resultSet.getInt(1));
            temp.setPanneauSolaire(PanneauSolaire.getById(resultSet.getInt(2), connection));
            temp.setBatterie(Batterie.getById(resultSet.getInt(3), connection));

            sourceEnergies.add(temp);
        }

        resultSet.close();
        statement.close();
        if (isOpen) connection.close();

        return sourceEnergies;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Batterie getBatterie() {
        return batterie;
    }

    public void setBatterie(Batterie batterie) {
        this.batterie = batterie;
    }

    public PanneauSolaire getPanneauSolaire() {
        return panneauSolaire;
    }

    public void setPanneauSolaire(PanneauSolaire panneauSolaire) {
        this.panneauSolaire = panneauSolaire;
    }
}
