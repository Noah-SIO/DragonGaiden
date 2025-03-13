package io.github.Noah_SIO;
import java.sql.Connection;
import java.sql.DriverManager;

public class TestSQLite {
    public static void main(String[] args) {
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("SQLite ok");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver SQLite non trouvé : " + e.getMessage());
        }
        
        
        String url = "jdbc:sqlite:test.db"; // Fichier de base de données SQLite

        try {
            Class.forName("org.sqlite.JDBC"); // Charger le driver
            Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                System.out.println("Connexion réussie à SQLite !");
            }
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}

