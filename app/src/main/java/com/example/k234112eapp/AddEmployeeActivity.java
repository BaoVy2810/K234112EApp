package com.example.k234112eapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.models.Employee;

import java.util.ArrayList;

public class AddEmployeeActivity extends AppCompatActivity {
    EditText edtId, edtName, edtPhone;
    AutoCompleteTextView actBirthplace;
    ArrayList <String> listBirthPlace;
    ArrayAdapter<String> adapterBirthPlace;
    ImageView imgSave, imgCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_employee);
        addViews();
        loadEditData();
        addEvents();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addViews() {
        edtId = findViewById(R.id.edtId);
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        actBirthplace = findViewById(R.id.actBirthplace);
        imgSave = findViewById(R.id.imgSave);
        imgCancel = findViewById(R.id.imgCancel);

        String[] arrBirthplace = getResources().getStringArray(R.array.array_birthplace);
        adapterBirthPlace = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                arrBirthplace);
        actBirthplace.setAdapter(adapterBirthPlace);
    }

    private void loadEditData() {
        if (getIntent().hasExtra("id")) {
            edtId.setText(getIntent().getStringExtra("id"));
            edtName.setText(getIntent().getStringExtra("name"));
            edtPhone.setText(getIntent().getStringExtra("phone"));
            edtId.setEnabled(false);
        }
    }

    private void addEvents() {
        imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processAddNewEmployee();
            }
        });

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void processAddNewEmployee() {
        String id = edtId.getText().toString();
        String name = edtName.getText().toString();
        String phone = edtPhone.getText().toString();
        String birthplace = actBirthplace.getText().toString();
        Employee emp = new Employee(id, name, phone, birthplace);
        //step 1: get intent
        Intent intent=getIntent();
        //step 2: set data
        intent.putExtra("NEW_EMPLOYEE",emp);
        int departmentIndex = getIntent().getIntExtra("DEPARTMENT_INDEX", 1);
        intent.putExtra("DEPARTMENT_INDEX", departmentIndex);
        //step 3: set result
        setResult(888,intent);
        //step 4: finish
        finish();
    }
}
