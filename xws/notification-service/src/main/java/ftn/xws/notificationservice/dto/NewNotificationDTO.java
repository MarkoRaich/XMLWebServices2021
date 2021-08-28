package ftn.xws.notificationservice.dto;

import ftn.xws.notificationservice.model.NotificationType;

public class NewNotificationDTO {
    private NotificationType type;
    private String receiverUsername;
    private String initiatorUsername;
    private String resource;

    public NewNotificationDTO() { }

    public NewNotificationDTO(NotificationType type, String receiverUsername, String initiatorUsername, String resource) {
        this.type = type;
        this.receiverUsername = receiverUsername;
        this.initiatorUsername = initiatorUsername;
        this.resource = resource;
    }

    public NotificationType getType() { return type; }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public String getInitiatorUsername() {
        return initiatorUsername;
    }

    public String getResource() {
        return resource;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public void setInitiatorUsername(String initiatorUsername) {
        this.initiatorUsername = initiatorUsername;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
