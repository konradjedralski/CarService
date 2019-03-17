package pl.jedralski.CarService.dao;

import org.springframework.stereotype.Repository;
import pl.jedralski.CarService.dbconnection.DBConnector;
import pl.jedralski.CarService.exception.DatabaseException;
import pl.jedralski.CarService.exception.InputException;
import pl.jedralski.CarService.model.Car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CarDAOImpl implements CarDAO {
    @Override
    public Long findID(int year, String make, String model) throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnector.getConnection();
            String query = "SELECT id FROM cars WHERE year = ? and make = ? and model = ? LIMIT 1";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, year);
            preparedStatement.setString(2, make);
            preparedStatement.setString(3, model);
            resultSet = preparedStatement.executeQuery();
            Long id = null;
            if (resultSet == null) {
                throw new DatabaseException("Error returning result.");
            }
            if (resultSet.next()) {
                id = resultSet.getLong("id");
            }
            if (resultSet.next()) {
                throw new DatabaseException("Query returned more than 1 record.");
            }
            return id;
        } catch (SQLException e) {
            throw new DatabaseException("Problem with ID: " + e);
        } finally {
            DBConnector.closeResultSet(resultSet);
            DBConnector.closeStatement(preparedStatement);
            DBConnector.closeConnection(connection);
        }
    }

    @Override
    public Car findAllDataByID(Long id) throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnector.getConnection();
            String query = "SELECT year, make, model FROM cars WHERE id = ? LIMIT 1";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            Car car = null;
            if (resultSet == null) {
                throw new DatabaseException("Error returning result.");
            }
            if (resultSet.next()) {
                int year = resultSet.getInt("year");
                String make = resultSet.getString("make");
                String model = resultSet.getString("model");

                car = new Car(year, make, model);
            }
            if (resultSet.next()) {
                throw new DatabaseException("Query returned more than 1 record.");
            }
            return car;
        } catch (SQLException e) {
            throw new DatabaseException("Problem with ID: " + e);
        } finally {
            DBConnector.closeResultSet(resultSet);
            DBConnector.closeStatement(preparedStatement);
            DBConnector.closeConnection(connection);
        }
    }

    @Override
    public boolean findUserCar(Long id) throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnector.getConnection();
            String query = "SELECT id FROM user_car WHERE user_id = ? LIMIT 1";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
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
    public Car findAllDataUserCar(long id) throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnector.getConnection();
            String query = "SELECT year, make, model, car_photo FROM user_car JOIN cars ON user_car.car_id=cars.id WHERE user_id = ? LIMIT 1";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            Car car = null;
            if (resultSet == null) {
                throw new DatabaseException("Error returning result.");
            }
            if (resultSet.next()) {
                int year = resultSet.getInt("year");
                String make = resultSet.getString("make");
                String model = resultSet.getString("model");
                String carPhoto = resultSet.getString("car_photo");

                car = new Car(year, make, model, carPhoto);
            }
            if (resultSet.next()) {
                throw new DatabaseException("Query returned more than 1 record.");
            }
            return car;
        } catch (SQLException e) {
            throw new DatabaseException("Problem with ID: " + e);
        } finally {
            DBConnector.closeResultSet(resultSet);
            DBConnector.closeStatement(preparedStatement);
            DBConnector.closeConnection(connection);
        }
    }

    @Override
    public boolean addUserCar(Long userID, Long carID) throws DatabaseException, InputException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnector.getConnection();
            String query = "INSERT INTO user_car (user_id, car_id, car_photo) VALUES (?, ?, '')";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, userID);
            preparedStatement.setLong(2, carID);
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
    public boolean updateCarPhoto(Long userID, String photo) throws DatabaseException, InputException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnector.getConnection();
            String query = "UPDATE user_car SET car_photo = ? WHERE user_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, photo);
            preparedStatement.setLong(2, userID);
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
    public boolean deleteUserCar(Long userID) throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnector.getConnection();
            String query = "DELETE FROM user_car WHERE user_id = ?";
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
    public boolean addCar(int year, String make, String model) throws DatabaseException, InputException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnector.getConnection();
            String query = "INSERT INTO cars (year, make, model) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, year);
            preparedStatement.setString(2, make);
            preparedStatement.setString(3, model);
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
    public List<String> findMake() throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnector.getConnection();
            String query = "SELECT make FROM cars GROUP BY make ORDER BY make ASC";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            List<String> carMake = new ArrayList<>();

            if (resultSet == null) {
                throw new DatabaseException("Error returning result.");
            }
            while (resultSet.next()) {
                String make = resultSet.getString("make");
                carMake.add(new String(make));
            }
            return carMake;
        } catch (SQLException e) {
            throw new DatabaseException("Problem with ID: " + e);
        } finally {
            DBConnector.closeResultSet(resultSet);
            DBConnector.closeStatement(preparedStatement);
            DBConnector.closeConnection(connection);
        }
    }

    @Override
    public List<String> findModel() throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnector.getConnection();
            String query = "SELECT model FROM cars GROUP BY model ORDER BY model ASC";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            List<String> carModel = new ArrayList<>();

            if (resultSet == null) {
                throw new DatabaseException("Error returning result.");
            }
            while (resultSet.next()) {
                String model = resultSet.getString("model");
                carModel.add(new String(model));
            }
            return carModel;
        } catch (SQLException e) {
            throw new DatabaseException("Problem with ID: " + e);
        } finally {
            DBConnector.closeResultSet(resultSet);
            DBConnector.closeStatement(preparedStatement);
            DBConnector.closeConnection(connection);
        }
    }

    @Override
    public List<Integer> findYear() throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnector.getConnection();
            String query = "SELECT year FROM cars GROUP BY year ORDER BY year ASC";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            List<Integer> carYear = new ArrayList<>();

            if (resultSet == null) {
                throw new DatabaseException("Error returning result.");
            }
            while (resultSet.next()) {
                int year = resultSet.getInt("year");
                carYear.add(new Integer(year));
            }
            return carYear;
        } catch (SQLException e) {
            throw new DatabaseException("Problem with ID: " + e);
        } finally {
            DBConnector.closeResultSet(resultSet);
            DBConnector.closeStatement(preparedStatement);
            DBConnector.closeConnection(connection);
        }
    }
}
