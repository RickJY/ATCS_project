package com.example.myapplication;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText usernameEditText;
    private EditText passwordEditText;
    private GoogleSignInClient googleSignInClient;
    private static final String VALID_USERNAME = "123456";
    private static final String VALID_PASSWORD = "111111";

    private final ActivityResultLauncher<Intent> googleSignInLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                    handleSignInResult(task);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);
        Button guestButton = findViewById(R.id.guestButton);
        Button googleSignInButton = findViewById(R.id.googleSignInButton);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            Log.d(TAG, "Username entered: " + username);
            if (username.equals(VALID_USERNAME) && password.equals(VALID_PASSWORD)) {
                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                navigateToShowPage();
            } else {
                Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        });

        guestButton.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Logged in as Guest", Toast.LENGTH_SHORT).show();
            navigateToShowPage();
        });

        googleSignInButton.setOnClickListener(v -> signInWithGoogle());

        Button googleBtn = findViewById(R.id.googleSignInButton);
        Drawable icon = ContextCompat.getDrawable(this, R.drawable.ic_google_logo);
        if (icon != null) {
            int sizePx = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    24,
                    getResources().getDisplayMetrics()
            );
            icon.setBounds(0, 0, sizePx, sizePx);
        }
        googleBtn.setCompoundDrawables(icon, null, null, null); // 左、上、右、下


    }

    private void signInWithGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        googleSignInLauncher.launch(signInIntent);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                String username = account.getDisplayName();
                Log.d(TAG, "Google Sign-in username: " + username);
                Toast.makeText(this, "Welcome " + username, Toast.LENGTH_SHORT).show();
                navigateToShowPage();
            }
        } catch (ApiException e) {
            Log.e(TAG, "Google Sign-in failed", e);
        }
    }

    private void navigateToShowPage() {
        Intent intent = new Intent(MainActivity.this, ShowPageActivity.class);
        startActivity(intent);
    }
}
