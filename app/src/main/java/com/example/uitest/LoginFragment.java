package com.example.uitest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    EditText et_Username , et_Password;
    Button btLogin ;
    FirebaseAuth fAuth;
    GoogleSignInClient mGoogleSignInClient;

    private static int RC_SIGN_IN = 100;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup login = (ViewGroup) inflater.inflate(R.layout.fragment_login, container , false);

        et_Username = login.findViewById(R.id.etUsername);
        et_Password = login.findViewById(R.id.etPassword);
        btLogin = login.findViewById(R.id.btLogin);
        fAuth = FirebaseAuth.getInstance();

        //Animation for Login Text Id's
        et_Username.setTranslationX(-300);
        et_Password.setTranslationX(-300);
        btLogin.setTranslationX(-300);
        et_Username.setAlpha(0f);
        et_Password.setAlpha(0f);
        btLogin.setAlpha(0f);

        et_Username.animate().translationX(0).alpha(1f).setDuration(1000).setStartDelay(300).start();
        et_Password.animate().translationX(0).alpha(1f).setDuration(1000).setStartDelay(450).start();
        btLogin.animate().translationX(0).alpha(1f).setDuration(1000).setStartDelay(600).start();

        // OnClick for Login Button
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = et_Username.getText().toString().trim();
                String password = et_Password.getText().toString().trim();

                if (username.isEmpty()){
                    et_Username.setError("username empty!");
                    et_Username.requestFocus();
                    return;
                }

                if (password.isEmpty()){
                    et_Password.setError("password empty");
                    et_Password.requestFocus();
                    return;
                }

                //Authenticate User From Firebase
                fAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(),"login successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity() , MapActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getActivity(),"Login Failed!" +task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }


                    }
                });
            }
        });

        //Authentiation using gmail signin
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());

        // Set the dimensions of the sign-in button.
        SignInButton signInButton = login.findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        return login;
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();

                Toast.makeText(getActivity(),"User Email" + personEmail ,Toast.LENGTH_SHORT).show();
            }

            Intent intent = new Intent(getActivity() , MapActivity.class);
            startActivity(intent);

            // Signed in successfully, show authenticated UI.

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d("Message" ,e.toString());

        }
    }

}
