package pl.godziatkowski.roombookingapp.web.restapi.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.godziatkowski.roombookingapp.domain.user.bo.IUserBO;
import pl.godziatkowski.roombookingapp.domain.user.dto.UserSnapshot;
import pl.godziatkowski.roombookingapp.domain.user.finder.IUserSnapshotFinder;

@RestController
@RequestMapping("/user")
public class UserApi {

    private final IUserSnapshotFinder userSnapshotFinder;
    private final IUserBO userBO;

    @Autowired
    public UserApi(IUserSnapshotFinder userSnapshotFinder, IUserBO userBO) {
        this.userSnapshotFinder = userSnapshotFinder;
        this.userBO = userBO;
    }

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<List<User>> list() {
        List<UserSnapshot> userSnapshots = userSnapshotFinder.findAll();
        List<User> users = userSnapshots
            .stream()
            .map(User::new)
            .collect(Collectors.toList());

        return ResponseEntity
            .ok()
            .body(users);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}/grantAdminRights")
    public HttpEntity<User> changeUserRole(@PathVariable("id") Long id) {
        userBO.grantAdminRights(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
