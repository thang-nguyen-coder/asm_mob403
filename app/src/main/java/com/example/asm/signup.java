package com.example.asm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.asm.api.apiService;
import com.example.asm.model.auth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class signup extends AppCompatActivity {
    EditText edtUR, edtPassR, edtEmail;
    Button btnReg;

    String edtur, edtpr, edtemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edtUR = findViewById(R.id.edtUsername_frame3);
        edtPassR = findViewById(R.id.edtPassword_frame3);
        edtEmail = findViewById(R.id.edtEmail_frame3);
        btnReg = findViewById(R.id.btnSignUp_frame3);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtur = edtUR.getText().toString();
                edtemail = edtEmail.getText().toString();
                edtpr = edtPassR.getText().toString();


                RegU(edtur, edtpr, edtemail);
            }
        });

    }

    private void RegU(String edtur, String edtpr, String edtemail) {

        auth Auth = new auth(edtur, edtemail, edtpr, null);

        apiService.Apiservice.postU(Auth).enqueue(new Callback<auth>() {
            @Override
            public void onResponse(Call<auth> call, Response<auth> response) {
                Toast.makeText(signup.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(signup.this,
                        login.class);
                startActivity(intent);


            }

            @Override
            public void onFailure(Call<auth> call, Throwable t) {
                Log.e("API_CALL_ERROR", "Error code: " + t.getMessage());
                Toast.makeText(signup.this, "Sign Up Thất Bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}