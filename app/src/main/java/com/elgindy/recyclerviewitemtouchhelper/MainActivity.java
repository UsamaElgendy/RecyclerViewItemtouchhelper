package com.elgindy.recyclerviewitemtouchhelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MyRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private Button confirmDesiresBtn;
    private List<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createArray();

        recyclerView = findViewById(R.id.desiresRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MyRecyclerViewAdapter();
        adapter.setList(arrayList);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        confirmDesiresBtn = findViewById(R.id.confirmDesiresOfDepartment);
        confirmDesiresBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putDesiresInDatabase();
            }
        });


    }

    private void createArray() {
        // here you can fitch data from any where like firebase or local database (SQLite)
        // in this video we will make a small array list
        arrayList = new ArrayList<>();
        arrayList.add("one");
        arrayList.add("two");
        arrayList.add("three");
        arrayList.add("four");
        arrayList.add("five");
        arrayList.add("sex");
        arrayList.add("seven");
        arrayList.add("eight");

    }


    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            Collections.swap(arrayList, fromPosition, toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        }
    };

    private void putDesiresInDatabase() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("student_desires");

        HashMap<String, Object> studentMap = new HashMap<>();

        studentMap.put("desires", arrayList);

        ref.child("desires").updateChildren(studentMap);

        Toast.makeText(this, "Your Desires update successfully.", Toast.LENGTH_SHORT).show();

    }


}
