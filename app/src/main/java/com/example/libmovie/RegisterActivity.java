package com.example.libmovie;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button reg = findViewById(R.id.register);
        reg.setOnClickListener(view -> registra());

    }

    public void registra(){
        PersonDatabase appDB = PersonDatabase.getInstance(this);


        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                EditText username = (EditText)findViewById(R.id.username);
                String userText = username.getText().toString();

                EditText et = (EditText)findViewById(R.id.password);
                String pswText = et.getText().toString();

                List<Person> p = appDB.personDAO().getPersonList();
                for(int i=0; i<p.size(); i++){
                    if(p.get(i).nickname.equals(userText) || userText.isEmpty() || pswText.isEmpty()){
                        new Handler(Looper.getMainLooper()).post(
                                new Runnable() {
                                    public void run() {
                                        // yourContext is Activity or Application context
                                        Toast.makeText(getBaseContext(), "INVALID USERNAME OR PASSWORD", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );

                        return;
                    }
                }
                appDB.personDAO().insertPerson(new Person(userText,pswText));
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}
