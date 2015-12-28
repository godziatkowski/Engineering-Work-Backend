package pl.godziatkowski.room_booking_app.config.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class AuthenticationBasic
   extends WebSecurityConfigurerAdapter {

   @Override
   protected void configure(HttpSecurity http)
      throws Exception {
      AuthenticationCommonUtil.configure(http);
   }

}
