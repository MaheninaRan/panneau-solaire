package panneau.model;

import panneau.model.connection.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class Salle {
    private int id;
    private String nom;
    private SourceEnergie sourceEnergie;

    public static int countEleve(int idsourceenergie, int partie, Date date, Connection connection) throws ClassNotFoundException, SQLException {
        boolean isOpen = false;
        if (connection == null) {
            isOpen = true;
            connection = new DBConnect().getConnection();
        }

        String sql = "SELECT SUM(nbr_personne) FROM presencesalle WHERE date = ? AND partie = ? AND idsalle IN (SELECT id FROM salle WHERE idsourceenergie = ?) ";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDate(1, date);
        statement.setInt(2, partie);
        statement.setInt(3, idsourceenergie);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }

        resultSet.close();
        statement.close();
        if (isOpen) connection.close();

        return 0;
    }

    public static int countAvgEleve(int idsourceenergie, int partie, int jour,Connection connection) throws ClassNotFoundException, SQLException {
        boolean isOpen = false;
        if (connection == null) {
            isOpen = true;
            connection = new DBConnect().getConnection();
        }

        String sql = "SELECT SUM(nbr_present) FROM v_avg_presence_salle WHERE partie=? AND jour=? AND idsalle IN (SELECT id FROM salle WHERE idsourceenergie=?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, partie);
        statement.setInt(2, jour);
        statement.setInt(3, idsourceenergie);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }

        resultSet.close();
        statement.close();
        if (isOpen) connection.close();

        return 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public SourceEnergie getSourceEnergie() {
        return sourceEnergie;
    }

    public void setSourceEnergie(SourceEnergie sourceEnergie) {
        this.sourceEnergie = sourceEnergie;
    }
}
