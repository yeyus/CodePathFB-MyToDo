package com.ea7jmf.codepathfbtodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.ea7jmf.codepathfbtodo.model.Task;

import java.util.Calendar;
import java.util.Date;

public class EditItemActivity extends AppCompatActivity {

    long taskId;
    int taskPosition;
    Task editingTask;

    EditText etItemValue;
    DatePicker dpDue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        taskId = getIntent().getLongExtra("taskId", -1);
        taskPosition = getIntent().getIntExtra("taskPosition", -1);

        editingTask = Task.getById(taskId);

        setContentView(R.layout.activity_edit_item);
        etItemValue = (EditText) findViewById(R.id.etItemValue);
        dpDue = (DatePicker) findViewById(R.id.dpDue);
        etItemValue.setText(editingTask.name);

        Calendar c = Calendar.getInstance();
        if (editingTask.dueAt != null) {
            c.setTimeInMillis(editingTask.dueAt.getTime());
        }

        dpDue.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year, monthOfYear, dayOfMonth);
                editingTask.dueAt = new Date(c.getTimeInMillis());
            }
        });
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
