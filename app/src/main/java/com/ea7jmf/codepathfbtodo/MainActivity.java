package com.ea7jmf.codepathfbtodo;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.ea7jmf.codepathfbtodo.adapters.TaskAdapter;
import com.ea7jmf.codepathfbtodo.fragments.EditTaskDialogFragment;
import com.ea7jmf.codepathfbtodo.model.Task;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements EditTaskDialogFragment.EditTaskDialogListener {

    public static final int EDIT_REQUEST_CODE = 1;

    List<Task> todoItems;
    TaskAdapter itemsAdapter;
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
//                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
//                i.putExtra("taskId", todoItems.get(position).getId());
//                i.putExtra("taskPosition", position);
//                startActivityForResult(i, EDIT_REQUEST_CODE);
                showEditDialog(todoItems.get(position).getId(), position);
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

    public void onAddItem(View view) {
        Task t = new Task();
        t.name = etEditText.getText().toString();
        itemsAdapter.add(t);
        etEditText.setText("");
        t.save();
    }

    private void populateArrayItems() {
        readItems();
        itemsAdapter = new TaskAdapter(this, todoItems);
    }

    private void readItems() {
        todoItems = Task.getAll();
    }

    private void showEditDialog(long taskId, int taskPosition) {
        FragmentManager fm = getSupportFragmentManager();
        EditTaskDialogFragment editNameDialogFragment = EditTaskDialogFragment.newInstance(taskId, taskPosition);
        editNameDialogFragment.show(fm, "fragment_edit_task");
    }

    @Override
    public void onFinishEditDialog(long taskId, int taskPosition) {
        todoItems.set(taskPosition, Task.getById(taskId));
        itemsAdapter.notifyDataSetChanged();
    }
}
