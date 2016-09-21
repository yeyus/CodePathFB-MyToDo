package com.ea7jmf.codepathfbtodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.ea7jmf.codepathfbtodo.model.Task;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int EDIT_REQUEST_CODE = 1;

    List<Task> todoItems;
    ArrayAdapter<Task> itemsAdapter;
    ListView lvItems;
    EditText etEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        todoItems = new ArrayList<>();
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(itemsAdapter);
        etEditText = (EditText) findViewById(R.id.etEditText);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                i.putExtra("taskId", todoItems.get(position).getId());
                i.putExtra("taskPosition", position);
                startActivityForResult(i, EDIT_REQUEST_CODE);
            }
        });

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Task removedTask = todoItems.remove(position);
                removedTask.delete();
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    private void populateArrayItems() {
        readItems();
        itemsAdapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1, todoItems);
    }

    private void readItems() {
        todoItems = Task.getAll();
    }

    public void onAddItem(View view) {
        Task t = new Task();
        t.name = etEditText.getText().toString();
        itemsAdapter.add(t);
        etEditText.setText("");
        t.save();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == EDIT_REQUEST_CODE) {
            long modifiedTaskId = data.getExtras().getLong("taskId");
            int modifiedTaskPosition = data.getExtras().getInt("taskPosition", -1);

            todoItems.set(modifiedTaskPosition, Task.getById(modifiedTaskId));
            itemsAdapter.notifyDataSetChanged();
        }
    }
}
