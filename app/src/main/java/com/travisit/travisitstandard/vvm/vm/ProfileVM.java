package com.travisit.travisitstandard.vvm.vm;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.travisit.travisitstandard.data.Client;
import com.travisit.travisitstandard.model.Area;
import com.travisit.travisitstandard.model.Language;
import com.travisit.travisitstandard.model.User;
import com.travisit.travisitstandard.model.forms.EditGuideProfileForm;
import com.travisit.travisitstandard.model.forms.EditTravelerProfileForm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileVM extends ViewModel {
    public MutableLiveData<User> profileMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ArrayList<Area>> categoriesMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ArrayList<Language>> languagesMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<User> filePMutableLiveData = new MutableLiveData<>();
    CompositeDisposable compositeDisposable;
    public ArrayList<Integer> selectedAreas = new ArrayList<>();
    public ArrayList<Integer> selectedInterests = new ArrayList<>();

    public void editGuideProfile(String fullName, String email, String phone, String education, String workExperience, Integer hourlyRate, Integer membershipNumber, Integer licenseNumber) {
        EditGuideProfileForm editProfileForm = new EditGuideProfileForm(fullName, email, phone, education, workExperience, hourlyRate, membershipNumber, licenseNumber);
        Observable<User> observable = Client.getINSTANCE().editGuideProfile(editProfileForm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(observable.subscribe(o -> profileMutableLiveData.setValue(o), e -> Log.d("PVMError", e.getMessage())));
    }
    public void editGuideProfileWithLinks(String fullName, String email, String phone, String education, String workExperience, Integer hourlyRate, Integer membershipNumber, Integer licenseNumber, String facebookLink, String twitterLink, String linkedinLink, String instagramLink) {
        EditGuideProfileForm editProfileForm = new EditGuideProfileForm(fullName, email, phone, education, workExperience, hourlyRate, membershipNumber, licenseNumber, facebookLink, twitterLink, linkedinLink, instagramLink);
        Observable<User> observable = Client.getINSTANCE().editGuideProfile(editProfileForm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(observable.subscribe(o -> profileMutableLiveData.setValue(o), e -> Log.d("PVMError", e.getMessage())));
    }
    public void editTravelerProfile(String fullName, String email, String phone, String dateOfBirth, String nationality) {
        EditTravelerProfileForm editProfileForm = new EditTravelerProfileForm(fullName, email, phone, dateOfBirth, nationality);
        Observable<User> observable = Client.getINSTANCE().editTravelerProfile(editProfileForm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(observable.subscribe(o -> profileMutableLiveData.setValue(o), e -> Log.d("PVMError", e.getMessage())));
    }

    public void getInterests() {
       
    }
    public void getAreas() {
       
    }
    public void getLanguages() {
        Observable<ArrayList<Language>> observable = Client.getINSTANCE().getLanguages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(observable.subscribe(o -> languagesMutableLiveData.setValue(o), e -> Log.d("PVMError", e.getMessage())));
    }

  //  private ArrayList<Area> parseCategories(JsonObject jsonObject) {
//        ArrayList<Category> categories = new ArrayList<Category>();
//        JsonArray jsonArray = jsonObject.get("category").getAsJsonArray();
//        Log.d("businessXXXXXX", jsonArray.toString());
//
//        for (int i = 0; i < jsonArray.size(); i++) {
//            JsonObject categoryObject = jsonArray.get(i).getAsJsonObject();
//            Category category = new Category(
//                    categoryObject.get("id").getAsInt(),
//                    categoryObject.get("name").getAsString()
//            );
//            categories.add(category);
//        }
//        return categories;
        //}

    public void uploadFile(String filePath) {
        String fileName = "profilePicture";
        if (filePath != null && !filePath.isEmpty()) {
            File file1 = new File(filePath);
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("image/*"), file1);
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("logo", file1.getName(), requestFile);

            compositeDisposable = new CompositeDisposable();
            Observable<User> observable = Client.getINSTANCE().changeUserProfilePicture(body)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            compositeDisposable.add(observable.subscribe(o -> profileMutableLiveData.setValue(o), e -> Log.d("PVMError", e.getMessage())));
        }
    }
    public void getProfile() {
        Observable<User> observable = Client.getINSTANCE().getProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(observable.subscribe(o -> profileMutableLiveData.setValue(o), e -> Log.d("PVMError", e.getMessage())));
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        if(compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }
}
