package com.example.some;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.some.dal.SQLiteHelper;
import com.example.some.model.Work;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class UpdateDeleteActivity extends AppCompatActivity implements View.OnClickListener {

    public Spinner sp;
    private EditText eTitle, eContent, eDate;
    private RadioButton r1Minh, rLamChung;
    private Button btUpdate, btBack, btRemove;
    private Work work;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        initView();
        btUpdate.setOnClickListener(this);
        btBack.setOnClickListener(this);
        btRemove.setOnClickListener(this);
        eDate.setOnClickListener(this);

        Intent intent = getIntent();
        work = (Work) intent.getSerializableExtra("work");
        eTitle.setText(work.getTitle());
        eContent.setText(work.getContent());
        eDate.setText(work.getDate());
        if (work.isCooperated()) {
            rLamChung.setChecked(true);
        } else {
            r1Minh.setChecked(true);
        }
        int position = 0;
        for (int i = 0; i < sp.getCount(); i++) {
            if (sp.getItemAtPosition(i).toString().equalsIgnoreCase(work.getStatus())){
                position = i;
                break;
            }
        }
        sp.setSelection(position);
    }

    private void initView() {
        sp = findViewById(R.id.spStatus);
        eTitle = findViewById(R.id.tvTitle);
        eContent = findViewById(R.id.tvContent);
        eDate = findViewById(R.id.tvDate);
        r1Minh = findViewById(R.id.radio1Minh);
        rLamChung = findViewById(R.id.radioLamChung);
        btUpdate = findViewById(R.id.btUpdate);
        btRemove = findViewById(R.id.btRemove);
        btBack = findViewById(R.id.btBack);

        sp.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner,
                getResources().getStringArray(R.array.status)));
    }

    @Override
    public void onClick(View v) {
        SQLiteHelper db = new SQLiteHelper(this);

        if (v == eDate) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(UpdateDeleteActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String date = "";
                    String day = "";
                    if (dayOfMonth < 10) {
                        day = "0" + dayOfMonth;
                    } else {
                        day += dayOfMonth;
                    }
                    if (month > 8) {
                        date = year + "-" + (month + 1) + "-" + day;
                    } else {
                        date = year + "-0" + (month + 1) + "-" + day;
                    }
                    eDate.setText(date);
                }
            }, year, month, day);
            dialog.show();
        }

        if (v == btBack) {
            finish();
        }

        if (v == btUpdate) {
            String title = eTitle.getText().toString();
            String content = eContent.getText().toString();
            String status = sp.getSelectedItem().toString();
            String date = eDate.getText().toString();
            boolean isCooperated = false;
            if (rLamChung.isChecked()) {
                isCooperated = true;
            }
            if(!title.isEmpty() && !content.isEmpty() && !status.isEmpty() && !date.isEmpty()) {
                int id = work.getId();
                Work work = new Work(id, title, content, date, status, isCooperated);
                db.updateWork(work);
                finish();
            } else {
                Snackbar.make(btUpdate, "Kiểm tra các trường và nhập lại!", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        }

        if (v == btRemove) {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Thông báo xóa");
            builder.setMessage("Bạn có chắc chắn muốn xóa công việc " + work.getTitle() + " không?");
            builder.setIcon(R.drawable.ic_remove);
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.delete(work.getId());
                    finish();
                }
            });
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

}