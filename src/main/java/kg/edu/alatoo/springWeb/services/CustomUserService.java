package kg.edu.alatoo.springWeb.services;

import kg.edu.alatoo.springWeb.dao.UserDAO;
import kg.edu.alatoo.springWeb.exceptions.UserNotFoundException;
import kg.edu.alatoo.springWeb.modules.Role;
import kg.edu.alatoo.springWeb.modules.User;
import kg.edu.alatoo.springWeb.repos.RoleRepository;
import kg.edu.alatoo.springWeb.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserService implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDAO(
                userRepository.findByUsername(username.toLowerCase())
                        .orElseThrow(()->new UserNotFoundException(username)) // loadUserByUsername must never return null
        );
    }

    @Override
    public User createUser(User user) {
        user.setId(null);
        user.setUsername(user.getUsername().toLowerCase());
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User createUserWithRoles(User user, String... roles) {
        log.debug(user.toString());
        user.setId(null);
        user.setUsername(user.getUsername().toLowerCase());
        user.setPassword(encoder.encode(user.getPassword()));
        Set<Role> roleSet = new HashSet<>();
        for (String role :
                roles) {
            roleSet.add(getRole(role));
        }
        user.setRoles(roleSet);
        return userRepository.save(user);
    }

    private Role getRole(String role) {
        role = role.toUpperCase();
        Role roleObj = roleRepository.findById(role).orElse(new Role(role));
        return roleRepository.save(roleObj);
    }

    @Override
    public User updateUser(Long id, User user) {
        User existedUser = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        if (StringUtils.hasText(user.getUsername())) existedUser.setUsername(user.getUsername().toLowerCase());
        if (StringUtils.hasText(user.getFirstName())) existedUser.setFirstName(user.getFirstName());
        if (StringUtils.hasText(user.getLastName())) existedUser.setLastName(user.getLastName());
        return userRepository.save(existedUser);
    }

    @Override
    public User updateUser(String username, User user) {
        User existedUser = userRepository.findByUsername(username.toLowerCase()).orElseThrow(UserNotFoundException::new);
        if (StringUtils.hasText(user.getFirstName())) existedUser.setFirstName(user.getFirstName());
        if (StringUtils.hasText(user.getLastName())) existedUser.setLastName(user.getLastName());
        return userRepository.save(existedUser);
    }

    @Override
    public void deleteUser(String username) {
        userRepository.deleteByUsername(username.toLowerCase());
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username.toLowerCase());
    }

    public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new UserNotFoundException("Could not find any customer with the email " + email);
        }
    }

    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    public void updatePassword(User user, String newPassword) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(newPassword);

        user.setResetPasswordToken(null);
        userRepository.save(user);
    }
}
