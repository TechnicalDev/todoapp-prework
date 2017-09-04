package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lvItems;
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;

    private final int REQUEST_CODE = 10;
    private int editItemPOS = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView)findViewById(R.id.lvItems);
        loadItems();
        setupListViewListener();
    }

    private void loadItems(){
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        lvItems.setAdapter(itemsAdapter);
    }

    public void onAddItem(View v){
        EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
        ToDo todo = new ToDo();
        todo.todoItem = etNewItem.getText().toString();
        writeItem(todo);
        etNewItem.setText("");
        loadItems();
    }

    private void setupListViewListener(){
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View item, int pos, long id) {
                        deleteItem(pos);
                        return true;
                    }
                }
        );
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View item, int pos, long id){
                        editItemPOS = pos;
                        launchEditView((String)adapterView.getItemAtPosition(pos));
                    }
                }
        );
    }

    private void readItems(){
        // Get singleton instance of database
        TodoItemDatabase databaseHelper = TodoItemDatabase.getInstance(this);
        // Get all todos from database
        List<ToDo> todos = databaseHelper.getAllToDos();
        items = new ArrayList<String>();
        for (ToDo todo : todos) {
            items.add(todo.todoItem);
        }
    }

    private void writeItem(ToDo todo){
        // Get singleton instance of database
        TodoItemDatabase databaseHelper = TodoItemDatabase.getInstance(this);
        // Add sample todoitem to the database
        databaseHelper.addToDo(todo);
    }

    private void deleteItem(int todoId){
        // Get singleton instance of database
        TodoItemDatabase databaseHelper = TodoItemDatabase.getInstance(this);
        // delete todoitem to the database
        databaseHelper.deleteTodo(todoId);
        items.remove(todoId);
        itemsAdapter.notifyDataSetChanged();
    }

    public void launchEditView(String todoItem) {
        // first parameter is the context, second is the class of the activity to launch
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        // put "extras" into the bundle for access in the second activity
        i.putExtra("todoItemValue",todoItem);
        startActivityForResult(i,REQUEST_CODE); // brings up the edit activity
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String todoItem = data.getExtras().getString("todo");
            updateItem(todoItem);
            // Toast the name to display temporarily on screen
            Toast.makeText(this, todoItem, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateItem(String todoItem){
        // Get singleton instance of database
        TodoItemDatabase databaseHelper = TodoItemDatabase.getInstance(this);
        // Update todoitem to the database
        databaseHelper.updateToDo(editItemPOS,todoItem);
        items.set(editItemPOS,todoItem);
        itemsAdapter.notifyDataSetChanged();
    }

}
