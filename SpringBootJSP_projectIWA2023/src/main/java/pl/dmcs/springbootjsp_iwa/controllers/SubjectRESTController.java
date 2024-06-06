package pl.dmcs.springbootjsp_iwa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dmcs.springbootjsp_iwa.model.Student;
import pl.dmcs.springbootjsp_iwa.model.Subject;
import pl.dmcs.springbootjsp_iwa.model.Team;
import pl.dmcs.springbootjsp_iwa.repository.StudentRepository;
import pl.dmcs.springbootjsp_iwa.repository.AddressRepository;
import pl.dmcs.springbootjsp_iwa.repository.SubjectRepository;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/subjects")
public class SubjectRESTController {

    private SubjectRepository subjectRepository;
    private StudentRepository studentRepository;


    @Autowired
    public SubjectRESTController(StudentRepository studentRepository, AddressRepository addressRepository, SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
        this.studentRepository = studentRepository;
    }

    //Find all teams
    @RequestMapping(method = RequestMethod.GET/*, produces = "application/xml"*/)
    //@GetMapping
    public List<Subject> getAllSubjects() { return subjectRepository.findAll();
    }

    //Find a team, enter the id of the team
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public Subject findOneSubject(@RequestBody Subject subject, @PathVariable("id") long id) { return subjectRepository.findById(id);
    }
    /*
        //Find the students of a team, enter the id of the team
        @RequestMapping(value="/{id}/students", method = RequestMethod.GET)
        public List<Student> findTeamStudents(@PathVariable("id") long id) {
            return studentRepository.findByTeamListId(id);
        }
    */
    //Add a Subject
    @RequestMapping(method = RequestMethod.POST)
    //@PostMapping
    public ResponseEntity<Subject> addSubject(@RequestBody Subject subject) {
        Subject subject_name = subjectRepository.findBySubjectName(subject.getSubjectName());
        if (subject_name == null){
            subjectRepository.save(subject);
        }
        else{
            System.out.println("This subject already exist");
        }
        return new ResponseEntity<Subject>(subject, HttpStatus.CREATED);
    }

    //Add a student to a subject, enter the id of the student
    @RequestMapping(value = "/{id}/student", method = RequestMethod.POST)
    //@PostMapping
    public ResponseEntity<Subject> addTeamSubject(@PathVariable("id") long id, @RequestBody Subject subject) {
        Student student= studentRepository.findById(id);
        if (student == null){
            System.out.println("This student does not exist, please create this student first");
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
/*
    //Change the name of a team
    @RequestMapping(value="/{id}", method = RequestMethod.PATCH)
    //@PatchMapping("/{id}")
    public ResponseEntity<Team> updatePartOfTeam(@RequestBody Map<String, Object> updates, @PathVariable("id") long id) {
        Team team = teamRepository.findById(id);
        if (team == null) {
            System.out.println("Team not found!");
            return new ResponseEntity<Team>(HttpStatus.NOT_FOUND);
        }
        partialUpdate(team,updates);
        return new ResponseEntity<Team>(HttpStatus.NO_CONTENT);
    }*/


    //Delete a member of a team, enter the id of the student, in the body put id + teamName
   /* @RequestMapping(value="/{id}/student", method = RequestMethod.DELETE)
    //@DeleteMapping("/{id}")
    public ResponseEntity<Team> deleteStudentOfTeam (@RequestBody Team team, @PathVariable("id") long id) {
        Student student = studentRepository.findById(id);
        if (student == null) {
            System.out.println("Student not found!");
            return new ResponseEntity<Team>(HttpStatus.NOT_FOUND);
        }
        List<Team> teams = student.getTeamList();

        for (Iterator<Team> iterator = teams.iterator(); iterator.hasNext(); ) {
            Team currentTeam = iterator.next();
            if (currentTeam.getId() == team.getId()) {
                iterator.remove();
            }
        }

        student.setTeamList(teams);
        studentRepository.save(student);

        return new ResponseEntity<Team>(HttpStatus.NO_CONTENT);
    }*/

    //Delete a subject
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    //@DeleteMapping("/{id}")
    public ResponseEntity<Subject> deleteSubject (@PathVariable("id") long id) {

            subjectRepository.deleteById(id);
            return new ResponseEntity<Subject>(HttpStatus.NOT_FOUND);

    }

    /*
    private void partialUpdate(Team team, Map<String, Object> updates) {
        if (updates.containsKey("teamName")) {
            team.setTeamName((String) updates.get("teamName"));
        }
        teamRepository.save(team);
    }*/




}





