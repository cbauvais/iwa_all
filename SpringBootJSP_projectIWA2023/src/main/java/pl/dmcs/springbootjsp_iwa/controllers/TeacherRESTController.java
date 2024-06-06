package pl.dmcs.springbootjsp_iwa.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dmcs.springbootjsp_iwa.model.*;
import pl.dmcs.springbootjsp_iwa.repository.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/teachers")
public class TeacherRESTController {
    private StudentRepository studentRepository;
    private TeacherRepository teacherRepository;

    private TeamRepository teamRepository;
    private SubjectRepository subjectRepository;
    private UserRepository userRepository;

    @Autowired
    public TeacherRESTController(UserRepository userRepository, SubjectRepository subjectRepository, StudentRepository studentRepository, TeacherRepository teacherRepository, TeamRepository teamRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.teamRepository = teamRepository;
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
    }

    //RETURN ALL Teachers
    @RequestMapping(method = RequestMethod.GET/*, produces = "application/xml"*/)
    //@GetMapping
    public List<Teacher> findAllTeachers() { return teacherRepository.findAll();
    }

    @RequestMapping(value="/{lastname}/{firstname}", method = RequestMethod.GET, name = "getStudentByLastname")
    public ResponseEntity<Teacher> findTeachersByLastnameAndFirstname(@PathVariable("lastname") String lastname, @PathVariable ("firstname") String firstname) {
        Teacher teacher = teacherRepository.findByLastnameAndFirstname(lastname, firstname);
        if (teacher != null) {
            return new ResponseEntity<>(teacher, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    //@PostMapping
    public ResponseEntity<Teacher> addTeacher(@RequestBody Teacher teacher) {
        /*if (student.getAddress().getId() <= 0)
        {
            addressRepository.save(student.getAddress());
        }*/
        teacherRepository.save(teacher);
        return new ResponseEntity<Teacher>(teacher, HttpStatus.CREATED);
    }

    @RequestMapping(value="/more", method = RequestMethod.POST)
    //@PostMapping
    public ResponseEntity<Teacher> addMoreTeacher(@RequestBody List<Teacher> teachers) {
        teacherRepository.saveAll(teachers);
        return new ResponseEntity<Teacher>(HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    //@DeleteMapping("/{id}")
    public ResponseEntity<Teacher> deleteTeacher (@PathVariable("id") long id) {
        Teacher teacher = teacherRepository.findById(id);
        if (teacher == null) {
            System.out.println("Student not found!");
            return new ResponseEntity<Teacher>(HttpStatus.NOT_FOUND);
        }
        teacherRepository.deleteById(id);
        return new ResponseEntity<Teacher>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    //@DeleteMapping("/{id}")
    public ResponseEntity<Teacher> deleteAllTeachers () {
        teacherRepository.deleteAll();
        return new ResponseEntity<Teacher>(HttpStatus.NO_CONTENT);
    }


    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    //@PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(@RequestBody Teacher teacher, @PathVariable("id") long id) {
        teacher.setId(id);
        teacherRepository.save(teacher);
        return new ResponseEntity<Teacher>(teacher, HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PATCH)
    //@PatchMapping("/{id}")
    public ResponseEntity<Teacher> updatePartOfTeacher(@RequestBody Map<String, Object> updates, @PathVariable("id") long id) {
        Teacher teacher = teacherRepository.findById(id);
        if (teacher == null) {
            System.out.println("Teacher not found!");
            return new ResponseEntity<Teacher>(HttpStatus.NOT_FOUND);
        }
        partialUpdate(teacher,updates);
        return new ResponseEntity<Teacher>(HttpStatus.NO_CONTENT);
    }

    //add a subject to a teacher
    @RequestMapping(value = "/{id}/addSubject", method = RequestMethod.POST)
    //@PostMapping
    public ResponseEntity<Subject> addSubject(@PathVariable("id") long id, @RequestBody Subject subject) {
        Teacher teacher= teacherRepository.findById(id);
        if (teacher == null){
            System.out.println("This teacher does not exist, please create this teacher first");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            teacher.getSubjectList().add(subject);
            Subject exists = subjectRepository.findById(subject.getId());
            System.out.println(exists);
            if (exists == null) {
                subjectRepository.save(subject);
            }
            teacherRepository.save(teacher);
            return new ResponseEntity<>(subject, HttpStatus.CREATED);
        }

    }

    //associate an account
    @RequestMapping(value="/{id}/addAccount", method = RequestMethod.PATCH)
    public ResponseEntity<Teacher> associateToAccount(@Valid @RequestBody User user, @PathVariable("id") long id) {
        Teacher teacher = teacherRepository.findById(id);
        Optional<User> accountToAssociate = userRepository.findByUsername(user.getUsername());
        if (teacher != null && accountToAssociate.isPresent()) {
            User userToAssociate = accountToAssociate.get();
            teacher.setUser(userToAssociate);
            teacherRepository.save(teacher);
            user.setTeacher(teacher);
            userRepository.save(userToAssociate);
            return new ResponseEntity<Teacher>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<Teacher>(HttpStatus.NOT_FOUND);
    }


    private void partialUpdate(Teacher teacher, Map<String, Object> updates) {
        if (updates.containsKey("firstname") && updates.get("firstname") != "") {
            teacher.setFirstname((String) updates.get("firstname"));
        }
        if (updates.containsKey("lastname") && updates.get("lastname") != "") {
            teacher.setLastname((String) updates.get("lastname"));
        }
        if (updates.containsKey("email") && updates.get("email") != "") {
            teacher.setEmail((String) updates.get("email"));
        }
        if (updates.containsKey("telephone") && updates.get("telephone") != "")  {
            teacher.setTelephone((String) updates.get("telephone"));
        }
        teacherRepository.save(teacher);
    }


}
