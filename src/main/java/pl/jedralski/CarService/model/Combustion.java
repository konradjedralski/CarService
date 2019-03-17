package pl.jedralski.CarService.model;


import java.sql.Date;

public class Combustion {

    private Long userId;
    private Date date;
    private double fuelLitres;
    private double kilometersTraveled;
    private double literPrice;
    private double combustionValue;
    private double refuelingPrice;
    private String month;


    public Combustion() {
    }

    public Combustion(Date date, double fuelLitres, double kilometersTraveled, double literPrice, double combustionValue, double refuelingPrice) {
        this.date = date;
        this.fuelLitres = fuelLitres;
        this.kilometersTraveled = kilometersTraveled;
        this.literPrice = literPrice;
        this.combustionValue = combustionValue;
        this.refuelingPrice = refuelingPrice;
    }

    public Combustion(Date date, double combustionValue) {
        this.date = date;
        this.combustionValue = combustionValue;
    }

    public Combustion(String month, double combustionValue) {
        this.month = month;
        this.combustionValue = combustionValue;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getFuelLitres() {
        return fuelLitres;
    }

    public void setFuelLitres(double fuelLitres) {
        this.fuelLitres = fuelLitres;
    }

    public double getKilometersTraveled() {
        return kilometersTraveled;
    }

    public void setKilometersTraveled(double kilometersTraveled) {
        this.kilometersTraveled = kilometersTraveled;
    }

    public double getLiterPrice() {
        return literPrice;
    }

    public void setLiterPrice(double literPrice) {
        this.literPrice = literPrice;
    }

    public double getCombustionValue() {
        return combustionValue;
    }

    public void setCombustionValue(double combustionValue) {
        this.combustionValue = combustionValue;
    }

    public double getRefuelingPrice() {
        return refuelingPrice;
    }

    public void setRefuelingPrice(double refuelingPrice) {
        this.refuelingPrice = refuelingPrice;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
