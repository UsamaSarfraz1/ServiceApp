package com.myapp.serviceapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.serviceapp.R;
import com.myapp.serviceapp.activities.admin_panel.CategoryActivity;
import com.myapp.serviceapp.activities.user_panel.MainCategoryActivity;
import com.myapp.serviceapp.activities.user_panel.PostActivity;
import com.myapp.serviceapp.model.ChildCategory;
import com.myapp.serviceapp.model.ParentCategory;

import java.util.List;
import java.util.zip.Inflater;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<ParentCategory> categoryList;
    private Context context;
    private String tag;

    public CategoryAdapter(List<ParentCategory> categoryList, Context context,String tag) {
        this.categoryList = categoryList;
        this.context = context;
        this.tag=tag;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view= layoutInflater.inflate(R.layout.item_category,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        ParentCategory category=categoryList.get(position);
        holder.title.setText(category.getCatParentName());
        holder.cardView.setOnClickListener(v -> {
            if (tag.equals("child")) {
                Intent intent = new Intent(context, PostActivity.class);
                intent.putExtra("cat", category);
                context.startActivity(intent);
            }else{
                Intent intent= new Intent(context, MainCategoryActivity.class);
                intent.putExtra("cat",category);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private CardView cardView;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cat_container);
            title=itemView.findViewById(R.id.cat_name);
        }
    }
}