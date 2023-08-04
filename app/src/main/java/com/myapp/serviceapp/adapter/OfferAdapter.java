package com.myapp.serviceapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myapp.serviceapp.databinding.ItemOfferBinding;
import com.myapp.serviceapp.helper.Constants;
import com.myapp.serviceapp.helper.SharedPrefsManager;
import com.myapp.serviceapp.model.Offers;

import java.util.List;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.OfferViewHolder> {
    Context context;
    List<Offers> offersList;
    boolean status;
    DatabaseReference mRef;
    OfferCallBacks offerCallBacks;
    SharedPrefsManager sharedPrefsManager;
    public OfferAdapter(Context context, List<Offers> offersList,boolean status,OfferCallBacks offerCallBacks) {
        this.context = context;
        this.offersList = offersList;
        this.status=status;
        this.offerCallBacks=offerCallBacks;
        mRef=FirebaseDatabase.getInstance().getReference(Constants.USERS);
        sharedPrefsManager=new SharedPrefsManager(context);

    }

    @NonNull
    @Override
    public OfferAdapter.OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOfferBinding binding=ItemOfferBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new OfferViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferAdapter.OfferViewHolder holder, int position) {
        holder.bind(offersList.get(position));
    }

    @Override
    public int getItemCount() {
        return offersList.size();
    }

    public class OfferViewHolder extends RecyclerView.ViewHolder {
        ItemOfferBinding binding;

        public OfferViewHolder(@NonNull ItemOfferBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

        public void bind(Offers offers){
            if (status) {
                binding.btnAssign.setVisibility(View.VISIBLE);
                binding.phone.setVisibility(View.VISIBLE);

                if (offers.isCompleted()){
                    binding.btnComplete.setVisibility(View.GONE);
                    binding.btnAssign.setVisibility(View.GONE);
                    binding.btnReview.setVisibility(View.VISIBLE);
                }

                if (offers.isAssigned()){
                    binding.btnComplete.setVisibility(View.VISIBLE);
                    binding.btnAssign.setVisibility(View.GONE);
                }
            }




            if (offers.getFreelancer_id().equals(sharedPrefsManager.getUser().getUserId())&&offers.isAssigned()){
                binding.phone.setVisibility(View.VISIBLE);
            }


            mRef.child(offers.getFreelancer_id()).child("reviewsList").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    binding.totalReviews.setText(String.valueOf(snapshot.getChildrenCount())+" Reviews");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            binding.name.setText(offers.getFreelancerName());

            binding.phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    offerCallBacks.onClickPhone(getAdapterPosition());
                }
            });
            if (offers.isReviewed()){
                binding.btnReview.setText("Reviewed");
                binding.btnReview.setEnabled(false);
                binding.btnReview.setClickable(false);
                binding.btnAssign.setVisibility(View.GONE);
                binding.btnComplete.setVisibility(View.GONE);
            }

            binding.btnAssign.setOnClickListener(v -> {
                offerCallBacks.onClickAssign(getAdapterPosition());
                    binding.btnAssign.setVisibility(View.GONE);
                    binding.btnComplete.setVisibility(View.VISIBLE);
            });

            binding.btnComplete.setOnClickListener(v -> {
                offerCallBacks.onClickComplete(getAdapterPosition());
            });

            binding.btnReview.setOnClickListener(v -> offerCallBacks.onClickReview(getAdapterPosition()));
        }
    }


    public interface OfferCallBacks{
        void onClickAssign(int position);
        void onClickComplete(int position);
        void onClickReview(int position);
        void onClickPhone(int position);
    }
}
