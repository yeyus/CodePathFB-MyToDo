package com.ea7jmf.codepathfbtodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    String text;
    int position;

    EditText etItemValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        text = getIntent().getStringExtra("text");
        position = getIntent().getIntExtra("position", -1);

        setContentView(R.layout.activity_edit_item);
        etItemValue = (EditText) findViewById(R.id.etItemValue);
        etItemValue.setText(text);
    }

    public void onEdit(View view) {
        Intent data = new Intent();
        data.putExtra("text", etItemValue.getText().toString());
        data.putExtra("position", position);

        setResult(MainActivity.RESULT_OK, data);
        this.finish();
    }
}
