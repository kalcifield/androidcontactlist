package com.learn2crack.recyclerswipeview;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.learn2crack.recyclerswipeview.database.DBContract;
import com.learn2crack.recyclerswipeview.database.DBCursorToObjectArray;
import com.learn2crack.recyclerswipeview.database.DBSQLiteHelper;

import com.learn2crack.recyclerswipeview.databinding.ActivityMainBinding;

import com.learn2crack.recyclerswipeview.model.User;

import java.util.ArrayList;

public class ContactManagerActivity extends AppCompatActivity implements UserAdapter.OnCardClickListner{
    private RecyclerView.LayoutManager nLayoutManager;
    private ActivityMainBinding binding;
    private UserAdapter adapter;
//    databasereadingstuff
//    should it be closed?
    private SQLiteDatabase database;
    private Cursor cursor;
//    endofdatabasestuff

//    private Paint p = new Paint();
//    private AlertDialog.Builder alertDialog;
//    private EditText et_username;
//    private int edit_position;
//    private View view;
//    private boolean add = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
//        fillFakeDataDB();
        readFromDb();
        initViews();
//        initDialog();

    }

    private void readFromDb() {
        database = new DBSQLiteHelper(this).getWritableDatabase();
        cursor = database.rawQuery(DBContract.SELECT_ALL_FROM + DBContract.Contact.TABLE_NAME, null);
//        DBCursorToObjectArray conv = new DBCursorToObjectArray(cursor);
//        userList = conv.getArray();

    }

//    private void fillFakeDataDB () {
//        database = new DBSQLiteHelper(this).getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(DBContract.Contact.COLUMN_NAME, "kakesz");
//        values.put(DBContract.Contact.COLUMN_PHONE_NUMBER, "makesz");
//        long newRowId = database.insert(DBContract.Contact.TABLE_NAME, null, values);
//        database.insert(DBContract.Contact.TABLE_NAME, null, values);
//        database.insert(DBContract.Contact.TABLE_NAME, null, values);
//    }

    private void initViews() {
        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContact();
            }
        });
        nLayoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(nLayoutManager);

        adapter = new UserAdapter(this, new DBCursorToObjectArray(cursor).getArray());
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnCardClickListner(this);
        adapter.notifyDataSetChanged();
//        initSwipe();
    }

//    private void initSwipe() {
//        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//
//            @Override
//            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//                int position = viewHolder.getAdapterPosition();
//
//                if (direction == ItemTouchHelper.LEFT){
//                    removeContact(position);
//                } else {
////                    removeView();
////                    edit_position = position;
////                    alertDialog.setTitle("Edit Contact");
////                    et_username.setText(userList.get(position).getName());
////                    alertDialog.show();
//                }
//            }
//
//            @Override
//            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//
//                Bitmap icon;
//                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
//
//                    View itemView = viewHolder.itemView;
//                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
//                    float width = height / 3;
//
//                    if(dX > 0){
//                        p.setColor(Color.parseColor("#388E3C"));
//                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
//                        c.drawRect(background,p);
//                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_edit_white);
//                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
//                        c.drawBitmap(icon,null,icon_dest,p);
//                    } else {
//                        p.setColor(Color.parseColor("#D32F2F"));
//                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
//                        c.drawRect(background,p);
//                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_white);
//                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
//                        c.drawBitmap(icon,null,icon_dest,p);
//                    }
//                }
//                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//            }
//        };
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
//        itemTouchHelper.attachToRecyclerView(binding.recyclerView);
//    }

    private void saveContact() {
        ContentValues values = new ContentValues();

        String name = binding.nameEditText.getText().toString();
        String phone = binding.phoneNumberEditText.getText().toString();
        if(name.trim().equals("") ||
                phone.trim().equals("")) {
            Toast.makeText(this, "Please fill every field.", Toast.LENGTH_LONG).show();
            return;
        }
        int phoneNumber = Integer.parseInt(phone);
        values.put(DBContract.Contact.COLUMN_NAME, name);
        values.put(DBContract.Contact.COLUMN_PHONE_NUMBER, phoneNumber);

//        returns -1 if an error occured
        int rowId = (int) database.insert(DBContract.Contact.TABLE_NAME, null, values);

        adapter.addUser(new User(rowId, name,phoneNumber));
        Toast.makeText(this, "Contact saved.", Toast.LENGTH_SHORT).show();

        binding.nameEditText.setText("");
        binding.phoneNumberEditText.setText("");

    }

    private void removeContact(int position) {
        int id = adapter.getUserId(position);
        try {
            database.execSQL(DBContract.Contact.DELETE_BY_CONTACT_ID + id);
            adapter.removeUser(position);
        }catch (Exception e) {
            Toast.makeText(this, "Something went wrong.", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "Contact removed", Toast.LENGTH_SHORT).show();
    }

    private void confirmDelete(final View view, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete contact?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        removeContact(position);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

//    private void removeView(){
//        if(view.getParent()!=null) {
//            ((ViewGroup) view.getParent()).removeView(view);
//        }
//    }
//    private void initDialog(){
//        alertDialog = new AlertDialog.Builder(this);
//        view = getLayoutInflater().inflate(R.layout.dialog_layout,null);
//        alertDialog.setView(view);
//        alertDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if(add){
//                    add =false;
//                    String name = et_username.getText().toString();
//                    User tempUser = new User(name,32);
//                    adapter.addUser(tempUser);
//
//                    dialog.dismiss();
//                } else {
//                    User currentUser = userList.get(edit_position);
//                    currentUser.setName(et_username.getText().toString());
//                    userList.set(edit_position,currentUser);
//                    adapter.notifyDataSetChanged();
//                    dialog.dismiss();
//                }
//
//            }
//        });
//        alertDialog.setCancelable(false);
//        et_username = (EditText)view.findViewById(R.id.nameEditText);
//    }


    @Override
    public void OnCardClicked(View view, int position) {
        confirmDelete(view, position);
    }
}
