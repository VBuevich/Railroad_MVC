package railroad.persistence.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

/**
 * @author vbuevich
 *
 * Hibernate entity class
 */
@Entity
@Table(name="train")
public class Train {
    @NotNull
    private Integer trainNumber;
    private Collection<Schedule> schedulesByTrainNumber;
    private Collection<Ticket> ticketsByTrainNumber;
    private Collection<Seatmap> seatmapsByTrainNumber;
    @NotEmpty @Size(min=1, max=3)
    private String templateId;

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    @Id
    @Column(name = "train_number", nullable = false)
    public Integer getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(Integer trainNumber) {
        this.trainNumber = trainNumber;
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

        return true;
    }

    /**
     * @return hashcode
     */
    @Override
    public int hashCode() {
        int result = trainNumber != null ? trainNumber.hashCode() : 0;
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

    @Basic
    @Column(name = "template_id")
    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }
}
