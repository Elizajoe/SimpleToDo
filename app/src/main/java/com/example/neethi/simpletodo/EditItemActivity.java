package com.example.neethi.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class EditItemActivity extends Activity {

    private EditText etName;
    private int position;
    private String editName;
    String savedItemText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Intent i =getIntent();
        editName = i.getStringExtra("editItem");
        position = i.getIntExtra("position",0);


        etName =(EditText) findViewById(R.id.editItem);
        etName.setText(editName);
        etName.setSelection(etName.getText().length());
        savedItemText=etName.getText().toString();
        System.out.println("the new item is "+ savedItemText);

       /* String itemText = etName.getText().toString(); // string to passback
        Intent data = new Intent();
        data.putExtra("name",itemText);
        data.putExtra("position",position);
        //setResult(RESULT_OK, data);
        //finish();*/

    }

    public void onSavedItem(View v) {
        savedItemText = etName.getText().toString();
        Intent data = new Intent();
        data.putExtra("name",savedItemText);
        data.putExtra("position",position);
        setResult(Activity.RESULT_OK, data);
        finish();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
