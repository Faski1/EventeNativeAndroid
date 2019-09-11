package com.example.evente.helper;

import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.evente.R;


/**
 * Created by Sahzudin Mahmic on 26.5.2018..
 */

public class Validation {

    private Activity activity;

    public Validation(Activity activity) {
        this.activity = activity;
    }

    public boolean validateEmpty(EditText textInput, TextInputLayout textInputLayout) {
        if (textInput.getText().toString().trim().isEmpty()) {
            textInputLayout.setError(activity.getResources().getString(R.string.errMsgFieldReq));
            requestFocus(textInput);
            return false;

        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    public boolean validateEmail(EditText textInput, TextInputLayout textInputLayout) {
        if (!isValidEmail(textInput.getText().toString().trim())) {
            textInputLayout.setError(activity.getResources().getString(R.string.errorMsgEmailNotValid));
            requestFocus(textInput);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    public boolean validatePassword(EditText textInput, TextInputLayout textInputLayout) {
        if (textInput.getText().toString().trim().length()<8) {
            textInputLayout.setError(activity.getResources().getString(R.string.errorMsgPasswordLength));
            requestFocus(textInput);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    public boolean validateLength50(EditText textInput, TextInputLayout textInputLayout) {
        if (textInput.getText().toString().trim().length()>50) {
            textInputLayout.setError(activity.getResources().getString(R.string.errorMsgMaxLen50));
            requestFocus(textInput);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }



    public boolean validateLength20(EditText textInput, TextInputLayout textInputLayout) {
        if (textInput.getText().toString().trim().length()>30) {
            textInputLayout.setError(activity.getResources().getString(R.string.errorMsgMaxLen20));
            requestFocus(textInput);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    public void setErrorMessage(EditText textInput, TextInputLayout textInputLayout, boolean valid, String message)
    {
        if (!valid) {
            textInputLayout.setError(message);
            requestFocus(textInput);
        } else {
            textInputLayout.setErrorEnabled(false);
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}

