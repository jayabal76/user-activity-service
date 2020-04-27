package com.useractivity.service.impl;

import com.useractivity.UserActionInfo;
import com.useractivity.customError.RecordNotFoundException;
import com.useractivity.model.UserSession;
import com.useractivity.model.UserSessionId;
import com.useractivity.repo.IUserSession;
import com.useractivity.service.IUserActivityService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserActivityServiceImpl implements IUserActivityService {
    private static final Logger LOG = Logger.getLogger(UserActivityServiceImpl.class);

    @Autowired
    IUserSession iUserSession;

    @Override
    public UserSession updateOrCreateUserActivityLog(String userId, String sessionId, UserActionInfo userActionInfo){
        // fetch employee by composite key
        Optional<UserSession> optionalUserSession = iUserSession.findById(new UserSessionId(userId, sessionId));
        if(optionalUserSession.isPresent()){
            UserSession updateUserSession = optionalUserSession.get();
            updateUserSession.addActions(userActionInfo.getActions());
            return iUserSession.save(updateUserSession);
        } else {
            UserSession userSession = new UserSession();
            userSession.setUserId(userId);
            userSession.setSessionId(sessionId);
            userSession.setActions(userActionInfo.getActions());
            return iUserSession.save(userSession);
        }

    }
    @Override
    public UserSession findByUserSessionId(String userId, String sessionId){
        Optional<UserSession> optionalUserSession = iUserSession.findById(new UserSessionId(userId, sessionId));
        if(optionalUserSession.isPresent())
            return optionalUserSession.get();
        else
            throw new RecordNotFoundException("Not found user with session id");
    }


    @Override
    public List<UserSession>  findByUserId(String userId){
        Optional<List<UserSession>> optionalUserSession = iUserSession.findByUserId(userId);
        if(optionalUserSession.isPresent())
            return optionalUserSession.get();
        else
            throw new RecordNotFoundException("Not found user actvity");

    }

}
