package ftn.xws.notificationservice.model;

import javax.persistence.*;

@Entity
public class NotificationSettingsPerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_settings_per_profile_generator")
    @SequenceGenerator(name="notification_settings_per_profile_generator", sequenceName = "notification_settings_per_profile_seq", allocationSize=50, initialValue = 100)
    private long id;
    @Column(name = "profile")
    private String profile;
    @Column(name = "notifyOnFollow")
    private boolean notifyOnFollw;
    @Column(name = "notifyOnAcceptedFollowRequest")
    private boolean notifyOnAcceptedFollowRequest;
    @Column(name = "notifyOnNonFollowedMessage")
    private boolean notifyOnNonFollowedMessage;
    @Column(name = "notifyOnNonFollowedComment")
    private boolean notifyOnNonFollowedComment;
    @Column(name = "notifyOnNonFollowedRating")
    private boolean notifyOnNonFollowedRating;

    public NotificationSettingsPerProfile() {

    }

    public NotificationSettingsPerProfile(String profile) {
        this.profile = profile;
        this.notifyOnFollw = true;
        this.notifyOnAcceptedFollowRequest = true;
        this.notifyOnNonFollowedMessage = true;
        this.notifyOnNonFollowedComment = true;
        this.notifyOnNonFollowedRating = true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) { this.id = id; }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) { this.profile = profile; }

    public boolean isNotifyOnFollw() {
        return notifyOnFollw;
    }

    public boolean isNotifyOnAcceptedFollowRequest() {
        return notifyOnAcceptedFollowRequest;
    }

    public boolean isNotifyOnNonFollowedMessage() {
        return notifyOnNonFollowedMessage;
    }

    public boolean isNotifyOnNonFollowedComment() {
        return notifyOnNonFollowedComment;
    }

    public boolean isNotifyOnNonFollowedRating() {
        return notifyOnNonFollowedRating;
    }
}
