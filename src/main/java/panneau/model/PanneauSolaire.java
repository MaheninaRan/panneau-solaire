package panneau.model;

import panneau.model.connection.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PanneauSolaire {
    private int id;
    private double puissance;

    public static PanneauSolaire getById(int id, Connection connection) throws SQLException, ClassNotFoundException {
        boolean isOpen = false;
        PanneauSolaire result = null;
        if (connection == null) {
            connection = new DBConnect().getConnection();
            isOpen = true;
        }

        String sql = "SELECT * FROM panneausolaire WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            result = new PanneauSolaire();
            result.setId(resultSet.getInt(1));
            result.setPuissance(resultSet.getDouble(2));
        }

        resultSet.close();
        preparedStatement.close();
        if (isOpen) connection.commit();

        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPuissance() {
        return puissance;
    }

    public void setPuissance(double puissance) {
        this.puissance = puissance;
    }
}
