package CIMF.Balance.tnBackendSpringBoot.email;


public interface EmailSender {

    void sendEmail (String toEmail, String email);
}
