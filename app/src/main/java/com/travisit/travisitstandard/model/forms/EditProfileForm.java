package com.travisit.travisitstandard.model.forms;

import java.io.Serializable;

public class EditProfileForm implements Serializable {
    String fullName;
    String email;
    String phone;

    public EditProfileForm(String fullName, String email, String phone) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
    }
}
