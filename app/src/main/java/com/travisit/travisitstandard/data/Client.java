package com.travisit.travisitstandard.data;

import com.google.gson.JsonObject;
import com.travisit.travisitstandard.model.User;
import com.travisit.travisitstandard.model.forms.SignInForm;
import com.travisit.travisitstandard.model.forms.SignUpForm;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Interceptor;
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

    public Observable<User> signup(SignUpForm userData) {
        return services.signUpBusiness(userData);
    }

    public Observable<User> signIn(SignInForm userData) {
        return services.signInBusiness(userData);
    }

}
