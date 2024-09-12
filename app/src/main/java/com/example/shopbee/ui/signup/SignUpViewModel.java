package com.example.shopbee.ui.signup;

import com.example.shopbee.data.Repository;
import com.example.shopbee.ui.common.base.BaseViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.saadahmedev.popupdialog.PopupDialog;

public class SignUpViewModel extends BaseViewModel<SignUpNavigator> {
    public SignUpViewModel(Repository repository) {
        super(repository);
    }

    void signUp(String name,String email, String password, String confirmPassword) {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            getNavigator().emptyBox();
        } else if (!password.equals(confirmPassword)) {
            getNavigator().passwordNotMatch();
        } else {
            setIsLoading(true);
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task->{
                        setIsLoading(false);
                        if (task.isSuccessful()) {
                            getNavigator().signUpSuccess();
                        } else {
                            getNavigator().signUpFailed(task.getException());
                        }
                    });
        }
    }
}
