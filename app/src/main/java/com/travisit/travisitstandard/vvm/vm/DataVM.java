package com.travisit.travisitstandard.vvm.vm;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.travisit.travisitstandard.data.Client;
import com.travisit.travisitstandard.model.Offer;
import com.travisit.travisitstandard.model.Tour;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DataVM extends ViewModel {
    public MutableLiveData<ArrayList<Offer>> offersMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ArrayList<Tour>> toursMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> deletingMutableLiveData = new MutableLiveData<>();

    CompositeDisposable compositeDisposable;

    public void getOffers() {
        Observable<ArrayList<Offer>> observable = Client.getINSTANCE().getOffers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(observable.subscribe(o -> {
            offersMutableLiveData.setValue(o);
        }, e -> Log.d("PVMError", e.getMessage())));
    }
    public void getTours() {
        Observable<ArrayList<Tour>> observable = Client.getINSTANCE().getTours()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(observable.subscribe(o -> {
            toursMutableLiveData.setValue(o);
        }, e -> Log.d("PVMError", e.getMessage())));
    }

    public void deleteTour(int id) {
        Completable observable = Client.getINSTANCE().deleteTour(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(observable.subscribe(() ->
                        deletingMutableLiveData.setValue("done")
                , e -> Log.d("PVMError", e.getMessage())));
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
