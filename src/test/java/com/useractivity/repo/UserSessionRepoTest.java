package com.useractivity.repo;

import com.useractivity.model.UserAction;
import com.useractivity.model.UserSession;
import com.useractivity.model.UserSessionId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserSessionRepoTest {
    @Autowired
    private IUserSession repo;

    private UserSession getUserSession(){
        UserSession userSession = new UserSession();
        userSession.setUserId("1");
        userSession.setSessionId("1a");
        UserAction userAction = new UserAction();
        userAction.setType("CLICK");
        userAction.getProperties().put("viewedId", "FDJKLHSLD");
        userSession.setActions(Arrays.asList(userAction));
        return userSession;
    }
    @Test
    public void saveAndFindById() {
        UserSession userSession = getUserSession();
        repo.save(userSession);
        repo.save(userSession);
        Optional<List<UserSession>> optionalUserSession = repo.findByUserId(userSession.getUserId());
        Assert.assertTrue(optionalUserSession.isPresent());
        List<UserSession> userSessions = optionalUserSession.get();
        UserSession foundUserSessionObj = userSessions.get(0);
        Assert.assertTrue(foundUserSessionObj.getUserId().equals("1"));
        Assert.assertTrue(foundUserSessionObj.getSessionId().equals("1a"));
        Assert.assertTrue(foundUserSessionObj.getActions().get(0).getType().equals("CLICK"));
    }

    @Test
    public void saveAndFindByUserSessionId() {
        UserSession userSession = getUserSession();
        repo.save(userSession);
        Optional<UserSession> optionalUserSession = repo.findById(new UserSessionId(
               userSession.getUserId(), userSession.getSessionId()));
        Assert.assertTrue(optionalUserSession.isPresent());
        UserSession foundUserSessionObj = optionalUserSession.get();
        Assert.assertTrue(foundUserSessionObj.getUserId().equals("1"));
        Assert.assertTrue(foundUserSessionObj.getSessionId().equals("1a"));
        Assert.assertTrue(foundUserSessionObj.getActions().get(0).getType().equals("CLICK"));
    }

    @Test
    public void findByUserSessionIdAndUpdate() {
        UserSession userSession = getUserSession();
        repo.save(userSession);
        Optional<UserSession> optionalUserSession = repo.findById(new UserSessionId(
                userSession.getUserId(), userSession.getSessionId()));
        Assert.assertTrue(optionalUserSession.isPresent());
        UserSession foundUserSessionObj = optionalUserSession.get();

        UserAction userAction = new UserAction();
        userAction.setType("NAVIGATE");
        userAction.getProperties().put("pageFrom", "communities");
        userSession.setActions(Arrays.asList(userAction));
        foundUserSessionObj.addActions(Arrays.asList(userAction));

        Assert.assertTrue(foundUserSessionObj.getUserId().equals("1"));
        Assert.assertTrue(foundUserSessionObj.getSessionId().equals("1a"));
        Assert.assertTrue(foundUserSessionObj.getActions().get(0).getType().equals("CLICK"));
        Assert.assertTrue(foundUserSessionObj.getActions().get(1).getType().equals("NAVIGATE"));

    }

}
