package pl.dmcs.springbootjsp_iwa.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;

@Entity
public class Student {


    @Id
    @GeneratedValue
    private long id;
    private String firstname;
    private String lastname;
    private String email;
    private String telephone;


    @OneToOne(cascade = CascadeType.ALL)
    private User user;


/*
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.MERGE)
    private Address address;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Team> teamList;
    // Commented out due to simplify http requests sent from angular app */

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Subject> subjectList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
/*
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
    }
    // Commented out due to simplify http requests sent from angular app*/

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }
}




