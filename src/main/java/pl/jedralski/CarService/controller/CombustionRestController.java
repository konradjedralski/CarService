package pl.jedralski.CarService.controller;

import org.decimal4j.util.DoubleRounder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.jedralski.CarService.exception.DatabaseException;
import pl.jedralski.CarService.model.Combustion;
import pl.jedralski.CarService.service.CombustionService;
import pl.jedralski.CarService.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.sql.Date;

@Controller
@RequestMapping("/combustion-rest")
public class CombustionRestController {

    @Autowired
    UserService userService;
    @Autowired
    CombustionService combustionService;

    @RequestMapping(value = "/chart", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List> chartData(Principal principal) throws DatabaseException {
        List<Combustion> combustionListBefore = new ArrayList<>();
        List<Combustion> combustionListAfter = new ArrayList<>();
        for (Combustion combustion : combustionService.findAllDataAsc(userService.findUserIDByUsername(principal.getName()))) {
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
                        if (dateName == ""){
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
                        }
                        combustionListAfter.add(new Combustion(dateName, DoubleRounder.round(value, 2)));
                        value = 0.0;
                        dateName = "";
                        j = 0;
                    }
                }

                if ((i + 1) == combustionListBefore.size()) {
                    if ((i - 1) < 0){
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
                        combustionListAfter.add(new Combustion(dateName, DoubleRounder.round(value, 2)));
                        value = 0.0;
                        dateName = "";
                        j = 0;
                    } else {
                        combustion2 = combustionListBefore.get(i - 1);
                        date2 = combustion2.getDate();
                        cal2.setTime(date2);
                        month2 = cal2.get(Calendar.MONTH);
                        year2 = cal2.get(Calendar.YEAR);
                        if (month1 == month2 && year1 == year2) {
                            value += combustion1.getCombustionValue();
                            value = value / (j + 1);
                            combustionListAfter.add(new Combustion(dateName, DoubleRounder.round(value, 2)));
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
                            combustionListAfter.add(new Combustion(dateName, DoubleRounder.round(value, 2)));
                            value = 0.0;
                            dateName = "";
                            j = 0;
                        }
                    }
                }
            }
        }
        return new ResponseEntity<>(combustionListAfter, HttpStatus.CREATED);
    }
}
