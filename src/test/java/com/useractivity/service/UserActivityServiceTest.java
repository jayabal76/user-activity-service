package com.useractivity.service;

import com.useractivity.UserActionInfo;
import com.useractivity.customError.RecordNotFoundException;
import com.useractivity.model.UserAction;
import com.useractivity.model.UserSession;
import com.useractivity.repo.IUserSession;
import com.useractivity.service.impl.UserActivityServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UserActivityServiceTest {

    @TestConfiguration
    static class UserActivityServiceImplTestContextConfiguration {

        @Bean
        public IUserActivityService iUserActivityService() {
            return new UserActivityServiceImpl();
        }
    }

    @MockBean(name = "repo")
    IUserSession iUserSession;
    @Autowired
    private IUserActivityService iUserActivityService;


    private UserSession getUserSession(){
        UserSession userSession = new UserSession();
        userSession.setUserId("1");
        userSession.setSessionId("1a");
        UserAction userAction = new UserAction();
        userAction.setType("CLICK");
        userAction.getProperties().put("viewedId", "FDJKLHSLD");
        userSession.setActions(new ArrayList<>(Arrays.asList(userAction)));
        return userSession;
    }


    @Test
    public void test_findByUserId() {
        UserSession userSession = getUserSession();
        when(iUserSession.findByUserId(anyString())).thenReturn(Optional.of(Arrays.asList(userSession)));
        List<UserSession> userSessions =  iUserActivityService.findByUserId("1");
        Assert.assertEquals(userSessions.get(0), userSession);
    }
    @Test(expected= RecordNotFoundException.class)
    public void test_findByUserId_notFound() {
        UserSession userSession = getUserSession();
        when(iUserSession.findByUserId(anyString())).thenReturn(Optional.empty());
       iUserActivityService.findByUserId("1");
    }

    @Test
    public void test_findByUserSessionId() {
        UserSession userSession = getUserSession();

        when(iUserSession.findById(anyObject())).thenReturn(Optional.of(userSession));
        UserSession  userSessions =  iUserActivityService.findByUserSessionId("1", "1a");
        Assert.assertEquals(userSessions, userSession);
    }

    @Test(expected= RecordNotFoundException.class)
    public void test_findByUserSessionId_notFound() {
        UserSession userSession = getUserSession();
        when(iUserSession.findById(anyObject())).thenReturn(Optional.empty());
        iUserActivityService.findByUserSessionId("1", "1a");
    }

    @Test
    public void test_updateOrCreateUserActivityLog_create() {
        UserSession userSession = getUserSession();

        UserAction userAction = new UserAction();
        userAction.setType("NAVIGATE");
        userAction.getProperties().put("pageFrom", "communities");
        UserActionInfo userActionInfo = new UserActionInfo();
        userActionInfo.setActions(new ArrayList<>(Arrays.asList(userAction)));

        when(iUserSession.findById(anyObject())).thenReturn(Optional.empty());
        when(iUserSession.save(anyObject())).thenReturn(userSession);

        UserSession  userSessions =  iUserActivityService.updateOrCreateUserActivityLog("1", "1a", userActionInfo);

        Assert.assertTrue(userSessions.getUserId().equals("1"));
        Assert.assertTrue(userSessions.getSessionId().equals("1a"));
        Assert.assertTrue(userSessions.getActions().get(0).getType().equals("CLICK"));


    }
    @Test
    public void test_updateOrCreateUserActivityLog_update() {
        UserSession userSession = getUserSession();

        UserAction userAction = new UserAction();
        userAction.setType("NAVIGATE");
        userAction.getProperties().put("pageFrom", "communities");

        UserActionInfo userActionInfo = new UserActionInfo();
        userActionInfo.setActions(new ArrayList<>(Arrays.asList(userAction)));

        UserSession expected = getUserSession();
        expected.addActions(Arrays.asList(userAction));

        when(iUserSession.findById(anyObject())).thenReturn(Optional.of(userSession));
        when(iUserSession.save(anyObject())).thenReturn(expected);

        UserSession  userSessions =  iUserActivityService.updateOrCreateUserActivityLog("1", "1a", userActionInfo);

        Assert.assertTrue(userSessions.getUserId().equals("1"));
        Assert.assertTrue(userSessions.getSessionId().equals("1a"));
        Assert.assertTrue(userSessions.getActions().get(0).getType().equals("CLICK"));
        Assert.assertTrue(userSessions.getActions().get(1).getType().equals("NAVIGATE"));


    }
}
