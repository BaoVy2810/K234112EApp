package com.example.k234112eapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.models.ListUserAccount;
import com.example.models.UserAccount;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LoginActivity extends AppCompatActivity {
    EditText edtUserName;
    EditText edtPassword;
    TextView txtMessage;
    CheckBox chkSaveLogin;
    String name_share_pref = "LoginInfo";
    RadioButton radAdmin, radEmployee;
    Button btnLogin,btnExit;

    public static final String DATABASE_NAME = "K234112ESales.sqlite";
    // chỉ đổi tên file database_name thôi
    public static final String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database = null;
    private void copyDataBase(){
        try{
            File dbFile = getDatabasePath(DATABASE_NAME);
            if(!dbFile.exists()){
                if(CopyDBFromAsset()){
                    Toast.makeText(LoginActivity.this,
                            "Copy database successful!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(LoginActivity.this,
                            "Copy database fail!", Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception e){
            Log.e("Error: ", e.toString());
        }
    }

    private boolean CopyDBFromAsset() {
        String dbPath = getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
        try {
            InputStream inputStream = getAssets().open(DATABASE_NAME);
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if(!f.exists()){
                f.mkdir();
            }
            OutputStream outputStream = new FileOutputStream(dbPath);
            byte[] buffer = new byte[1024]; int length;
            while((length=inputStream.read(buffer))>0){
                outputStream.write(buffer,0, length);
            }
            outputStream.flush();  outputStream.close(); inputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        addViews();
        copyDataBase();
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
        btnLogin=findViewById(R.id.btnLogin);
        btnExit = findViewById(R.id.btnExit);
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
                //Intent intent=new Intent(LoginActivity.this,OrderManagementActivity.class);
                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
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
    public void loginSystemOld(View view) {
        String username=edtUserName.getText().toString();
        String password=edtPassword.getText().toString();
        if(username.equalsIgnoreCase("admin") &&
                password.equals("123"))
        {
            boolean saved=chkSaveLogin.isChecked();
            SharedPreferences preferences=getSharedPreferences(name_share_pref,MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("UserName",username);
            editor.putString("Password",password);
            editor.putBoolean("Saved",saved);
            editor.commit();

            txtMessage.setText(getString(R.string.str_login_success));
            if(radAdmin.isChecked()) {
                //dĩ nhiên ta phải kiểm tra account này có quyền admin hay ko (tính sau)
                //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                Intent intent = new Intent(LoginActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
            else
            {
                Intent intent = new Intent(LoginActivity.this, EmployeeAdvancedManagementActivity.class);
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
    BroadcastReceiver internetStateReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if(action.equals(ConnectivityManager.CONNECTIVITY_ACTION))
            {

            }
            ConnectivityManager connectivirtManager= (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
            if (connectivirtManager != null)
            {
                NetworkInfo networkInfo = connectivirtManager.getActiveNetworkInfo();
                if(networkInfo != null && networkInfo.isConnected())
                {
                    btnLogin.setEnabled(true);
                }
                else
                {
                    btnLogin.setEnabled(false);
                }
            }
        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences=getSharedPreferences(name_share_pref,MODE_PRIVATE);
        String username=preferences.getString("UserName","");
        String password=preferences.getString("Password","");
        boolean saved=preferences.getBoolean("Saved",false);
        if(saved)
        {
            edtUserName.setText(username);
            edtPassword.setText(password);
        }
        chkSaveLogin.setChecked(saved);
        IntentFilter intentFilter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(internetStateReceiver, intentFilter);
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(internetStateReceiver);
    }
}
// lưu tạm thời trong onPause
// phục hồi trong onResume