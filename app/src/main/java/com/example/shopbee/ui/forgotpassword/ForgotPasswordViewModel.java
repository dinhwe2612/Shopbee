package com.example.shopbee.ui.forgotpassword;

import com.example.shopbee.data.Repository;
import com.example.shopbee.ui.common.base.BaseViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ForgotPasswordViewModel extends BaseViewModel<ForgotPasswordNavigator> {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public ForgotPasswordViewModel(Repository repository) {
        super(repository);
    }
    public void sentEmail(String email) {
        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        getNavigator().sentEmailSuccess();
                    } else {
                        getNavigator().sentEmailFailed(Objects.requireNonNull(task.getException()).getMessage());
                    }
                });
    }
}
