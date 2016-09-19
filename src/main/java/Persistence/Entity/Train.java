package Persistence.Entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * @author vbuevich
 *
 * Hibernate entity class
 */
@Entity
public class Train {
    private Integer trainNumber;
    private Integer seats;
    private Collection<Schedule> schedulesByTrainNumber;
    private Collection<Ticket> ticketsByTrainNumber;
    private Collection<Seatmap> seatmapsByTrainNumber;

    @Id
    @Column(name = "train_number", nullable = false)
    public Integer getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(Integer trainNumber) {
        this.trainNumber = trainNumber;
    }

    @Basic
    @Column(name = "seats", nullable = false)
    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
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

        Train train = (Train) o;

        if (trainNumber != null ? !trainNumber.equals(train.trainNumber) : train.trainNumber != null) return false;
        if (seats != null ? !seats.equals(train.seats) : train.seats != null) return false;

        return true;
    }

    /**
     * @return hashcode
     */
    @Override
    public int hashCode() {
        int result = trainNumber != null ? trainNumber.hashCode() : 0;
        result = 31 * result + (seats != null ? seats.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "trainByTrainNumber")
    public Collection<Schedule> getSchedulesByTrainNumber() {
        return schedulesByTrainNumber;
    }

    public void setSchedulesByTrainNumber(Collection<Schedule> schedulesByTrainNumber) {
        this.schedulesByTrainNumber = schedulesByTrainNumber;
    }

    @OneToMany(mappedBy = "trainByTrainNumber")
    public Collection<Ticket> getTicketsByTrainNumber() {
        return ticketsByTrainNumber;
    }

    public void setTicketsByTrainNumber(Collection<Ticket> ticketsByTrainNumber) {
        this.ticketsByTrainNumber = ticketsByTrainNumber;
    }

    @OneToMany(mappedBy = "trainByTrainNumber")
    public Collection<Seatmap> getSeatmapsByTrainNumber() {
        return seatmapsByTrainNumber;
    }

    public void setSeatmapsByTrainNumber(Collection<Seatmap> seatmapsByTrainNumber) {
        this.seatmapsByTrainNumber = seatmapsByTrainNumber;
    }
}
