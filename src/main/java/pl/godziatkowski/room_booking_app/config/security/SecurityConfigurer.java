package pl.godziatkowski.room_booking_app.config.security;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import pl.godziatkowski.room_booking_app.sharedkernel.constant.Profiles;
import pl.godziatkowski.room_booking_app.web.filter.RequestResponseLogFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,
   prePostEnabled = true)
public class SecurityConfigurer {

   @Bean
   public Filter requestResponseLogFilter() {
      return new RequestResponseLogFilter();
   }

   @Bean
   @Profile(Profiles.BASIC_AUTHENTICATION)
   public AuthenticationBasic authenticationBasic() {
      return new AuthenticationBasic();
   }

}