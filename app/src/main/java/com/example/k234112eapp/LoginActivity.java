package com.example.k234112eapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.models.ListUserAccount;
import com.example.models.UserAccount;

public class LoginActivity extends AppCompatActivity {
    EditText edtUserName;
    EditText edtPassword;
    TextView txtMessage;
    CheckBox chkSaveLogin;
    String name_share_pref = "LoginInfo";
    RadioButton radAdmin, radEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        addViews();
        loadLoginFields();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addViews() {
        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        txtMessage = findViewById(R.id.txtMessage);
        chkSaveLogin = findViewById(R.id.chkSaveLogin);
        radAdmin = findViewById(R.id.radAdmin);
        radEmployee = findViewById(R.id.radEmployee);
    }

    private void loadLoginFields() {
        SharedPreferences preferences = getSharedPreferences(name_share_pref, MODE_PRIVATE);
        boolean saved = preferences.getBoolean("Saved", false);
        if (saved) {
            String username = preferences.getString("Username", "");
            String password = preferences.getString("Password", "");
            edtUserName.setText(username);
            edtPassword.setText(password);
        } else {
            edtUserName.setText(getString(R.string.str_default_username));
            edtPassword.setText(getString(R.string.str_default_password));
        }
        chkSaveLogin.setChecked(saved);
    }
    public void loginSystem(View view) {
        String username = edtUserName.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        UserAccount uc = ListUserAccount.login(username, password);
        if (uc!=null)
        {
            boolean saved = chkSaveLogin.isChecked();
            SharedPreferences preferences = getSharedPreferences(name_share_pref, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Username", username);
            editor.putString("Password", password);
            editor.putBoolean("Saved", saved);
            editor.apply();

            txtMessage.setText(getString(R.string.str_login_success));
            if (radAdmin.isChecked()) {
                //dĩ nhiên ta phải kiểm tra account này có quyền admin hay ko (tính sau)
                //Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                Intent intent=new Intent(LoginActivity.this,OrderManagementActivity.class);
                intent.putExtra("USER_LOGIN",uc);
                startActivity(intent);
            }
            else
            {
                Intent intent=new Intent(LoginActivity.this,EmployeeAdvancedManagementActivity.class);
                startActivity(intent);
            }
        }
        else
        {
            txtMessage.setText(getString(R.string.str_login_failed));
        }
    }
    public void exitSystem(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle(getString(R.string.str_exit));
        builder.setMessage(getString(R.string.str_want_exit));
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton(getString(R.string.str_yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton(getString(R.string.str_no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}
