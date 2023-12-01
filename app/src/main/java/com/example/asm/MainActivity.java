package com.example.asm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm.adapter.SanPhamAdapter;
import com.example.asm.api.apiService;
import com.example.asm.model.SanPham;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView reyc;
    FloatingActionButton btnadd;
    TextView tensp, giasp, motasp, soluongsp;

    private List<SanPham> ltsSP;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screenn);


        reyc = findViewById(R.id.recyclerView);
        btnadd = findViewById(R.id.floatbtnSanPham);

        tensp = findViewById(R.id.idTenSanPham);
        giasp = findViewById(R.id.idGiaSanPham);
        motasp = findViewById(R.id.idMoTaSanPham);
        soluongsp = findViewById(R.id.idSoLuongSanpham);
        SharedPreferences sharedPreferences = getSharedPreferences("USER_INFO", MODE_PRIVATE);
        String role = sharedPreferences.getString("role", "");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        reyc.setLayoutManager(linearLayoutManager);
        callApiGetSP();
        ltsSP = new ArrayList<>();


        if (role.equals("1")) {
            btnadd.setVisibility(View.VISIBLE);

        }
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSPNew();
            }
        });
    }


    private void callApiGetSP() {
        apiService.Apiservice.getallSP().enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                if (response.isSuccessful()) {
                    ltsSP = response.body();
                    SanPhamAdapter sanPhamAdapter = new SanPhamAdapter(ltsSP, MainActivity.this);
                    reyc.setAdapter(sanPhamAdapter);
                } else {
                    Log.e("API_CALL_ERROR", "Error code: " + response.code());
                    Toast.makeText(MainActivity.this, "Thất Bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {
                Log.e("API_CALL_ERROR", "Error: " + t.getMessage());

                Toast.makeText(MainActivity.this, "Thất Bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class HttpConnect extends AsyncTask<Void, Void, String> {

        public static final String SERVER = "";
        String result = "";
        String readLine = "";

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(SERVER);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                while ((readLine = bufferedReader.readLine()) != null) {

                    stringBuilder.append(readLine);
                }
                result = stringBuilder.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e("Loi", e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    public void addSPNew() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this)
                .setNegativeButton("Thêm Mới", null)
                .setPositiveButton("Hủy", null);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialong_addsp, null);
        EditText edtTen = view.findViewById(R.id.edtTenSP);
        EditText edtMota = view.findViewById(R.id.edtMoTaSP);
        EditText edtgia = view.findViewById(R.id.edtGiaSP);
        EditText edtimg = view.findViewById(R.id.edtImgSP);
        builder.setView(view);
        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        alertDialog.show();

        alertDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                                                                                                         @Override
                                                                                                         public void onClick(View v) {
                                                                                                             String edtten = edtTen.getText().toString();
                                                                                                             String edtmota = edtMota.getText().toString();
                                                                                                             String edtGia = edtgia.getText().toString();
                                                                                                             String edtImg = edtimg.getText().toString();

                                                                                                             callApAddSP(edtten,
                                                                                                                     edtmota,
                                                                                                                     edtGia,
                                                                                                                     edtImg);


                                                                                                             alertDialog.dismiss();

                                                                                                         }
                                                                                                     }
        );
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    private void callApAddSP(String edtten, String edtImg, String edtGia, String edtmota) {

        SanPham sanPham = new SanPham(null, edtten, edtGia, edtmota, edtImg);
        apiService.Apiservice.addSP(sanPham).enqueue(new Callback<SanPham>() {
            @Override
            public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                if (response.isSuccessful()) {
                    ltsSP.add(response.body());
                    SanPhamAdapter sanPhamAdapter = new SanPhamAdapter(ltsSP, MainActivity.this);
                    reyc.setAdapter(sanPhamAdapter);
                } else {
                    Log.e("API_CALL_ERROR", "Error code: " + response.code());
                    Toast.makeText(MainActivity.this, "Thất Bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SanPham> call, Throwable t) {
                Log.e("API_CALL_ERROR", "Error: " + t.getMessage());

                Toast.makeText(MainActivity.this, "Thất Bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

}