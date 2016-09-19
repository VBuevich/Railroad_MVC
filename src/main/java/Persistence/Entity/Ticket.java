package Persistence.Entity;

import javax.persistence.*;

/**
 * @author vbuevich
 *
 * Hibernate entity class
 */
@Entity
public class Ticket {
    private Integer ticketId;
    private Integer passengerId;
    private Integer trainNumber;
    private String departureStation;
    private String arrivalStation;
    private Boolean isOneWay;
    private Passenger passengerByPassengerId;
    private Train trainByTrainNumber;
    private Station stationByDepartureStation;
    private Station stationByArrivalStation;
    private String seat;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) // SEQUENCE
    @Column(name = "ticket_id", nullable = false)
    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    @Basic
    @Column(name = "passenger_id", nullable = false, insertable = false, updatable = false) // IU
    public Integer getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Integer passengerId) {
        this.passengerId = passengerId;
    }

    @Basic
    @Column(name = "train_number", nullable = false, insertable = false, updatable = false) // IU
    public Integer getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(Integer trainNumber) {
        this.trainNumber = trainNumber;
    }

    @Basic
    @Column(name = "departure_station", nullable = false, length = 20, insertable = false, updatable = false) // IU
    public String getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    @Basic
    @Column(name = "arrival_station", nullable = false, length = 20, insertable = false, updatable = false) // IU
    public String getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    @Basic
    @Column(name = "is_one_way", nullable = false)
    public Boolean getOneWay() {
        return isOneWay;
    }

    public void setOneWay(Boolean oneWay) {
        isOneWay = oneWay;
    }

    /**
     *
     * @param o Object to compare
     * @return true if equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (ticketId != null ? !ticketId.equals(ticket.ticketId) : ticket.ticketId != null) return false;
        if (passengerId != null ? !passengerId.equals(ticket.passengerId) : ticket.passengerId != null) return false;
        if (trainNumber != null ? !trainNumber.equals(ticket.trainNumber) : ticket.trainNumber != null) return false;
        if (departureStation != null ? !departureStation.equals(ticket.departureStation) : ticket.departureStation != null)
            return false;
        if (arrivalStation != null ? !arrivalStation.equals(ticket.arrivalStation) : ticket.arrivalStation != null)
            return false;
        if (isOneWay != null ? !isOneWay.equals(ticket.isOneWay) : ticket.isOneWay != null) return false;

        return true;
    }

    /**
     * @return hashcode
     */
    @Override
    public int hashCode() {
        int result = ticketId != null ? ticketId.hashCode() : 0;
        result = 31 * result + (passengerId != null ? passengerId.hashCode() : 0);
        result = 31 * result + (trainNumber != null ? trainNumber.hashCode() : 0);
        result = 31 * result + (departureStation != null ? departureStation.hashCode() : 0);
        result = 31 * result + (arrivalStation != null ? arrivalStation.hashCode() : 0);
        result = 31 * result + (isOneWay != null ? isOneWay.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "passenger_id", referencedColumnName = "passenger_id", nullable = false)
    public Passenger getPassengerByPassengerId() {
        return passengerByPassengerId;
    }

    public void setPassengerByPassengerId(Passenger passengerByPassengerId) {
        this.passengerByPassengerId = passengerByPassengerId;
    }

    @ManyToOne
    @JoinColumn(name = "train_number", referencedColumnName = "train_number", nullable = false)
    public Train getTrainByTrainNumber() {
        return trainByTrainNumber;
    }

    public void setTrainByTrainNumber(Train trainByTrainNumber) {
        this.trainByTrainNumber = trainByTrainNumber;
    }

    @ManyToOne
    @JoinColumn(name = "departure_station", referencedColumnName = "station_name", nullable = false)
    public Station getStationByDepartureStation() {
        return stationByDepartureStation;
    }

    public void setStationByDepartureStation(Station stationByDepartureStation) {
        this.stationByDepartureStation = stationByDepartureStation;
    }

    @ManyToOne
    @JoinColumn(name = "arrival_station", referencedColumnName = "station_name", nullable = false)
    public Station getStationByArrivalStation() {
        return stationByArrivalStation;
    }

    public void setStationByArrivalStation(Station stationByArrivalStation) {
        this.stationByArrivalStation = stationByArrivalStation;
    }

    @Basic
    @Column(name = "seat", nullable = false, length = 4)
    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }
}
