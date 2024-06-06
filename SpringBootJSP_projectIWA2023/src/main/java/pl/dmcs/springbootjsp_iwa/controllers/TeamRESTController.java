package pl.dmcs.springbootjsp_iwa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dmcs.springbootjsp_iwa.model.Student;
import pl.dmcs.springbootjsp_iwa.model.Team;
import pl.dmcs.springbootjsp_iwa.repository.StudentRepository;
import pl.dmcs.springbootjsp_iwa.repository.AddressRepository;
import pl.dmcs.springbootjsp_iwa.repository.TeamRepository;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/team")
public class TeamRESTController {

    private TeamRepository teamRepository;
    private StudentRepository studentRepository;


    @Autowired
    public TeamRESTController(StudentRepository studentRepository, AddressRepository addressRepository, TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
        this.studentRepository = studentRepository;
    }

    //Find all teams
    @RequestMapping(method = RequestMethod.GET/*, produces = "application/xml"*/)
    //@GetMapping
    public List<Team> findAllTeams() { return teamRepository.findAll();
    }

    //Find a team, enter the id of the team
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public Team findOneTeam(@RequestBody Team team, @PathVariable("id") long id) { return teamRepository.findById(id);
    }
/*
    //Find the students of a team, enter the id of the team
    @RequestMapping(value="/{id}/students", method = RequestMethod.GET)
    public List<Student> findTeamStudents(@PathVariable("id") long id) {
        return studentRepository.findByTeamListId(id);
    }
*/
    //Add a Team
    @RequestMapping(method = RequestMethod.POST)
    //@PostMapping
    public ResponseEntity<Team> addTeam(@RequestBody Team team) {
        Team team_name = teamRepository.findByTeamName(team.getTeamName());
        if (team_name == null){
            teamRepository.save(team);
        }
        else{
            System.out.println("This team already exist");
        }
        return new ResponseEntity<Team>(team, HttpStatus.CREATED);
    }

    /*//Add a student to a team, enter the id of the student
    @RequestMapping(value = "/{id}/student", method = RequestMethod.POST)
    //@PostMapping
    public ResponseEntity<Team> addTeamStudent(@PathVariable("id") long id, @RequestBody Team team) {
        Student student= studentRepository.findById(id);
        if (student == null){
            System.out.println("This student does not exist, please create this student first");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            student.getTeamList().add(team);
            Team exists = teamRepository.findById(team.getId());
            System.out.println(exists);
            if (exists == null) {
                teamRepository.save(team);
            }
            studentRepository.save(student);
            return new ResponseEntity<>(team, HttpStatus.CREATED);
        }

    }*/

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
    }


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
/*
    //Delete a team, if there is no student in, enter the team id
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    //@DeleteMapping("/{id}")
    public ResponseEntity<Team> deleteTeam (@PathVariable("id") long id) {
        List<Student> students = studentRepository.findByTeamListId(id);

        if (students.isEmpty()) {
            teamRepository.deleteById(id);
            return new ResponseEntity<Team>(HttpStatus.NOT_FOUND);
        }
        System.out.println("Students found, please remove them from the team before");
        return new ResponseEntity<Team>(HttpStatus.NO_CONTENT);
    }
*/

    private void partialUpdate(Team team, Map<String, Object> updates) {
        if (updates.containsKey("teamName")) {
            team.setTeamName((String) updates.get("teamName"));
        }
        teamRepository.save(team);
    }




}





