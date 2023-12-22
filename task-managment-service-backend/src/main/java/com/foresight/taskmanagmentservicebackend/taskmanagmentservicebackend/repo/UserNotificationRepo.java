package com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.repo;

import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.collection.UserNotification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNotificationRepo extends MongoRepository<UserNotification,String> {
}
