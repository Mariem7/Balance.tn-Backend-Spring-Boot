package CIMF.Balance.tnBackendSpringBoot.registration;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString

public class RegistrationRequest {

    private final String username;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String function;
    private final Long phoneNumber;
    private final Long nicNumber;
    private final LocalDate createTime;



}
