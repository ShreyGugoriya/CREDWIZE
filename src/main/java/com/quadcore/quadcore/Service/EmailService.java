package com.quadcore.quadcore.Service;

import com.quadcore.quadcore.Entities.UserInfo;
import com.quadcore.quadcore.Enum.EmailSubjects;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    final Configuration configuration;
    final JavaMailSender javaMailSender;

    public EmailService(Configuration configuration, JavaMailSender javaMailSender) {
        this.configuration = configuration;
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(UserInfo user) throws MessagingException, IOException, TemplateException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        if(user.getSubject()== EmailSubjects.Onboarding){
            helper.setSubject("Credwize Onboarding");
        }
        else if(user.getSubject()==EmailSubjects.Account_Deactivated)
        {
            helper.setSubject("Account Deactivation");
        }
        else if(user.getSubject()==EmailSubjects.Card_Allocation)
        {
            helper.setSubject("Card Allocation");
        }
        else if (user.getSubject()==EmailSubjects.OTP_Confirmation)
        {
            helper.setSubject("OTP Confirmation");
        }
        helper.setTo(user.getEmail());
        String emailContent = getEmailContent(user);
        helper.setText(emailContent, true);
        javaMailSender.send(mimeMessage);
    }

    String getEmailContent(UserInfo user) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("user", user);

        if(user.getSubject()== EmailSubjects.Onboarding){
            configuration.getTemplate("onboarding.html").process(model, stringWriter);
        }
        else if(user.getSubject()==EmailSubjects.Account_Deactivated)
        {
            configuration.getTemplate("deactivation.html").process(model, stringWriter);
        }
        else if(user.getSubject()==EmailSubjects.Card_Allocation)
        {
            configuration.getTemplate("card.html").process(model, stringWriter);
        }
        else if (user.getSubject()==EmailSubjects.OTP_Confirmation)
        {
            configuration.getTemplate("otp.html").process(model, stringWriter);
        }
        return stringWriter.getBuffer().toString();
    }
}