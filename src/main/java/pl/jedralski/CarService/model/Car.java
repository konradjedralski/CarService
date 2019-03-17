package pl.jedralski.CarService.model;

public class Car {

    private Long carID;
    private int year;
    private String make;
    private String model;
    private String carPhoto;


    public Car() {
    }

    public Car(Long id, int year, String make, String model) {
        this.carID = id;
        this.year = year;
        this.make = make;
        this.model = model;
    }

    public Car(int year, String make, String model) {
        this.year = year;
        this.make = make;
        this.model = model;
    }

    public Car(int year, String make, String model, String carPhoto) {
        this.year = year;
        this.make = make;
        this.model = model;
        this.carPhoto = carPhoto;
    }

    public Long getCarID() {
        return carID;
    }

    public void setCarID(Long id) {
        this.carID = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCarPhoto() {
        return carPhoto;
    }

    public void setCarPhoto(String photo) {
        this.carPhoto = photo;
    }
}
