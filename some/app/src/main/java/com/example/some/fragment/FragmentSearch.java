package com.example.some.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.some.R;
import com.example.some.adapter.RecyclerViewAdapter;
import com.example.some.dal.SQLiteHelper;
import com.example.some.model.Work;

import java.util.Calendar;
import java.util.List;

public class FragmentSearch extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private SearchView searchView;
    private Button btSearch;
    private EditText eFrom,eTo;
    private Spinner spinner;
    private SQLiteHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        adapter=new RecyclerViewAdapter();
        db=new SQLiteHelper(getContext());
        List<Work> list=db.getAll();
        adapter.setList(list);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Work> list1=db.searchByTitle(s);
                adapter.setList(list1);
                return true;
            }
        });
        eFrom.setOnClickListener(this);
        eTo.setOnClickListener(this);
        btSearch.setOnClickListener(this);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String cate=spinner.getItemAtPosition(position).toString();
                List<Work> list;
                if(!cate.equalsIgnoreCase("all")){
                    list=db.searchByCategory(cate);
                }
                else list=db.getAll();

                adapter.setList(list);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //adapter.setItemListener();
    }

    private void initView(View view) {
        recyclerView=view.findViewById(R.id.recycleView);
        searchView=view.findViewById(R.id.searchView);
        btSearch=view.findViewById(R.id.button);
        eFrom=view.findViewById(R.id.eFrom);
        eTo=view.findViewById(R.id.eTo);
        spinner=view.findViewById(R.id.spinner);
        String[] arr=getResources().getStringArray(R.array.status);
        String[] arr1=new String[arr.length+1];
        arr1[0]="All";
        for(int i=0;i<arr.length;i++){
            arr1[i+1]=arr[i];
        }
        spinner.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.item_spinner,arr1));

    }

    @Override
    public void onClick(View view) {
        if(view==eFrom){
            final Calendar c= Calendar.getInstance();
            int y=c.get(Calendar.YEAR);
            int m=c.get(Calendar.MONTH);
            int d=c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog p=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int yyyy, int mm, int dd) {
                    String date="";
                    if(dd>9){
                        date=dd+"/";
                    }else date="0"+dd+"/";
                    if(mm>8){
                        date+=(mm+1)+"/"+yyyy;
                    }else date+="0"+(mm+1)+"/"+yyyy;
                    eFrom.setText(date);
                }
            },y,m,d);
            p.show();
        }
        if(view==eTo){
            final Calendar c= Calendar.getInstance();
            int y=c.get(Calendar.YEAR);
            int m=c.get(Calendar.MONTH);
            int d=c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog p=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int yyyy, int mm, int dd) {
                    String date="";
                    if(dd>9){
                        date=dd+"/";
                    }else date="0"+dd+"/";
                    if(mm>8){
                        date+=(mm+1)+"/"+yyyy;
                    }else date+="0"+(mm+1)+"/"+yyyy;
                    eTo.setText(date);
                }
            },y,m,d);
            p.show();
        }
        if(view==btSearch){
            String from=eFrom.getText().toString();
            String to=eTo.getText().toString();
            if(!from.isEmpty()&&!to.isEmpty()){
                List<Work> list=db.searchByDateFromTo(from,to);
                adapter.setList(list);
            }

        }
    }
}
