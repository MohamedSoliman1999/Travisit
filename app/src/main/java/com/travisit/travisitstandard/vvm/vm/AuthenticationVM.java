package com.travisit.travisitstandard.vvm.vm;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.travisit.travisitstandard.data.Client;
import com.travisit.travisitstandard.model.User;
import com.travisit.travisitstandard.model.forms.EmailForm;
import com.travisit.travisitstandard.model.forms.ResetPasswordForm;
import com.travisit.travisitstandard.model.forms.SignInForm;
import com.travisit.travisitstandard.utils.TravisitApp;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class AuthenticationVM extends ViewModel {
    public MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<JsonObject> newCodeMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<JsonObject> codeValidationMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<JsonObject> passwordResetMutableLiveData = new MutableLiveData<>();

    CompositeDisposable compositeDisposable;
    public void signIn(String email, String password){
        SignInForm signInForm = new SignInForm(email, password);
        Observable<Response<User>> observable = Client.getINSTANCE().signIn(signInForm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(
                observable.subscribe(o-> {
                    if (o.body().getError()!=null){
                        Toast.makeText(TravisitApp.getInstance().getApplicationContext(), o.body().getError(), Toast.LENGTH_SHORT).show();
                    }else{
                        userMutableLiveData.setValue(o.body());
                        Toast.makeText(TravisitApp.getInstance().getApplicationContext(), "Sign In Successfully", Toast.LENGTH_SHORT).show();
                    }
                    },
                e-> {Log.e("PVMErrorff",e.getMessage());
                    Toast.makeText(TravisitApp.getInstance().getApplicationContext(), "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                }));
    }

    public void RequestNewPasswordCode(String email){
        EmailForm emailForm = new EmailForm(email);
        Observable<JsonObject> observable = Client.getINSTANCE().requestResetPasswordCode(emailForm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(observable.subscribe(o->newCodeMutableLiveData.setValue(o), e-> Log.d("PVMError",e.getMessage())));
    }

    public void validateResetCode(String resetCode){
        Observable<JsonObject> observable = Client.getINSTANCE().confirmResetCode(resetCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(observable.subscribe(o->codeValidationMutableLiveData.setValue(o), e-> Log.d("PVMError",e.getMessage())));
    }

    public void passwordReset(String resetCode, String newPassword){
        ResetPasswordForm resetPasswordForm = new ResetPasswordForm(resetCode, newPassword);
        Observable<JsonObject> observable = Client.getINSTANCE().resetPassword(resetPasswordForm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(observable.subscribe(o->passwordResetMutableLiveData.setValue(o), e-> Log.d("PVMError",e.getMessage())));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        try {
            compositeDisposable.clear();
        }catch (Exception e){e.printStackTrace();}
    }
}
