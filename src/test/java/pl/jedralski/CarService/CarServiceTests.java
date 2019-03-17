package pl.jedralski.CarService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.jedralski.CarService.dao.CarDAO;
import pl.jedralski.CarService.exception.DatabaseException;
import pl.jedralski.CarService.exception.InputException;
import pl.jedralski.CarService.model.Combustion;
import pl.jedralski.CarService.service.CarService;
import pl.jedralski.CarService.service.CombustionService;
import pl.jedralski.CarService.service.UserService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarServiceTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    CarDAO carDao;
    @Autowired
    CarService carService;
    @Autowired
    CombustionService combustionService;
    @Autowired
    UserService userService;

    @Test
    public void findIDTest() throws DatabaseException {
        int year = 2002;
        String make = "Audi";
        String model = "A4";
        System.out.println(carService.findID(year, make, model));
    }

    @Test
    public void findAllDataByIDTest() throws DatabaseException {
        long id = 3708;
        System.out.println(carService.findAllDataByID(id).getYear());
        System.out.println(carService.findAllDataByID(id).getMake());
        System.out.println(carService.findAllDataByID(id).getModel());
    }

    @Test
    public void findAllDataUserCarTest() throws DatabaseException {
        long id = 1;
        System.out.println(carService.findAllDataUserCar(id).getYear());
        System.out.println(carService.findAllDataUserCar(id).getMake());
        System.out.println(carService.findAllDataUserCar(id).getModel());
        System.out.println(carService.findAllDataUserCar(id).getCarPhoto());
    }

    @Test
    public void addUserCar() throws DatabaseException, InputException {
        long userID = 2;
        long carId = 1;
        carService.addUserCar(userID, carId);
    }

    @Test
    public void findAllCombustionData() throws DatabaseException {
        long userID = 1;
        for (Combustion combustion : combustionService.findAllDataDesc(userID)) {
            System.out.println(combustion.getDate() + " " + combustion.getFuelLitres() + " " + combustion.getKilometersTraveled() + " " + combustion.getLiterPrice());
        }
    }
/*
	@Test
	public void addCombustion() throws DatabaseException, ParseException, InputException {

		long userID = 1;
		//String pattern = "yyyy-MM-dd";
		//SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		//Date date = simpleDateFormat.parse("2018-10-23");
		Date date = Date.valueOf("2018-10-23");
		double fuelLitres = 43.01;
		double kilometersTraveled = 376.50;
		double literPrice = 2.39;

		combustionService.addCombustion(userID, date, fuelLitres, kilometersTraveled, literPrice);
	}
*/

    @Test
    public void chartData() throws DatabaseException {
        List<Combustion> combustionListBefore = new ArrayList<>();
        List<Combustion> combustionListAfter = new ArrayList<>();
        for (Combustion combustion : combustionService.findAllDataAsc(userService.findUserIDByUsername("konrado33"))) {
            combustionListBefore.add(new Combustion(combustion.getDate(), combustion.getCombustionValue()));
        }

        int j = 0, month1, month2, year1, year2;
        double value = 0.0;
        String dateName = "";
        Combustion combustion1, combustion2;
        Date date1, date2;
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        for (int i = 0; i < combustionListBefore.size(); i++) {

            if ((i + 1) <= combustionListBefore.size()) {
                combustion1 = combustionListBefore.get(i);
                date1 = combustion1.getDate();
                cal1.setTime(date1);
                month1 = cal1.get(Calendar.MONTH);
                year1 = cal1.get(Calendar.YEAR);

                if ((i + 1) < combustionListBefore.size()) {
                    combustion2 = combustionListBefore.get(i + 1);
                    date2 = combustion2.getDate();
                    cal2.setTime(date2);
                    month2 = cal2.get(Calendar.MONTH);
                    year2 = cal2.get(Calendar.YEAR);
                    if (month1 == month2 && year1 == year2) {
                        value += combustion1.getCombustionValue();
                        j++;
                        if ((month1 + 1) == 1) {
                            dateName = "Styczeń " + year1;
                        }
                        if ((month1 + 1) == 2) {
                            dateName = "Luty " + year1;
                        }
                        if ((month1 + 1) == 3) {
                            dateName = "Marzec " + year1;
                        }
                        if ((month1 + 1) == 4) {
                            dateName = "Kwiecień " + year1;
                        }
                        if ((month1 + 1) == 5) {
                            dateName = "Maj " + year1;
                        }
                        if ((month1 + 1) == 6) {
                            dateName = "Czerwiec " + year1;
                        }
                        if ((month1 + 1) == 7) {
                            dateName = "Lipiec " + year1;
                        }
                        if ((month1 + 1) == 8) {
                            dateName = "Sierpień " + year1;
                        }
                        if ((month1 + 1) == 9) {
                            dateName = "Wrzesień " + year1;
                        }
                        if ((month1 + 1) == 10) {
                            dateName = "Październik " + year1;
                        }
                        if ((month1 + 1) == 11) {
                            dateName = "Listopad " + year1;
                        }
                        if ((month1 + 1) == 12) {
                            dateName = "Grudzień " + year1;
                        }
                    } else {
                        value += combustion1.getCombustionValue();
                        value = value / (j + 1);
                        combustionListAfter.add(new Combustion(dateName, value));
                        value = 0.0;
                        dateName = "";
                        j = 0;
                    }
                }

                if ((i + 1) == combustionListBefore.size()) {
                    combustion2 = combustionListBefore.get(i - 1);
                    date2 = combustion2.getDate();
                    cal2.setTime(date2);
                    month2 = cal2.get(Calendar.MONTH);
                    year2 = cal2.get(Calendar.YEAR);
                    if (month1 == month2 && year1 == year2){
                        value += combustion1.getCombustionValue();
                        value = value / (j + 1);
                        combustionListAfter.add(new Combustion(dateName, value));
                        value = 0.0;
                        dateName = "";
                        j = 0;
                    } else {
                        value = combustion1.getCombustionValue();
                        if ((month1 + 1) == 1) {
                            dateName = "Styczeń " + year1;
                        }
                        if ((month1 + 1) == 2) {
                            dateName = "Luty " + year1;
                        }
                        if ((month1 + 1) == 3) {
                            dateName = "Marzec " + year1;
                        }
                        if ((month1 + 1) == 4) {
                            dateName = "Kwiecień " + year1;
                        }
                        if ((month1 + 1) == 5) {
                            dateName = "Maj " + year1;
                        }
                        if ((month1 + 1) == 6) {
                            dateName = "Czerwiec " + year1;
                        }
                        if ((month1 + 1) == 7) {
                            dateName = "Lipiec " + year1;
                        }
                        if ((month1 + 1) == 8) {
                            dateName = "Sierpień " + year1;
                        }
                        if ((month1 + 1) == 9) {
                            dateName = "Wrzesień " + year1;
                        }
                        if ((month1 + 1) == 10) {
                            dateName = "Październik " + year1;
                        }
                        if ((month1 + 1) == 11) {
                            dateName = "Listopad " + year1;
                        }
                        if ((month1 + 1) == 12) {
                            dateName = "Grudzień " + year1;
                        }
                        combustionListAfter.add(new Combustion(dateName, value));
                        value = 0.0;
                        dateName = "";
                        j = 0;
                    }
                }
            }
        }
        for (Combustion combustion : combustionListAfter) {
            System.out.println(combustion.getMonth() + " " + combustion.getCombustionValue());
        }
    }
}
