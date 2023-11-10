package com.foresight.usermanagementservicebackend;




import com.foresight.usermanagementservicebackend.entity.UserRole;
import com.foresight.usermanagementservicebackend.model.UserCreateRequest;
import com.foresight.usermanagementservicebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserManagementServiceBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserManagementServiceBackendApplication.class, args);
    }

    @Bean
    @Autowired
    CommandLineRunner runner(UserService service)
    {
        return (x)->
                service.addUser(new UserCreateRequest("Abdelrahman","essam","abdo@foresight.com","123456aA@", UserRole.BUSINESS_MANAGER,true));

    }

}
