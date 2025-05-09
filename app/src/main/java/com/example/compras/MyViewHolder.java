package com.example.compras;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView tvName;
    EditText tvQuantity;
    CheckBox inCartCheckBox;

    public MyViewHolder(View view) {
        super(view);
        tvName = view.findViewById(R.id.tvName);
        tvQuantity = view.findViewById(R.id.etQuantity);
        inCartCheckBox = view.findViewById(R.id.inCartCheckBox);
    }
}