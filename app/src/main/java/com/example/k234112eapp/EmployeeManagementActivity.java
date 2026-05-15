package com.example.k234112eapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class EmployeeManagementActivity extends AppCompatActivity {
    Button btnExit;
    ListView lvEmployee;
    ArrayList<String>listEmployee;
    ArrayAdapter<String>adapterEmployee;
    EditText edtId,edtName,edtPhone;
    int selectedItemIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employee_management);
        addViews();
        addEvents();
        loadData();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void loadData() {
        listEmployee.add("e1-Jungkook-0916829171");
        listEmployee.add("e2-TaeHyung-0927289911");
        listEmployee.add("e3-James-0917189372");
        listEmployee.add("e4-Keonho-0962718819");
        listEmployee.add("e5-Sean-0289292829");
        //nói Adapter cập nhật giao diện
        adapterEmployee.notifyDataSetChanged();
    }

    private void addEvents() {
        btnExit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                processExit();
            }
        });
        lvEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                displayEmployeeInfor(i);
            }
        });
    }

    private void displayEmployeeInfor(int i) {
        selectedItemIndex = i;
        String data=listEmployee.get(i);
        String[]items=data.split("-");
        //hiển thị items[0]-->id, items[1]->name , items[2]->phone
        edtId.setText(items[0]);
        edtName.setText(items[1]);
        edtPhone.setText(items[2]);
    }

    private void processExit() {
        Dialog custom=new Dialog(this);
        custom.setContentView(R.layout.custom_dialog);
        ImageView imgSave=custom.findViewById(R.id.imgSave);
        ImageView imgCancel=custom.findViewById(R.id.imgCancel);
        imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                custom.dismiss();
            }
        });
        custom.show();
    }
    private void addViews() {
        btnExit=findViewById(R.id.btnExit);
        lvEmployee=findViewById(R.id.lvEmployee);
        listEmployee=new ArrayList<>();
        adapterEmployee= new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                listEmployee);
        lvEmployee.setAdapter(adapterEmployee);

        edtId=findViewById(R.id.edtId);
        edtName=findViewById(R.id.edtName);
        edtPhone=findViewById(R.id.edtPhone);
    }

    public void saveEmployee(View view) {
        String id = edtId.getText().toString();
        String name = edtName.getText().toString();
        String phone = edtPhone.getText().toString();
        String newEmployee = id + "-" + name + "-" + phone;

        int indexUpdate = -1;
        for (int i = 0; i < listEmployee.size(); i++) {
            String existingData = listEmployee.get(i);
            String[] items = existingData.split("-");
            if (items[0].equals(id)) {
                indexUpdate = i;
                break;
            }
        }

        if (indexUpdate != -1) {
            // Nếu id đã tồn tại thì cập nhật
            listEmployee.set(indexUpdate, newEmployee);
        } else {
            // Nếu id của employee chưa tồn tại thì thêm mới
            listEmployee.add(newEmployee);
        }

        // Cập nhật lại giao diện ListView
        adapterEmployee.notifyDataSetChanged();

        // Xóa trắng các trường nhập liệu sau khi lưu
        edtId.setText("");
        edtName.setText("");
        edtPhone.setText("");
        edtId.requestFocus();
    }

    public void removeEmployee(View view) {
        if (selectedItemIndex == -1) {
            Toast.makeText(this, R.string.str_msg_select_employee, Toast.LENGTH_SHORT).show();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.str_confirm_delete_title);
        builder.setMessage(R.string.str_confirm_delete_msg);
        builder.setIcon(android.R.drawable.ic_delete);
        builder.setPositiveButton(R.string.str_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listEmployee.remove(selectedItemIndex);
                adapterEmployee.notifyDataSetChanged();
                selectedItemIndex = -1;
                edtId.setText("");
                edtName.setText("");
                edtPhone.setText("");
                edtId.requestFocus();
                Toast.makeText(EmployeeManagementActivity.this, R.string.str_delete_success, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.str_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}