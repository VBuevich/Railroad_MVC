package railroad.persistence.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * @author vbuevich
 *
 * Hibernate entity class
 */
@Entity
@Table(name="train")
public class Train implements Serializable {
    private int trainNumber;
    private String templateId;
    private Collection<Schedule> schedulesByTrainNumber;
    private Collection<Ticket> ticketsByTrainNumber;
    private Collection<TemplateTrain> templatesByTemplateId;
    private Collection<Seatmap> seatmapsByTrainNumber;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) // SEQUENCE
    @Column(name = "train_number")
    public int getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    @Basic
    @Column(name = "template_id")
    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Train train = (Train) o;

        if (trainNumber != train.trainNumber) return false;
        if (templateId != null ? !templateId.equals(train.templateId) : train.templateId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = trainNumber;
        result = 31 * result + (templateId != null ? templateId.hashCode() : 0);
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

    @OneToMany(mappedBy = "trainByTemplateId")
    public Collection<TemplateTrain> getTemplatesByTemplateId() {
        return templatesByTemplateId;
    }

    public void setTemplatesByTemplateId(Collection<TemplateTrain> templatesByTemplateId) {
        this.templatesByTemplateId = templatesByTemplateId;
    }

    @OneToMany(mappedBy = "trainByTrainNumber")
    public Collection<Seatmap> getSeatmapsByTrainNumber() {
        return seatmapsByTrainNumber;
    }

    public void setSeatmapsByTrainNumber(Collection<Seatmap> seatmapsByTrainNumber) {
        this.seatmapsByTrainNumber = seatmapsByTrainNumber;
    }
}
