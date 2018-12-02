package com.appspot.airpeepee.airpeepee.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appspot.airpeepee.airpeepee.R;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {
    private List<Comment> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mCommentview;
        public TextView mUserIdview;
        public MyViewHolder(View v) {
            super(v);
            mCommentview = v.findViewById(R.id.view_comment);
            mUserIdview = v.findViewById(R.id.view_user_id);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CommentAdapter(List<Comment> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CommentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_card, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mCommentview.setText(mDataset.get(position).getCommentText());
        holder.mUserIdview.setText(mDataset.get(position).getId());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
