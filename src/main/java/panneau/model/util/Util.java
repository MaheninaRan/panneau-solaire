package panneau.model.util;

import panneau.model.connection.DBConnect;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Util {

    public static boolean difference(Time time1, Time time2, int tolerance) {
        long differenceEnMillis = time1.getTime() - time2.getTime();
        long differenceEnMinutes = TimeUnit.MILLISECONDS.toMinutes(differenceEnMillis);

        return Math.abs(differenceEnMinutes) <= tolerance;
    }
    public static double generateNumberWithZeros(int nbr_zero) {
        double result;
        double divisor = 1.0;

        for (int i = 0; i < nbr_zero; i++) {
            divisor *= 10.0;
        }

        result = 1.0 / divisor;

        return result;
    }
    public static double average(double[] data) {
        int count = data.length;
        if (data.length == 0) return 0;

        double somme = 0.0;
        for (double d : data) {
            if (d == 0.0) {
                count--;
            }
            somme += d;
        }

        return somme/count;
    }
    public static List<Date> getAllDate(Connection connection) throws SQLException, ClassNotFoundException {
        boolean isOpen = false;
        List<Date> dates = new ArrayList<>();
        if (connection == null) {
            connection = new DBConnect().getConnection();
            isOpen = true;
        }

        String sql = "SELECT DISTINCT(date) FROM v_presencesalle ORDER BY date ASC";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            dates.add(resultSet.getDate(1));
        }

        resultSet.close();
        preparedStatement.close();
        if (isOpen) connection.close();

        return dates;
    }
    public static int getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        dayOfWeek = (dayOfWeek == Calendar.SUNDAY) ? 0 : (dayOfWeek - 1);

        return dayOfWeek;
    }
    public static Time addTime(Time time, double hoursToAdd) {

        long second = (int) (hoursToAdd * 60 * 60);

        LocalTime localTime = time.toLocalTime();
        LocalTime newLocalTime = localTime.plusSeconds(second);
        return Time.valueOf(newLocalTime);
    }
    public static java.sql.Date parseToDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date d_temp = sdf.parse(date);

        return new java.sql.Date(d_temp.getTime());
    }
}
