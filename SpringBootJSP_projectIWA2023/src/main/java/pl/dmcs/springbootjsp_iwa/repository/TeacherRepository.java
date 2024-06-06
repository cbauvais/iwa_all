package pl.dmcs.springbootjsp_iwa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.dmcs.springbootjsp_iwa.model.Teacher;
import pl.dmcs.springbootjsp_iwa.model.Team;

import java.util.List;


@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long>{
    Teacher findById(long id);

    List<Teacher> findByLastname(String lastname);

    Teacher findByLastnameAndFirstname(String lastname, String firstname);

    // List<Student> findByTeamListId(long id);

    //Student findByAccountId(long id);
}