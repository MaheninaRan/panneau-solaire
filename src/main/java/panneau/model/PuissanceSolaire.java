package panneau.model;

import panneau.model.connection.DBConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PuissanceSolaire {
    private int id;
    private Date date;
    private Time debut;
    private Time fin;
    private double niveau;

    public static List<PuissanceSolaire> findAllByDate(Date date, Connection connection) throws SQLException, ClassNotFoundException {
        boolean isOpen = false;
        List<PuissanceSolaire> puissanceSolaires = new ArrayList<>();
        if (connection == null) {
            connection = new DBConnect().getConnection();
            isOpen = true;
        }

        String sql = "SELECT * FROM puissancesolaire WHERE date = ? ORDER BY debut ASC";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDate(1, date);
        ResultSet resultSet = statement.executeQuery();
        PuissanceSolaire temp;
        while (resultSet.next()) {
            temp = new PuissanceSolaire();
            temp.setId(resultSet.getInt(1));
            temp.setDate(resultSet.getDate(2));
            temp.setDebut(resultSet.getTime(3));
            temp.setFin(resultSet.getTime(4));
            temp.setNiveau(resultSet.getDouble(5));

            puissanceSolaires.add(temp);
        }

        resultSet.close();
        statement.close();
        if (isOpen) connection.close();

        return puissanceSolaires;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getDebut() {
        return debut;
    }

    public void setDebut(Time debut) {
        this.debut = debut;
    }

    public Time getFin() {
        return fin;
    }

    public void setFin(Time fin) {
        this.fin = fin;
    }

    public double getNiveau() {
        return niveau;
    }

    public void setNiveau(double niveau) {
        this.niveau = niveau;
    }
}
