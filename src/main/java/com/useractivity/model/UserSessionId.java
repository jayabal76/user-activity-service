package com.useractivity.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//@Embeddable
public class UserSessionId implements Serializable {

   // @Id
//    @Column(name="userid")
//    @NotBlank(message = "userId must not be empty")
    private String userId;

//
//    @Column(name="sessionid")
//    @NotBlank(message = "sessionId must not be empty")
    private String sessionId;



    public UserSessionId() {
    }

    public UserSessionId(String userId, String sessionId) {
        this.userId = userId;
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSessionId that = (UserSessionId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(sessionId, that.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, sessionId);
    }
}
