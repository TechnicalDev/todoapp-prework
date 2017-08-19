package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String todoItem = getIntent().getStringExtra("todoItemValue");
        EditText etEditItem = (EditText) findViewById(R.id.etEditItem);
        etEditItem.setText(todoItem);
    }

    public void onSaveItem(View v){
        EditText etEditItem = (EditText) findViewById(R.id.etEditItem);
        // Prepare data intent
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("todo", etEditItem.getText().toString());
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        // closes the activity and returns to first screen
        this.finish();
    }
}
