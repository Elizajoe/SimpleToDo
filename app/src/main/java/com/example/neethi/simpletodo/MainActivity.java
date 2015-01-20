package com.example.neethi.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends Activity {

    private ArrayList<String> todoItems;
    private ArrayAdapter<String> todoAdapter;
    private ListView lvItems;
    private EditText etNewItem;
    private final int REQUEST_CODE = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etNewItem = (EditText)findViewById(R.id.etNewItem);
        lvItems = (ListView)findViewById(R.id.lvItems);
        readItems();
        todoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,todoItems );
        lvItems.setAdapter(todoAdapter);
        setupListViewListener();
        setupEditFeature();

    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                todoItems.remove(position);
                todoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });




    }

    private void setupEditFeature(){
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                String editItem = todoItems.get(position);
                Intent i = new Intent(getBaseContext(), EditItemActivity.class);
                i.putExtra("editItem",editItem);

                i.putExtra("position",position);
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }





    @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String name = data.getExtras().getString("name");
            System.out.println(" Received the new value here " +name );
            int position =data.getExtras().getInt("position",0);
            //int pos =Integer.parseInt(position);
            todoItems.set(position,name);
            todoAdapter.notifyDataSetChanged();
        }

    }





    public void onAddedItem(View v){
        String itemText =etNewItem.getText().toString();
        todoAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }

    public void readItems() {
        File filesDir =getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try{
            todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
        }catch(IOException e){
           todoItems =new ArrayList<String>();
        }

    }


    private void writeItems(){
        File filesDir =getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile,todoItems);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
