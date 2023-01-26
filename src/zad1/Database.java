package zad1;

import javax.swing.*;
import java.sql.*;
import java.util.Objects;

public class Database {
    TravelData listaWycieczek;
    private String URL = "jdbc:mysql://root@localhost:3306/project2";

    int id = 1;
    TravelData wycieczki;
    Connection polaczenie;
    String oferta =
            "CREATE TABLE Oferta(" +
                    "id integer PRIMARY KEY," +
                    "kraj varchar(40)," +
                    "wyjazd Date," +
                    "powrot Date," +
                    "miejsce varchar(20)," +
                    "cena varchar(20)," +
                    "waluta varchar(10))";


    public Database(String URL, TravelData listaWycieczek) {
        this.listaWycieczek = listaWycieczek;
        try {
            this.polaczenie = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void create() {
        String dropStatement = "drop table if exists Oferta";
        Statement statement = null;
        try {
            polaczenie = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (polaczenie == null) {
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        try {
            statement = Objects.requireNonNull(this.polaczenie).createStatement();
            statement.executeUpdate(dropStatement);
            statement.executeUpdate(oferta);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement wstaw = polaczenie.prepareStatement("INSERT INTO Oferta VALUES(?,?,?,?,?,?,?)");
            for (String wycieczka : listaWycieczek.getResultList()) {
                String[] info = wycieczka.split("\\t");

                polaczenie.createStatement().execute(
                        "INSERT INTO Oferta(id, kraj, wyjazd, powrot, miejsce, cena, waluta) VALUES "
                                + "(" + id + ", " + "'" + info[0] + "', " + "'" + info[1] + "', " + "'" + info[2] + "', " + "'" + info[3] + "'," + "'" + info[4] + "', " + "'" + info[5] + "')"
                );
                id = id + 1;
                statement.close();

            }

        } catch (SQLException e) {
            System.err.println("Problem z tworzeniem tabeli i insertem");
            e.printStackTrace();
        }
    }


    public void showGui() {
        SwingUtilities.invokeLater(() ->
                new Table(listaWycieczek)
        );

    }

}
