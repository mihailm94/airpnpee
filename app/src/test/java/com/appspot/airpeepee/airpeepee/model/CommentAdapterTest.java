package com.appspot.airpeepee.airpeepee.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class CommentAdapterTest {

    private static List<Comment> FAKE_LIST  = new ArrayList<Comment>()
    {
        {
            add(new Comment("1", new User(), "1 comment"));
            add(new Comment("2", new User(), "2 comment"));
            add(new Comment("3", new User(), "3 comment"));
            add(new Comment("4", new User(), "4 comment"));
            add(new Comment("5", new User(), "5 comment"));
            add(new Comment("6", new User(), "6 comment"));
        }
    };

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void onCreateViewHolder() {
    }

    @Test
    public void onBindViewHolder() {
    }

    @Test
    public void getItemCount() {
    }

    @Test
    public void onCreateViewHolder1() {
    }

    @Test
    public void onBindViewHolder1() {
    }

    @Test
    public void onBindViewHolder2() {
    }

    @Test
    public void createViewHolder() {
    }

    @Test
    public void bindViewHolder() {
    }

    @Test
    public void getItemViewType() {
    }

    @Test
    public void setHasStableIds() {
    }

    @Test
    public void getItemId() {
    }

    @Test
    public void getItemCount1() {
    }

    @Test
    public void hasStableIds() {
    }

    @Test
    public void onViewRecycled() {
    }

    @Test
    public void onFailedToRecycleView() {
    }

    @Test
    public void onViewAttachedToWindow() {
    }

    @Test
    public void onViewDetachedFromWindow() {
    }

    @Test
    public void hasObservers() {
    }

    @Test
    public void registerAdapterDataObserver() {
    }

    @Test
    public void unregisterAdapterDataObserver() {
    }

    @Test
    public void onAttachedToRecyclerView() {
    }

    @Test
    public void onDetachedFromRecyclerView() {
    }

    @Test
    public void notifyDataSetChanged() {
    }

    @Test
    public void notifyItemChanged() {
    }

    @Test
    public void notifyItemChanged1() {
    }

    @Test
    public void notifyItemRangeChanged() {
    }

    @Test
    public void notifyItemRangeChanged1() {
    }

    @Test
    public void notifyItemInserted() {
    }

    @Test
    public void notifyItemMoved() {
    }

    @Test
    public void notifyItemRangeInserted() {
    }

    @Test
    public void notifyItemRemoved() {
    }

    @Test
    public void notifyItemRangeRemoved() {
    }
}