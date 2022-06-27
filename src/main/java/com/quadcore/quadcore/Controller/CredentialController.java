package com.quadcore.quadcore.Controller;

import com.quadcore.quadcore.Entities.Credential;
import com.quadcore.quadcore.Entities.User;
import com.quadcore.quadcore.Service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class CredentialController {

    @Autowired
    CredentialService credentialService;
    @RequestMapping(value = "/credential/",method = RequestMethod.POST)
    public Credential createCredential(@RequestBody Credential credential){
        return  credentialService.createCredential(credential);
    }

    @RequestMapping(value = "/credential/", method = RequestMethod.GET)
    public List<Credential> getCredentials(){
        return credentialService.getCredentials();
    }

    @RequestMapping(value = "/credential/employee/{employeeID}", method = RequestMethod.GET)
    public Boolean getCredentialsByEmployeeID(@PathVariable("employeeID") int employeeID){
        return credentialService.getCredentialsByEmployeeID(employeeID);
    }
}
