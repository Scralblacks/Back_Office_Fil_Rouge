package Entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Task {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_task")
    private Long idTask;
    @Basic
    @Column(name = "name_task")
    private String nameTask;
    @Basic
    @Column(name = "date_created")
    private LocalDate dateCreated;
    @Basic
    @Column(name = "date_task_start")
    private Timestamp dateTaskStart;
    @Basic
    @Column(name = "date_task_end")
    private Timestamp dateTaskEnd;
    @Basic
    @Column(name = "description")
    private String description;

    @OneToMany(targetEntity = Event.class, mappedBy = "task", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Event> events = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_planning", referencedColumnName = "id_planning", nullable = false)
    private Planning planning;

    public Long getIdTask() {
        return idTask;
    }

    public void setIdTask(Long idTask) {
        this.idTask = idTask;
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Timestamp getDateTaskStart() {
        return dateTaskStart;
    }

    public void setDateTaskStart(Timestamp dateTaskStart) {
        this.dateTaskStart = dateTaskStart;
    }

    public Timestamp getDateTaskEnd() {
        return dateTaskEnd;
    }

    public void setDateTaskEnd(Timestamp dateTaskEnd) {
        this.dateTaskEnd = dateTaskEnd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (idTask != task.idTask) return false;
        if (nameTask != null ? !nameTask.equals(task.nameTask) : task.nameTask != null) return false;
        if (dateCreated != null ? !dateCreated.equals(task.dateCreated) : task.dateCreated != null) return false;
        if (dateTaskStart != null ? !dateTaskStart.equals(task.dateTaskStart) : task.dateTaskStart != null)
            return false;
        if (dateTaskEnd != null ? !dateTaskEnd.equals(task.dateTaskEnd) : task.dateTaskEnd != null) return false;
        if (description != null ? !description.equals(task.description) : task.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idTask.intValue();
        result = 31 * result + (nameTask != null ? nameTask.hashCode() : 0);
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        result = 31 * result + (dateTaskStart != null ? dateTaskStart.hashCode() : 0);
        result = 31 * result + (dateTaskEnd != null ? dateTaskEnd.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    public Collection<Event> getEventsByIdTask() {
        return events;
    }

    public void setEvents(List<Event> eventsByIdTask) {
        this.events = eventsByIdTask;
    }

    public Planning getPlanningByIdPlanning() {
        return planning;
    }

    public void setPlanning(Planning planningByIdPlanning) {
        this.planning = planningByIdPlanning;
    }


}
