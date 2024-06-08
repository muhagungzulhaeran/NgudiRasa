package com.example.ngudirasa.Authentication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.ngudirasa.MainActivity;
import com.example.ngudirasa.Model.ApiHandler;
import com.example.ngudirasa.Model.DataUser;
import com.example.ngudirasa.Model.DbHelper;
import com.example.ngudirasa.Model.SavetoDb;
import com.example.ngudirasa.R;

import java.util.ArrayList;
import java.util.List;
public class Login extends AppCompatActivity {

    private List<DataUser> dataList;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            // Handle the swipe-to-refresh action
            fetchDataAndPopulate();
        });

        // Reset the user data in the database
        SavetoDb.resetUserData(Login.this);

        dataList = fetchDataFromDb();

        // If data is not available in SQLite, fetch from the internet (Google Sheets) and save to SQLite
        if (dataList.isEmpty()) {
            ApiHandler.fetchDataUser(Login.this, new ApiHandler.VolleyCallbackUser() {
                @Override
                public void onSuccess(List<DataUser> dataUsers) {
                    try {
                        // Save data to SQLite
                        SavetoDb.saveUsertoDb(Login.this, dataUsers);

                        // Update the local dataList
                        dataList = dataUsers;

                        // Continue with any additional logic if needed
                    } catch (Exception e) {
                        e.printStackTrace();
                        // Handle the exception, log, or show an error message
                    }
                }

                @Override
                public void onError(String error) {
                    // Handle error
                }
            });
        } else {

        }

        Button loginButton = findViewById(R.id.login_button);
        TextView username, password;

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredUsername = username.getText().toString();
                String enteredPassword = PasswordUtils.encryptPassword(password.getText().toString());

                if (enteredPassword != null) {
                    // Check username and encrypted password in SQLite
                    if (checkCredentials(enteredUsername, enteredPassword)) {
                        // Login successful, perform necessary actions
                        // For example, navigate to another activity
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivityForResult(intent,0);
                        finish(); // Finish the current activity


                    } else {
                        // Incorrect username or password, show an error message
                        // You may want to display a Toast or set an error message on the UI
                        showToast("Username atau Password tidak benar");
                    }
                }
            }
        });


        TextView signUpButton = findViewById(R.id.textSignup);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();

        // Perform actions when returning to this activity

        // Reset user data and fetch from the database
        SavetoDb.resetUserData(Login.this);
        dataList = fetchDataFromDb();

        Log.d("LoginActivity", "Fetched data size: " + dataList.size());

        // Add any additional logic you need after returning from the Register activity
    }
    private List<DataUser> fetchDataFromDb() {
        DbHelper dbHelper = new DbHelper(Login.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<DataUser> dataList = new ArrayList<>();

        // Specify the columns you want to retrieve
        String[] columns = {
                DbHelper.COLUMN_ID_USER,
                DbHelper.COLUMN_NAMA_USER,
                DbHelper.COLUMN_PASSWORD,
                DbHelper.COLUMN_VALID,
        };


        Cursor cursor = db.query(DbHelper.TABLE_NAME_USER, columns, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String idUser = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_ID_USER));
                @SuppressLint("Range") String namaUser = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_NAMA_USER));
                @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_PASSWORD));
                @SuppressLint("Range") String valid = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_VALID));

                DataUser data = new DataUser(idUser, namaUser, password, valid, valid);
                dataList.add(data);
            } while (cursor.moveToNext());

            cursor.close();
        } else {

            Log.d("DataList", "No data found in the database.");
        }

        // Close the database
        db.close();
        Log.d("DataList", "Fetched data size: " + dataList.size());
        return dataList;
    }

    public boolean checkCredentials(String enteredUsername, String enteredPassword) {
        DbHelper dbHelper = new DbHelper(Login.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {dbHelper.COLUMN_ID_USER};

        String selection = dbHelper.COLUMN_NAMA_USER + " = ? AND " + dbHelper.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {enteredUsername, enteredPassword};

        Cursor cursor = db.query(dbHelper.TABLE_NAME_USER, columns, selection, selectionArgs, null, null, null);

        boolean credentialsValid = cursor != null && cursor.getCount() > 0;

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return credentialsValid;
    }

    private boolean checkCredentials2(String username, String password) {
        DbHelper dbHelper = new DbHelper(Login.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define the columns you want to retrieve
        String[] projection = {
                DbHelper.COLUMN_VALID
        };

        // Define the WHERE clause
        String selection =  DbHelper.COLUMN_NAMA_USER + " = ? AND " +
                DbHelper.COLUMN_PASSWORD + " = ?";

        // Define the selection arguments
        String[] selectionArgs = {username, password};

        // Query the database
        Cursor cursor = db.query(
                DbHelper.TABLE_NAME_USER,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean isValid = false;

        if (cursor.moveToFirst()) {
            // Retrieve the value of the 'valid' column
            int validColumnIndex = cursor.getColumnIndex(DbHelper.COLUMN_VALID);
            isValid = cursor.getInt(validColumnIndex) == 1; // Assuming 1 represents true
        }

        // Close the cursor and database
        cursor.close();
        dbHelper.close();

        return isValid;
    }


    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        //Do your work here in ActivityA
        SavetoDb.resetUserData(Login.this);

        dataList = fetchDataFromDb();

        // If data is not available in SQLite, fetch from the internet (Google Sheets) and save to SQLite
        if (dataList.isEmpty()) {
            ApiHandler.fetchDataUser(Login.this, new ApiHandler.VolleyCallbackUser() {
                @Override
                public void onSuccess(List<DataUser> dataUsers) {
                    try {
                        // Save data to SQLite
                        SavetoDb.saveUsertoDb(Login.this, dataUsers);

                        // Update the local dataList
                        dataList = dataUsers;

                        // Continue with any additional logic if needed
                    } catch (Exception e) {
                        e.printStackTrace();
                        // Handle the exception, log, or show an error message
                    }
                }

                @Override
                public void onError(String error) {
                    // Handle error
                }
            });
        }
    }

    private void fetchDataAndPopulate() {

        SavetoDb.resetUserData(Login.this);

        dataList = fetchDataFromDb();

        // If data is not available in SQLite, fetch from the internet (Google Sheets) and save to SQLite
        if (dataList.isEmpty()) {
            ApiHandler.fetchDataUser(Login.this, new ApiHandler.VolleyCallbackUser() {
                @Override
                public void onSuccess(List<DataUser> dataUsers) {
                    try {
                        // Save data to SQLite
                        SavetoDb.saveUsertoDb(Login.this, dataUsers);

                        // Update the local dataList
                        dataList = dataUsers;
                        swipeRefreshLayout.setRefreshing(false);
                        // Continue with any additional logic if needed
                    } catch (Exception e) {
                        e.printStackTrace();
                        // Handle the exception, log, or show an error message
                    }
                }

                @Override
                public void onError(String error) {
                    // Handle error
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }
    }

}
