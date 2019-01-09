package com.appspot.airpeepee.airpeepee.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import androidx.test.core.app.ApplicationProvider;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;


import static org.junit.Assert.*;

public class CommentTest {
    private static final String FAKE_ID = "714h27123c24";
    private static final String FAKE_CommentText = "best Toilet in the world";
    private static final User FAKE_User = new User();


    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getId() {
        Comment myObjectUnderTest = new Comment(FAKE_ID,FAKE_User,FAKE_CommentText);
        String result = myObjectUnderTest.getId();
        assertEquals(result,FAKE_ID);
    }

    @Test
    public void setId() {
        Comment myObjectUnderTest = new Comment();
        myObjectUnderTest.setId("714h27123c24");
        String result = myObjectUnderTest.getId();
        assertEquals(result,FAKE_ID);
    }

    @Test
    public void getCommentText() {
        Comment myObjectUnderTest = new Comment();
        myObjectUnderTest.setCommentText("best Toilet in the world");
        String result = myObjectUnderTest.getCommentText();
        assertEquals(result,FAKE_CommentText);
    }

    @Test
    public void setCommentText() {
        Comment myObjectUnderTest = new Comment();
        myObjectUnderTest.setCommentText("best Toilet in the world");
        String result = myObjectUnderTest.getCommentText();
        assertEquals(result,FAKE_CommentText);
    }

    @Test
    public void getUser() {
        Comment myObjectUnderTest = new Comment(FAKE_ID,FAKE_User,FAKE_CommentText);
        User user =new User();
        myObjectUnderTest.setUser(user);
        User result =myObjectUnderTest.getUser();
        assertSame(result,user);
    }

    @Test
    public void setUser() {
        Comment myObjectUnderTest = new Comment(FAKE_ID,FAKE_User,FAKE_CommentText);
        User user =new User();
        myObjectUnderTest.setUser(user);
        User result =myObjectUnderTest.getUser();
        assertSame(result,user);
    }
}