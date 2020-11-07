package com.travisit.travisitstandard.data;

import com.google.gson.JsonObject;
import com.travisit.travisitstandard.model.Language;
import com.travisit.travisitstandard.model.Offer;
import com.travisit.travisitstandard.model.Tour;
import com.travisit.travisitstandard.model.TourComment;
import com.travisit.travisitstandard.model.User;
import com.travisit.travisitstandard.model.forms.EmailForm;
import com.travisit.travisitstandard.model.forms.ResetPasswordForm;
import com.travisit.travisitstandard.model.forms.SignInForm;
import com.travisit.travisitstandard.model.forms.SignUpForm;
import com.travisit.travisitstandard.model.forms.EditTravelerProfileForm;
import com.travisit.travisitstandard.model.forms.EditGuideProfileForm;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Services {

    @POST("users/signup")
    public Observable<User>
    signUp(@Body SignUpForm userData);

    @POST("users/login")
    public Observable<User>
    signIn(@Body SignInForm userData);

    @POST("business/resetPasswordCode")
    Observable<JsonObject> sendResetPasswordCode(@Body EmailForm email);

    //    @GET("business/confirmResetCode/?{resetCode}")
    @GET("business/confirmResetCode")
    Observable<JsonObject> confirmResetCode(@Query("resetCode") String resetCode);

    @POST("business/resetPassword")
    Observable<JsonObject> resetPassword(@Body ResetPasswordForm newPassword);


    @PUT("traveler")
    public Observable<User>
    editTravelerProfile(@Body EditTravelerProfileForm userData);
    //profile
    @PUT("guide")
    public Observable<User>
    editGuideProfile(@Body EditGuideProfileForm userData);
    @GET("business/me")
    Observable<User> getProfile();

    @Multipart
    @PUT("users/updateProfilePicture")
    Observable<User> changeUserProfilePicture(@Part MultipartBody.Part ppFile);
    //Offer
    @GET("offers")
    Observable<ArrayList<Offer>> getOffers();

    //Tour
    @GET("tour")
    public Observable<ArrayList<Tour>> getTours();
    @DELETE("offers/{tour_id}")
    Completable deleteTour(@Path("tour_id") int id);
    @POST("tour")
    Observable<Tour> addTour(@Body Tour tour);
    @Multipart
    @PUT("tour/images/{tour_id}")
    Observable<Tour> uploadTourPhotos(@Part MultipartBody.Part photo1, @Part MultipartBody.Part photo2, @Part MultipartBody.Part photo3);

    //tour comment
   @GET("tour-comments")
   Observable<ArrayList<TourComment>> getTourComments(@Query("TourId") Integer offerId, @Query("page_number") Integer pageNumber, @Query("page_size") Integer pageSize);
   @POST("tour-comments")
   Observable<TourComment>addTourComment(@Body TourComment tourComment);
   @PUT("tour-comments/{comment_id}")
   Observable<TourComment>editTourComment(@Path("comment_id")int commentId,@Body TourComment tourComment);
   @DELETE("tour-comments/{comment_id}")
   void deleteTourComment(@Path("comment_id")int commentId);

   @GET("languages")
   Observable<ArrayList<Language>> getLanguages(@Query("page_number") Integer pageNumber, @Query("page_size") Integer pageSize);

}
