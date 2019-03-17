package pl.jedralski.CarService.dao;

import org.springframework.stereotype.Repository;
import pl.jedralski.CarService.dbconnection.DBConnector;
import pl.jedralski.CarService.exception.DatabaseException;
import pl.jedralski.CarService.exception.InputException;
import pl.jedralski.CarService.model.Combustion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CombustionDAOImpl implements CombustionDAO {
    @Override
    public List<Combustion> findAllDataDesc(Long userID) throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnector.getConnection();
            String query = "SELECT refueling_date, fuel_litres, kilometers_traveled, liter_price, combustion_value, refueling_price FROM combustion WHERE user_id = ? ORDER BY refueling_date DESC";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, userID);
            resultSet = preparedStatement.executeQuery();
            List<Combustion> combustion = new ArrayList<>();

            if (resultSet == null) {
                throw new DatabaseException("Error returning result.");
            }
            while (resultSet.next()) {
                Date refuelingDate  = resultSet.getDate("refueling_date");
                double fuelLitres = resultSet.getDouble("fuel_litres");
                double kilometersTraveled = resultSet.getDouble("kilometers_traveled");
                double literPrice = resultSet.getDouble("liter_price");
                double combustionValue = resultSet.getDouble("combustion_value");
                double refuelingPrice = resultSet.getDouble("refueling_price");

                combustion.add(new Combustion(refuelingDate, fuelLitres, kilometersTraveled, literPrice, combustionValue, refuelingPrice));
            }
            return combustion;
        } catch (SQLException e) {
            throw new DatabaseException("Problem with ID: " + e);
        } finally {
            DBConnector.closeResultSet(resultSet);
            DBConnector.closeStatement(preparedStatement);
            DBConnector.closeConnection(connection);
        }
    }

    @Override
    public List<Combustion> findAllDataAsc(Long userID) throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnector.getConnection();
            String query = "SELECT refueling_date, fuel_litres, kilometers_traveled, liter_price, combustion_value, refueling_price FROM combustion WHERE user_id = ? ORDER BY refueling_date ASC";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, userID);
            resultSet = preparedStatement.executeQuery();
            List<Combustion> combustion = new ArrayList<>();

            if (resultSet == null) {
                throw new DatabaseException("Error returning result.");
            }
            while (resultSet.next()) {
                Date refuelingDate  = resultSet.getDate("refueling_date");
                double fuelLitres = resultSet.getDouble("fuel_litres");
                double kilometersTraveled = resultSet.getDouble("kilometers_traveled");
                double literPrice = resultSet.getDouble("liter_price");
                double combustionValue = resultSet.getDouble("combustion_value");
                double refuelingPrice = resultSet.getDouble("refueling_price");

                combustion.add(new Combustion(refuelingDate, fuelLitres, kilometersTraveled, literPrice, combustionValue, refuelingPrice));
            }
            return combustion;
        } catch (SQLException e) {
            throw new DatabaseException("Problem with ID: " + e);
        } finally {
            DBConnector.closeResultSet(resultSet);
            DBConnector.closeStatement(preparedStatement);
            DBConnector.closeConnection(connection);
        }
    }

    @Override
    public boolean addCombustion(Long userID, Date date, double fuelLitres, double kilometersTraveled, double literPrice, double combustionValue, double refuelingPrice) throws DatabaseException, InputException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnector.getConnection();
            String query = "INSERT INTO combustion (user_id, refueling_date, fuel_litres, kilometers_traveled, liter_price, combustion_value, refueling_price) VALUES (?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, userID);
            preparedStatement.setDate(2, date);
            preparedStatement.setDouble(3, fuelLitres);
            preparedStatement.setDouble(4, kilometersTraveled);
            preparedStatement.setDouble(5, literPrice);
            preparedStatement.setDouble(6, combustionValue);
            preparedStatement.setDouble(7, refuelingPrice);
            preparedStatement.executeUpdate();
            return true;
        } catch (IllegalArgumentException e) {
            throw new InputException("Insert failed. ", e);
        } catch (SQLException e) {
            throw new DatabaseException("Update failed. ", e);
        } finally {
            DBConnector.closeStatement(preparedStatement);
            DBConnector.closeConnection(connection);
        }
    }

    @Override
    public boolean checkUserCombustion(Long userID) throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnector.getConnection();
            String query = "SELECT user_id FROM combustion WHERE user_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, userID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet == null) {
                return false;
            }
            if (resultSet.next()) {
                return true;
            }
            if (resultSet.next()) {
                throw new DatabaseException("Query returned more than 1 record.");
            } else return false;
        } catch (SQLException e) {
            throw new DatabaseException("Problem with ID: " + e);
        } finally {
            DBConnector.closeResultSet(resultSet);
            DBConnector.closeStatement(preparedStatement);
            DBConnector.closeConnection(connection);
        }
    }

    @Override
    public boolean deleteCombustion(Date date, Long userID) throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnector.getConnection();
            String query = "DELETE FROM combustion WHERE refueling_date = ? AND user_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, date);
            preparedStatement.setLong(2, userID);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DatabaseException("Delete failed. ", e);
        } finally {
            DBConnector.closeStatement(preparedStatement);
            DBConnector.closeConnection(connection);
        }
    }

    @Override
    public boolean deleteUserCombustion(Long userID) throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnector.getConnection();
            String query = "DELETE FROM combustion WHERE user_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, userID);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DatabaseException("Delete failed. ", e);
        } finally {
            DBConnector.closeStatement(preparedStatement);
            DBConnector.closeConnection(connection);
        }
    }

    @Override
    public double summaryCombustion(Long userID) throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnector.getConnection();
            String query = "SELECT ((SELECT SUM(combustion_value) FROM combustion WHERE user_id = ?)/(SELECT COUNT(id) FROM combustion WHERE user_id = ?)) AS summary_combustion FROM combustion WHERE user_id = ? LIMIT 1";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, userID);
            preparedStatement.setLong(2, userID);
            preparedStatement.setLong(3, userID);
            resultSet = preparedStatement.executeQuery();
            double summaryCombustion = 0;

            if (resultSet == null) {
                throw new DatabaseException("Error returning result.");
            }
            while (resultSet.next()) {
                double sCombustion= resultSet.getDouble("summary_combustion");
                summaryCombustion = sCombustion;
            }
            return summaryCombustion;
        } catch (SQLException e) {
            throw new DatabaseException("Problem with ID: " + e);
        } finally {
            DBConnector.closeResultSet(resultSet);
            DBConnector.closeStatement(preparedStatement);
            DBConnector.closeConnection(connection);
        }
    }
}

