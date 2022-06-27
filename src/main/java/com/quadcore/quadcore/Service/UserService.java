package com.quadcore.quadcore.Service;

import com.quadcore.quadcore.Entities.Credential;
import com.quadcore.quadcore.Entities.User;
import com.quadcore.quadcore.Enum.EmployeeStatus;
import com.quadcore.quadcore.Repo.USerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    CredentialService credentialService;
    @Autowired
    USerRepository uSerRepository;
    public List<User> getUser(){
        return uSerRepository.findAll();
    }

    public User getUserById(int id) {
        System.out.println(uSerRepository.findById(id).get());
        return uSerRepository.findById(id).get();
    }

    public User createUser(User user) {
        return uSerRepository.save(user);
    }

    public List<?> searchUser(String userName) {

//        List<?> user= uSerRepository.searchByUserName(userName);
//        List<Credential> credentials= credentialService.getCredentials();

        return uSerRepository.searchByUserName(userName);
    }
    public Long countActiveUser() {
        List<User> user = uSerRepository.findAll();
        Long c = Long.valueOf(0);
        for(User user1:user){
            if(user1.getEmployee_status()== EmployeeStatus.Active) c++;
        }
        return c;
    }
    public Long countInActiveUser() {
        List<User> user = uSerRepository.findAll();
        Long c = Long.valueOf(0);
        for(User user1:user){
            if(user1.getEmployee_status()==EmployeeStatus.Inactive) c++;
        }
        return c;
    }

    public List<?> searchActiveUser(String userName) {
        return uSerRepository.searchActiveByUserName(userName);
    }


}
