package com.example.shopbee.ui.common.dialogs.changePassword;

public class changePasswordEvent {
    private String newPassword;

    public changePasswordEvent(String newPassword){
        this.newPassword = newPassword;
    }
    String getNewPassword(){
        return newPassword;
    }
}
