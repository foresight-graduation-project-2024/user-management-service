package com.foresight.usermanagementservicebackend;




import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserManagementServiceBackendApplication {
    public static void main(String[] args) {
        //Dotenv.configure().load();
        SpringApplication.run(UserManagementServiceBackendApplication.class, args);
    }

}
