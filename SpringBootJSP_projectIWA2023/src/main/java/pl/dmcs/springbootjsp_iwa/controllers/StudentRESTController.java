package pl.dmcs.springbootjsp_iwa.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dmcs.springbootjsp_iwa.message.request.SignUpForm;
import pl.dmcs.springbootjsp_iwa.model.*;
import pl.dmcs.springbootjsp_iwa.repository.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/students")
public class StudentRESTController {

    private StudentRepository studentRepository;
    private AddressRepository addressRepository;

    private UserRepository userRepository;

    private TeamRepository teamRepository;
    private SubjectRepository subjectRepository;

    @Autowired
    public StudentRESTController(SubjectRepository subjectRepository, UserRepository userRepository, StudentRepository studentRepository, AddressRepository addressRepository, TeamRepository teamRepository) {
        this.studentRepository = studentRepository;
        this.addressRepository = addressRepository;
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
    }

    //RETURN ALL STUDENTS
    @RequestMapping(method = RequestMethod.GET/*, produces = "application/xml"*/)
    //@GetMapping
    public List<Student> findAllStudents() { return studentRepository.findAll();
    }

    //RETURN 1 STUDENT
    /*@RequestMapping(value="/{id}", method = RequestMethod.GET, name = "getStudentById")
    public Student findOneStudent(@RequestBody Student student, @PathVariable("id") long id) {
        return studentRepository.findById(id);
    }*/

    @RequestMapping(value="/{lastname}/{firstname}", method = RequestMethod.GET, name = "getStudentByLastname")
    public ResponseEntity<Student> findStudentsByLastnameAndFirstname(@PathVariable("lastname") String lastname, @PathVariable ("firstname") String firstname) {
        Student student = studentRepository.findByLastnameAndFirstname(lastname, firstname);
        if (student != null) {
            return new ResponseEntity<>(student, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //add subject to a student
    @RequestMapping(value = "/{id}/addSubject", method = RequestMethod.POST)
    //@PostMapping
    public ResponseEntity<Subject> addSubject(@PathVariable("id") long id, @RequestBody Subject subject) {
        Student student= studentRepository.findById(id);
        if (student == null){
            System.out.println("This student does not exist, please create this teacher first");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            student.getSubjectList().add(subject);
            Subject exists = subjectRepository.findById(subject.getId());
            System.out.println(exists);
            if (exists == null) {
                subjectRepository.save(subject);
            }
            studentRepository.save(student);
            return new ResponseEntity<>(subject, HttpStatus.CREATED);
        }

    }

    @RequestMapping(method = RequestMethod.POST)
    //@PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student, Team team) {
        /*if (student.getAddress().getId() <= 0)
        {
            addressRepository.save(student.getAddress());
        }*/
        studentRepository.save(student);
        return new ResponseEntity<Student>(student, HttpStatus.CREATED);
    }

    @RequestMapping(value="/more", method = RequestMethod.POST)
    //@PostMapping
    public ResponseEntity<Student> addMoreStudent(@RequestBody List<Student> students) {
        studentRepository.saveAll(students);
        return new ResponseEntity<Student>(HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    //@DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent (@PathVariable("id") long id) {
        Student student = studentRepository.findById(id);
        if (student == null) {
            System.out.println("Student not found!");
            return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
        }
        studentRepository.deleteById(id);
        return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    //@DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteAllStudents () {
        studentRepository.deleteAll();
        return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
    }


    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    //@PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student, @PathVariable("id") long id) {
        student.setId(id);
        studentRepository.save(student);
        return new ResponseEntity<Student>(student, HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PATCH)
    //@PatchMapping("/{id}")
    public ResponseEntity<Student> updatePartOfStudent(@RequestBody Map<String, Object> updates, @PathVariable("id") long id) {
        Student student = studentRepository.findById(id);
        if (student == null) {
            System.out.println("Student not found!");
            return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
        }
        partialUpdate(student,updates);
        return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
    }

    //associate an account
    @RequestMapping(value="/{id}/addAccount", method = RequestMethod.PATCH)
    public ResponseEntity<Student> associateToAccount(@Valid @RequestBody User user, @PathVariable("id") long id) {
        Student student = studentRepository.findById(id);
        Optional<User> accountToAssociate = userRepository.findByUsername(user.getUsername());
        if (student != null && accountToAssociate.isPresent()) {
            User userToAssociate = accountToAssociate.get();
            student.setUser(userToAssociate);
            studentRepository.save(student);
            user.setStudent(student);
            userRepository.save(userToAssociate);
            return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
    }

    private void partialUpdate(Student student, Map<String, Object> updates) {
        if (updates.containsKey("firstname") && updates.get("firstname") != "") {
            student.setFirstname((String) updates.get("firstname"));
        }
        if (updates.containsKey("lastname") && updates.get("lastname") != "") {
            student.setLastname((String) updates.get("lastname"));
        }
        if (updates.containsKey("email") && updates.get("email") != "") {
            student.setEmail((String) updates.get("email"));
        }
        if (updates.containsKey("telephone") && updates.get("telephone") != "")  {
            student.setTelephone((String) updates.get("telephone"));
        }

        studentRepository.save(student);
    }

}





