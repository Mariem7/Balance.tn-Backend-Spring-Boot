package CIMF.Balance.tnBackendSpringBoot.employee;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

//Annotation from Lumbok will provide us with getters, setters,constrcutor and equals and hashcode methods
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
//means that this class is a JPA entity
@Entity
//means that this is a database table
//@Table(name="users")

public class Employee  implements UserDetails {

    //it will be generated automatically (will create a sequence)
    //A sequence is an object in Oracle that is used to generate a number sequence.
    //This can be useful when you need to create a unique number to act as a primary key
    @SequenceGenerator(name = "employee_sequence",
            sequenceName = "employee_sequence",
            //The amount to increment by when allocating sequence numbers from the sequence (by default it's 50, and we change it to 1)
            allocationSize = 1)
    //the id will be the primary key of our table
    @Id
    //how do we generate the value of the Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_sequence")
    private Long employeeId;

    //the username which will be the email
    private String username;

    private String password;

    private String firstName;

    private String lastName;

    //the function of the user {function in the society: accountant or CEO}
    private String function;

    //the Phone number
    private Long phoneNumber;

    //the National Identity Card Number
    private Long nicNumber;

    //the time of sign up
    @Column(nullable = false)
    private LocalDate createTime;

    //the role of the user {user or Admin}
    @Enumerated(EnumType.STRING)
    private EmployeeRole employeeRole;

    private Boolean locked = false;
    private Boolean enabled =false;

    //define a constructor without Id
    public Employee(String username,
                    String password,
                    String firstName,
                    String lastName,
                    String function,
                    Long phoneNumber,
                    Long nicNumber,
                    LocalDate createTime,
                    EmployeeRole employeeRole
    ) {

        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.function = function;
        this.phoneNumber = phoneNumber;
        this.nicNumber = nicNumber;
        setCreateTime(LocalDate.now());
        this.employeeRole = employeeRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(employeeRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getUsername(){
        return username;
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
