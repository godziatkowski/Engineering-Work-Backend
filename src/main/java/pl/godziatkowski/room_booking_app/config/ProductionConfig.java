package pl.godziatkowski.room_booking_app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import pl.godziatkowski.room_booking_app.sharedkernel.constant.Profiles;

@Configuration
@PropertySource("classpath:/defaultConfig.properties")
@Profile({Profiles.PRODUCTION, Profiles.DEVELOPMENT})
public class ProductionConfig {

}
