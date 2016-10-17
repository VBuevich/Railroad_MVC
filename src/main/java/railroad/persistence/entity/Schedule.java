package railroad.persistence.entity;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Time;

/**
 * @author vbuevich
 *
 * Hibernate entity class
 */
@Entity
@Table(name="schedule")
public class Schedule {
    @NotNull
    private int scheduleId;
    @NotEmpty @Size(min=2, max=20)
    private String stationName;
    @NotNull @Min(1)
    private int trainNumber;
    @NotNull @DateTimeFormat(pattern="HH:mm")
    private Time time;
    private Station stationByStationName;
    private Train trainByTrainNumber;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) // SEQUENCE
    @Column(name = "schedule_id")
    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    @Basic
    @Column(name = "station_name")
    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
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
    @Column(name = "time")
    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Schedule schedule = (Schedule) o;

        if (scheduleId != schedule.scheduleId) return false;
        if (trainNumber != schedule.trainNumber) return false;
        if (stationName != null ? !stationName.equals(schedule.stationName) : schedule.stationName != null)
            return false;
        if (time != null ? !time.equals(schedule.time) : schedule.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = scheduleId;
        result = 31 * result + (stationName != null ? stationName.hashCode() : 0);
        result = 31 * result + trainNumber;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "station_name", referencedColumnName = "station_name", nullable = false, insertable = false, updatable = false) // IU
    public Station getStationByStationName() {
        return stationByStationName;
    }

    public void setStationByStationName(Station stationByStationName) {
        this.stationByStationName = stationByStationName;
    }

    @ManyToOne
    @JoinColumn(name = "train_number", referencedColumnName = "train_number", nullable = false, insertable = false, updatable = false) // IU
    public Train getTrainByTrainNumber() {
        return trainByTrainNumber;
    }

    public void setTrainByTrainNumber(Train trainByTrainNumber) {
        this.trainByTrainNumber = trainByTrainNumber;
    }
}
