package Railroad;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by VBuevich on 01.09.2016.
 */
@Entity
public class Train {
    private Integer trainNumber;
    private Integer seats;
    private Collection<Schedule> schedulesByTrainNumber;
    private Collection<Ticket> ticketsByTrainNumber;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Train train = (Train) o;

        if (trainNumber != null ? !trainNumber.equals(train.trainNumber) : train.trainNumber != null) return false;
        if (seats != null ? !seats.equals(train.seats) : train.seats != null) return false;

        return true;
    }

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
}
