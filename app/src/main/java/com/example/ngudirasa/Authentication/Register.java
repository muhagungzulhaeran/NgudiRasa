package com.example.ngudirasa.Authentication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ngudirasa.Model.ApiHandler;
import com.example.ngudirasa.Model.DataUser;
import com.example.ngudirasa.Model.SavetoDb;
import com.example.ngudirasa.R;

import java.util.List;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button registButton = findViewById(R.id.register_button);

        TextView email, username, password;
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);


        registButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredEmail = email.getText().toString();
                String enteredUsername = username.getText().toString();
                String enteredPassword = password.getText().toString();

                // Encrypt the password using SHA-1
                String encryptedPassword = PasswordUtils.encryptPassword(enteredPassword);

                // Check if the entered data is not empty
                if (!TextUtils.isEmpty(enteredEmail) && !TextUtils.isEmpty(enteredUsername) && !TextUtils.isEmpty(enteredPassword)) {
                    // Call the registration API
                    ApiHandler.registerUser(Register.this, enteredEmail, enteredUsername, encryptedPassword, new ApiHandler.VolleyCallbackUser() {
                        @Override
                        public void onSuccess(List<DataUser> dataUsers) {
                            // Handle registration success if needed

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // Handle registration success if needed
                                    Context appContext = getApplicationContext();
                                    SavetoDb.saveUsertoDb(appContext, dataUsers);
                                    Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                    SavetoDb.resetUserData(Register.this);
                                    Intent intent = new Intent();
                                    setResult(RESULT_OK,intent );
                                    finish();
                                }
                            });

                        }

                        @Override
                        public void onError(String error) {
                            // Handle registration error
                            Toast.makeText(Register.this, "Registration failed: " + error, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // Show a toast if any field is empty
                    Toast.makeText(Register.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });




        TextView signUpButton =findViewById(R.id.textLogin);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
