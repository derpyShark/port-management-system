package web.db.kpi.backend.services;

import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.db.kpi.backend.interfaces.IUserService;
import web.db.kpi.backend.models.User;
import web.db.kpi.backend.repositories.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long userId) throws NotFoundException {
        Optional<User> userMaybe = userRepository.findById(userId);
        if(userMaybe.isPresent()){
            return userMaybe.get();
        }
        else
            throw new NotFoundException("No user with such an Id: " + userId);
    }



    @Override
    public User update(Long userId, User userDetails) throws NotFoundException {
        User oldUser = getById(userId);
        oldUser.setLogin(userDetails.getLogin());
        oldUser.setPasswordHash(userDetails.getPasswordHash());
        oldUser.setName(userDetails.getName());
        oldUser.setSurname(userDetails.getSurname());
        return userRepository.save(oldUser);
    }

    @Override
    public User create(User userObject) {
        userObject.setTimeCreated(new Date(System.currentTimeMillis()));
        return userRepository.save(userObject);
    }

    @Override
    public void delete(Long userId) throws NotFoundException{
        userRepository.delete(getById(userId));
    }

    @Override
    public User getByLogin(String login) throws NotFoundException {
        Optional<User> userMaybe = userRepository.findByLogin(login);
        if(userMaybe.isPresent()){
            return userMaybe.get();
        }
        else
            throw new NotFoundException("No user with such a login: " + login);
    }
}
