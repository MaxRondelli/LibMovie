package com.example.libmovie;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    static boolean correct;
    static String loggedUser="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        RelativeLayout relativeLayout = findViewById(R.id.login);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();


        Button btn = findViewById(R.id.sign_in);
        btn.setOnClickListener(view -> openMain());

        Button reg = findViewById(R.id.register);
        reg.setOnClickListener(view -> registra());
    }

    public void registra(){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void openMain() {

        PersonDatabase appDB = PersonDatabase.getInstance(this);

        correct = false;
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                /*
                // DELETE ALL DB ELEMENT
                List<Person> tmp = appDB.personDAO().getPersonList();
                for(int i=0; i<tmp.size(); i++) {
                    appDB.personDAO().deletePerson(tmp.get(i));
                }
                */

                EditText username = (EditText)findViewById(R.id.username);
                String userText = username.getText().toString();

                EditText et = (EditText)findViewById(R.id.password);
                String pswText = et.getText().toString();

                List<Person> p = appDB.personDAO().getPersonList();
                for(int i=0; i<p.size(); i++){
                    if(p.get(i).nickname.equals(userText) && p.get(i).password.equals(pswText)){
                        loggedUser = userText;
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        return;
                    }
                }
                new Handler(Looper.getMainLooper()).post(
                        new Runnable() {
                            public void run() {
                                // yourContext is Activity or Application context
                                Toast.makeText(getBaseContext(), "INVALID USERNAME OR PASSWORD", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });



    }
}
