package com.myapp.serviceapp.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.fragment.app.DialogFragment;

import com.myapp.serviceapp.R;

public class ReviewDialogFragment extends DialogFragment {

    private RatingBar ratingBar;
    private EditText commentEditText;
    private int position;
    public ReviewDialogFragment(int position){
        this.position=position;
    }
    public interface ReviewDialogListener {
        void onReviewSubmitted(float rating, String comment,int position);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_review, null);

        builder.setView(dialogView)
                .setTitle("Leave a Review")
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        float rating = ratingBar.getRating();
                        String comment = commentEditText.getText().toString().trim();

                        // Call the listener to notify the review submission
                        ReviewDialogListener listener = (ReviewDialogListener) getActivity();
                        listener.onReviewSubmitted(rating, comment,position);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });

        ratingBar = dialogView.findViewById(R.id.ratingBar);
        commentEditText = dialogView.findViewById(R.id.commentEditText);

        return builder.create();
    }
}
