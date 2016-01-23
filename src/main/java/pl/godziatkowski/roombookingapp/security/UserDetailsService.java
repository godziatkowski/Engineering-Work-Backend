package pl.godziatkowski.roombookingapp.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;
import pl.godziatkowski.roombookingapp.domain.user.finder.IUserSnapshotFinder;

@Component("userDetailsService")
public class UserDetailsService
   implements org.springframework.security.core.userdetails.UserDetailsService {

   private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

   private final IUserSnapshotFinder userSnapshotFinder;

   @Autowired
   public UserDetailsService(IUserSnapshotFinder userSnapshotFinder) {
      this.userSnapshotFinder = userSnapshotFinder;
   }

   @Override
   @Transactional
   public UserDetails loadUserByUsername(final String login) {
      log.debug("Authenticating {}", login);

      UserSnapshot userSnapshot = userSnapshotFinder.findOneByLoginIgnoreCase(login);

      if (userSnapshot == null) {
         throw new UsernameNotFoundException("User with login " + login + " was not found in database");
      }

      return new CustomUserDetails(userSnapshot);

   }
}
