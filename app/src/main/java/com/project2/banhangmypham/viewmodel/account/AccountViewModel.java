package com.project2.banhangmypham.viewmodel.account;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.project2.banhangmypham.model.AccountResponse;
import com.project2.banhangmypham.model.MessageResponse;
import com.project2.banhangmypham.model.User;
import com.project2.banhangmypham.repository.account.IAccountRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AccountViewModel extends ViewModel {
    public static final String TAG = "AccountViewModel";
    private IAccountRepository accountRepository ;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MutableLiveData<AccountResponse> _loggedLiveData = new MutableLiveData<>();
    private LiveData<AccountResponse> loggedLiveData = _loggedLiveData;

    private final MutableLiveData<AccountResponse> _signUpLiveData = new MutableLiveData<>();
    private LiveData<AccountResponse> signUpLiveData = _signUpLiveData;

    private final MutableLiveData<Boolean> _logOutLiveData = new MutableLiveData<>();
    private LiveData<Boolean> logOutLiveData = _logOutLiveData;

    private final MutableLiveData<String> _uploadImageLiveData = new MutableLiveData<>();
    private LiveData<String> uploadImageLiveData = _uploadImageLiveData;

    private final MutableLiveData<User> _userInfoLiveData = new MutableLiveData<>();
    private LiveData<User> userInfoLiveData = _userInfoLiveData;

    private final MutableLiveData<AccountResponse> _changePasswordLiveData = new MutableLiveData<>();
    private LiveData<AccountResponse> changePasswordLiveData = _changePasswordLiveData;

    private final MutableLiveData<AccountResponse> _updateInfoLiveData = new MutableLiveData<>();
    private LiveData<AccountResponse> updateInfoLiveData = _updateInfoLiveData;

    private final MutableLiveData<AccountResponse> _resetPasswordLiveData = new MutableLiveData<>();
    private LiveData<AccountResponse> resetPasswordLiveData = _resetPasswordLiveData;

    private final MutableLiveData<AccountResponse> _validTokenLiveData = new MutableLiveData<>();
    private LiveData<AccountResponse> validTokenLiveData = _validTokenLiveData;

    public LiveData<AccountResponse> getResetPasswordLiveData() {
        return resetPasswordLiveData;
    }

    public LiveData<AccountResponse> getUpdateInfoLiveData() {
        return updateInfoLiveData;
    }

    public LiveData<AccountResponse> getChangePasswordLiveData() {
        return changePasswordLiveData;
    }
    public void setLoginState(User loginState){
        _userInfoLiveData.postValue(loginState);
    }
    public LiveData<User> getUserInfoLiveData() {
        return userInfoLiveData;
    }

    public LiveData<AccountResponse> getLoggedLiveData() {
        return loggedLiveData;
    }

    public LiveData<AccountResponse> getSignUpLiveData() {
        return signUpLiveData;
    }

    public LiveData<String> getUploadImageLiveData() {
        return uploadImageLiveData;
    }

    public LiveData<Boolean> getLogOutLiveData() {
        return logOutLiveData;
    }

    public LiveData<AccountResponse> getValidTokenLiveData() {
        return validTokenLiveData;
    }

    public void setAccountRepository(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void login(String password, String email) {
        Disposable disposable = accountRepository.logIn(email,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result ->{
                            Log.d(TAG, "login: ====> result - "+ result.getCode() + " "+result.getMessage() + " " + result.getToken());
                                _loggedLiveData.postValue(result);
                            },
                            error ->{
                                Log.d(TAG, "login: ====> error = " + error.getMessage());
                                _loggedLiveData.postValue(new AccountResponse(404, error.getMessage()));
                            }
                        );

        compositeDisposable.add(disposable);
    }

    public void logOut() {
        Disposable disposable = accountRepository.logOut()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result ->{
                            _logOutLiveData.postValue(true);
                        },
                        error ->{
                            _logOutLiveData.postValue(false);
                        }
                );
        compositeDisposable.add(disposable);
    }
    public void signUp(User user){
        Disposable disposable = accountRepository.signUp(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        _signUpLiveData::postValue,
                        error ->{
                            _signUpLiveData.postValue(new AccountResponse(404, error.getMessage()));

                        }
                );
        compositeDisposable.add(disposable);
    }
    public void changePassword(String newPassword, String oldPassword, String uid) {
        Disposable disposable = accountRepository.changePassword(newPassword, oldPassword, uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        _changePasswordLiveData::postValue,
                        error ->{
                            _changePasswordLiveData.postValue(new AccountResponse(404,error.getMessage()));
                        }
                );
        compositeDisposable.add(disposable);
    }
    public void updateInfoAccount(User user) {
        Disposable disposable = accountRepository.updateInfoAccount(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        _updateInfoLiveData::postValue,
                        error ->{
                            _updateInfoLiveData.postValue(new AccountResponse(404,error.getMessage()));
                        }
                );
        compositeDisposable.add(disposable);
    }
    public void resetPassword(String email) {
        Disposable disposable = accountRepository.resetEmail(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        _resetPasswordLiveData::postValue,
                        error ->{
                            _resetPasswordLiveData.postValue(new AccountResponse(404, error.getMessage()));
                        }
                );
        compositeDisposable.add(disposable);
    }

    public void checkValidToken(String token) {
        Disposable disposable = accountRepository.checkTokenValid(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        _validTokenLiveData::postValue,
                        error ->{
                            _validTokenLiveData.postValue(new AccountResponse(404, error.getMessage()));
                        }
                );
        compositeDisposable.add(disposable);
    }
    public void clear(){
        compositeDisposable.clear();
    }
    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        compositeDisposable = null;
        super.onCleared();
    }
}
