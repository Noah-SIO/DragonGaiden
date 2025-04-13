package io.github.Noah_SIO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLiteManager {

    private static final String URL = "jdbc:sqlite:dragonGaidenDb.db";

    public SQLiteManager() {
        /////////////Test extension et connexion//////////////
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("Driver SQLite chargé avec succès !");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver SQLite non trouvé : " + e.getMessage());
        }
        
        /////////////////si base non présente recréé/////////////
        try (Connection conn = DriverManager.getConnection(URL)) {
            if (conn != null) {
                String sqlCreateTable = "CREATE TABLE IF NOT EXISTS scores ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "pseudo TEXT NOT NULL, "
                        + "score INTEGER NOT NULL, "
                        + "date TEXT NOT NULL"
                        + ");";
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(sqlCreateTable);
                    System.out.println("Table 'scores' prête à l'emploi !");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur de connexion ou de création : " + e.getMessage());
        }
    }
    ///////////////////////////


    ///////////////Insert fin de partie//////////////////
    public void insererScore(String pseudo, int score, String date) {
        String sqlInsert = "INSERT INTO scores (pseudo, score, date) VALUES (?, ?, ?);";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
            pstmt.setString(1, pseudo);
            pstmt.setInt(2, score);
            pstmt.setString(3, date);
            pstmt.executeUpdate();
            System.out.println("Score inséré avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion : " + e.getMessage());
        }
    }
    ///////////////////////////




    ///////////Les 3 meilleurs joueurs////////////////
    public ArrayList<String[]> recupererTopScores() {
        String sqlSelectTop3 = "SELECT * FROM scores ORDER BY score DESC, id DESC LIMIT 3;";
        ArrayList<String[]> scoresTable = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlSelectTop3)) {

            while (rs.next()) {
                String[] scoreEntry = new String[3];
                scoreEntry[0] = rs.getString("pseudo"); // Pseudo
                scoreEntry[1] = String.valueOf(rs.getInt("score")); // Score
                scoreEntry[2] = rs.getString("date"); // Date
                scoresTable.add(scoreEntry);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des scores : " + e.getMessage());
        }

        return scoresTable;
    }
    ///////////////////////////





}
