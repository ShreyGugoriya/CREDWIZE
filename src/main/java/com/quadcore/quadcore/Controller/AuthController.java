package com.quadcore.quadcore.Controller;
import com.quadcore.quadcore.Entities.RequestToken;
import com.quadcore.quadcore.Entities.ResponseHandler;
import com.quadcore.quadcore.Entities.User;
import com.quadcore.quadcore.Entities.UserInfo;
import com.quadcore.quadcore.Enum.EmailSubjects;
import com.quadcore.quadcore.Enum.EmployeeStatus;
import com.quadcore.quadcore.Repo.CredentialRepository;
import com.quadcore.quadcore.Repo.USerRepository;
import com.quadcore.quadcore.Service.EmailSender;
import com.quadcore.quadcore.Service.EmailService;
import com.quadcore.quadcore.Service.OTPService;
import com.quadcore.quadcore.Service.UserService;
import com.quadcore.quadcore.payload.request.LoginRequest;
import com.quadcore.quadcore.payload.response.JwtResponse;
import com.quadcore.quadcore.security.jwt.JwtUtils;
import com.quadcore.quadcore.security.services.UserDetailsImpl;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

@Transactional
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    CredentialRepository userRepository;

    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    EmailService emailService;

    @Autowired
    EmailSender emailSender;
    @Autowired
    USerRepository user;

    @Autowired
    OTPService otpService;

    @PostMapping("/signin")
    public ResponseEntity<RequestToken> authenticateUser(@RequestBody LoginRequest loginRequest) throws MessagingException, TemplateException, IOException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String jwt = jwtUtils.generateJwtToken(authentication);
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user1 = user.findByEmail(loginRequest.getUsername());
        if(user1.getEmployee_status()== EmployeeStatus.Inactive)
            return ResponseEntity.ok(new RequestToken("Inactive user",HttpStatus.BAD_REQUEST));
        int otp = otpService.generateOTP(loginRequest.getUsername());
        System.out.println("otp = "+otp);
        LocalTime validTime = LocalTime.now().plusMinutes(4);
        UserInfo userInfo = new UserInfo(null, loginRequest.getUsername(), EmailSubjects.OTP_Confirmation, otp,null,0);

//        emailSender.sendEmail(loginRequest.getUsername(), "CredWise verification code", "Your OTP is " + otp + " for your Credwize account login and valid " +
//                "till "+validTime+" .\nDo not share this OTP to anyone for security reasons.\nThank you.");
        emailService.sendEmail(userInfo);
        return ResponseEntity.ok(new RequestToken("ABC",HttpStatus.OK));
    }


    @RequestMapping(value = "/validateOtp/{username}/{password}/{otpnum}", method = RequestMethod.GET)
    public ResponseEntity<?> validateOtp(@PathVariable("username") String username, @PathVariable("password") String password, @PathVariable("otpnum") int otpnum) {

        final String SUCCESS = "Entered Otp is valid";
        final String FAIL = "Entered Otp is NOT valid. Please Retry!";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (otpnum >= 0) {

            int serverOtp = otpService.getOtp(username);
            if (serverOtp > 0) {
                if (otpnum == serverOtp) {
                    otpService.clearOTP(username);

                    return ResponseEntity.ok(new JwtResponse(jwt,
                            userDetails.getUser()));
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestToken("ABC",HttpStatus.BAD_REQUEST));
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestToken("ABC",HttpStatus.BAD_REQUEST));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RequestToken("ABC",HttpStatus.BAD_REQUEST));
        }
    }
}