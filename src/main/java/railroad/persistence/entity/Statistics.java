package railroad.persistence.entity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * @author vbuevich
 *
 * Hibernate entity class
 */
@Entity
public class Statistics {
    private int statisticsId;
    private Timestamp datetime;
    private String passengerName;
    private String passengerSurname;
    private Date passengerDob;
    private String passengerEmail;
    private int trainNumber;
    private String trainType;
    private String departureStation;
    private String arrivalStation;
    private String seat;
    private Boolean isOneWay;
    private Date departureDate;
    private Time departureTime;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) // SEQUENCE
    @Column(name = "statistics_id")
    public int getStatisticsId() {
        return statisticsId;
    }

    public void setStatisticsId(int statisticsId) {
        this.statisticsId = statisticsId;
    }

    @Basic
    @Column(name = "datetime")
    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    @Basic
    @Column(name = "passenger_name")
    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    @Basic
    @Column(name = "passenger_surname")
    public String getPassengerSurname() {
        return passengerSurname;
    }

    public void setPassengerSurname(String passengerSurname) {
        this.passengerSurname = passengerSurname;
    }

    @Basic
    @Column(name = "passenger_dob")
    public Date getPassengerDob() {
        return passengerDob;
    }

    public void setPassengerDob(Date passengerDob) {
        this.passengerDob = passengerDob;
    }

    @Basic
    @Column(name = "passenger_email")
    public String getPassengerEmail() {
        return passengerEmail;
    }

    public void setPassengerEmail(String passengerEmail) {
        this.passengerEmail = passengerEmail;
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
    @Column(name = "train_type")
    public String getTrainType() {
        return trainType;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
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

    public void setIsOneWay(Boolean isOneWay) {
        this.isOneWay = isOneWay;
    }

    @Basic
    @Column(name = "departure_date")
    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    @Basic
    @Column(name = "departure_time")
    public Time getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
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

        Statistics that = (Statistics) o;

        if (statisticsId != that.statisticsId) return false;
        if (trainNumber != that.trainNumber) return false;
        if (isOneWay != that.isOneWay) return false;
        if (datetime != null ? !datetime.equals(that.datetime) : that.datetime != null) return false;
        if (passengerName != null ? !passengerName.equals(that.passengerName) : that.passengerName != null)
            return false;
        if (passengerSurname != null ? !passengerSurname.equals(that.passengerSurname) : that.passengerSurname != null)
            return false;
        if (passengerDob != null ? !passengerDob.equals(that.passengerDob) : that.passengerDob != null) return false;
        if (passengerEmail != null ? !passengerEmail.equals(that.passengerEmail) : that.passengerEmail != null)
            return false;
        if (trainType != null ? !trainType.equals(that.trainType) : that.trainType != null) return false;
        if (departureStation != null ? !departureStation.equals(that.departureStation) : that.departureStation != null)
            return false;
        if (arrivalStation != null ? !arrivalStation.equals(that.arrivalStation) : that.arrivalStation != null)
            return false;
        if (seat != null ? !seat.equals(that.seat) : that.seat != null) return false;
        if (departureDate != null ? !departureDate.equals(that.departureDate) : that.departureDate != null)
            return false;
        if (departureTime != null ? !departureTime.equals(that.departureTime) : that.departureTime != null)
            return false;

        return true;
    }

    /**
     * @return hashcode
     */
    @Override
    public int hashCode() {
        int result = statisticsId;
        result = 31 * result + (datetime != null ? datetime.hashCode() : 0);
        result = 31 * result + (passengerName != null ? passengerName.hashCode() : 0);
        result = 31 * result + (passengerSurname != null ? passengerSurname.hashCode() : 0);
        result = 31 * result + (passengerDob != null ? passengerDob.hashCode() : 0);
        result = 31 * result + (passengerEmail != null ? passengerEmail.hashCode() : 0);
        result = 31 * result + trainNumber;
        result = 31 * result + (trainType != null ? trainType.hashCode() : 0);
        result = 31 * result + (departureStation != null ? departureStation.hashCode() : 0);
        result = 31 * result + (arrivalStation != null ? arrivalStation.hashCode() : 0);
        result = 31 * result + (seat != null ? seat.hashCode() : 0);
        result = 31 * result + (isOneWay != null ? isOneWay.hashCode() : 0);
        result = 31 * result + (departureDate != null ? departureDate.hashCode() : 0);
        result = 31 * result + (departureTime != null ? departureTime.hashCode() : 0);
        return result;
    }
}
