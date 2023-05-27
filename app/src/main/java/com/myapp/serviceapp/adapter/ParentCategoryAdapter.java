package com.myapp.serviceapp.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.serviceapp.R;
import com.myapp.serviceapp.model.ParentCategory;

import java.util.List;

public class ParentCategoryAdapter extends RecyclerView.Adapter<ParentCategoryAdapter.ParentViewHolder> {
    private List<ParentCategory> parentCategoryList;
    private Context context;

    public ParentCategoryAdapter(List<ParentCategory> parentCategoryList, Context context) {
        this.parentCategoryList = parentCategoryList;
        this.context = context;
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
    }

    @Override
    public int getItemCount() {
        return parentCategoryList.size();
    }

    public class ParentViewHolder extends RecyclerView.ViewHolder {
        TextView catTitle;
        public ParentViewHolder(@NonNull View itemView) {
            super(itemView);
            catTitle=itemView.findViewById(R.id.cat_title);
        }
    }
}
