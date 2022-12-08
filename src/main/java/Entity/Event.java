package Entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
public class Event {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_event")
    private Long idEvent;
    @Basic
    @Column(name = "date_created")
    private LocalDate dateCreated;


    @ManyToOne
    @JoinColumn(name = "id_action", referencedColumnName = "id_action", nullable = false)
    private Action action;

    @ManyToOne
    @JoinColumn(name="id_task", referencedColumnName = "id_task", nullable = false)
    private Task task;

    @ManyToOne
    @JoinColumn(name="id_planning", referencedColumnName = "id_planning", nullable = false)
    private Planning planning;

    public Long getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Long idEvent) {
        this.idEvent = idEvent;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (idEvent != event.idEvent) return false;
        if (dateCreated != null ? !dateCreated.equals(event.dateCreated) : event.dateCreated != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idEvent.intValue();
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        return result;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Planning getPlanning() {
        return planning;
    }

    public void setPlanning(Planning planning) {
        this.planning = planning;
    }
}
