package com.bolivar.upgradedpokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bolivar.upgradedpokedex.api.API;
import com.bolivar.upgradedpokedex.model.dto.request.LoginDto;
import com.bolivar.upgradedpokedex.model.dto.request.SignupDto;
import com.bolivar.upgradedpokedex.model.dto.response.UserDto;
import com.bolivar.upgradedpokedex.model.dto.response.RefreshTokenDto;
import com.bolivar.upgradedpokedex.prefs.AppPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);

        loginButton = findViewById(R.id.login);
        signupButton = findViewById(R.id.signup);

        AppPreferences.initialize(getApplicationContext());

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                login(username, password);
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                signup(username, password);
            }
        });
    }

    private void login(String username, String password) {
        LoginDto loginDto = new LoginDto(username, password);
        Call<RefreshTokenDto> call = API.userApi().login(loginDto);

        call.enqueue(new Callback<RefreshTokenDto>() {
            @Override
            public void onResponse(Call<RefreshTokenDto> call, Response<RefreshTokenDto> response) {
                if (response.isSuccessful()) {
                    RefreshTokenDto refreshTokenDto = response.body();
                    if (refreshTokenDto != null) {
                        AppPreferences.getInstance().setAccessToken(refreshTokenDto.getAccessToken());
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RefreshTokenDto> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void signup(String username, String password) {
        SignupDto signupDto = new SignupDto(username, password);
        Call<UserDto> call = API.userApi().signup(signupDto);

        call.enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                if (response.isSuccessful()) {
                    UserDto userDto = response.body();
                    if (userDto != null) {
                        Toast.makeText(MainActivity.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Signup Failed: No user data received", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Log error body
                    String errorBody = response.errorBody() != null ? response.errorBody().toString() : "Unknown error";
                    Toast.makeText(MainActivity.this, "Signup Failed: " + errorBody, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}

