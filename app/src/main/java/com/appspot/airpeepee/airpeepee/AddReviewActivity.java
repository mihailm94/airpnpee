package com.appspot.airpeepee.airpeepee;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.AdapterView;


import com.appspot.airpeepee.airpeepee.model.Comment;
import com.appspot.airpeepee.airpeepee.model.DataHolder;
import com.appspot.airpeepee.airpeepee.model.Rating;
import com.appspot.airpeepee.airpeepee.model.Toilet;
import com.appspot.airpeepee.airpeepee.model.User;
import com.appspot.airpeepee.airpeepee.model.db;
import com.google.android.gms.maps.model.LatLng;

@SuppressLint("ValidFragment")
public class AddReviewActivity extends DialogFragment  {

    private LatLng latLng;

    public AddReviewActivity() {super();}


    @SuppressLint("ValidFragment")
    AddReviewActivity(LatLng latLng){
        super();
        this.latLng=latLng;
    }

    //public AddReviewActivity(){}

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    public  NoticeDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (AddReviewActivity.NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(this.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View view =inflater.inflate(R.layout.activity_review_toilet, null);

        RatingBar cleanRating = (RatingBar) view.findViewById(R.id.ratingBarClean);
        RatingBar ratingRating = (RatingBar) view.findViewById(R.id.ratingBarRating);
        //TextView toiletComment = (TextView) view.findViewById(R.id.textCommentInput);

        cleanRating.setRating(5);
        ratingRating.setRating(5);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        reviewToilet(view);


                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddReviewActivity.this.getDialog().cancel();
                    }
                });






        return builder.create();
    }



    protected void reviewToilet(View view){

        Toilet toilet = DataHolder.getInstance().findToiletbyLatLng(latLng);

        User user = DataHolder.getInstance().getUser();

        //float cleanliness = (((RatingBar)view.findViewById(R.id.ratingBarClean)).getRating());

        float rating = (((RatingBar)view.findViewById(R.id.ratingBarRating)).getRating());

        String comment = ((EditText)view.findViewById(R.id.textCommentInput)).getText().toString();

        if(!comment.equals("")) {
            toilet.getComments().add(new Comment(user.getId(), comment));
        }

        if(rating!=0)
            toilet.getRatings().add(new Rating(user.getId(),rating));



        db.editToilet(toilet);
        db.updateData();

    }



}
