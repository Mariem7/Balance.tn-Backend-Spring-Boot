package CIMF.Balance.tnBackendSpringBoot.email;

import CIMF.Balance.tnBackendSpringBoot.employee.Employee;
import freemarker.template.Configuration;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender{

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    @Override
    //to not block the user
    @Async
    public void sendEmail(String toEmail, String email) {

        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email,true);
            helper.setTo(toEmail);
            helper.setSubject("Confirm you email");
            helper.setFrom("balance.cimf.tn@gmail.com");
            mailSender.send(mimeMessage);
        }catch(MessagingException e){
            LOGGER.error("Fail to send email", e);
            throw new IllegalStateException("fail to send email");
        }
    }

}
