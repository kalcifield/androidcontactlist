package com.learn2crack.recyclerswipeview.database;

import android.provider.BaseColumns;


public final class DBContract {

    public static final String SELECT_ALL_FROM = "SELECT * FROM ";

//    public static final String SELECT_EMPLOYEE_WITH_EMPLOYER = "SELECT * " +
//            "FROM " + Employee.TABLE_NAME + " ee INNER JOIN " + Employer.TABLE_NAME + " er " +
//            "ON ee." + Employee.COLUMN_EMPLOYER_ID + " = er." + Employer._ID + " WHERE " +
//            "ee." + Employee.COLUMN_FIRSTNAME + " like ? AND ee." + Employee.COLUMN_LASTNAME + " like ?";

    private DBContract() {
    }

    public static class Contact implements BaseColumns {
        public static final String TABLE_NAME = "contact";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PHONE_NUMBER = "phonenumber";

        public static final String DELETE_BY_CONTACT_ID = "DELETE FROM " + TABLE_NAME +
                " WHERE _id = ";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PHONE_NUMBER + " INTEGER" + ")";        // TODO: 2017.10.05. obv not text
    }
}
