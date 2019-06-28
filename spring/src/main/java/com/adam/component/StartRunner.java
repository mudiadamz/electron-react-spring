package com.adam.component;

import com.adam.model.UserDto;
import com.adam.model.User;
import com.adam.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartRunner implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(StartRunner.class);

    @Autowired
    private UserService userService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        UserDto userDto = new UserDto("admin", "admin", "Adam Mudianto", "mudi.adamz@gmail.com");
        User user = userService.register(userDto);
        logger.info("Init user : "+ user);
    }
}
