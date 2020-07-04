package com.travisit.travisitstandard.model.forms;


public class SignInForm extends EmailForm {

    private String password;

    public SignInForm(String email, String password) {
        super(email);
        this.password = password;
    }
}
