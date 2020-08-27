package com.travisit.travisitstandard.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.travisit.travisitstandard.model.Language;
import com.travisit.travisitstandard.model.Offer;
import com.travisit.travisitstandard.model.Tour;
import com.travisit.travisitstandard.model.User;
import com.travisit.travisitstandard.model.forms.EditGuideProfileForm;
import com.travisit.travisitstandard.model.forms.EditTravelerProfileForm;
import com.travisit.travisitstandard.model.forms.EmailForm;
import com.travisit.travisitstandard.model.forms.ResetPasswordForm;
import com.travisit.travisitstandard.model.forms.SignInForm;
import com.travisit.travisitstandard.model.forms.SignUpForm;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    private static OkHttpClient okHttpClient;
    private Services services;
    private static Client INSTANCE;

    private static OkHttpClient initHttpClient(String token){
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(Const.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Const.REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Const.WRITE_TIMEOUT, TimeUnit.SECONDS);
        httpClient.addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Request.Builder requestBuilder = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json");
//                        .addHeader("Accept", "application/json")

                if (token != null)
                    requestBuilder.addHeader("Authorization", "JWT "+token);
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        return httpClient.build();
    }

    public Client() {
        if(okHttpClient == null){
            reinstantiateClient(null);
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.DEFAULT_SERVER_ADDRESS)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        services = retrofit.create(Services.class);
    }

    public static Client getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new Client();
        }
        return INSTANCE;
    }

    public static void reinstantiateClient(String token) {
        okHttpClient = initHttpClient(token);
        INSTANCE = new Client();
    }

    public Observable<User> signUp(SignUpForm userData) {
        return services.signUp(userData);
    }

    public Observable<User> signIn(SignInForm userData) {
        return services.signIn(userData);
    }

    public Observable<JsonObject> requestResetPasswordCode(EmailForm email) {
        return services.sendResetPasswordCode(email);
    }

    public Observable<JsonObject> confirmResetCode(String resetCode) {
        return services.confirmResetCode(resetCode);
    }

    public Observable<JsonObject> resetPassword(ResetPasswordForm newPassword) {
        return services.resetPassword(newPassword);
    }


    public Observable<User> editTravelerProfile(EditTravelerProfileForm userData) {
        return services.editTravelerProfile(userData);
    }

    public Observable<User> editGuideProfile(EditGuideProfileForm userData) {
        return services.editGuideProfile(userData);
    }

    public Observable<User> changeUserProfilePicture(MultipartBody.Part ppFile) {
        return services.changeUserProfilePicture(ppFile);
    }
    public Observable<ArrayList<Tour>> getTours() {
        return services.getTours();
    }
    public Observable<ArrayList<Offer>> getOffers() {
        return services.getOffers();
    }
    public Completable deleteTour(int id) {
        return services.deleteTour(id);
    }
    public Observable<ArrayList<Language>> getLanguages() {
        return services.getLanguages(0,50);
    }

}
