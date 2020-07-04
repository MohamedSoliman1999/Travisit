package com.travisit.travisitstandard.model.forms;

public class SignUpForm extends EmailForm {
    private String fullName;
    private String password;
    private String type;
    private String phone;

    public SignUpForm(String email, String fullName, String password, String type, String phone) {
        super(email);
        this.fullName = fullName;
        this.password = password;
        this.type = type;
        this.phone = phone;
    }
}
