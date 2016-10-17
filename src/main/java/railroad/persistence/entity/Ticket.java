package railroad.persistence.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author vbuevich
 *
 * Hibernate entity class
 */
@Entity
@Table(name="ticket")
public class Ticket implements Serializable {
    @NotNull
    private int ticketId;
    @NotNull @Min(1)
    private int passengerId;
    @NotNull @Min(1)
    private int trainNumber;
    @NotEmpty @Size(min=2, max=20)
    private String departureStation;
    @NotEmpty @Size(min=2, max=20)
    private String arrivalStation;
    @NotEmpty @Size(min=1, max=4)
    private String seat;
    @NotNull
    private Boolean isOneWay;
    private UserDetails userDetailsByPassengerId;
    private Train trainByTrainNumber;
    private Station stationByDepartureStation;
    private Station stationByArrivalStation;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) // SEQUENCE
    @Column(name = "ticket_id")
    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    @Basic
    @Column(name = "passenger_id")
    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    @Basic
    @Column(name = "train_number")
    public int getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    @Basic
    @Column(name = "departure_station")
    public String getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    @Basic
    @Column(name = "arrival_station")
    public String getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    @Basic
    @Column(name = "seat")
    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    @Basic
    @Column(name = "is_one_way")
    public Boolean getIsOneWay() {
        return isOneWay;
    }

    public void setIsOneWay(Boolean oneWay) {
        isOneWay = oneWay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (ticketId != ticket.ticketId) return false;
        if (passengerId != ticket.passengerId) return false;
        if (trainNumber != ticket.trainNumber) return false;
        if (departureStation != null ? !departureStation.equals(ticket.departureStation) : ticket.departureStation != null)
            return false;
        if (arrivalStation != null ? !arrivalStation.equals(ticket.arrivalStation) : ticket.arrivalStation != null)
            return false;
        if (seat != null ? !seat.equals(ticket.seat) : ticket.seat != null) return false;
        if (isOneWay != null ? !isOneWay.equals(ticket.isOneWay) : ticket.isOneWay != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ticketId;
        result = 31 * result + passengerId;
        result = 31 * result + trainNumber;
        result = 31 * result + (departureStation != null ? departureStation.hashCode() : 0);
        result = 31 * result + (arrivalStation != null ? arrivalStation.hashCode() : 0);
        result = 31 * result + (seat != null ? seat.hashCode() : 0);
        result = 31 * result + (isOneWay != null ? isOneWay.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "passenger_id", referencedColumnName = "user_id", nullable = false, insertable = false, updatable = false) // IU
    public UserDetails getUserDetailsByPassengerId() {
        return userDetailsByPassengerId;
    }

    public void setUserDetailsByPassengerId(UserDetails userDetailsByPassengerId) {
        this.userDetailsByPassengerId = userDetailsByPassengerId;
    }

    @ManyToOne
    @JoinColumn(name = "train_number", referencedColumnName = "train_number", nullable = false, insertable = false, updatable = false) // IU
    public Train getTrainByTrainNumber() {
        return trainByTrainNumber;
    }

    public void setTrainByTrainNumber(Train trainByTrainNumber) {
        this.trainByTrainNumber = trainByTrainNumber;
    }

    @ManyToOne
    @JoinColumn(name = "departure_station", referencedColumnName = "station_name", nullable = false, insertable = false, updatable = false) // IU
    public Station getStationByDepartureStation() {
        return stationByDepartureStation;
    }

    public void setStationByDepartureStation(Station stationByDepartureStation) {
        this.stationByDepartureStation = stationByDepartureStation;
    }

    @ManyToOne
    @JoinColumn(name = "arrival_station", referencedColumnName = "station_name", nullable = false, insertable = false, updatable = false) // IU
    public Station getStationByArrivalStation() {
        return stationByArrivalStation;
    }

    public void setStationByArrivalStation(Station stationByArrivalStation) {
        this.stationByArrivalStation = stationByArrivalStation;
    }
}
