package com.useractivity.controller;

import com.google.gson.Gson;
import com.useractivity.UserActionInfo;
import com.useractivity.customError.RecordNotFoundException;
import com.useractivity.model.UserSession;
import com.useractivity.service.IUserActivityService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * UserActivityControllerTests
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserActivityController.class)
public class UserActivityControllerTests {

    @MockBean(name = "repo")
    private IUserActivityService iUserActivityService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void testgetUserActivityLog() throws Exception {
        String uri = "/users/1";

        UserSession userSession = new UserSession();
        userSession.setUserId("1");

        when(iUserActivityService.findByUserId(anyString())).thenReturn(Arrays.asList(userSession));
        mvc.perform(get(uri)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(new Gson().toJson(Arrays.asList(userSession))));
    }

    @Test
    public void testgetUserActivityLog_notfound() throws Exception {
        when(iUserActivityService.findByUserId(anyString())).thenThrow(new RecordNotFoundException("not found"));
        mvc.perform(get("/users/1")).andExpect(status().isNotFound());
    }

//    @Test
//    public void newActivityLog() throws Exception {
//        String uri = "/users/1/session/1a";
//
//        UserSession userSession = new UserSession();
//        userSession.setUserId("1");
//        when(iUserActivityService.updateOrCreateUserActivityLog(anyString(),anyString(),any(UserActionInfo.class))).thenReturn(userSession);
//        String json = new Gson().toJson(userSession);
//        mvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(content().json(json));
//    }


}