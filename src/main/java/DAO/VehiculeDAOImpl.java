package DAO;

import models.Vehicule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehiculeDAOImpl implements VehiculeDAO{

    private final String tableVehicule = "vehicule";
    private final String idVehicule="id";
    private final String immatriculation = "immatriculation";
    private final String marque = "marque";
    private final String modele = "modele";
    private final String dateMiseCirulation = "date_mise_circulation";
    private final String kilometrage = "kilometrage";


    /**
     * Création d'un vehicule en bdd.
     * @param vehicule
     */
    @Override
    public boolean creer(Vehicule vehicule) {
        String sql =  "insert into " + tableVehicule +
                " (" + immatriculation + ", "
                + marque + ", "
                + modele + ", "
                + dateMiseCirulation + ", "
                + kilometrage +
                ") values (?,?,?,?,?)";
        try(Connection cnx = DAOFactory.getInstance().getConnexion();
            PreparedStatement ps = cnx.prepareStatement(sql);) {
            ps.setString(1, vehicule.getImmatriculation());
            ps.setString(2,vehicule.getMarque());
            ps.setString(3, vehicule.getModele());
            ps.setDate(4,java.sql.Date.valueOf(vehicule.getDateMiseEnCirculation()));
            ps.setInt(5,vehicule.getKilometrage());
            return ps.executeUpdate()>0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Vehicule trouverParId(int id) {
        String sql = "SELECT * FROM " + tableVehicule + " WHERE " + idVehicule + "= " + id;
        try(Connection cnx = DAOFactory.getInstance().getConnexion();
            PreparedStatement ps = cnx.prepareStatement(sql)){
                ResultSet rs = ps.executeQuery();

                if(rs.next()){
                    Vehicule v = new Vehicule(
                            rs.getInt("id"),
                            rs.getString("immatriculation"),
                            rs.getString("marque"),
                            rs.getString("modele"),
                            rs.getDate("date_mise_circulation").toLocalDate(),
                            rs.getInt("kilometrage")
                    );
                    return v;
                }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Vehicule trouverParImmat(String immat) {
        String sql = "SELECT * FROM " + tableVehicule + " WHERE " + immatriculation + " = '" + immat +"'";
        try(Connection cnx = DAOFactory.getInstance().getConnexion();
            PreparedStatement ps = cnx.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Vehicule v = new Vehicule(
                        rs.getInt("id"),
                        rs.getString("immatriculation"),
                        rs.getString("marque"),
                        rs.getString("modele"),
                        rs.getDate("date_mise_circulation").toLocalDate(),
                        rs.getInt("kilometrage")
                );
                return v;
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    @Override
    public List<Vehicule> lister() {
        String sql = "SELECT * FROM " + tableVehicule;
        List<Vehicule> vehicules = new ArrayList<>();

        try(Connection cnx = DAOFactory.getInstance().getConnexion();
            PreparedStatement ps = cnx.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Vehicule v = new Vehicule(
                        rs.getInt("id"),
                        rs.getString("immatriculation"),
                        rs.getString("marque"),
                        rs.getString("modele"),
                        rs.getDate("date_mise_circulation").toLocalDate(),
                        rs.getInt("kilometrage")
                );
                vehicules.add(v);
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return vehicules;
    }

    @Override
    public boolean modifier(Vehicule vehicule) {
        String sql = "UPDATE " + tableVehicule + " set "
                + immatriculation + " = ?, "
                + marque + " = ?, "
                + modele + " = ?, "
                + dateMiseCirulation + " = ?, "
                + kilometrage + " = ? where "+
                idVehicule + " = ?";
        try(Connection cnx = DAOFactory.getInstance().getConnexion();
            PreparedStatement ps = cnx.prepareStatement(sql);){
            ps.setString(1,vehicule.getImmatriculation());
            ps.setString(2,vehicule.getMarque());
            ps.setString(3,vehicule.getModele());
            ps.setDate(4,java.sql.Date.valueOf(vehicule.getDateMiseEnCirculation()));
            ps.setInt(5,vehicule.getKilometrage());
            ps.setInt(6,vehicule.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public boolean supprimer(int id) {
        String sql = "DELETE from " + tableVehicule + " where " + idVehicule + " = ?";
        try(Connection cnx = DAOFactory.getInstance().getConnexion();
            PreparedStatement ps = cnx.prepareStatement(sql);) {
            ps.setInt(1,id);
            return ps.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean supprimerParImmat(String immat) {
        String sql = "DELETE from " + tableVehicule + " where " + immatriculation + " = ?";
        try(Connection cnx = DAOFactory.getInstance().getConnexion();
            PreparedStatement ps = cnx.prepareStatement(sql);) {
            ps.setString(1,immat);
            return ps.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
