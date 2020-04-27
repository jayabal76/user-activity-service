package com.useractivity.service;

import com.useractivity.UserActionInfo;
import com.useractivity.model.UserSession;

import java.util.List;

public interface IUserActivityService {
    UserSession updateOrCreateUserActivityLog(String userId, String sessionId, UserActionInfo UserActionInfo);
    UserSession findByUserSessionId(String userId, String sessionId);
    List<UserSession> findByUserId(String userId);
}
