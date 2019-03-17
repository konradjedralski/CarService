package pl.jedralski.CarService.dao;

import pl.jedralski.CarService.exception.DatabaseException;
import pl.jedralski.CarService.exception.InputException;
import pl.jedralski.CarService.model.Car;

import java.util.List;

public interface CarDAO {

    Long findID (int year, String make, String model) throws DatabaseException;

    Car findAllDataByID (Long id) throws DatabaseException;

    boolean findUserCar (Long id) throws DatabaseException;

    Car findAllDataUserCar (long id) throws DatabaseException;

    boolean addUserCar (Long userID, Long carID) throws DatabaseException, InputException;

    boolean updateCarPhoto (Long userID, String photo) throws DatabaseException, InputException;

    boolean deleteUserCar (Long userID) throws DatabaseException;

    boolean addCar (int year, String make, String model) throws DatabaseException, InputException;

    List<String> findMake() throws DatabaseException;

    List<String> findModel() throws DatabaseException;

    List<Integer> findYear() throws DatabaseException;

}
