package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Commission {
    private Employe employe;
    private Reparation reparation;
    private double commission;
    private Appareil appareil;
    
    
    public static List<Commission> getCommissionFilterByPeriode(Connection connection, 
            String dateDebutMin, String dateDebutMax) throws Exception {
        List<Commission> liste = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            StringBuilder query = new StringBuilder(
                "SELECT * FROM vue_commission_employe WHERE 1=1"
            );
            if (dateDebutMin != null && !dateDebutMin.isEmpty()) {
                query.append(" AND date_reparation >= ?");
            }
            if (dateDebutMax != null && !dateDebutMax.isEmpty()) {
                query.append(" AND date_reparation <= ?");
            }
            
            statement = connection.prepareStatement(query.toString());
            
            int index = 1;
            if (dateDebutMin != null && !dateDebutMin.isEmpty()) {
                statement.setDate(index++, Date.valueOf(dateDebutMin));
            }
            if (dateDebutMax != null && !dateDebutMax.isEmpty()) {
                statement.setDate(index++, Date.valueOf(dateDebutMax));
            }
            
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Commission instance = new Commission();
                
                // Créer l'objet Employe
                Employe employe = new Employe();
                employe.setId_employe(resultSet.getString("id_employe"));
                employe.setNom(resultSet.getString("nom_employe"));
                instance.setEmploye(employe);
                
                // Créer l'objet Reparation
                Reparation reparation = new Reparation();
                reparation.setId_reparation(resultSet.getString("id_reparation"));
                reparation.setDate_debut(resultSet.getTimestamp("date_reparation").toLocalDateTime());
                reparation.setPrix(resultSet.getDouble("prix_reparation"));
                Appareil appareil = Appareil.getById(resultSet.getString("id_appareil"), connection);
				reparation.setAppareil(appareil);
                instance.setReparation(reparation);

                // Créer l'objet Appareil

                // Définir la commission
                instance.setCommission(resultSet.getDouble("commission"));
                
                liste.add(instance);
            }
            
            return liste;
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
        }
    }
    
    // Constructeurs
    public Commission() {}
    
    public Commission(Employe employe, Reparation reparation, double commission) {
        this.employe = employe;
        this.reparation = reparation;
        this.commission = commission;
    }
    
    // Getters et Setters
    public Employe getEmploye() {
        return employe;
    }
    
    public void setEmploye(Employe employe) {
        this.employe = employe;
    }
    
    public Reparation getReparation() {
        return reparation;
    }
    
    public void setReparation(Reparation reparation) {
        this.reparation = reparation;
    }
    
    public double getCommission() {
        return commission;
    }
    
    public void setCommission(double commission) {
        this.commission = commission;
    }

    public Appareil getAppareil() {
        return appareil;
    }

    public void setAppareil(Appareil appareil) {
        this.appareil = appareil;
    }
}