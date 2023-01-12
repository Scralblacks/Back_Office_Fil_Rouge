package Entity;

import jakarta.persistence.*;

@Entity(name="Share")
@Table(name="share")
public class Share {

    @EmbeddedId
    private ShareId id;

    @ManyToOne
    @JoinColumn(name = "fk_planning")
    @MapsId("planningId")
    private Planning planning;

    @ManyToOne
    @JoinColumn(name = "fk_user")
    @MapsId("userId")
    private Users users;

    @Basic
    @Column(name = "is_read_only", nullable = false)
    private boolean isReadOnly;

    public Share(){}

    public Share(Planning planning, Users user, boolean isReadOnly){
        this.planning = planning;
        this.users = user;
        this.isReadOnly = isReadOnly;
    }

    public Share(ShareId id, Planning planning, Users user, boolean isReadOnly){
        this.id = id;
        this.planning = planning;
        this.users = user;
        this.isReadOnly = isReadOnly;
    }

    public ShareId getId() {
        return id;
    }

    public void setId(ShareId id) {
        this.id = id;
    }

    public Planning getPlanning() {
        return planning;
    }

    public void setPlanning(Planning planning) {
        this.planning = planning;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public boolean getIsReadOnly() {
        return isReadOnly;
    }

    public void setIsReadOnly(boolean isReadOnly) {
        this.isReadOnly = isReadOnly;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Share share = (Share) o;

        if (isReadOnly != share.isReadOnly) return false;

        return true;
    }
}
