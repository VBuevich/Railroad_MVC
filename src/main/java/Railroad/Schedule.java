package Railroad;

import javax.persistence.*;
import java.sql.Time;

/**
 * Created by VBuevich on 01.09.2016.
 */
@Entity
public class Schedule {
    private Integer scheduleId;
    private String stationName;
    private Integer trainNumber;
    private Time time;
    private Train trainByTrainNumber;
    private Station stationByStationName;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) // SEQUENCE
    @Column(name = "schedule_id", nullable = false)
    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    @Basic
    @Column(name = "station_name", nullable = false, length = 20, insertable = false, updatable = false) // IU
    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
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
    @Column(name = "time", nullable = false)
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

        if (scheduleId != null ? !scheduleId.equals(schedule.scheduleId) : schedule.scheduleId != null) return false;
        if (stationName != null ? !stationName.equals(schedule.stationName) : schedule.stationName != null)
            return false;
        if (trainNumber != null ? !trainNumber.equals(schedule.trainNumber) : schedule.trainNumber != null)
            return false;
        if (time != null ? !time.equals(schedule.time) : schedule.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = scheduleId != null ? scheduleId.hashCode() : 0;
        result = 31 * result + (stationName != null ? stationName.hashCode() : 0);
        result = 31 * result + (trainNumber != null ? trainNumber.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
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
    @JoinColumn(name = "station_name", referencedColumnName = "station_name", nullable = false)
    public Station getStationByStationName() {
        return stationByStationName;
    }

    public void setStationByStationName(Station stationByStationName) {
        this.stationByStationName = stationByStationName;
    }
}
