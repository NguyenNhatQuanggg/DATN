package com.project2.banhangmypham.animation;

import static kotlinx.coroutines.flow.FlowKt.subscribeOn;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.project2.banhangmypham.admin.HomeAdminActivity;
import com.project2.banhangmypham.common_screen.LoginActivity;
import com.project2.banhangmypham.MainActivity;
import com.project2.banhangmypham.localstorage.LocalStorageManager;
import com.project2.banhangmypham.model.User;
import com.project2.banhangmypham.repository.account.AccountRepositoryImpl;
import com.project2.banhangmypham.service.ApiClientService;
import com.project2.banhangmypham.viewmodel.account.AccountViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SplashScreen extends AppCompatActivity {

    AccountViewModel accountViewModel ;
    String userInfo = "";
    boolean isFlag = false ;
    Thread timer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        accountViewModel.setAccountRepository(new AccountRepositoryImpl());

        accountViewModel.getValidTokenLiveData().observe(this, result -> {
            isFlag = true;
            if (timer != null ){
                timer.interrupt();
            }
            if (result != null) {
                if (result.getCode() == 200) {
                    User user = new Gson().fromJson(userInfo, User.class);
                    if (!result.isData()) {
                        Intent intentUser =  new Intent(SplashScreen.this, MainActivity.class);
                        intentUser.putExtra("loginState",user);
                        Log.d("TAG", "onCreate: ===> user = "+ user);
                        startActivity(intentUser);
                    }else {
                        Intent intentUser =  new Intent(SplashScreen.this, HomeAdminActivity.class);
                        intentUser.putExtra("loginState",user);
                        startActivity(intentUser);
                    }
                } else {

                    startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                }
                finish();
            }
        });
        timer = new Thread() {
            @SuppressLint("CheckResult")
            public void run() {
                try {
                    sleep(700);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    Log.d("TAG", "run: ====> verify ");
                    Flowable<Pair<String, String>> combinedData = Flowable.zip(
                            LocalStorageManager.getInstance().userData,
                            LocalStorageManager.getInstance().tokenData,
                            Pair::new);

                    combinedData.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(result -> {
                                if (isFlag) return;
                                if (result.first.isEmpty()) {
                                    isFlag = true ;
                                    timer.interrupt();
                                    startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                                    finish();
                                } else {
                                    ApiClientService.getInstance().setToken(result.first);
                                    userInfo = result.second;
                                    accountViewModel.checkValidToken(result.first);
                                }
                            }, error ->{
                                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                                finish();
                            });
                }
            }
        };
        timer.start();
    }
}
