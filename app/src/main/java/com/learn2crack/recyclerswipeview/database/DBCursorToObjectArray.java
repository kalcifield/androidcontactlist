package com.learn2crack.recyclerswipeview.database;

import android.database.Cursor;

import com.learn2crack.recyclerswipeview.model.User;

import java.util.ArrayList;


public class DBCursorToObjectArray {
    private Cursor mCursor;
    private ArrayList<User> userArrayList;
    public DBCursorToObjectArray(Cursor cursor) {
        this.mCursor = cursor;
    }

    public ArrayList<User> getArray() {
        userArrayList = new ArrayList<>();
        mCursor.moveToFirst();
        while (mCursor.moveToNext()) {
            int userId = mCursor.getInt(mCursor.getColumnIndexOrThrow(DBContract.Contact._ID));
            String name = mCursor.getString(mCursor.getColumnIndexOrThrow(DBContract.Contact.COLUMN_NAME));
            int phone = mCursor.getInt(mCursor.getColumnIndexOrThrow(DBContract.Contact.COLUMN_PHONE_NUMBER));
            User tmp = new User(userId,name,phone);
            userArrayList.add(tmp);
        }
        return this.userArrayList;
    }
}
