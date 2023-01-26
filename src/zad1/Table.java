package zad1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Locale;


public class Table extends JFrame {


    TravelData travelData;
    JTable tabela;
    DefaultTableModel projekt;

    public Table(TravelData data) {
        this.travelData = data;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        projekt = new DefaultTableModel();
        projekt.addColumn("Nazwa kraju");
        projekt.addColumn("Data Wyjazdu");
        projekt.addColumn("Data Powrotu");
        projekt.addColumn("Miejsce");
        projekt.addColumn("Cena");
        projekt.addColumn("Waluta");
        tabela = new JTable();
        tabela.setModel(projekt);
        tabela.setEnabled(false);



        add(new JScrollPane(tabela), BorderLayout.CENTER);
        dodajButt();
        pack();

    }


    void dodajButt() {
        JPanel przycisk = new JPanel(new GridBagLayout());

        tlumaczPL("pl", przycisk);
        zmienNaPOL();

        add(przycisk, BorderLayout.EAST);
        tlumaczANG("en", przycisk);
    }

    void tlumaczANG(String langname, JPanel buttons) {
        JButton button = new JButton(langname);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Locale.setDefault(new Locale(langname));
                zmienNaANG();
                JTableHeader ang = tabela.getTableHeader();
                TableColumnModel columnModel = ang.getColumnModel();

                columnModel.getColumn(0).setHeaderValue("Country");
                columnModel.getColumn(1).setHeaderValue("Departure");
                columnModel.getColumn(2).setHeaderValue("Arrival");
                columnModel.getColumn(3).setHeaderValue("Destination");
                columnModel.getColumn(4).setHeaderValue("Price");
                columnModel.getColumn(5).setHeaderValue("Currency");
                ang.repaint();


            }
        });

        buttons.add(button);
    }

    void tlumaczPL(String langname, JPanel buttons) {
        JButton button = new JButton(langname);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Locale.setDefault(new Locale(langname));
                zmienNaPOL();
                JTableHeader tlumaczPL = tabela.getTableHeader();
                TableColumnModel columnModel = tlumaczPL.getColumnModel();
                columnModel.getColumn(0).setHeaderValue("Kraj");
                columnModel.getColumn(1).setHeaderValue("Wyjazd");
                columnModel.getColumn(2).setHeaderValue("Powrot");
                columnModel.getColumn(3).setHeaderValue("Miejsce");
                columnModel.getColumn(4).setHeaderValue("Koszt");
                columnModel.getColumn(5).setHeaderValue("Waluta");
                tlumaczPL.repaint();

            }
        });

        buttons.add(button);
    }


    public TravelData getTravelData() {
        return travelData;
    }

    public void zmienNaANG() {
        String dateFormat = "yyyy-MM-dd";
        projekt.setRowCount(0);
        List<String> odlist = travelData.getOffersDescriptionsList("en_GB", dateFormat);
        for (String s : odlist) {
            String co[] = s.split("\t");
            Object[] infomacje = new Object[6];
            infomacje[0] = co[0];
            infomacje[1] = co[1];
            infomacje[2] = co[2];
            infomacje[3] = co[3];
            infomacje[4] = co[4];
            infomacje[5] = co[5];
            projekt.addRow(infomacje);
        }
    }

    public void zmienNaPOL() {
        String dateFormat = "yyyy-MM-dd";
        projekt.setRowCount(0);
        List<String> odlist = travelData.getOffersDescriptionsList("pl_PL", dateFormat);
        for (int i = 0; i < odlist.size(); i++) {
            String co[] = odlist.get(i).split("\t");
            Object[] infomacje = new Object[6];
            infomacje[0] = co[0];
            infomacje[1] = co[1];
            infomacje[2] = co[2];
            infomacje[3] = co[3];
            infomacje[4] = co[4];
            infomacje[5] = co[5];
            projekt.addRow(infomacje);
        }


    }
}

