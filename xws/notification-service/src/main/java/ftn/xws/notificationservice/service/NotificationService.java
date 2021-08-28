package ftn.xws.notificationservice.service;



import java.util.List;

import ftn.xws.notificationservice.dto.NewNotificationDTO;
import ftn.xws.notificationservice.dto.NotificationDTO;

public interface NotificationService {

    List<NotificationDTO> getAll(String requestedBy);

    void create(NewNotificationDTO dto);
}
