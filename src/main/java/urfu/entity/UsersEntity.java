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
@Table(name = "users", schema = "public", catalog = "project")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersEntity {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "id", nullable = false)
  private Integer id;

  @Basic
  @Column(name = "tg_id", nullable = false)
  private Integer tgId;

  @Basic
  @Column(name = "chat_id", nullable = false)
  private Integer chatId;

  @Basic
  @CreationTimestamp
  @Column(name = "created_at", nullable = false)
  private Timestamp createdAt;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UsersEntity that = (UsersEntity) o;
    return Objects.equals(id, that.id)
        && Objects.equals(tgId, that.tgId)
        && Objects.equals(chatId, that.chatId)
        && Objects.equals(createdAt, that.createdAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, tgId, createdAt);
  }
}
