package pl.godziatkowski.roombookingapp.domain.user.finder;

import java.util.List;
import java.util.Map;
import java.util.Set;

import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;

public interface IUserSnapshotFinder {

    UserSnapshot findOneById(Long id);

    UserSnapshot findOneByLoginIgnoreCase(String login);

    List<UserSnapshot> findAll();

    Map<Long, UserSnapshot> findAsMapByUserIdIn(Set<Long> ids);

    Integer findCountOfUsersWithRoleAdmin();

}
