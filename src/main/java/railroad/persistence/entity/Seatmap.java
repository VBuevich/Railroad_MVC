package railroad.persistence.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author vbuevich
 *
 * Hibernate entity class
 */
@Entity
@Table(name="seatmap")
public class Seatmap {
    @NotNull
    private int seatmapId;
    @NotNull @Min(1)
    private int trainNumber;
    @NotEmpty @Size(min=1, max=4)
    private String seat;
    @Min(1)
    private Integer passengerOwner;
    private Train trainByTrainNumber;
    private UserDetails userDetailsByPassengerOwner;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) // SEQUENCE
    @Column(name = "seatmap_id")
    public int getSeatmapId() {
        return seatmapId;
    }

    public void setSeatmapId(int seatmapId) {
        this.seatmapId = seatmapId;
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
    @Column(name = "seat")
    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    @Basic
    @Column(name = "passenger_owner")
    public Integer getPassengerOwner() {
        return passengerOwner;
    }

    public void setPassengerOwner(Integer passengerOwner) {
        this.passengerOwner = passengerOwner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Seatmap seatmap = (Seatmap) o;

        if (seatmapId != seatmap.seatmapId) return false;
        if (trainNumber != seatmap.trainNumber) return false;
        if (seat != null ? !seat.equals(seatmap.seat) : seatmap.seat != null) return false;
        if (passengerOwner != null ? !passengerOwner.equals(seatmap.passengerOwner) : seatmap.passengerOwner != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = seatmapId;
        result = 31 * result + trainNumber;
        result = 31 * result + (seat != null ? seat.hashCode() : 0);
        result = 31 * result + (passengerOwner != null ? passengerOwner.hashCode() : 0);
        return result;
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
    @JoinColumn(name = "passenger_owner", referencedColumnName = "user_id", insertable = false, updatable = false) // IU
    public UserDetails getUserDetailsByPassengerOwner() {
        return userDetailsByPassengerOwner;
    }

    public void setUserDetailsByPassengerOwner(UserDetails userDetailsByPassengerOwner) {
        this.userDetailsByPassengerOwner = userDetailsByPassengerOwner;
    }
}
