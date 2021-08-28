package ftn.xws.notificationservice.service;

import java.util.List;

import ftn.xws.notificationservice.model.NotificationSettingsPerFollow;
import ftn.xws.notificationservice.model.NotificationSettingsPerProfile;

public interface SettingsService {

    void createSettingsPerProfile(String profile);

    void createSettingsPerFollow(long followId, String profile);

    void deleteSettingsPerFollow(long followId);

    void update(NotificationSettingsPerProfile settings, String requestedBy);

    void update(NotificationSettingsPerFollow settings, String requestedBy);

    List<NotificationSettingsPerFollow> getByProfile(String profile);

    NotificationSettingsPerProfile get(String profile);
}
