package railroad.persistence.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;

/**
 * @author vbuevich
 *
 * Hibernate entity class
 */
@Entity
@Table(name="station")
public class Station {
    @NotEmpty @Size(min=2, max=20)
    private String stationName;
    private Collection<Schedule> schedulesByStationName;
    private Collection<Ticket> ticketsByStationName;
    private Collection<Ticket> ticketsByStationName_0;

    @Id
    @Column(name = "station_name")
    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Station station = (Station) o;

        if (stationName != null ? !stationName.equals(station.stationName) : station.stationName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return stationName != null ? stationName.hashCode() : 0;
    }

    @OneToMany(mappedBy = "stationByStationName")
    public Collection<Schedule> getSchedulesByStationName() {
        return schedulesByStationName;
    }

    public void setSchedulesByStationName(Collection<Schedule> schedulesByStationName) {
        this.schedulesByStationName = schedulesByStationName;
    }

    @OneToMany(mappedBy = "stationByDepartureStation")
    public Collection<Ticket> getTicketsByStationName() {
        return ticketsByStationName;
    }

    public void setTicketsByStationName(Collection<Ticket> ticketsByStationName) {
        this.ticketsByStationName = ticketsByStationName;
    }

    @OneToMany(mappedBy = "stationByArrivalStation")
    public Collection<Ticket> getTicketsByStationName_0() {
        return ticketsByStationName_0;
    }

    public void setTicketsByStationName_0(Collection<Ticket> ticketsByStationName_0) {
        this.ticketsByStationName_0 = ticketsByStationName_0;
    }
}
