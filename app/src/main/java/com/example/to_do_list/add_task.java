package com.example.to_do_list;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.Locale;

public class add_task extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // Find views for date, time, and button
        EditText etTaskTitle = findViewById(R.id.et_task_title);  // Title input field
        EditText etTaskDate = findViewById(R.id.et_task_date);
        EditText etTaskTime = findViewById(R.id.et_task_time);
        Button addTaskButton = findViewById(R.id.btn_add_task);

        // Date Picker
        etTaskDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, monthOfYear, dayOfMonth) -> {
                String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year1);
                etTaskDate.setText(selectedDate);
            }, year, month, day);
            datePickerDialog.show();
        });

        // Time Picker
        etTaskTime.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute1) -> {
                String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute1);
                etTaskTime.setText(selectedTime);
            }, hour, minute, true);
            timePickerDialog.show();
        });

        // Button click listener
        addTaskButton.setOnClickListener(v -> {
            // Get data from EditText fields
            String taskTitle = etTaskTitle.getText().toString();  // Getting task title
            String taskDate = etTaskDate.getText().toString();
            String taskTime = etTaskTime.getText().toString();

            // Create an Intent to pass data to AddTaskActivity
            Intent intent = new Intent(add_task.this, AddTaskActivity.class);
            intent.putExtra("TASK_TITLE", taskTitle);  // Passing task title
            intent.putExtra("TASK_DATE", taskDate);  // Passing task date
            intent.putExtra("TASK_TIME", taskTime);  // Passing task time

            startActivity(intent);  // Start the next activity
        });
    }
}
