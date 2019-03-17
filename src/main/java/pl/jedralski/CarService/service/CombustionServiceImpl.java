package pl.jedralski.CarService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jedralski.CarService.dao.CombustionDAO;
import pl.jedralski.CarService.exception.DatabaseException;
import pl.jedralski.CarService.exception.InputException;
import pl.jedralski.CarService.model.Combustion;

import java.sql.Date;
import java.util.List;

@Service
public class CombustionServiceImpl  implements CombustionService {

    @Autowired
    private CombustionDAO combustionDAO;

    @Override
    public List<Combustion> findAllDataDesc(Long userID) throws DatabaseException {
        return combustionDAO.findAllDataDesc(userID);
    }

    @Override
    public List<Combustion> findAllDataAsc(Long userID) throws DatabaseException {
        return combustionDAO.findAllDataAsc(userID);
    }

    @Override
    public boolean addCombustion(Long userID, Date date, double fuelLitres, double kilometersTraveled, double literPrice, double combustionValue, double refuelingPrice) throws DatabaseException, InputException {
        return combustionDAO.addCombustion(userID, date, fuelLitres, kilometersTraveled, literPrice, combustionValue, refuelingPrice);
    }

    @Override
    public boolean checkUserCombustion(Long userID) throws DatabaseException {
        return combustionDAO.checkUserCombustion(userID);
    }

    @Override
    public boolean deleteCombustion(Date date, Long userID) throws DatabaseException {
        return combustionDAO.deleteCombustion(date, userID);
    }

    @Override
    public boolean deleteUserCombustion(Long userID) throws DatabaseException {
        return combustionDAO.deleteUserCombustion(userID);
    }

    @Override
    public double summaryCombustion(Long userID) throws DatabaseException {
        return combustionDAO.summaryCombustion(userID);
    }
}
