package com.example.some;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class ReportInformationActivity extends AppCompatActivity {
    private Button btnOk;
    private EditText birthDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_information);
        birthDay=findViewById(R.id.birthday);
        birthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c= Calendar.getInstance();
                int y=c.get(Calendar.YEAR);
                int m=c.get(Calendar.MONTH);
                int d=c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog p=new DatePickerDialog(ReportInformationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int yyyy, int mm, int dd) {
                        String date1="";
                        if(dd>9){
                            date1=dd+"/";
                        }else date1="0"+dd+"/";
                        if(mm>8){
                            date1+=(mm+1)+"/"+yyyy;
                        }else date1+="0"+(mm+1)+"/"+yyyy;
                        birthDay.setText(date1);
                    }
                },y,m,d);
                p.show();
            }
        });
        btnOk=findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ReportInformationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}