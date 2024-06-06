package pl.dmcs.springbootjsp_iwa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.dmcs.springbootjsp_iwa.model.User;
import pl.dmcs.springbootjsp_iwa.repository.AddressRepository;
import pl.dmcs.springbootjsp_iwa.repository.StudentRepository;
import pl.dmcs.springbootjsp_iwa.repository.TeamRepository;
import pl.dmcs.springbootjsp_iwa.repository.UserRepository;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/users")
public class UserRESTController {

    private StudentRepository studentRepository;
    private AddressRepository addressRepository;

    private UserRepository userRepository;

    private TeamRepository teamRepository;

    @Autowired
    public UserRESTController(UserRepository userRepository, StudentRepository studentRepository, AddressRepository addressRepository, TeamRepository teamRepository) {
        this.studentRepository = studentRepository;
        this.addressRepository = addressRepository;
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping(value="{username}", method = RequestMethod.GET, name = "getStudentById")
    public User getUserByUsername(@PathVariable("username") String username) {
        return userRepository.findByUsername(username).orElse(null);
    }



}
