package pl.godziatkowski.roombookingapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncodingService
   implements IPasswordEncodingService {

   private final BCryptPasswordEncoder passwordEncoder;

   @Autowired
   public PasswordEncodingService(BCryptPasswordEncoder passwordEncoder) {
      this.passwordEncoder = passwordEncoder;
   }

   @Override
   public String encode(String rawPassword) {
      return passwordEncoder.encode(rawPassword);
   }

   @Override
   public boolean isMatch(String passwordToCheck, String rawPassword) {
      return passwordEncoder.matches(passwordToCheck, rawPassword);
   }
}
