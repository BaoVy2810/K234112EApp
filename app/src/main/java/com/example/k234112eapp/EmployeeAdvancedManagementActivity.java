package com.example.k234112eapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.adapters.EmployeeAdapter;
import com.example.models.Department;
import com.example.models.Employee;

import java.util.ArrayList;

public class EmployeeAdvancedManagementActivity extends AppCompatActivity {
    ListView lvEmployee;
    EmployeeAdapter adapterEmployee;
    Spinner spDepartment;
    ArrayList<Department> listOfDepartment;
    ArrayAdapter<Department> adapterDepartment;
    Employee selectedEmployee = null;
    ImageView imgAddEmployee, imgEditEmployee, imgDeleteEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employee_advanced_management);
        addViews();
        sampleData();
        refreshEmployeeList(0);
        addEvents();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addEvents() {
        lvEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedEmployee = adapterEmployee.getItem(i);
            }
        });

        spDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                refreshEmployeeList(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        imgAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedIndex = spDepartment.getSelectedItemPosition();
                if (selectedIndex == 0) {
                    Toast.makeText(EmployeeAdvancedManagementActivity.this,
                            "Vui lòng chọn phòng ban trước khi thêm nhân viên", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(EmployeeAdvancedManagementActivity.this, AddEmployeeActivity.class);
                intent.putExtra("DEPARTMENT_INDEX", selectedIndex);
                startActivityForResult(intent, 9999);
            }
        });

        imgEditEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedEmployee == null) {
                    Toast.makeText(EmployeeAdvancedManagementActivity.this,
                            R.string.str_msg_select_employee, Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(EmployeeAdvancedManagementActivity.this, AddEmployeeActivity.class);
                intent.putExtra("id", selectedEmployee.getId());
                intent.putExtra("name", selectedEmployee.getName());
                intent.putExtra("phone", selectedEmployee.getPhone());
                startActivity(intent);
            }
        });

        imgDeleteEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedEmployee == null) {
                    Toast.makeText(EmployeeAdvancedManagementActivity.this,
                            R.string.str_msg_select_employee, Toast.LENGTH_SHORT).show();
                    return;
                }
                new AlertDialog.Builder(EmployeeAdvancedManagementActivity.this)
                        .setTitle(R.string.str_confirm_delete_title)
                        .setMessage(R.string.str_confirm_delete_msg)
                        .setPositiveButton(R.string.str_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                removeEmployeeFromDepartments(selectedEmployee);
                                refreshEmployeeList(spDepartment.getSelectedItemPosition());
                                selectedEmployee = null;
                                Toast.makeText(EmployeeAdvancedManagementActivity.this,
                                        R.string.str_delete_success, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(R.string.str_no, null)
                        .show();
            }
        });
    }

    private void removeEmployeeFromDepartments(Employee employee) {
        for (Department department : listOfDepartment) {
            ArrayList<Employee> employees = department.getListOfEmployee();
            for (int i = employees.size() - 1; i >= 0; i--) {
                if (employees.get(i).getId().equals(employee.getId())) {
                    employees.remove(i);
                }
            }
        }
    }

    private void sampleData() {
        Department d0 = new Department("-1", "-------ALL-------");
        Department d1 = new Department("1", "Phòng hành chính");
        Department d2 = new Department("2", "Phòng nhân sự");
        Department d3 = new Department("3", "Phòng tài chính");
        Department d4 = new Department("4", "Phòng kỹ thuật");
        listOfDepartment.add(d0);
        listOfDepartment.add(d1);
        listOfDepartment.add(d2);
        listOfDepartment.add(d3);
        listOfDepartment.add(d4);
        adapterDepartment.notifyDataSetChanged();

        d1.addEmployee(new Employee("1", "Kim Namjoon", "0900000001"));
        d1.addEmployee(new Employee("2", "Kim Seokjin", "0900000002"));
        d1.addEmployee(new Employee("3", "Min Yoongi", "0900000003"));
        d1.addEmployee(new Employee("4", "Jung Hoseok", "0900000004"));
        d1.addEmployee(new Employee("5", "Park Jimin", "0900000005"));
        d1.addEmployee(new Employee("6", "Kim Taehyung", "0900000006"));
        d1.addEmployee(new Employee("7", "Jeon Jungkook", "0900000007"));

        d2.addEmployee(new Employee("8", "Kim Jisoo", "0900000008"));
        d2.addEmployee(new Employee("9", "Kim Jennie", "0900000009"));
        d2.addEmployee(new Employee("10", "Park Chaeyoung", "0900000010"));
        d2.addEmployee(new Employee("11", "Lalisa Manobal", "0900000011"));

        d3.addEmployee(new Employee("12", "Im Nayeon", "0900000012"));
        d3.addEmployee(new Employee("13", "Yoo Jeongyeon", "0900000013"));
        d3.addEmployee(new Employee("14", "Hirai Momo", "0900000014"));
        d3.addEmployee(new Employee("15", "Minatozaki Sana", "0900000015"));
        d3.addEmployee(new Employee("16", "Park Jihyo", "0900000016"));
        d3.addEmployee(new Employee("17", "Myoui Mina", "0900000017"));
        d3.addEmployee(new Employee("18", "Kim Dahyun", "0900000018"));
        d3.addEmployee(new Employee("19", "Son Chaeyoung", "0900000019"));
        d3.addEmployee(new Employee("20", "Chou Tzuyu", "0900000020"));

        ArrayList<Employee> listOfEmp4 = new ArrayList<>();
        listOfEmp4.add(new Employee("26", "Kim Minseok", "0900000026"));
        listOfEmp4.add(new Employee("27", "Kim Junmyeon", "0900000027"));
        listOfEmp4.add(new Employee("28", "Zhang Yixing", "0900000028"));
        listOfEmp4.add(new Employee("29", "Byun Baekhyun", "0900000029"));
        listOfEmp4.add(new Employee("30", "Kim Jongdae", "0900000030"));
        listOfEmp4.add(new Employee("31", "Park Chanyeol", "0900000031"));
        listOfEmp4.add(new Employee("32", "Do Kyungsoo", "0900000032"));
        listOfEmp4.add(new Employee("33", "Kim Jongin", "0900000033"));
        listOfEmp4.add(new Employee("34", "Oh Sehun", "0900000034"));
        listOfEmp4.add(new Employee("35", "Bang Chan", "0900000035"));
        listOfEmp4.add(new Employee("36", "Lee Know", "0900000036"));
        listOfEmp4.add(new Employee("37", "Changbin", "0900000037"));
        listOfEmp4.add(new Employee("38", "Hyunjin", "0900000038"));
        listOfEmp4.add(new Employee("39", "Han Jisung", "0900000039"));
        listOfEmp4.add(new Employee("40", "Felix", "0900000040"));
        listOfEmp4.add(new Employee("41", "Seungmin", "0900000041"));
        listOfEmp4.add(new Employee("42", "I.N", "0900000042"));
        listOfEmp4.add(new Employee("43", "S.Coups", "0900000043"));
        listOfEmp4.add(new Employee("44", "Jeonghan", "0900000044"));
        listOfEmp4.add(new Employee("45", "Joshua", "0900000045"));
        listOfEmp4.add(new Employee("46", "Jun", "0900000046"));
        listOfEmp4.add(new Employee("47", "Hoshi", "0900000047"));
        listOfEmp4.add(new Employee("48", "Wonwoo", "0900000048"));
        listOfEmp4.add(new Employee("49", "Woozi", "0900000049"));
        listOfEmp4.add(new Employee("50", "DK", "0900000050"));
        listOfEmp4.add(new Employee("51", "Mingyu", "0900000051"));
        listOfEmp4.add(new Employee("52", "The8", "0900000052"));
        listOfEmp4.add(new Employee("53", "Seungkwan", "0900000053"));
        listOfEmp4.add(new Employee("54", "Vernon", "0900000054"));
        listOfEmp4.add(new Employee("55", "Dino", "0900000055"));
        listOfEmp4.add(new Employee("56", "Yeji", "0900000056"));
        listOfEmp4.add(new Employee("57", "Lia", "0900000057"));
        listOfEmp4.add(new Employee("58", "Ryujin", "0900000058"));
        listOfEmp4.add(new Employee("59", "Chaeryeong", "0900000059"));
        listOfEmp4.add(new Employee("60", "Yuna", "0900000060"));
        listOfEmp4.add(new Employee("61", "Karina", "0900000061"));
        listOfEmp4.add(new Employee("62", "Giselle", "0900000062"));
        listOfEmp4.add(new Employee("63", "Winter", "0900000063"));
        listOfEmp4.add(new Employee("64", "Ningning", "0900000064"));
        listOfEmp4.add(new Employee("65", "Minji", "0900000065"));
        listOfEmp4.add(new Employee("66", "Hanni", "0900000066"));
        listOfEmp4.add(new Employee("67", "Danielle", "0900000067"));
        listOfEmp4.add(new Employee("68", "Haerin", "0900000068"));
        listOfEmp4.add(new Employee("69", "Hyein", "0900000069"));
        d4.addListEmployee(listOfEmp4);
    }

    private void refreshEmployeeList(int departmentIndex) {
        adapterEmployee.clear();
        if (departmentIndex == 0) {
            for (int j = 1; j < listOfDepartment.size(); j++) {
                adapterEmployee.addAll(listOfDepartment.get(j).getListOfEmployee());
            }
        } else {
            adapterEmployee.addAll(listOfDepartment.get(departmentIndex).getListOfEmployee());
        }
        adapterEmployee.notifyDataSetChanged();
        selectedEmployee = null;
    }

    private void addViews() {
        lvEmployee = findViewById(R.id.lvEmployee);
        adapterEmployee = new EmployeeAdapter(this, R.layout.item_custom_employee);
        lvEmployee.setAdapter(adapterEmployee);

        spDepartment = findViewById(R.id.spDepartment);
        listOfDepartment = new ArrayList<>();
        adapterDepartment = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                listOfDepartment);
        adapterDepartment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDepartment.setAdapter(adapterDepartment);

        imgAddEmployee = findViewById(R.id.imgAddEmployee);
        imgEditEmployee = findViewById(R.id.imgEditEmployee);
        imgDeleteEmployee = findViewById(R.id.imgDeleteEmployee);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9999 && resultCode == 888)
        {
            Employee emp=(Employee)data.getSerializableExtra("NEW_EMPLOYEE");
            int departmentIndex = data.getIntExtra("DEPARTMENT_INDEX", 1); // mặc định index 1 nếu lỗi
            Department targetDepartment = listOfDepartment.get(departmentIndex);
            targetDepartment.addEmployee(emp);
            refreshEmployeeList(spDepartment.getSelectedItemPosition());

        }
    }
}
