package com.travisit.travisitstandard.vvm.vm;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.travisit.travisitstandard.data.Client;
import com.travisit.travisitstandard.model.Tour;
import com.travisit.travisitstandard.model.TourComment;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class TourVM extends ViewModel {
    public MutableLiveData<ArrayList<Tour>> toursMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Tour> tourMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ArrayList<TourComment>> TourCommentsMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> deletingMutableLiveData = new MutableLiveData<>();
    public String startDate = null;
    public String endDate = null;
    public Boolean isPublic=true;
    //public ArrayList<Integer> selectedTags = new ArrayList<>();
    CompositeDisposable compositeDisposable;
    CompositeDisposable compositeDisposable2;
    public MutableLiveData<Tour> photosMutableLiveData = new MutableLiveData<>();
    public void addtour(Tour tour) {
        Observable<Tour> observable = Client.getINSTANCE().addTour(tour)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(observable.subscribe(o -> tourMutableLiveData.setValue(o), e -> Log.d("PVMError", e.getMessage())));
    }
    /*
    public void edittour(Tour tour) {
        Observable<Tour> observable = Client.getINSTANCE().updatetour(tour)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(observable.subscribe(o -> tourMutableLiveData.setValue(o), e -> Log.d("PVMError", e.getMessage())));
    }
*/
    public void uploadFiles(String filePath1, String filePath2, String filePath3) {

        if (filePath1 != null && !filePath1.isEmpty()
                && filePath2 != null && !filePath2.isEmpty()
                && filePath3 != null && !filePath3.isEmpty()) {
            File file1 = new File(filePath1);
            File file2 = new File(filePath2);
            File file3 = new File(filePath3);
            RequestBody requestFile1 =
                    RequestBody.create(MediaType.parse("image/*"), file1);
            MultipartBody.Part body1 =
                    MultipartBody.Part.createFormData("firstImage", file1.getName(), requestFile1);

            RequestBody requestFile2 =
                    RequestBody.create(MediaType.parse("image/*"), file2);
            MultipartBody.Part body2 =
                    MultipartBody.Part.createFormData("secondImage", file2.getName(), requestFile2);

            RequestBody requestFile3 =
                    RequestBody.create(MediaType.parse("image/*"), file3);
            MultipartBody.Part body3 =
                    MultipartBody.Part.createFormData("thirdImage", file3.getName(), requestFile3);

            compositeDisposable2 = new CompositeDisposable();
            Observable<Tour> observable = Client.getINSTANCE().uploadTourPhotos(body1, body2, body3)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            compositeDisposable2.add(observable.subscribe(o -> photosMutableLiveData.setValue(o), e -> Log.d("PVMError", e.getMessage())));

        }
    }

//    private ArrayList<Tour> parseTours(JsonArray jsonArray) {
//        ArrayList<Tour> tours = new ArrayList<Tour>();
////        JsonArray jsonArray = jsonObject.get("rows").getAsJsonArray();
//        Log.d("rbusinessXXXXXX", jsonArray.toString());
//
//        for (int i = 0; i < jsonArray.size(); i++) {
//            JsonObject tourObject = jsonArray.get(i).getAsJsonObject();
//            Log.d("addedtour","startdate: "+tourObject.get("startDate").getAsString());
//            Log.d("addedtour","enddate: "+tourObject.get("endDate").getAsString());
///*
//            String img1 = "";
//            String img2 = "";
//            String img3 = "";
//            if(tourObject.has("firstImage")){
//                img1 = tourObject.get("firstImage").getAsString();
//                Log.d("addedtour","img1:happy: "+img1);
//            } else {
//                Log.d("addedtour","img1:not sad: "+img1);
//            }
//*/
//
//            Tour tour = new Tour(
//                    tourObject.get("id").getAsInt(),
//                    tourObject.get("title").getAsString(),
//                    tourObject.get("program").getAsString(),
//                    tourObject.get("hourRate").getAsDouble(),
//                    tourObject.get("startDate").getAsString(),
//                    tourObject.get("endDate").getAsString(),
//                    tourObject.get("type").getAsString()
//
//            );
//            /*                    tourObject.get("firstImage").getAsString(),
//                    tourObject.get("secondImage").getAsString(),
//                    tourObject.get("thirdImage").getAsString()*/
//            tours.add(tour);
//        }
//        return tours;
//    }
    /*
    public void getTourComments(Integer tourId, Integer pageNumber, Integer pageSize) {
        Observable<ArrayList<TourComment>> observable = Client.getINSTANCE().getTourComments(tourId, pageNumber, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(observable.subscribe(o -> {
            Log.d("hey","heyg"+o.size());
            TourCommentsMutableLiveData.setValue(o);
        }, e -> Log.d("PVMError", e.getMessage())));
    }*/
    @Override
    protected void onCleared() {
        super.onCleared();
        if(compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }
}
