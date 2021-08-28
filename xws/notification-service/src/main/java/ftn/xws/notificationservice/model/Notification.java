package ftn.xws.notificationservice.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_generator")
    @SequenceGenerator(name="notification_generator", sequenceName = "notification_seq", allocationSize=50, initialValue = 100)
    private long id;
    @Column(name = "type")
    private NotificationType type;
    @Column(name = "receiverUsername")
    private String receiverUsername;
    @Column(name = "initiatorUsername")
    private String initiatorUsername;
    @Column(name = "resource")
    private String resource;
    @Column(name = "created")
    private LocalDateTime created;

    public Notification(NotificationType type, String receiverUsername, String initiatorUsername, String resource, LocalDateTime created) {
        this.type = type;
        this.receiverUsername = receiverUsername;
        this.initiatorUsername = initiatorUsername;
        this.resource = resource;
        this.created = created;
    }

    public Notification() { }

    public String getResource() { return resource; }

    public LocalDateTime getCreated() { return created; }

    public String getText() {
        return initiatorUsername + " " + type.getContent();
    }

    public NotificationType getType() { return type; }
}
