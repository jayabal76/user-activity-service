package com.useractivity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name="user_action")
public class UserAction implements Serializable {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="actionid")
    private Integer actionId;

    @Column(name="type")
    private String type;

    @CreationTimestamp
    private LocalDateTime time;

    @ElementCollection
    private Map<String, String> properties;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "user_id_fk", referencedColumnName = "userid"),
            @JoinColumn(name = "session_id_fk", referencedColumnName = "sessionid"),
    })
    private UserSession userSession;

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserSession getUserSession() {
        return userSession;
    }

    public void setUserSession(UserSession userSession) {
        this.userSession = userSession;
    }

    public Map<String, String> getProperties() {
        if(properties == null)
            properties = new HashMap<>();
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "UserAction{" +
                "actionId=" + actionId +
                ", type='" + type + '\'' +
                '}';
    }
}
