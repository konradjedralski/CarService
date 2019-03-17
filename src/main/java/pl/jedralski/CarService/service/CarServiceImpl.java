package pl.jedralski.CarService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jedralski.CarService.dao.CarDAO;
import pl.jedralski.CarService.exception.DatabaseException;
import pl.jedralski.CarService.exception.InputException;
import pl.jedralski.CarService.model.Car;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarDAO carDAO;

    @Override
    public Long findID(int year, String make, String model) throws DatabaseException {
        return carDAO.findID(year, make, model);
    }

    @Override
    public Car findAllDataByID(Long id) throws DatabaseException {
        return carDAO.findAllDataByID(id);
    }

    @Override
    public boolean findUserCar(Long id) throws DatabaseException {
        return carDAO.findUserCar(id);
    }

    @Override
    public Car findAllDataUserCar(long id) throws DatabaseException {
        return carDAO.findAllDataUserCar(id);
    }

    @Override
    public boolean addUserCar(Long userID, Long carID) throws DatabaseException, InputException {
        return carDAO.addUserCar(userID, carID);
    }

    @Override
    public boolean updateCarPhoto(Long userID, String photo) throws DatabaseException, InputException {
        return carDAO.updateCarPhoto(userID, photo);
    }

    @Override
    public boolean deleteUserCar(Long userID) throws DatabaseException {
        return carDAO.deleteUserCar(userID);
    }

    @Override
    public boolean addCar(int year, String make, String model) throws DatabaseException, InputException {
        return carDAO.addCar(year, make, model);
    }

    @Override
    public List<String> findMake() throws DatabaseException {
        return carDAO.findMake();
    }

    @Override
    public List<String> findModel() throws DatabaseException {
        return carDAO.findModel();
    }

    @Override
    public List<Integer> findYear() throws DatabaseException {
        return carDAO.findYear();
    }
}
