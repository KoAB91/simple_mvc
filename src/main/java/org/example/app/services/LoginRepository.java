package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.LoginForm;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LoginRepository implements ProjectRepository<LoginForm> {

    private final Logger logger = Logger.getLogger(LoginRepository.class);
    private final List<LoginForm> repo = new ArrayList<>();

    @Override
    public List<LoginForm> retreiveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(LoginForm form) {
        form.setId(form.hashCode());
        logger.info("store new user with user-form: " + form);
        repo.add(form);
    }

    @Override
    public boolean removeItemById(Integer itemIdToRemove) {
        return false;
    }

    @Override
    public boolean removeItemByParameter(LoginForm item) {
        return false;
    }
}
