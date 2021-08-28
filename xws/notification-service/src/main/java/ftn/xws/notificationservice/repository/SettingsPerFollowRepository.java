package ftn.xws.notificationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.xws.notificationservice.model.NotificationSettingsPerFollow;

import java.util.Optional;

@Repository
public interface SettingsPerFollowRepository extends JpaRepository<NotificationSettingsPerFollow, Long> {

    Optional<NotificationSettingsPerFollow> findByFollowId(long followId);
}

