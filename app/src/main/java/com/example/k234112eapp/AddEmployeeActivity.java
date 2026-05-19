package com.example.k234112eapp;

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

public class AddEmployeeActivity extends AppCompatActivity {
    EditText edtId, edtName, edtPhone;
    AutoCompleteTextView actBirthplace;
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
                String id = edtId.getText().toString().trim();
                String name = edtName.getText().toString().trim();
                String phone = edtPhone.getText().toString().trim();
                if (id.isEmpty() || name.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(AddEmployeeActivity.this,
                            R.string.str_login_failed, Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(AddEmployeeActivity.this,
                        R.string.str_save, Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
