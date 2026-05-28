package com.example.k234112eapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.models.DataWareHouse;
import com.example.models.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class OrderManagementActivity extends AppCompatActivity {
    TextView txtFromDate;
    TextView txtToDate;
    ImageView imgFromDate;
    ImageView imgToDate;
    ImageView imgClearFilter;
    ImageView imgFilter;
    ListView lvOrder;
    ArrayList<Order> orders;
    ArrayAdapter<Order> orderAdapter;
    SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
    Calendar calFromDate= Calendar.getInstance();
    Calendar calToDate= Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dateFromListener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(android.widget.DatePicker datePicker, int i, int i1, int i2) {
            calFromDate.set(Calendar.YEAR,i);
            calFromDate.set(Calendar.MONTH,i1);
            calFromDate.set(Calendar.DAY_OF_MONTH,i2);
            txtFromDate.setText(sdf.format(calFromDate.getTime()));
        }
    };

    DatePickerDialog.OnDateSetListener dateToListener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(android.widget.DatePicker datePicker, int i, int i1, int i2) {
            calToDate.set(Calendar.YEAR,i);
            calToDate.set(Calendar.MONTH,i1);
            calToDate.set(Calendar.DAY_OF_MONTH,i2);
            txtToDate.setText(sdf.format(calToDate.getTime()));
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_management);
        addViews();
        addEvents();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addEvents() {
        imgFromDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                selectFromDate();
            }
        });
        imgToDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                selectToDate();

            }
        });
        imgClearFilter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                orderAdapter.clear();
                orderAdapter.addAll(orders);
                orderAdapter.notifyDataSetChanged();
            }
        });
        imgFilter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Date fromDate=calFromDate.getTime();
                Date toDate=calToDate.getTime();
                ArrayList<Order> filtered=new ArrayList<>();
                orders=DataWareHouse.filterOrdersByDate(fromDate,toDate);
                orderAdapter.clear();
                orderAdapter.addAll(orders);
                orderAdapter.notifyDataSetChanged();
            }
        });
    }

    private void selectToDate() {
        DatePickerDialog picker=new DatePickerDialog(this,
                dateToListener,
                calToDate.get(Calendar.YEAR),
                calToDate.get(Calendar.MONTH),
                calToDate.get(Calendar.DAY_OF_MONTH)
        );
        picker.show();
    }

    private void selectFromDate() {
        DatePickerDialog picker=new DatePickerDialog(this,
                dateFromListener,
                calFromDate.get(Calendar.YEAR),
                calFromDate.get(Calendar.MONTH),
                calFromDate.get(Calendar.DAY_OF_MONTH)
        );
        picker.show();
    }

    private void addViews() {
        txtFromDate = findViewById(R.id.txtFromDate);
        txtToDate = findViewById(R.id.txtToDate);
        imgFromDate = findViewById(R.id.imgFromDate);
        imgToDate = findViewById(R.id.imgToDate);
        imgClearFilter = findViewById(R.id.imgClearFilter);
        imgFilter = findViewById(R.id.imgFilter);

        lvOrder = findViewById(R.id.lvOrder);
        orders = DataWareHouse.getOrders();
        orderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, orders);
        lvOrder.setAdapter(orderAdapter);

    }

}