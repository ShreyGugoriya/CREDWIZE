package com.quadcore.quadcore.Controller;

import com.quadcore.quadcore.Entities.CardAllocation;
import com.quadcore.quadcore.Entities.Credential;
import com.quadcore.quadcore.Entities.User;
import com.quadcore.quadcore.Entities.UserInfo;
import com.quadcore.quadcore.Enum.EmailSubjects;
import com.quadcore.quadcore.Enum.Role;

import com.quadcore.quadcore.Service.*;

import com.quadcore.quadcore.Service.CredentialService;
import com.quadcore.quadcore.Service.EmailSender;
import com.quadcore.quadcore.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    CredentialService credentialService;

    @Autowired
    CardAllocationService cardAllocationService;
    @Autowired
    EmailService emailService;
    @Autowired
    UserService userService;
    @Autowired
    EmailSender emailSender;

    @RequestMapping(value = "/user/",method = RequestMethod.GET)
    public ResponseEntity<?> getUser(){
        try {

            return ResponseEntity.ok(userService.getUser());
        }
        catch (Exception exception){
            System.out.println(exception);
            return ResponseEntity.ok(exception.getMessage());
        }
    }

    // Get all the employee
    @RequestMapping(value = "/user/{id}/",method = RequestMethod.GET)
    public User getUserById(@PathVariable("id") int id){
        try {

            return userService.getUserById(id);
        }
        catch (Exception exception){
            System.out.println(exception);
            return null;
        }
    }

    @RequestMapping(value = "/user/",method = RequestMethod.POST)
    public User createUser(@RequestBody User user){
        return  userService.createUser(user);

    }

    @RequestMapping(value = "/user/search/{userName}", method = RequestMethod.GET)
    public List<?> searchUser(@PathVariable("userName") String userName){
        return userService.searchUser(userName);
    }

    @RequestMapping(value = "/user/status/{employeeId}", method = RequestMethod.PUT)
    public User updateUserStatus(@PathVariable ("employeeId") int employeeId, @RequestBody User user) throws Exception {

        User user1 = getUserById(employeeId);
        if(user1==null){
            throw new Exception("User does not exist");
        }
//        UserInfo userInfo = new UserInfo(userService.getUserById(employeeId).getName(), userService.getUserById(employeeId).getEmail(), EmailSubjects.Account_Deactivated,0,null,employeeId);

        List<CardAllocation> cardAllocationList1 = cardAllocationService.getCardAllocation();
        for(CardAllocation cardAllocation : cardAllocationList1)
        {
            if(cardAllocation.getUser().getEmployee_id()==employeeId){
                cardAllocation.getCard().setCardStatus(false);
            }
        }
        List<Credential> credentials= credentialService.getCredentials();
        for(Credential credential1:credentials){
            if(credential1.getUser().getEmployee_id()==employeeId && credential1.getRole()== Role.ROLE_BOTH){
                credential1.setRole(Role.ROLE_ADMIN);
                credentialService.createCredential(credential1);
                String username1 = user1.getName();
                UserInfo userInfo = new UserInfo(userService.getUserById(employeeId).getName(), userService.getUserById(employeeId).getEmail(), EmailSubjects.Account_Deactivated,0,null,employeeId);

//                emailSender.sendEmail(user1.getEmail(), "Deactivation", "Hi "+username1+", \n\nYour Credwize user account has been deactivated. Checkout your last 6 months transaction" +
//                        "on this link \n https://credwize-backend-urtjok3rza-wl.a.run.app/transaction-details/"+employeeId+"/export/pdf/ \nThank you " );
                emailService.sendEmail(userInfo);
                return userService.createUser(user1);
            }
        }
        String username1 = user1.getName();
        user1.setEmployee_status(user.getEmployee_status());
//        emailSender.sendEmail(user1.getEmail(), "Deactivation", "Hi "+username1+", \n\nYour Credwize account has been deactivated. Checkout your last 6 months transaction" +
//                "on this link \n https://credwize-backend-urtjok3rza-wl.a.run.app/transaction-details/"+employeeId+"/export/pdf/ \nThank you " );

        UserInfo userInfo = new UserInfo(userService.getUserById(employeeId).getName(), userService.getUserById(employeeId).getEmail(), EmailSubjects.Account_Deactivated,0,null,employeeId);
        emailService.sendEmail(userInfo);


        return userService.createUser(user1);
    }

    @RequestMapping(value = "/active-user/",method = RequestMethod.GET)
    public Long countActiveUser(){
        return  userService.countActiveUser();

    }
    @RequestMapping(value = "/inactive-user/",method = RequestMethod.GET)
    public Long countInActiveUser(){
        return  userService.countInActiveUser();
    }
    @RequestMapping(value = "/user/search-allocation/{userName}", method = RequestMethod.GET)
    public List<?> searchActiveUser(@PathVariable("userName") String userName){
        return userService.searchActiveUser(userName);
    }
}
