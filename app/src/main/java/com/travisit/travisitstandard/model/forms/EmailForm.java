package com.travisit.travisitstandard.model.forms;

import java.io.Serializable;

public class EmailForm implements Serializable {
    private String email;

    public EmailForm(String email) {
        this.email = email;
    }

}
