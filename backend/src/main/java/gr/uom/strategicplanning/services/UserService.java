package gr.uom.strategicplanning.services;

import gr.uom.strategicplanning.controllers.requests.UserRequest;
import gr.uom.strategicplanning.controllers.responses.OrganizationResponse;
import gr.uom.strategicplanning.controllers.responses.UserResponse;
import gr.uom.strategicplanning.models.domain.Organization;
import gr.uom.strategicplanning.models.users.User;
import gr.uom.strategicplanning.repositories.OrganizationRepository;
import gr.uom.strategicplanning.repositories.UserRepository;
import net.bytebuddy.utility.nullability.AlwaysNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User createUser(UserRequest userRequest) {
        Optional<User> userOptional = userRepository.findByEmail(userRequest.getEmail());
        if(userOptional.isEmpty()){
            User user = new User();
            user.setName(userRequest.getName());
            user.setEmail(userRequest.getEmail());
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            Organization organization = organizationRepository.findById(userRequest.getOrganizationId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Organization not found"));
            user.setOrganization(organization);
            user.setRoles("SIMPLE");
            user.setVerified(false);
            return userRepository.save(user);
        }
        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Email is used from another user");
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void createOrganization(String name, User admin) {
        Organization organization = new Organization();
        organization.setName(name);
        organization.addUser(admin);
        admin.setOrganization(organization);
        organizationRepository.save(organization);
    }

    public User getUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent()){
            return userOptional.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }

    public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            return userOptional.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }

    public Optional<List<UserResponse>> getUsersByOrganizationId(Long id) {
        Optional<Organization> organizationOptional = organizationRepository.findById(id);
        if(organizationOptional.isPresent()){
            Organization organization = organizationOptional.get();
            List<User> users = organization.getUsers();
            List<UserResponse> userResponses = UserResponse.convertToUserResponseList(users);
            return Optional.of(userResponses);
        }
        return Optional.empty();
    }
}
