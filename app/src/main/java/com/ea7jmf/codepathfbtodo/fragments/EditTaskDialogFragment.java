package com.ea7jmf.codepathfbtodo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.ea7jmf.codepathfbtodo.R;
import com.ea7jmf.codepathfbtodo.model.Task;

import java.util.Calendar;
import java.util.Date;

public class EditTaskDialogFragment extends DialogFragment implements View.OnClickListener {

    private long taskId;
    private int taskPosition;

    EditText etItemValue;
    DatePicker dpDue;
    Button btnEdit;
    Task editingTask;

    public interface EditTaskDialogListener {
        void onFinishEditDialog(long taskId, int taskPosition);
    }

    public EditTaskDialogFragment() {}

    public static EditTaskDialogFragment newInstance(long taskId, int taskPosition) {
        EditTaskDialogFragment frag = new EditTaskDialogFragment();
        Bundle args = new Bundle();
        args.putLong("taskId", taskId);
        args.putInt("taskPosition", taskPosition);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_item, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        btnEdit = (Button) view.findViewById(R.id.btnEdit);
        etItemValue = (EditText) view.findViewById(R.id.etItemValue);
        dpDue = (DatePicker) view.findViewById(R.id.dpDue);

        // Fetch arguments from bundle and set title
        taskId = getArguments().getLong("taskId");
        taskPosition = getArguments().getInt("taskPosition");
        editingTask = Task.load(Task.class, taskId);

        btnEdit.setOnClickListener(this);

        etItemValue.setText(editingTask.name);
        getDialog().setTitle("Edit To Do");

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

        // Show soft keyboard automatically and request focus to field
        etItemValue.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void onClick(View v) {
        // persist the modification
        editingTask.name = etItemValue.getText().toString();
        editingTask.save();

        // call listener
        EditTaskDialogListener listener = (EditTaskDialogListener) getActivity();
        listener.onFinishEditDialog(taskId, taskPosition);

        // Close the dialog and return back to the parent activity
        dismiss();
    }
}
