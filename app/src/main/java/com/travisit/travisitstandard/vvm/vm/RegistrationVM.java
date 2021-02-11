package com.travisit.travisitstandard.vvm.vm;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.travisit.travisitstandard.data.Client;
import com.travisit.travisitstandard.model.User;
import com.travisit.travisitstandard.model.forms.SignUpForm;
import com.travisit.travisitstandard.utils.TravisitApp;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class RegistrationVM extends ViewModel {
    public MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
    CompositeDisposable compositeDisposable;
   public void signUp(String email, String password, String userType, String fullName){
       SignUpForm signUpForm = new SignUpForm(email, fullName, password, userType);
            Observable<User> observable = Client.getINSTANCE().signUp(signUpForm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(observable.subscribe(
                o-> {
                    if (o.getError()!=null){
                        Toast.makeText(TravisitApp.getInstance().getApplicationContext(), o.getError(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        userMutableLiveData.setValue(o);
                        Toast.makeText(TravisitApp.getInstance().getApplicationContext(), "Sign Up Successfully", Toast.LENGTH_SHORT).show();
                    }
                },
                e-> {
                    Toast.makeText(TravisitApp.getInstance().getApplicationContext(), "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                    Log.e("PVMError",e.getMessage());
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        try {
            compositeDisposable.clear();
        }catch (Exception e){}
    }
}
