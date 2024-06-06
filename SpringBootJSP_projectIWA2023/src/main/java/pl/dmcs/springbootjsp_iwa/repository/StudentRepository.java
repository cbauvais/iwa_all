package pl.dmcs.springbootjsp_iwa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.dmcs.springbootjsp_iwa.model.Student;
import pl.dmcs.springbootjsp_iwa.model.Team;

import java.util.List;


@Repository
public interface StudentRepository extends JpaRepository<Student,Long>{
    Student findById(long id);

    List<Student> findByLastname(String lastname);

    Student findByLastnameAndFirstname(String lastname, String firstname);

    // List<Student> findByTeamListId(long id);

    //Student findByAccountId(long id);
}