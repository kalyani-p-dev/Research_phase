package com.zensar.actuators;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.stereotype.Component;
import java.util.Random;

@Component
public class CustomHealthIndicator extends AbstractHealthIndicator {
    @Override
    protected void doHealthCheck(Builder builder) throws Exception {
        Random random = new Random();
        int randomNo = random.nextInt(100);
        if(randomNo%2 == 0) {
            builder.up();
        }
        else {
            builder.down();
        }
    }
}
