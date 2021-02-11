package com.travisit.travisitstandard.vvm.vm;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.travisit.travisitstandard.data.BusinessGuid;
import com.travisit.travisitstandard.data.Client;
import com.travisit.travisitstandard.model.User;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TourGuidVM extends ViewModel {
    public MutableLiveData<ArrayList<BusinessGuid>> arrayListMutableLiveData=new MutableLiveData<ArrayList<BusinessGuid>>();
    CompositeDisposable compositeDisposable;
    public void getTourGuid(int page,int limit){
        Observable<ArrayList<BusinessGuid>> observable = Client.getINSTANCE().getGuides(page,limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(observable.subscribe(o -> arrayListMutableLiveData.setValue(o), e -> Log.d("PVMError", e.getMessage())));
   }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (compositeDisposable!=null){
            compositeDisposable.clear();
        }
    }
}
