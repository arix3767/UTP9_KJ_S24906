package zad1;

import javax.swing.*;
import java.sql.*;
import java.util.List;
import java.util.Objects;

public class Database {
    private static final String INSERT_SQL_TEMPLATE = "INSERT INTO Oferta VALUES(?,?,?,?,?,?,?)";
    private static final String DROP_STATEMENT_SQL = "drop table if exists Oferta";
    private static final String URL = "jdbc:mysql://root@localhost:3306/project2";
    private static final String CREATE_TABLE_SQL =
            "CREATE TABLE Oferta(" +
                    "id integer PRIMARY KEY," +
                    "kraj varchar(40)," +
                    "wyjazd Date," +
                    "powrot Date," +
                    "miejsce varchar(20)," +
                    "cena varchar(20)," +
                    "waluta varchar(10))";

    int id = 1;
    Connection polaczenie;
    TravelData listaWycieczek;


    public Database(String URL, TravelData listaWycieczek) {
        this.listaWycieczek = listaWycieczek;
        try {
            this.polaczenie = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void create() {
        try (Connection conn = DriverManager.getConnection(URL)) {
            try (Statement statement = conn.createStatement()) {
                statement.executeUpdate(DROP_STATEMENT_SQL);
                statement.executeUpdate(CREATE_TABLE_SQL);
            }
            for (List<String> info : listaWycieczek.getAllTrips()) {
                try (PreparedStatement preparedStatement = conn.prepareStatement(INSERT_SQL_TEMPLATE)) {
                    preparedStatement.setInt(1, id);
                    preparedStatement.setString(2, info.get(1));
                    preparedStatement.setString(3, info.get(2));
                    preparedStatement.setString(4, info.get(3));
                    preparedStatement.setString(5, info.get(4));
                    preparedStatement.setString(6, info.get(5));
                    preparedStatement.setString(7, info.get(6));
                    preparedStatement.execute();
                    id++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showGui() {
        SwingUtilities.invokeLater(() ->
                new Table(listaWycieczek)
        );

    }

}

