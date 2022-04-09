package CIMF.Balance.tnBackendSpringBoot.employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

//It is a class-level annotation. The repository is a DAOs (Data Access Object) that access the database directly.
//The repository does all the operations related to the database.
@Repository

//Database transaction is a single logical unit of work which accesses and possibly modifies the contents of a database
@Transactional(readOnly = true)
public interface EmployeeRepository  extends JpaRepository<Employee,Long> {

    //We can create a select query based on the attribute of the Employee Class
    //Which is the username
    Optional<Employee> findByUsername(String username);

    @Transactional
    @Modifying
    @Query("update Employee a set a.enabled = TRUE where a.username =?1")
    int enableEmployee(String username);

}
