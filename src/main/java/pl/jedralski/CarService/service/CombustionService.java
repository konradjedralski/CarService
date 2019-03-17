package pl.jedralski.CarService.service;

import pl.jedralski.CarService.exception.DatabaseException;
import pl.jedralski.CarService.exception.InputException;
import pl.jedralski.CarService.model.Combustion;

import java.sql.Date;
import java.util.List;

public interface CombustionService {

    List<Combustion> findAllDataDesc(Long userID) throws DatabaseException;

    List<Combustion> findAllDataAsc(Long userID) throws DatabaseException;

    boolean addCombustion (Long userID, Date date, double fuelLitres, double kilometersTraveled, double literPrice, double combustionValue, double refuelingPrice) throws DatabaseException, InputException;

    boolean checkUserCombustion (Long userID) throws DatabaseException;

    boolean deleteCombustion (Date date, Long userID) throws DatabaseException;

    boolean deleteUserCombustion (Long userID) throws DatabaseException;

    double summaryCombustion(Long userID) throws DatabaseException;
}
