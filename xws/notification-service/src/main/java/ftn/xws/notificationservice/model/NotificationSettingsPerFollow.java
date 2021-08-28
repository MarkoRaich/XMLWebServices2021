package ftn.xws.notificationservice.model;

import javax.persistence.*;

@Entity
public class NotificationSettingsPerFollow {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_settings_per_follow_generator")
    @SequenceGenerator(name="notification_settings_per_follow_generator", sequenceName = "notification_settings_per_follow_seq", allocationSize=50, initialValue = 100)
    private long id;
    @Column(name = "followId")
    private long followId;
    @Column(name = "profile")
    private String profile;
    @Column(name = "notifyOnMessage")
    private boolean notifyOnMessage;
    @Column(name = "notifyOnPost")
    private boolean notifyOnPost;
    @Column(name = "notifyOnStory")
    private boolean notifyOnStory;
    @Column(name = "notifyOnComment")
    private boolean notifyOnComment;
    @Column(name = "notifyOnRating")
    private boolean notifyOnRating;

    public NotificationSettingsPerFollow(long followId, String profile, boolean notifyOnMessage, boolean notifyOnPost, boolean notifyOnStory, boolean notifyOnComment, boolean notifyOnRating) {
        this.followId = followId;
        this.profile = profile;
        this.notifyOnMessage = notifyOnMessage;
        this.notifyOnPost = notifyOnPost;
        this.notifyOnStory = notifyOnStory;
        this.notifyOnComment = notifyOnComment;
        this.notifyOnRating = notifyOnRating;
    }

    public NotificationSettingsPerFollow() {

    }

    public NotificationSettingsPerFollow(long followId, String profile) {
        this.followId = followId;
        this.profile = profile;
        this.notifyOnMessage = true;
        this.notifyOnPost = false;
        this.notifyOnStory = false;
        this.notifyOnComment = true;
        this.notifyOnRating = true;
    }

    public void setNotifyOnMessage(boolean notifyOnMessage) {
        this.notifyOnMessage = notifyOnMessage;
    }

    public void setNotifyOnPost(boolean notifyOnPost) {
        this.notifyOnPost = notifyOnPost;
    }

    public void setNotifyOnStory(boolean notifyOnStory) {
        this.notifyOnStory = notifyOnStory;
    }

    public void setNotifyOnComment(boolean notifyOnComment) {
        this.notifyOnComment = notifyOnComment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) { this.id = id; }

    public long getFollowId() {
        return followId;
    }

    public boolean isNotifyOnMessage() {
        return notifyOnMessage;
    }

    public boolean isNotifyOnPost() {
        return notifyOnPost;
    }

    public boolean isNotifyOnStory() {
        return notifyOnStory;
    }

    public boolean isNotifyOnComment() {
        return notifyOnComment;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public boolean isNotifyOnRating() {
        return notifyOnRating;
    }

}