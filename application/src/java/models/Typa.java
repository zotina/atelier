package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Typa {
    private String id_typa;
    private String libelle;

    public Typa() {
    }

    public Typa(String id_typa, String libelle) {
        this.id_typa = id_typa;
        this.libelle = libelle;
    }

    public String getId_typa() {
        return id_typa;
    }

    public void setId_typa(String id_typa) {
        this.id_typa = id_typa;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    // Méthode d'insertion
    public void insert(Connection connection) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO typa (libelle) VALUES (?) RETURNING id_typa";
            statement = connection.prepareStatement(query);
            statement.setString(1, getLibelle());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                this.id_typa = resultSet.getString("id_typa");
            }
            System.out.println("Données Typa insérées avec succès");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            if (resultSet != null)
                resultSet.close();
            if (statement != null)
                statement.close();
        }
    }

    // Méthode de mise à jour
    public void update(Connection connection) throws Exception {
        PreparedStatement statement = null;
        try {
            String query = "UPDATE typa SET libelle = ? WHERE id_typa = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, getLibelle());
            statement.setString(2, getId_typa());
            statement.executeUpdate();
            System.out.println("Données Typa mises à jour avec succès");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            if (statement != null)
                statement.close();
        }
    }

    // Méthode de suppression
    public static void delete(String id_typa, Connection connection) throws Exception {
        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM typa WHERE id_typa = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, id_typa);
            statement.executeUpdate();
            System.out.println("Données Typa supprimées avec succès");
        } catch (Exception e) {
            throw new Exception("Erreur lors de la suppression de Typa avec ID id_typa: " + e.getMessage());
        } finally {
            if (statement != null)
                statement.close();
        }
    }

    // Méthode pour récupérer tous les enregistrements
    public static List<Typa> getAll(Connection connection) throws Exception {
        List<Typa> liste = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM typa";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Typa typa = new Typa();
                typa.setId_typa(resultSet.getString("id_typa"));
                typa.setLibelle(resultSet.getString("libelle"));
                liste.add(typa);
            }
            return liste;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (resultSet != null)
                resultSet.close();
            if (statement != null)
                statement.close();
        }
    }

    // Méthode pour récupérer un enregistrement par son ID
    public static Typa getById(String id_typa, Connection connection) throws Exception {
        Typa typa = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM typa WHERE id_typa = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, id_typa);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                typa = new Typa();
                typa.setId_typa(resultSet.getString("id_typa"));
                typa.setLibelle(resultSet.getString("libelle"));
            }
            return typa;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (resultSet != null)
                resultSet.close();
            if (statement != null)
                statement.close();
        }
    }
}
