package pl.dmcs.springbootjsp_iwa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dmcs.springbootjsp_iwa.model.Student;
import pl.dmcs.springbootjsp_iwa.model.Subject;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Subject findById(long id);

    Subject findBySubjectName(String subjectName);

}


