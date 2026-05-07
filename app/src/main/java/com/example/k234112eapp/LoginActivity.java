package com.example.k234112eapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
    /*
    Declare all variables for interactive view
     * */
    EditText edtUserName;
    EditText edtPassword;
    TextView txtMessage;
    CheckBox chkSaveLogin;
    String name_share_pref="LoginInfo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        addViews();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addViews() {
        edtUserName=findViewById(R.id.edtUserName);
        edtPassword=findViewById(R.id.edtPassword);
        txtMessage=findViewById(R.id.txtMessage);
        chkSaveLogin=findViewById(R.id.chkSaveLogin);
    }

    public void loginSystem(View view){
        String username=edtUserName.getText().toString();
        String password=edtPassword.getText().toString();
        if(username.equalsIgnoreCase("admin")&&
                password.equals("123"))
        {
            boolean saved=chkSaveLogin.isChecked();
            SharedPreferences preferences=getSharedPreferences(name_share_pref,MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("Username",username);
            editor.putString("Password",password);
            editor.putBoolean("Saved",saved);
            editor.commit(); //lưu lại những edit trên


            txtMessage.setText(getString(R.string.str_login_success));
            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else
        {
            txtMessage.setText(getString(R.string.str_login_failed));
        }
    }

    public void exitSystem(View view) {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences=getSharedPreferences(name_share_pref,MODE_PRIVATE);
        String username=preferences.getString("UserName","");
        String password=preferences.getString("PassWord","");
        boolean saved=preferences.getBoolean("Saved",false);
        if(saved)
        {
            edtUserName.setText(username);
            edtPassword.setText(password);
        }
        chkSaveLogin.setChecked(saved);
    }
}