package com.quadcore.quadcore.Service;

import com.quadcore.quadcore.Entities.Credential;
import com.quadcore.quadcore.Repo.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    @Autowired
    CredentialRepository credentialRepository;


    public Credential createCredential(Credential credential) {
        return credentialRepository.save(credential);
    }

    public List<Credential> getCredentials() {
        return credentialRepository.findAll();
    }

    public Boolean getCredentialsByEmployeeID(int employeeID) {
        List<Credential> credentials= credentialRepository.findAll();
        for(Credential credential1:credentials){
            if(credential1.getUser().getEmployee_id()==employeeID) return true;
        }
        return false;
    }
}
