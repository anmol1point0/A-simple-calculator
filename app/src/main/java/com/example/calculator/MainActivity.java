package com.example.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String saveop = "=";
    private static final String saveop1 = "operand1";
    //   int flag=0;
    private EditText result;
    private EditText newNumber;
    private TextView displayOperation;
    private Double operand1 = null;
    private String pendingoperation = "=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = (EditText) findViewById(R.id.result);
        newNumber = (EditText) findViewById(R.id.newNumber);
        displayOperation = (TextView) findViewById(R.id.operation);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                newNumber.append(b.getText().toString());
            }
        };
        View.OnClickListener listener1 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newNumber.getText().toString().isEmpty()) {
                    newNumber.append("-");
                } else {
                    try {
                        Double doubleValue = Double.valueOf(newNumber.getText().toString());
                        newNumber.setText("");
                        doubleValue *= -1;
                        newNumber.append(doubleValue.toString());
                    } catch (NumberFormatException e) {
                        newNumber.setText("");
                    }

                }
            }
        };
        View.OnClickListener listener2 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!newNumber.getText().toString().isEmpty()) {
                    String s = newNumber.getText().toString();
                    s = s.substring(0, s.length() - 1);
                    newNumber.setText("");
                    newNumber.append(s);
                } else if (pendingoperation == "=") {
                    newNumber.setText("");
                    operand1 = null;
                    result.setText("");
                } else {
                    pendingoperation = "=";
                    displayOperation.setText(pendingoperation);
                }
            }
        };
        findViewById(R.id.button0).setOnClickListener(listener);
        findViewById(R.id.button1).setOnClickListener(listener);
        findViewById(R.id.button2).setOnClickListener(listener);
        findViewById(R.id.button3).setOnClickListener(listener);
        findViewById(R.id.button4).setOnClickListener(listener);
        findViewById(R.id.button5).setOnClickListener(listener);
        findViewById(R.id.button6).setOnClickListener(listener);
        findViewById(R.id.button7).setOnClickListener(listener);
        findViewById(R.id.button8).setOnClickListener(listener);
        findViewById(R.id.button9).setOnClickListener(listener);
        findViewById(R.id.buttonDot).setOnClickListener(listener);
        findViewById(R.id.button10).setOnClickListener(listener1);
        findViewById(R.id.clear).setOnClickListener(listener2);
        View.OnClickListener oplistener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b1 = (Button) view;
                String op = b1.getText().toString();
                String s = newNumber.getText().toString();
                try {
                    Double doublevalue = Double.valueOf(s);
                    solve(doublevalue, op);
                } catch (NumberFormatException e) {
                    newNumber.setText("");
                }
                pendingoperation = op;
                displayOperation.setText(pendingoperation);
            }
        };
        findViewById(R.id.buttonPlus).setOnClickListener(oplistener);
        findViewById(R.id.buttonDivide).setOnClickListener(oplistener);
        findViewById(R.id.buttonMinus).setOnClickListener(oplistener);
        findViewById(R.id.buttonequals).setOnClickListener(oplistener);
        findViewById(R.id.buttonMultiply).setOnClickListener(oplistener);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(saveop, pendingoperation);
        if (operand1 != null) {
            outState.putDouble(saveop1, operand1);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingoperation = savedInstanceState.getString(saveop);
        operand1 = savedInstanceState.getDouble(saveop1);
        displayOperation.setText(pendingoperation);
    }

    private void solve(Double s, String op) {
        if (operand1 == null || operand1 == 0) {
            operand1 = s;
        } else {
            if (pendingoperation.equals("=")) {
                pendingoperation = op;
            }
            switch (pendingoperation) {
                case "=":
                    operand1 = s;
                    break;
                case "+":
                    operand1 += s;
                    break;
                case "-":
                    operand1 -= s;
                    break;
                case "/":
                    if (s == 0)
                        operand1 = 0.0;
                    else
                        operand1 /= s;
                    break;
                case "*":
                    operand1 *= s;
                    break;
            }
        }
        result.setText(operand1.toString());
        newNumber.setText("");
    }


}
