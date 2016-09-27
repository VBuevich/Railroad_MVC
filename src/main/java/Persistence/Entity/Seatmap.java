package Persistence.Entity;

import javax.persistence.*;

/**
 * Created by VBuevich on 19.09.2016.
 */
@Entity
@Table(name="seatmap")
public class Seatmap {
    private Integer seatmapId;
    private Integer trainNumber;
    private String seat;
    private Integer passengerOwner;
    private Train trainByTrainNumber;
    private Passenger passengerByPassengerOwner;

    public void setSeatmapId(int seatmapId) {
        this.seatmapId = seatmapId;
    }

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) // SEQUENCE
    @Column(name = "seatmap_id", nullable = false)
    public Integer getSeatmapId() {
        return seatmapId;
    }

    public void setSeatmapId(Integer seatmapId) {
        this.seatmapId = seatmapId;
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
    @Column(name = "seat", nullable = false, length = 4)
    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    @Basic
    @Column(name = "passenger_owner", nullable = true, insertable = false, updatable = false) // IU)
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

        if (seatmapId != null ? !seatmapId.equals(seatmap.seatmapId) : seatmap.seatmapId != null) return false;
        if (trainNumber != null ? !trainNumber.equals(seatmap.trainNumber) : seatmap.trainNumber != null) return false;
        if (seat != null ? !seat.equals(seatmap.seat) : seatmap.seat != null) return false;
        if (passengerOwner != null ? !passengerOwner.equals(seatmap.passengerOwner) : seatmap.passengerOwner != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = seatmapId != null ? seatmapId.hashCode() : 0;
        result = 31 * result + (trainNumber != null ? trainNumber.hashCode() : 0);
        result = 31 * result + (seat != null ? seat.hashCode() : 0);
        result = 31 * result + (passengerOwner != null ? passengerOwner.hashCode() : 0);
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
    @JoinColumn(name = "passenger_owner", referencedColumnName = "passenger_id")
    public Passenger getPassengerByPassengerOwner() {
        return passengerByPassengerOwner;
    }

    public void setPassengerByPassengerOwner(Passenger passengerByPassengerOwner) {
        this.passengerByPassengerOwner = passengerByPassengerOwner;
    }
}
