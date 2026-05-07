package com.example.k234112eapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CalculatorActivity extends AppCompatActivity {
    private static final String PREF_CALCULATOR = "calculator_pref";
    private static final String KEY_FORMULA = "key_formula";

    EditText edtFormula;
    Button btnDel,btn_Calculate;
    TextView txtMC, txtMR, txtMPlus, txtMMinus, txtMS, txtM;
    View.OnClickListener m_onclick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator);
        addViews();
        addEvents();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences preferences = getSharedPreferences(PREF_CALCULATOR, MODE_PRIVATE);
        preferences.edit()
                .putString(KEY_FORMULA, edtFormula.getText().toString())
                .apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences(PREF_CALCULATOR, MODE_PRIVATE);
        String lastFormula = preferences.getString(KEY_FORMULA, "");
        edtFormula.setText(lastFormula);
    }

    private void addEvents() {
        btnDel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //get current data:
                String current_data=edtFormula.getText().toString();
                //remove last character
                String new_value="";
                if (current_data.length()>1)
                {
                    new_value=current_data.substring(0,current_data.length()-1);
                }
                //set new value:
                edtFormula.setText(new_value);
            }
        });
        btn_Calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //step 1: get data (formular)
                String formular=edtFormula.getText().toString();
                //step 2: calculate for +, -, *, :
                String result=calculateExpression(formular);
                //step 3:
                edtFormula.setText(result);
            }
        });
        m_onclick=new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (view.equals(txtM))
                {
                    //khách hàng nhấn txtM
                }
                else if (view.equals(txtMMinus))
                {
                    //khách hàng nhấn txtMinus
                }//không dùng dấu == để so sánh vì nó không hiểu so sánh ô nhớ khi dùng ==
            }
        };
        //m_onclick là biến có khả năng sinh sự kiện (variable as listener)
        //thuờg dùng để sharing sự kiến (từ 2 view trở lên)
        txtMC.setOnClickListener(m_onclick);
        txtMR.setOnClickListener(m_onclick);
        txtMPlus.setOnClickListener(m_onclick);
        txtMMinus.setOnClickListener(m_onclick);
        txtMS.setOnClickListener(m_onclick);
        txtM.setOnClickListener(m_onclick);
    }

    private void addViews() {
        edtFormula=findViewById(R.id.edtFormula);
        btnDel=findViewById(R.id.btnDel);
        btn_Calculate=findViewById(R.id.btn_Calculate);

        txtMC=findViewById(R.id.txtMC);
        txtMR=findViewById(R.id.txtMR);
        txtMPlus=findViewById(R.id.txtMPlus);
        txtMMinus=findViewById(R.id.txtMMinus);
        txtMS=findViewById(R.id.txtMS);
        txtM=findViewById(R.id.txtM);

    }

    public void processInputData(View view) {
        Button btn_clicked= (Button) view;
        //old value:
        String old_value=edtFormula.getText().toString();
        //new value:
        String input_value=btn_clicked.getText().toString();
        //new value (lasted value):
        String new_value=old_value+input_value;
        //show value for customer:
        edtFormula.setText(new_value);
    }

    public void processSpecialInput(View view) {
        int viewId = view.getId();
        String formula = edtFormula.getText().toString().trim();

        if (viewId == R.id.btn_c) {
            edtFormula.setText("");
            return;
        }

        if (viewId == R.id.btn_ce) {
            edtFormula.setText(clearCurrentEntry(formula));
            return;
        }

        if (viewId == R.id.btn_digit) {
            if (canAppendDecimal(formula)) {
                edtFormula.setText(formula.isEmpty() ? "0." : formula + ".");
            }
            return;
        }

        if (formula.isEmpty()) {
            return;
        }

        if (viewId == R.id.btn_divide) {
            edtFormula.setText(applyPercent(formula));
            return;
        }

        Double value = parseNumericValue(formula);
        if (value == null) return;

        if (viewId == R.id.btn1_x) {
            if (value == 0) {
                edtFormula.setText("Cannot divide by 0");
            } else {
                edtFormula.setText(formatNumber(1 / value));
            }
        } else if (viewId == R.id.btn_sqr_) {
            edtFormula.setText(formatNumber(value * value));
        } else if (viewId == R.id.btn_sqrt) {
            if (value < 0) {
                edtFormula.setText("Invalid input");
            } else {
                edtFormula.setText(formatNumber(Math.sqrt(value)));
            }
        }
    }

    private String clearCurrentEntry(String formula) {
        if (formula == null || formula.isEmpty()) return "";
        for (int i = formula.length() - 1; i >= 1; i--) {
            char c = formula.charAt(i);
            if (c == '+' || c == '-' || c == '*' || c == ':') {
                return formula.substring(0, i + 1);
            }
        }
        return "";
    }

    private boolean canAppendDecimal(String formula) {
        if (formula == null || formula.isEmpty()) return true;
        for (int i = formula.length() - 1; i >= 0; i--) {
            char c = formula.charAt(i);
            if (c == '.') return false;
            if (c == '+' || c == '-' || c == '*' || c == ':') return true;
        }
        return true;
    }

    private String applyPercent(String formula) {
        for (int i = formula.length() - 1; i >= 1; i--) {
            char c = formula.charAt(i);
            if (c == '+' || c == '-' || c == '*' || c == ':') {
                String left = formula.substring(0, i + 1);
                String right = formula.substring(i + 1).trim();
                if (right.isEmpty()) return formula;
                try {
                    double value = Double.parseDouble(right);
                    return left + formatNumber(value / 100.0);
                } catch (NumberFormatException e) {
                    return formula;
                }
            }
        }
        try {
            double value = Double.parseDouble(formula);
            return formatNumber(value / 100.0);
        } catch (NumberFormatException e) {
            return formula;
        }
    }

    private Double parseNumericValue(String formula) {
        try {
            return Double.parseDouble(formula);
        } catch (NumberFormatException e) {
            try {
                return Double.parseDouble(calculateExpression(formula));
            } catch (NumberFormatException ignored) {
                return null;
            }
        }
    }

    private String formatNumber(double value) {
        if (value == (long) value) return String.valueOf((long) value);
        return String.valueOf(value);
    }

    private String calculateExpression(String expression) {
        if (expression == null) return "";
        String exp = expression.trim();
        if (exp.isEmpty()) return "";

        int operatorIndex = -1;
        char operator = ' ';

        for (int i = 1; i < exp.length(); i++) {
            char c = exp.charAt(i);
            if (c == '+' || c == '-' || c == '*' || c == ':') {
                operatorIndex = i;
                operator = c;
                break;
            }
        }

        if (operatorIndex == -1) return exp;

        String leftPart = exp.substring(0, operatorIndex).trim();
        String rightPart = exp.substring(operatorIndex + 1).trim();

        if (leftPart.isEmpty() || rightPart.isEmpty()) return exp;

        try {
            double left = Double.parseDouble(leftPart);
            double right = Double.parseDouble(rightPart);
            double value;

            switch (operator) {
                case '+':
                    value = left + right;
                    break;
                case '-':
                    value = left - right;
                    break;
                case '*':
                    value = left * right;
                    break;
                case ':':
                    if (right == 0) return "Cannot divide by 0";
                    value = left / right;
                    break;
                default:
                    return exp;
            }

            if (value == (long) value) {
                return String.valueOf((long) value);
            }
            return String.valueOf(value);
        } catch (NumberFormatException e) {
            return exp;
        }
    }
}
/*
khi nào tương tác với khách hàng => onResume (phải chờ 1 chút để khởi động) => phục hồi = onResume
Killable (quản lí tụi này khi ở trạng thái chờ => có thể mất hết)
khi mún lưu tự động dữ liệu hành vi của khách hàng ở sự kiện nào? => lưu trong hàm ONPAUSE
login => onpause => onstop (che toàn bộ)
log in => onpause (đóng lại)
exit => onpause => onstop => ondestroy (tắt)
 */