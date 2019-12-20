package run.aquan.iron.system.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.aquan.iron.system.exception.UserNameAlreadyExistException;
import run.aquan.iron.system.model.entity.User;
import run.aquan.iron.system.model.params.RegisterUserParam;
import run.aquan.iron.system.repository.UserRepository;
import run.aquan.iron.system.service.UserService;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User findUserByUserName(String userName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException("No user found with username " + userName));
        return user;
    }

    @Override
    public void saveUser(RegisterUserParam registerUserParam) {
        Optional<User> optionalUser = userRepository.findByUsername(registerUserParam.getUsername());
        if (optionalUser.isPresent()) {
            throw new UserNameAlreadyExistException("User name already exist!Please choose another user name.");
        }
        User user = User.builder()
                .username(registerUserParam.getUsername())
                .password(bCryptPasswordEncoder.encode(registerUserParam.getPassword()))
                .roles("DEV,PM")
                .build();
        userRepository.save(user);
    }

    @Override
    public Page<User> pageBy(Integer pageNum, Integer pageSize) {
        Page<User> users = userRepository.findAll(PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "updataTime"));
        return users;
    }

    @Override
    public User getById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("No user found with Id " + id));
        return user;
    }

}
