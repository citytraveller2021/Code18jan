package com.example.uitest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpFragment extends Fragment {

    EditText et_username, et_password, et_confpass, et_Email;
    Button btSignup ;
    FirebaseAuth fAuth;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup signup = (ViewGroup) inflater.inflate(R.layout.fragment_sign_up, container , false);

        et_Email = (EditText) signup.findViewById(R.id.etEmail);
        et_username = (EditText) signup.findViewById(R.id.etSetUsername) ;
        et_password = (EditText) signup.findViewById(R.id.etSetPassword);
        et_confpass = (EditText) signup.findViewById(R.id.etConfirmPassword);
        btSignup = (Button) signup.findViewById(R.id.btSignUp);

        fAuth = FirebaseAuth.getInstance();

        //Onclick for SignUp Button
        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String confpass = et_confpass.getText().toString().trim();
                String email = et_Email.getText().toString().trim();

                if(username.isEmpty()) {
                    et_username.setError("username is required");
                    et_username.requestFocus();
                    return;
                }
                if(email.isEmpty()) {
                    et_Email.setError("Email is required");
                    et_Email.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    et_password.setError("Password is required");
                    et_password.requestFocus();
                    return;
                }

                if (password.length()<6) {
                    et_password.setError("Password less than 6 Charaters");
                    et_password.requestFocus();
                    return;
                }

                if (confpass.isEmpty()) {
                    et_confpass.setError("password is required");
                    et_confpass.requestFocus();
                    return;
                }
                if(!confpass.equals(password)){
                    et_confpass.setError("Password dosen't match");
                    et_confpass.requestFocus();
                    return;
                }

                //FireBase creating new user
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getActivity(),"Registration Successful Please login now",Toast.LENGTH_LONG).show();

                        }
                        else{
                            Toast.makeText(getActivity(),"Error " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        return signup;

    }
}
