package Service;

import java.sql.Time;

/**
 * Created by VBuevich on 11.09.2016.
 */
public class Offer {
    private Integer trainNumber;
    private String departureStation;
    private String arrivalStation;
    private Time departureTime;
    private Time arrivalTime;

    public Offer() {};

    public Integer getTrainNumber() { return trainNumber; }

    public void setTrainNumber(Integer trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public Time getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
    }

    public Time getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Time arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
