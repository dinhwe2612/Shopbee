package com.example.shopbee.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.shopbee.BR;
import com.example.shopbee.R;
import com.example.shopbee.databinding.LoginBinding;
import com.example.shopbee.di.component.ActivityComponent;
import com.example.shopbee.ui.common.base.BaseActivity;
import com.example.shopbee.ui.forgotpassword.ForgotPasswordActivity;
import com.example.shopbee.ui.main.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends BaseActivity<LoginBinding, LoginViewModel>
    implements LoginNavigator{
    private static final int RC_SIGN_IN = 1000;
    LoginBinding loginBinding;
    GoogleSignInClient mGoogleSignInClient;

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.login;
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = getViewDataBinding();
        viewModel.setNavigator(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    public void handleError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginWithEmailAndPassWord() {
        viewModel.login(loginBinding.emailText.getText().toString(), loginBinding.passwordText.getText().toString());
    }

    @Override
    public void loginWithFacebook() {

    }

    @Override
    public void loginWithGoogle() {
        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount account = null;
            try {
                account = task.getResult(ApiException.class);
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                viewModel.loginWithGoogle(credential);
            } catch (ApiException e) {
                Toast.makeText(this, "You should login with google to continue", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void openMainActivity() {
        Intent intent = MainActivity.newIntent(this);
        startActivity(intent);
    }

    @Override
    public void toForgotPassword() {
        Intent intent = ForgotPasswordActivity.newIntent(this);
        startActivity(intent);
    }

}
