package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private Logger logger = Logger.getLogger(LoginService.class);

    private final LoginRepository formRepo;

    @Autowired
    public LoginService(LoginRepository bookRepo) {
        this.formRepo = bookRepo;
    }

    public boolean authenticate(LoginForm loginForm){
        logger.info("try auth with user-form: " + loginForm);
        for(LoginForm form : formRepo.retreiveAll()){
            if(form.getUsername().equals(loginForm.getUsername())
                                        && form.getPassword().equals(loginForm.getPassword())){
                return true;
            }
        }
        return false;
    }

    public boolean register(LoginForm loginForm){
        logger.info("try register with user-form: " + loginForm);
        for(LoginForm form : formRepo.retreiveAll()){
            if(form.getUsername().equals(loginForm.getUsername())){
                return false;
            }
        }
        formRepo.store(loginForm);
        return true;
    }
}
