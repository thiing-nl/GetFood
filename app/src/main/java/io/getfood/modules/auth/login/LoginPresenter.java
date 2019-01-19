package io.getfood.modules.auth.login;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;

import net.steamcrafted.loadtoast.LoadToast;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import io.getfood.data.local.Globals;
import io.getfood.data.swagger.ApiException;
import io.getfood.data.swagger.api.UserControllerApi;
import io.getfood.data.swagger.models.User;
import io.getfood.data.swagger.models.UserAuthenticationRequest;
import io.getfood.models.ApiManager;
import io.getfood.util.PreferenceHelper;
import io.getfood.util.UserUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginPresenter implements LoginContract.Presenter {

    private final Handler handler;
    private final SharedPreferences sharedPreferences;
    private final LoginContract.View loginView;
    private final UserControllerApi api;

    LoginPresenter(@NonNull LoginContract.View loginView, SharedPreferences preferences, Looper looper) {
        this.loginView = checkNotNull(loginView, "loginView cannot be null");
        this.loginView.setPresenter(this);
        this.sharedPreferences = checkNotNull(preferences, "sharedPreferences cannot be null");
        this.handler = new Handler(checkNotNull(looper, "looper cannot be null"));

        api = new UserControllerApi();
        ApiManager.add(api.getApiClient(), sharedPreferences);
    }

    @Override
    public void start() {
        System.out.println("Start Login Presenter");
        Boolean loginStatus = PreferenceHelper.read(this.sharedPreferences, Globals.PrefKeys.LOGIN_STATUS, false);

        if (loginStatus) {
            loginView.showFingerPrintButton();
            loginView.showFingerAuth();
            loginView.setLoginButtonEnabled(true);
            loginView.setUsernameText(PreferenceHelper.read(this.sharedPreferences, Globals.PrefKeys.LOGIN_USERNAME, ""));
        } else {
            loginView.hideFingerPrintButton();
            loginView.setLoginButtonEnabled(false);
        }

        loginView.setLoginButtonEnabled(false);
    }

    @Override
    public void login(String username, String password) {
        LoadToast toast = loginView.createToast("Logging in...");
        UserAuthenticationRequest request = new UserAuthenticationRequest();
        request.setEmail(username);
        request.setPassword(password);

        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
                this.handler.post(toast::success);
                TimeUnit.MILLISECONDS.sleep(500);
                User user = api.userControllerAuthenticate(request);
                loginView.onLogin(user);
                UserUtil.saveUser(user, sharedPreferences);
            } catch (ApiException err) {
                System.out.println(err.getResponseBody());
                loginView.onError(err);
            } catch (Exception e) {
                loginView.onError(e.getMessage());
            }
        }).start();
    }

    @Override
    public void checkValid(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            loginView.setLoginButtonEnabled(false);
        } else {
            loginView.setLoginButtonEnabled(true);
        }
    }

    public void validateStoredToken() {
        ApiManager.setToken(UserUtil.getToken(sharedPreferences));
        LoadToast toast = loginView.createToast("Logging in...");

        new Thread(() -> {
            try {
                User user = api.userControllerCurrentUser();
                if (user != null) {
                    TimeUnit.MILLISECONDS.sleep(500);
                    this.handler.post(toast::success);
                    TimeUnit.MILLISECONDS.sleep(500);
                    loginView.onTokenValidated(user);
                } else {
                    TimeUnit.MILLISECONDS.sleep(500);
                    loginView.onError("Token expired!");
                    loginView.setUsernameText("");
                    this.handler.post(toast::error);
                }
            } catch (ApiException err) {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.handler.post(toast::error);
                System.out.println(err.getCode());
                if (err.getCode() != 401) {
                    loginView.onError(err);
                } else {
                    PreferenceHelper.clearAll(sharedPreferences);
                    ApiManager.setToken("");
                    loginView.onError("Please login again.");
                }

                System.out.println(err.getResponseBody());
                loginView.setUsernameText("");
            } catch (Exception e) {
                loginView.onError(e.getMessage());
            }
        }).start();
    }
}
