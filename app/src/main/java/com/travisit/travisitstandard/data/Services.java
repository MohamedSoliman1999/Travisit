package com.travisit.travisitstandard.data;

import com.google.gson.JsonObject;
import com.travisit.travisitstandard.model.User;
import com.travisit.travisitstandard.model.forms.SignInForm;
import com.travisit.travisitstandard.model.forms.SignUpForm;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.POST;

public interface Services {

    @POST("users/signup")
    public Observable<User>
    signUpBusiness(
            @Body SignUpForm userData);

    @POST("users/login")
    public Observable<User>
    signInBusiness(
            @Body SignInForm userData);

}
