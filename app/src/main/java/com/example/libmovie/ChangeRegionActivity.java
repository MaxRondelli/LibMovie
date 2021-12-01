package com.example.libmovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ChangeRegionActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButton;
    Button apply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_region);

        TextView currentChoice = (TextView) findViewById(R.id.choice);
        currentChoice.setText("Current choice: " + MainActivity.REGION);

        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        apply = (Button) findViewById(R.id.apply);

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(radioId);

                if (radioButton.getText().equals("United States")) MainActivity.REGION = "US";
                else if (radioButton.getText().equals("Italy")) MainActivity.REGION = "IT";
                else if (radioButton.getText().equals("Lithuania")) MainActivity.REGION = "LT";
                else if (radioButton.getText().equals("United Kingdom")) MainActivity.REGION = "UK";

                Intent intent = new Intent(ChangeRegionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}