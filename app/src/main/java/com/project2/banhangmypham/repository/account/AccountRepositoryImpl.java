package com.project2.banhangmypham.repository.account;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.project2.banhangmypham.model.AccountResponse;
import com.project2.banhangmypham.model.MessageResponse;
import com.project2.banhangmypham.model.User;
import com.project2.banhangmypham.service.ApiClientService;
import com.project2.banhangmypham.service.ApiMethod;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AccountRepositoryImpl implements IAccountRepository {
    public static final String TAG = "AccountRepositoryImpl";
    public final ApiClientService apiClientService = new ApiClientService();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @SuppressLint("CheckResult")
    @Override
    public Single<AccountResponse> logIn(String email, String password) {
        return Single.create(emitter -> {
            User user = new User();
            user.setUsername(email);
            user.setPassword(password);
            String jsonRequest = new Gson().toJson(user);
            Log.d(TAG, "logIn: ====> request = "+jsonRequest);
            apiClientService.makeRequest("http://10.0.2.2:5000/user/login", ApiMethod.POST, AccountResponse.class,
                            RequestBody.create(jsonRequest,JSON)
                    ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(emitter::onSuccess, emitter::onError);
        });
    }
    @SuppressLint("CheckResult")
    @Override
    public Single<MessageResponse> logOut() {
        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/user/logout", ApiMethod.GET, MessageResponse.class,
                           null
                    ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(emitter::onSuccess, emitter::onError);
        });
    }
    @SuppressLint("CheckResult")
    @Override
    public Single<AccountResponse> signUp(User user) {
        String jsonRequest = new Gson().toJson(user);
        Log.d(TAG, "signUp: ==> jsonRequest =  "+ jsonRequest);
        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/user/register", ApiMethod.POST, AccountResponse.class,
                        RequestBody.create(jsonRequest,JSON)
                    ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {
                        if (user != null) {
                            emitter.onSuccess(result);
                        }else {
                            emitter.onError(new Throwable("Object is null"));
                        }
                    }, emitter::onError);
        });
    }

    @SuppressLint({"NewApi", "CheckResult"})
    @Override
    public Single<AccountResponse> changePassword(String newPassword, String oldPassword, String uid) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("newPassword", newPassword);
            jsonObject.put("oldPassword", oldPassword);
            jsonObject.put("id", uid);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        Log.d(TAG, "updateInfoAccount: ==> jsonRequest =  "+ jsonObject.toString());
        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/user/changePassword", ApiMethod.PUT, AccountResponse.class,
                            RequestBody.create(jsonObject.toString(),JSON)
                    ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {
                        if (result != null) {
                            emitter.onSuccess(result);
                        }else {
                            emitter.onError(new Throwable("Object is null"));
                        }
                    }, emitter::onError);
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public Single<AccountResponse> updateInfoAccount(User user) {
        String jsonRequest = new Gson().toJson(user);
        Log.d(TAG, "updateInfoAccount: ==> jsonRequest =  "+ jsonRequest);
        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/user/update", ApiMethod.PUT, AccountResponse.class,
                            RequestBody.create(jsonRequest,JSON)
                    ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {
                        if (result != null) {
                            emitter.onSuccess(result);
                        }else {
                            emitter.onError(new Throwable("Object is null"));
                        }
                    }, emitter::onError);
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public Single<AccountResponse> resetEmail(String email) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", email);
        } catch (JSONException e) {
            Log.d(TAG, "resetEmail: e = "+ e.getMessage());
        }
        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/user/resetPassword", ApiMethod.POST, AccountResponse.class,
                            RequestBody.create(jsonObject.toString(),JSON)
                    ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {
                        if (result != null) {
                            emitter.onSuccess(result);
                        }else {
                            emitter.onError(new Throwable("Object is null"));
                        }
                    }, emitter::onError);
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public Single<AccountResponse> checkTokenValid(String token) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", token);
        } catch (JSONException e) {
            Log.d(TAG, "checkTokenValid: e = "+ e.getMessage());
        }
        Log.d(TAG, "checkTokenValid: ==== token = " + token);
        return Single.create(emitter -> {
            apiClientService.makeRequest("http://10.0.2.2:5000/user/verify-token", ApiMethod.POST, AccountResponse.class,
                            RequestBody.create(jsonObject.toString(),JSON)
                    ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {
                        if (result != null) {
                            emitter.onSuccess(result);
                        }else {
                            emitter.onError(new Throwable("Object is null"));
                        }
                    }, emitter::onError);
        });
    }
}
