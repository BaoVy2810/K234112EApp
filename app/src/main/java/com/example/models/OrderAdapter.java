package com.example.models;

import static com.example.models.OrderStatus.COMPLETED;
import static com.example.models.OrderStatus.NOT_PAYMENT;
import static com.example.models.OrderStatus.ON_LOGISTIC;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.k234112eapp.R;

public class OrderAdapter extends ArrayAdapter<Order> {
    Activity context;
    int resource;
    public OrderAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View custom = inflater.inflate(resource, null);
        Order order = getItem(position);
        TextView txtOrderDate = custom.findViewById(R.id.txtOrderDate);
        TextView txtOrderId = custom.findViewById(R.id.txtOrderId);
        TextView txtDeliveryInfo = custom.findViewById(R.id.txtDeliveryInfo);
        TextView txtTotalMoney = custom.findViewById(R.id.txtTotalMoney);
        txtOrderDate.setText(order.getOrderDate().toString());
        switch (order.getOrderStatus()){
            case COMPLETED:
                txtDeliveryInfo.setText(context.getString(R.string.str_order_status_completed));
                break;
            case NOT_PAYMENT:
                txtDeliveryInfo.setText(context.getString(R.string.str_order_status_not_payment));
                break;
            case ON_LOGISTIC:
                txtDeliveryInfo.setText(context.getString(R.string.str_order_status_on_logistic));
                break;
            case CUSTOMER_COMPLAINT:
                txtDeliveryInfo.setText(context.getString(R.string.str_order_status_customer_complaint));
                break;
        }
        txtOrderId.setText(order.getOrderId());
        txtTotalMoney.setText(DataWareHouse.sumOfMoney(order)+" VND");
        return custom;
    }
}
