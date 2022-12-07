package urfu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "notifiers", schema = "public", catalog = "project")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotifiersEntity
{
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "task_id", nullable = false)
    private Integer taskId;

    @Basic
    @Column(name = "notify_at", nullable = false)
    private Timestamp notifyAt;

    @Basic
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotifiersEntity that = (NotifiersEntity) o;
        return Objects.equals(taskId, that.taskId) && Objects.equals(notifyAt, that.notifyAt) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(taskId, notifyAt, createdAt);
    }
}
