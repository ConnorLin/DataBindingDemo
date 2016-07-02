package com.connorlin.databinding.model;

/**
 * ClassName: User
 * Description:
 * Author: connorlin
 * Date: Created on 2016-6-29.
 */
public class User {
    private final String mFirstName;
    private final String mLastName;
    private int mAge;

    public User(String firstName, String lastName, int age) {
        this(firstName, lastName);
        mAge = age;
    }

    public User(String firstName, String lastName) {
        mFirstName = firstName;
        mLastName = lastName;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getFullName() {
        return mFirstName + " " + mLastName;
    }

    public int getAge() {
        return mAge;
    }

    public boolean isAdult() {
        return mAge >= 18;
    }
}
