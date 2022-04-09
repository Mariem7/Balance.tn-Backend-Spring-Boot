package CIMF.Balance.tnBackendSpringBoot.employee;

import CIMF.Balance.tnBackendSpringBoot.registration.token.ConfirmationToken;
import CIMF.Balance.tnBackendSpringBoot.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EmployeeService implements UserDetailsService {

    //Not found message error
    private final static String USER_NOT_FOUND_MSG = "employee with email %s not found";

    //we will have a reference to the Employee Repository
    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return employeeRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG,username)));
    }

    //the sign up method
    public String singUpEmployee(Employee employee){
        //check if the user exist
        boolean employeeExists= employeeRepository.findByUsername(employee.getUsername()).isPresent();
        //if the user exists
        if(employeeExists){
            //TODO: if email not confirmed then send confirmation email
            boolean accoutConfirmed = employee.getEnabled();
            if(!accoutConfirmed){
                //send the confirmation email

            }else{
                throw new IllegalStateException("Account already exists");
            }

        }
        //encoding the password
        String encodePassword = bCryptPasswordEncoder.encode(employee.getPassword());
        //set the employee's password
        employee.setPassword(encodePassword);
        //save the employee
        employeeRepository.save(employee);

        //this is the type of token
        //UUID is a class that represents an immutable Universally Unique Identifie
        String token = UUID.randomUUID().toString();

        //Send confirmation token
        ConfirmationToken confirmationToken = new ConfirmationToken(
                //we will pass the token that we created
                token,
                //createdAt is now
                LocalDateTime.now(),
                //expiresAt after 1 hour
                LocalDateTime.now().plusMinutes(60),
                //we pass the user
                employee
        );
        //save the confirmation token
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        //return the token
        return token;
    }

    public int enableEmployee(String username){
        return employeeRepository.enableEmployee(username);
    }

}

