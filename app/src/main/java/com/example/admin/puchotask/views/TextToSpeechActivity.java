package com.example.admin.puchotask.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.admin.puchotask.R;

public class TextToSpeechActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);

        final EditText etInputText = findViewById(R.id.et_InputText);
        String content = etInputText.getText().toString();
    }

    public void start(Context context) {
        Intent intent = new Intent(context, TextToSpeechActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
}
