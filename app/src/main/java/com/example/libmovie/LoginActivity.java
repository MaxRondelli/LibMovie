package com.example.libmovie;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        RelativeLayout relativeLayout = findViewById(R.id.login);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();



        try {
            FileOutputStream fOut = openFileOutput("user.txt", MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);
            osw.write("marfra99x password\n");
            osw.write("root root\n");
            osw.write("superuser psw\n");
            osw.flush();
            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button btn = findViewById(R.id.sign_in);
        btn.setOnClickListener(view -> openMain());
    }

    public void openMain() {
        try {
            EditText username = (EditText)findViewById(R.id.username);
            String userText = username.getText().toString();

            EditText et = (EditText)findViewById(R.id.password);
            String pswText = et.getText().toString();

            Intent intent = new Intent(this, MainActivity.class);
            FileInputStream fin = openFileInput("user.txt");
            int c='\n';
            while(c=='\n' && !userText.isEmpty() && !pswText.isEmpty()) {
                String user = "";
                String psw = "";
                while ((c = fin.read()) != ' ') {
                    user = user + Character.toString((char) c);
                }

                while ((c = fin.read()) != '\n' && c != -1) {
                    psw = psw + Character.toString((char) c);
                }
                if(user.equals(userText) && psw.equals(pswText)){
                    startActivity(intent);
                    return;
                }
                System.out.println(user + " " + psw + " " + userText + " " + pswText + "\n");
            }
        } catch(Exception e){}

        Toast.makeText(getApplicationContext(),"Wrong username or password",Toast.LENGTH_SHORT).show();
    }
}
