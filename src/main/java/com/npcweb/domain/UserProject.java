package com.npcweb.domain;

import javax.persistence.*;

@Entity
@Table(name = "USER_PROJECT")
public class UserProject {
    @EmbeddedId
    private UserProjectId id;

    public void setId(UserProjectId id) {
        this.id = id;
    }

    public UserProjectId getId() {
        return id;
    }
}
