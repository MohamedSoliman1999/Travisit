package com.travisit.travisitstandard.vvm.vm;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.travisit.travisitstandard.data.Client;
import com.travisit.travisitstandard.model.Area;
import com.travisit.travisitstandard.model.User;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileVM extends ViewModel {
    public MutableLiveData<JsonObject> profileMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ArrayList<Area>> categoriesMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<User> filePMutableLiveData = new MutableLiveData<>();
    CompositeDisposable compositeDisposable;
    public ArrayList<Integer> selectedAreas = new ArrayList<>();
    public ArrayList<Integer> selectedInterests = new ArrayList<>();

    public void editGuideProfile() {
        
    }    
    public void editTravelerProfile() {
        
    }

    public void getInterests() {
       
    }
    public void getAreas() {
       
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

////    public void uploadFile(String filePath, Context context, FileType fileType) {
//    public void uploadFiles(String filePath1, String filePath2) {
////        String fileName = null;
////        if (fileType == FileType.GOVERNMENT_ISSUED_NUMBER) {
////            fileName = "governmentIssuedNumberImage";
////        } else {
////            fileName = "logo";
////        }
////        if (fileType != null && filePath != null && !filePath.isEmpty()) {
//        if (filePath1 != null && !filePath1.isEmpty() && filePath2 != null && !filePath2.isEmpty()) {
////            File file = new File(filePath);
////            RequestBody requestFile =
////                    null;
////            try {
////                requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),
////                         new Compressor(context).compressToFile(file));
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////            RequestBody requestFile =
////                    RequestBody.create(MediaType.parse("image/*"), file);
////            MultipartBody.Part body =
////                    MultipartBody.Part.createFormData(fileName, file.getName(), requestFile);
//
////            compositeDisposable = new CompositeDisposable();
//
////            if (fileType == FileType.GOVERNMENT_ISSUED_NUMBER) {
////                Observable<Business> observable = Client.getINSTANCE().uploadIssuedNumberLogo(body)
////                        .subscribeOn(Schedulers.io())
////                        .observeOn(AndroidSchedulers.mainThread());
////                compositeDisposable.add(observable.subscribe(o -> fileGMutableLiveData.setValue(o), e -> Log.d("PVMError", e.getMessage())));
////            } else {
////                Observable<Business> observable = Client.getINSTANCE().changeBusinessLogo(body)
////                        .subscribeOn(Schedulers.io())
////                        .observeOn(AndroidSchedulers.mainThread());
////                compositeDisposable.add(observable.subscribe(o -> fileLMutableLiveData.setValue(o), e -> Log.d("PVMError", e.getMessage())));
////            }
//            File file1 = new File(filePath1);
//            File file2 = new File(filePath2);
//            RequestBody requestFile1 =
//                    RequestBody.create(MediaType.parse("image/*"), file1);
//            MultipartBody.Part body1 =
//                    MultipartBody.Part.createFormData("logo", file1.getName(), requestFile1);
//
//            RequestBody requestFile2 =
//                    RequestBody.create(MediaType.parse("image/*"), file2);
//            MultipartBody.Part body2 =
//                    MultipartBody.Part.createFormData("governmentIssuedNumberImage", file2.getName(), requestFile2);
//
//            compositeDisposable = new CompositeDisposable();
//            Observable<Business> observable = Client.getINSTANCE().uploadBusinessPhotos(body1, body2)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread());
//            compositeDisposable.add(observable.subscribe(o -> photosMutableLiveData.setValue(o), e -> Log.d("PVMError", e.getMessage())));
//
//        }
//    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if(compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }
}
