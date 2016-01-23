package pl.godziatkowski.roombookingapp.domain.user.finder;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;
import pl.godziatkowski.roombookingapp.domain.user.entity.User;
import pl.godziatkowski.roombookingapp.domain.user.entity.UserRole;
import pl.godziatkowski.roombookingapp.domain.user.repository.IUserRepository;
import pl.godziatkowski.roombookingapp.sharedkernel.annotations.Finder;

@Finder
public class UserSnapshotFinder
    implements IUserSnapshotFinder {

    private final IUserRepository userRepository;

    @Autowired
    public UserSnapshotFinder(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserSnapshot findOneById(Long id) {
        User user = userRepository.findOne(id);
        return user != null ? user.toSnapshot() : null;
    }

    @Override
    public UserSnapshot findOneByLoginIgnoreCase(String login) {
        User user = userRepository.findOneByLoginIgnoreCase(login);
        return user != null ? user.toSnapshot() : null;
    }

    @Override
    public List<UserSnapshot> findAll() {
        return userRepository.findAll()
            .stream()
            .map(User::toSnapshot)
            .collect(Collectors.toList());
    }

    @Override
    public Map<Long, UserSnapshot> findAsMapByUserIdIn(Set<Long> ids) {
        return userRepository.findAll(ids)
            .stream()
            .map(User::toSnapshot)
            .collect(Collectors.toMap(UserSnapshot::getId, snapshot -> snapshot));
    }

    @Override
    public Integer findCountOfUsersWithRoleAdmin() {
        return userRepository.countByUserRoleAdmin(UserRole.ADMIN);
//        long count = userRepository.countByUserRoles(UserRole.ADMIN);//.get(0)[0];
//        return (int)count;
    }

}
