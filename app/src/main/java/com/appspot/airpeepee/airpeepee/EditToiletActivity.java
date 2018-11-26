package com.appspot.airpeepee.airpeepee;

import com.appspot.airpeepee.airpeepee.model.DataHolder;
import  com.appspot.airpeepee.airpeepee.model.db;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.appspot.airpeepee.airpeepee.model.Comment;
import com.appspot.airpeepee.airpeepee.model.Rating;
import com.appspot.airpeepee.airpeepee.model.Toilet;
import com.google.android.gms.maps.model.LatLng;
import com.mvc.imagepicker.ImagePicker;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;

@SuppressLint("ValidFragment")
public class EditToiletActivity extends DialogFragment  {

    @SuppressLint("ValidFragment")
    EditToiletActivity(LatLng latLng){
       super();
       this.latLng=latLng;
    }

    //Photo upload stuff
    private ImageView imageView;
    private TextView textView;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;

    private LatLng latLng;

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) context;
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

        final View view =inflater.inflate(R.layout.activity_edit_toilet, null);
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner_rating);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.rating_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        //image picking for tha toilet
        imageView = (ImageView) view.findViewById(R.id.edit_image);
        ImagePicker.setMinQuality(600, 600);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //startActivityForResult(ImagePicker.getPickImageIntent(this, "Please select your photo"), PICK_IMAGE_REQUEST);
                //ImagePicker.pickImage(AddActivity.this, PICK_IMAGE_REQUEST);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

            }
        });
        loadToiletData(view);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        editToilet(view);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditToiletActivity.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    public void loadToiletData(View view)
    {
        Toilet toilet = DataHolder.getInstance().findToiletbyLatLng(latLng);
        ((EditText)view.findViewById(R.id.edit_name)).setText(toilet.getName());
        ((EditText)view.findViewById(R.id.edit_opening_hours)).setText(toilet.getOpeninghours());
        ((EditText)view.findViewById(R.id.edit_description)).setText(toilet.getDescription());
        if(toilet.isWheelchair().equals("yes"))
            ((Switch)view.findViewById(R.id.edit_wheelchair)).setChecked(true);
        else
            ((Switch)view.findViewById(R.id.edit_wheelchair)).setChecked(false);
        ((Switch)view.findViewById(R.id.add_ausser_betrieb)).setChecked(toilet.isOutoforder());
        if (toilet.isFee().equals("yes"))
            ((Switch)view.findViewById(R.id.edit_fee)).setChecked(true);
        else
            ((Switch)view.findViewById(R.id.edit_fee)).setChecked(false);
        ((EditText)view.findViewById(R.id.add_cost)).setText(Double.toString(toilet.getCost()));

    }


    public void editToilet(View view){

        Toilet toilet = DataHolder.getInstance().findToiletbyLatLng(latLng);
        String name = ((EditText)view.findViewById(R.id.edit_name)).getText().toString();
        String opening_hours = ((EditText)view.findViewById(R.id.edit_opening_hours)).getText().toString();
        String description = ((EditText)view.findViewById(R.id.edit_description)).getText().toString();
        String comment = ((EditText)view.findViewById(R.id.add_comment)).getText().toString();
        boolean wheelchair=((Switch)view.findViewById(R.id.edit_wheelchair)).isSelected();
        boolean outoforder=((Switch)view.findViewById(R.id.add_ausser_betrieb)).isSelected();
        boolean fee=((Switch)view.findViewById(R.id.edit_fee)).isSelected();
        double cost =Double.parseDouble(((EditText)view.findViewById(R.id.add_cost)).getText().toString());
        int rating = Integer.parseInt(((Spinner)view.findViewById(R.id.spinner_rating)).getSelectedItem().toString());

        toilet.setName(name);
        toilet.setOpeninghours(opening_hours);
        toilet.setDescription(description);
        if(!comment.equals(""))
            toilet.getComments().add(new Comment("user_id",comment));
        if(wheelchair)
            toilet.setWheelchair("yes");
        else
            toilet.setWheelchair("no");
        if(fee) {
            toilet.setWheelchair("yes");
            toilet.setCost(cost);
        }
        else {
            toilet.setWheelchair("no");
            toilet.setCost(0);
        }
        toilet.setOutoforder(outoforder);
        if(rating!=0)
            toilet.getRatings().add(new Rating("user_id",rating));
        toilet.setPhotoUrl(db.uploadImage(filePath, getApplicationContext()));
        db.editToilet(toilet);
        db.updateData();
    }
}
