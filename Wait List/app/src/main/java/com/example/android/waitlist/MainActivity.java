package com.example.android.waitlist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.android.waitlist.data.WaitlistContract;
import com.example.android.waitlist.data.WaitlistDbHelper;


public class MainActivity extends AppCompatActivity {

    private GuestListAdapter mAdapter;
    private SQLiteDatabase mDb;
    private EditText mCategory_editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        android.app.FragmentManager manager=getFragmentManager();
        popfromclass dl=new popfromclass();
        dl.show(manager,null);



        setContentView(R.layout.activity_main);

        RecyclerView waitlistRecyclerView;

        waitlistRecyclerView = (RecyclerView) this.findViewById(R.id.category_list_view);
        mCategory_editText = (EditText) this.findViewById(R.id.category_editText);
        waitlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        WaitlistDbHelper dbHelper = new WaitlistDbHelper(this);

        mDb = dbHelper.getWritableDatabase();
        Cursor cursor = getAllGuests();

        mAdapter = new GuestListAdapter(this, cursor);

        waitlistRecyclerView.setAdapter(mAdapter);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                long id = (long) viewHolder.itemView.getTag();
                removeGuest(id);
                mAdapter.swapCursor(getAllGuests());
            }

        }).attachToRecyclerView(waitlistRecyclerView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu,menu);
        return true;}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        if(id==R.id.action_settings){
            Intent startSettingsActivity=new Intent(this,Settings_Avtivity.class);
            startActivity(startSettingsActivity);
            return true;

        }
        return super.onOptionsItemSelected(item);
    }


    public void addToWaitlist(View view) {
        if (mCategory_editText.getText().length() == 0) {
            return;
        }

        addNewGuest(mCategory_editText.getText().toString());

        mAdapter.swapCursor(getAllGuests());

        mCategory_editText.setText("");

    }


    private Cursor getAllGuests() {
        return mDb.query(
                WaitlistContract.WaitlistEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                WaitlistContract.WaitlistEntry.COLUMN_TIMESTAMP
        );
    }

    private long addNewGuest(String name) {
        ContentValues cv = new ContentValues();

        cv.put(WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME, name);
        return mDb.insert(WaitlistContract.WaitlistEntry.TABLE_NAME, null, cv);
    }

    private boolean removeGuest(long id) {
        return mDb.delete(WaitlistContract.WaitlistEntry.TABLE_NAME, WaitlistContract.WaitlistEntry._ID + "=" + id, null) > 0;
    }






}