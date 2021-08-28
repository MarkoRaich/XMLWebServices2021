package ftn.xws.notificationservice.model;

public enum NotificationType {
    MESSAGE,
    COMMENT,
    POST,
    STORY,
    RATING,
    NEW_FOLLOW,
    NEW_FOLLOW_REQUEST,
    FOLLOW_REQUEST_ACCEPTED;

    public String getContent() {
        switch (this) {
            case MESSAGE:
                return "sent you a message";
            case COMMENT:
                return "commented on your post";
            case POST:
                return "shared a post";
            case STORY:
                return "shared a story";
            case RATING:
                return "rated your post";
            case NEW_FOLLOW:
                return "started following you";
            case NEW_FOLLOW_REQUEST:
                return "requested to follow you";
            case FOLLOW_REQUEST_ACCEPTED:
                return "accepted your follow request";
            default:
                return "";
        }
    }
}
