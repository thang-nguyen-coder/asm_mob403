package com.example.asm.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm.ChiTietSanPham;
import com.example.asm.R;
import com.example.asm.UpdateSp;
import com.example.asm.api.apiService;
import com.example.asm.model.SanPham;
import com.example.asm.model.DeleteSp;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.SanPhamViewHolder> {


    private List<SanPham> ltsSP;
    private Context context;


    public SanPhamAdapter(List<SanPham> ltsSP, Context context) {
        this.ltsSP = ltsSP;
        this.context = context;
    }

    @NonNull
    @Override
    public SanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new SanPhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamViewHolder holder, int position) {
        SanPham sanPham = ltsSP.get(position);
        if (sanPham == null) {
            return;
        }
        String _id = ltsSP.get(position).get_id();
        String imgSrc = ltsSP.get(position).getImage();
        holder.txtTenSp.setText(sanPham.getNameproduct());
        holder.txtMoTaSP.setText(sanPham.getDescription());
        //holder.txtSoLuongSP.setText(sanPham.getImage());
        holder.txtGiaSP.setText(sanPham.getPrice());
        Picasso.get().load(Uri.parse(imgSrc)).into(holder.imgSP);
        SharedPreferences sharedPreferences = context.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("role", "");

        if (role.equals("1")) {

            holder.btnDelete.setVisibility(View.VISIBLE);
            holder.btnEdit.setVisibility(View.VISIBLE);
        }

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = holder.getAdapterPosition();
                callApiXoaSP(_id, adapterPosition);
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateSp.class);
                intent.putExtra("_idSp", _id);
                context.startActivity(intent);
            }


        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChiTietSanPham.class);
                intent.putExtra("_idSp", _id);
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        if (ltsSP != null) {
            return ltsSP.size();

        }
        return 0;
    }

    class SanPhamViewHolder extends RecyclerView.ViewHolder {

        TextView txtTenSp, txtMoTaSP, txtGiaSP, txtSoLuongSP;
        ImageView imgSP;
        Button btnEdit, btnDelete;

        public SanPhamViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTenSp = itemView.findViewById(R.id.idTenSanPham);
            txtMoTaSP = itemView.findViewById(R.id.idMoTaSanPham);
            txtGiaSP = itemView.findViewById(R.id.idGiaSanPham);

            imgSP = itemView.findViewById(R.id.imgSanPham);
            btnEdit = itemView.findViewById(R.id.btnSua);
            btnDelete = itemView.findViewById(R.id.btnXoa);
        }
    }

    private void callApiXoaSP(String _id, int position) {

        DeleteSp deleteSp = new DeleteSp(_id);
        apiService.Apiservice.xoaSP(deleteSp).enqueue(new Callback<DeleteSp>() {
            @Override
            public void onResponse(Call<DeleteSp> call, Response<DeleteSp> response) {
                if (response.isSuccessful()) {
                    ltsSP.remove(position);
                    notifyDataSetChanged();

                } else {
                    Log.e("API_CALL_ERROR", "Error code: " + response.code());

                }
            }

            @Override
            public void onFailure(Call<DeleteSp> call, Throwable t) {
                Log.e("API_CALL_ERROR", "Error: " + t.getMessage());


            }
        });
    }

    private void upDateSp(String _id, String imgsrc, String NameSp, String MoTa, String gia) {


    }

    private void callApiUpdateSp(String edtten, String edtImg, String edtGia, String edtmota) {
        SanPham sanPham = new SanPham(null, edtten, edtGia, edtmota, edtImg);

        apiService.Apiservice.updateSP(sanPham).enqueue(new Callback<SanPham>() {
            @Override
            public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                if (response.isSuccessful()) {

                    notifyDataSetChanged();

                } else {
                    Log.e("API_CALL_ERROR", "Error code: " + response.code());

                }
            }

            @Override
            public void onFailure(Call<SanPham> call, Throwable t) {

            }
        });

    }
}
