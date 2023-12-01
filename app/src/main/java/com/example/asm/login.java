package com.example.asm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.asm.api.apiService;
import com.example.asm.model.auth;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {

    TextInputEditText edtUser, edtPass;
    Button btnSignIn;
    String edtuser,edtpass;
    TextView btnReg;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUser = findViewById(R.id.edtUsername_frame2);
        edtPass = findViewById(R.id.edtPassword_frame2);
        btnSignIn = findViewById(R.id.btnNext_frame2);
        btnReg = findViewById(R.id.tvSignIn_frame2);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtpass = edtPass.getText().toString();
                edtuser = edtUser.getText().toString();

                SignIn(edtuser,edtpass);


            }
        });


        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this,
                        signup.class);
                startActivity(intent);
            }
        });

    }



        private void SignIn(String userN, String passW){

            auth Auth = new auth(userN,null,passW, null);

            apiService.Apiservice.checkSignIn(Auth).enqueue(new Callback<auth>() {
                @Override
                public void onResponse(Call<auth> call, Response<auth> response) {
                    sharedPreferences = getSharedPreferences("USER_INFO",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("role", response.body().getRole());
                    editor.commit();
                    Log.i("role","role" + response.body().getRole());
                    Intent intent = new Intent(login.this,
                            MainActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<auth> call, Throwable t) {
                    Log.e("API_CALL_ERROR", "Error code: " + t.getMessage());
                    Toast.makeText(login.this, "Sign In Thất Bại", Toast.LENGTH_SHORT).show();
                }
            });


        }
}