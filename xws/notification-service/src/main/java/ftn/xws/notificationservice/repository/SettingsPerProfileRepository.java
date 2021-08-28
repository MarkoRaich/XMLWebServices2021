package ftn.xws.notificationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.xws.notificationservice.model.NotificationSettingsPerProfile;

import java.util.Optional;

@Repository
public interface SettingsPerProfileRepository extends JpaRepository<NotificationSettingsPerProfile, Long> {

    Optional<NotificationSettingsPerProfile> findByProfile(String profile);
}
