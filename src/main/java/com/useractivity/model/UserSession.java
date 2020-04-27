package com.useractivity.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_session")
@IdClass(UserSessionId.class)
public class UserSession {
    @Id
    @Column(name="userid")
    @NotBlank(message = "userId must not be empty")
    private String userId;

    @Id
    @Column(name="sessionid")
    @NotBlank(message = "sessionId must not be empty")
    private String sessionId;


    @OneToMany(mappedBy = "userSession", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAction> actions = new ArrayList<>();

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

    public List<UserAction> getActions() {
        for (UserAction action: actions) {
            action.setUserSession(this);
        }
        return actions;
    }

    public void addActions(List<UserAction> actions) {
        for (UserAction action: actions) {
            action.setUserSession(this);
        }
        this.actions.addAll(actions);
    }

    public void setActions(List<UserAction> actions) {
        for (UserAction action: actions) {
            action.setUserSession(this);
        }
        this.actions = actions;
    }
}
