package com.myapp.serviceapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.serviceapp.R;
import com.myapp.serviceapp.model.ParentCategory;

import java.util.List;

public class ParentCategoryAdapter extends RecyclerView.Adapter<ParentCategoryAdapter.ParentViewHolder> {
    private final List<ParentCategory> parentCategoryList;
    private Context context;

    private OnItemButtonClickListener listener;

    public ParentCategoryAdapter(List<ParentCategory> parentCategoryList, Context context,OnItemButtonClickListener listener) {
        this.parentCategoryList = parentCategoryList;
        this.context = context;
        this.listener=listener;
    }

    @NonNull
    @Override
    public ParentCategoryAdapter.ParentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.item_parent_category,parent,false);
        return new ParentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentCategoryAdapter.ParentViewHolder holder, int position) {
        ParentCategory parentCategory=parentCategoryList.get(position);
        holder.catTitle.setText(parentCategory.getCatParentName());
        holder.btnDelete.setOnClickListener(view -> listener.onDeleteItem(holder.getAdapterPosition()));
        holder.btnEdit.setOnClickListener(view -> listener.onEditItem(holder.getAdapterPosition()));
    }
    @Override
    public int getItemCount() {
        return parentCategoryList.size();
    }

    public static class ParentViewHolder extends RecyclerView.ViewHolder {
        TextView catTitle;
        ImageButton btnDelete, btnEdit;
        public ParentViewHolder(@NonNull View itemView) {
            super(itemView);
            catTitle=itemView.findViewById(R.id.cat_title);
            btnDelete=itemView.findViewById(R.id.btn_delete);
            btnEdit=itemView.findViewById(R.id.btn_edit);
        }
    }

    public interface OnItemButtonClickListener{
        void onDeleteItem(int position);
        void onEditItem(int position);
    }
}
