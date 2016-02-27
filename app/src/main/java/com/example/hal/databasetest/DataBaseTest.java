package com.example.hal.databasetest;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class DataBaseTest extends AppCompatActivity {


    DataBase db;
    View view;
    SQLiteOpenHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DataBase();
        db.DBWritableOpen(getApplicationContext());

        Output();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etName = (EditText) findViewById(R.id.editText_name);
                EditText etAge = (EditText) findViewById(R.id.editText_age);

                String strName = etName.getText().toString();
                int intAge = Integer.parseInt(etAge.getText().toString());

                db.DBSave(strName, intAge);

                Snackbar.make(view, strName + ":" + intAge + " Saved", Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                db.DBUndo();
                                Output();
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_data_base_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.action_settings:
                return true;

            case R.id.action_delete:
                return true;

        }


        return super.onOptionsItemSelected(item);
    }

    /**
     * データをListViewに表示する
     */
    public void Output() {
        ListView listview = (ListView) findViewById(R.id.listView);

        Cursor c = db.TableQuery();
        String[] from = {"_id", "name", "age"};
        int[] to = {R.id.listlayout_tv1, R.id.listlayout_tv2, R.id.listlayout_tv3};

        SimpleCursorAdapter adapter =
                new SimpleCursorAdapter(this, R.layout.listviewlayout, c, from, to, 0);
        listview.setAdapter(adapter);
        //c.close();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View view, int pos, long id) {
                // 選択アイテムを取得
                helper = new DataBaseHelper(getApplicationContext());
                SQLiteDatabase db = helper.getReadableDatabase();

                Cursor c = db.query(DataBase.TABLE_NAME,
                        new String[]{DataBase.ID, DataBase.NAME, DataBase.AGE},
                        null, null, null, null, null);

                if (c.moveToPosition(pos)) {
                    // 検索結果をCursorから取り出す

                    Bundle bundle = new Bundle();

                    bundle.putInt(DataBase.ID, c.getInt(0));
                    bundle.putString(DataBase.NAME, c.getString(1));
                    bundle.putInt(DataBase.AGE, c.getInt(2));

                    TestDialogFragment dialog = new TestDialogFragment();
                    dialog.setArguments(bundle);
                    dialog.show(getFragmentManager(), "test");
                }
                c.close();
            }
        });

    }
}