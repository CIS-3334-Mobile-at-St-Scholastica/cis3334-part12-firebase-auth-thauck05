package css.cis3334.firebaseauthentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    // Declare all instances
    private TextView textViewStatus;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonGoogleLogin;
    private Button buttonCreateLogin;
    private Button buttonSignOut;
    private FirebaseAuth mAuth;

    /**
     * Bundle method to save the state of the of the application.
     * Can be used to reload the previous state.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Text views
        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        // Buttons
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonGoogleLogin = (Button) findViewById(R.id.buttonGoogleLogin);
        buttonCreateLogin = (Button) findViewById(R.id.buttonCreateLogin);
        buttonSignOut = (Button) findViewById(R.id.buttonSignOut);

        // Initialize FirebaseAuth instance.
        mAuth = FirebaseAuth.getInstance();

        // Calls signIn method to sign in existing user.
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                signIn(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });

        // Calls createAccount method to create new user and sign them in. Passes information to DB to create new user.
        buttonCreateLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createAccount(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });

        // Calls googleSignIn method to log in using Google info.
        // Currently not being used.
        buttonGoogleLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                googleSignIn();
            }
        });

        // Calls signOut method to sign the user out.
        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                signOut();
            }
        });
    }

    /**
     * Checks to see if user is currently authenticated before starting screen appears.
     *
     * NOTE: Removed log calls that logged signed in and signed out to the log file
     * Removed Toast calls that provided simple feedback about the operation.
     */
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            // Text displayed when user is signed in successfully
            textViewStatus.setText("Welcome! You are now Signed In.");
        } else {
            // Text displayed when user is signed out successfully
            textViewStatus.setText("You are Signed Out");
        }
    }

    /**
     * Method to create a new user with email address and password
     * Takes in email address and password, validates them, and creates new user
     *
     * NOTE: Removed log calls that logged signed in and signed out to the log file
     * Removed Toast calls that provided simple feedback about the operation.
     *
     * @param email
     * @param password
     */
    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            textViewStatus.setText("Welcome! You are now Signed In.");
                        } else {
                            // If sign in fails, update UI with a message to the user.
                            textViewStatus.setText("Please try again.");
                        }

                        // ...
                    }
                });
    }

    /**
     * Method to sign in new users with existing email address and password stored in DB.
     * Takes in email address and password, validates them against the DB, and signs the user in.
     *
     * NOTE: Removed log calls that logged signed in and signed out to the log file
     * Removed Toast calls that provided simple feedback about the operation.
     *
     * @param email
     * @param password
     */
    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            textViewStatus.setText("Welcome! You are now Signed In.");
                        } else {
                            // If sign in fails, display a message to the user.
                            textViewStatus.setText("Please try again.");
                        }

                        // ...
                    }
                });
    }

    /**
     * Method to sign user out of app.
     */
    private void signOut () {
        mAuth.signOut();
        textViewStatus.setText("You are now Signed Out. Good-bye!");
    }

    /**
     * Method to use Google information to sign in.
     *
     * NOTE: Currently not set up.
     */
    private void googleSignIn() {

    }




}
