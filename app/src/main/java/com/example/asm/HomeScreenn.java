package com.example.asm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;

import com.example.asm.adapter.SanPhamAdapter;
import com.example.asm.model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class HomeScreenn extends AppCompatActivity {

    private RecyclerView reyc;
    private Button btnadd;
    private List<SanPham> ltsSP;
    private SanPhamAdapter adapter;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screenn);

        //Ánh Xạ
        reyc = findViewById(R.id.recyclerView);
        ltsSP = new ArrayList<>();
        adapter = new SanPhamAdapter(ltsSP, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        reyc.setLayoutManager(linearLayoutManager);
        reyc.setAdapter(adapter);


    }
}