package com.useractivity.controller;

import com.useractivity.UserActionInfo;
import com.useractivity.model.UserSession;
import com.useractivity.service.IUserActivityService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserActivityController {
    private static final Logger LOG = Logger.getLogger(UserActivityController.class);

    @Autowired
    private IUserActivityService iUserActivityService;


    @GetMapping(value = "/users/{id}", produces = "application/json")
    public List<UserSession> getUserActivityLog(@PathVariable("id") String id) {
        return iUserActivityService.findByUserId(id);
    }

    @GetMapping(value = "/users/{userid}/session/{sessionid}", produces = "application/json")
    public UserSession findUserActivityLog(@PathVariable("userid") String userid,  @PathVariable("sessionid") String sessionid) {
        return iUserActivityService.findUserActivityLog(userid, sessionid);
    }

    @PutMapping(value = "/users/{userid}/session/{sessionid}", produces = "application/json")
    public UserSession updateOrCreateUserActivityLog(@PathVariable("userid") String userid, @PathVariable("sessionid") String sessionid,
                                                    @RequestBody UserActionInfo userActionInfo) {
        return iUserActivityService.updateOrCreateUserActivityLog(userid, sessionid, userActionInfo);
    }

}
