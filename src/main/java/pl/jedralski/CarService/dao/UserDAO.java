package pl.jedralski.CarService.dao;

import pl.jedralski.CarService.exception.DatabaseException;
import pl.jedralski.CarService.exception.InputException;
import pl.jedralski.CarService.model.User;

public interface UserDAO {

    Long findUserIDByUsername(String username) throws DatabaseException;

    User findAllData(String username) throws DatabaseException;

    boolean addUser(String username, String password, String firstName, String lastName, String email) throws InputException, DatabaseException;

    boolean updateUser(Long id, String username, String firstName, String lastName, String email, String photo) throws InputException, DatabaseException;

    boolean updateUserPassword(Long id, String password) throws InputException, DatabaseException;

    boolean findUsername(String username) throws DatabaseException;

    String getHash(Long id) throws DatabaseException;

    boolean deleteUser(Long id) throws DatabaseException;
}
