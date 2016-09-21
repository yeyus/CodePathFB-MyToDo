package com.ea7jmf.codepathfbtodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.ea7jmf.codepathfbtodo.model.Task;

public class EditItemActivity extends AppCompatActivity {

    long taskId;
    int taskPosition;
    Task editingTask;

    EditText etItemValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        taskId = getIntent().getLongExtra("taskId", -1);
        taskPosition = getIntent().getIntExtra("taskPosition", -1);

        editingTask = Task.getById(taskId);

        setContentView(R.layout.activity_edit_item);
        etItemValue = (EditText) findViewById(R.id.etItemValue);
        etItemValue.setText(editingTask.name);
    }

    public void onEdit(View view) {
        // persist edited task
        editingTask.name = etItemValue.getText().toString();
        editingTask.save();

        Intent data = new Intent();
        data.putExtra("taskId", taskId);
        data.putExtra("taskPosition", taskPosition);

        setResult(MainActivity.RESULT_OK, data);
        this.finish();
    }
}
