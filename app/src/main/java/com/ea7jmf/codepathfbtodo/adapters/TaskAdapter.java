package com.ea7jmf.codepathfbtodo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ea7jmf.codepathfbtodo.R;
import com.ea7jmf.codepathfbtodo.model.Task;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {

    public TaskAdapter(Context context, List<Task> tasks) {
        super(context, 0, tasks);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tvTaskName;
        TextView tvDueTime;
        final CheckBox cbDone;

        final Task task = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_task, parent, false);
        }

        tvTaskName = (TextView) convertView.findViewById(R.id.tvTaskName);
        tvDueTime = (TextView) convertView.findViewById(R.id.tvDueTime);
        cbDone = (CheckBox) convertView.findViewById(R.id.cbDone);

        tvTaskName.setText(task.name);
        cbDone.setChecked(task.done);

        cbDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.done = cbDone.isChecked();
                task.save();
            }
        });

        if (task.dueAt != null) {
            tvDueTime.setText(
                    DateUtils.getRelativeTimeSpanString(task.dueAt.getTime(), System.currentTimeMillis(), 0L, DateUtils.FORMAT_ABBREV_ALL));
            tvDueTime.setVisibility(View.VISIBLE);
        } else {
            tvDueTime.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }
}
