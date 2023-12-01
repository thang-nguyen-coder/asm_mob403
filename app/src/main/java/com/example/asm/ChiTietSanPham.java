package com.example.asm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asm.api.apiService;
import com.example.asm.model.SanPham;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietSanPham extends AppCompatActivity {
    ImageView imganh;
    TextView txtTenSp, txtGiaSp, txtSoLuongSp, txtMoTaSp;

    SanPham lstSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);

        imganh = findViewById(R.id.imgAnh_sanpham_chitiet);
        txtTenSp = findViewById(R.id.tvTen_sanpham_chitiet);
        txtGiaSp = findViewById(R.id.tvGia_sanpham_chitiet);
        txtSoLuongSp = findViewById(R.id.tvSo_luong);
        txtMoTaSp = findViewById(R.id.txtMotasp);
        Intent intent = getIntent();

        String _id = intent.getStringExtra("_idSp");
        CallApiChitietSp(_id);


//        txtTenSp.setText(lstSp.getNameproduct());
//        txtGiaSp.setText(lstSp.getPrice());
////        txtSoLuongSp.setText(lstSp.get);
//        txtMoTaSp.setText(lstSp.getDescription());

    }

    private void CallApiChitietSp(String id) {
        SanPham sanPham = new SanPham(id, null, null, null, null);

        apiService.Apiservice.ChitietSP(sanPham).enqueue(new Callback<SanPham>() {
            @Override
            public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                if (response.isSuccessful()) {
                    lstSp = response.body();

                    if (lstSp != null) {

                        txtTenSp.setText(lstSp.getNameproduct());
                        txtGiaSp.setText(lstSp.getPrice());
//        txtSoLuongSp.setText(lstSp.get);
                        txtMoTaSp.setText(lstSp.getDescription());
                        Picasso.get().load(Uri.parse(lstSp.getImage())).into(imganh);

                    }

                    Log.i("Thành công", "OBJ:" + response.body());
                } else {
                    Log.e("API_CALL_ERROR", "Error code: " + response.code());

                }


            }

            @Override
            public void onFailure(Call<SanPham> call, Throwable t) {
                Log.i("Thất bại", "lỗi:" + t.getMessage());
            }
        });
    }


}