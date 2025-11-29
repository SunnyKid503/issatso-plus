package com.university.forum.notificationManagement.Repositories;

import com.university.forum.notificationManagement.Models.Notification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {
}
