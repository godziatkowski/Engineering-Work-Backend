package pl.godziatkowski.roombookingapp.domain.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.godziatkowski.roombookingapp.domain.user.entity.User;
import pl.godziatkowski.roombookingapp.domain.user.entity.UserRole;

public interface IUserRepository
    extends JpaRepository<User, Long> {
    
    User findOneByLoginIgnoreCase(String login);

    @Query("SELECT COUNT(u.id) FROM User u JOIN u.userRoles r WHERE :userRole = r")
    Integer countByUserRoleAdmin(@Param("userRole") UserRole userRole);

}
