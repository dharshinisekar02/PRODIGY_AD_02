package com.example.to_do_list;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {

    // Static task list to persist data between activity instances
    private static ArrayList<Task> taskList = new ArrayList<>();
    private LinearLayout taskListContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task2);

        // Initialize UI components
        taskListContainer = findViewById(R.id.task_list_container);
        Button addTaskButton = findViewById(R.id.tv_add_task);

        // Get data from Intent (if any)
        Intent intent = getIntent();
        String taskTitle = intent.getStringExtra("TASK_TITLE");
        String taskDateStr = intent.getStringExtra("TASK_DATE");
        String taskTime = intent.getStringExtra("TASK_TIME");

        // Add a new task if details are provided
        if (taskTitle != null && taskDateStr != null && taskTime != null) {
            Task newTask = new Task(taskTitle, taskDateStr, taskTime, "");
            taskList.add(newTask);
        }

        // Display all tasks in the list
        refreshTaskList();

        // Handle Add Task button click
        addTaskButton.setOnClickListener(v -> {
            Intent intentNext = new Intent(AddTaskActivity.this, add_task.class);
            startActivity(intentNext);
        });
    }

    // Refresh the UI to display all tasks
    private void refreshTaskList() {
        taskListContainer.removeAllViews(); // Clear existing views
        for (Task task : taskList) {
            addTaskToView(task); // Add each task dynamically
        }
    }

    // Method to add a task to the UI dynamically
    private void addTaskToView(Task task) {
        // Inflate the task card layout
        View taskView = LayoutInflater.from(this).inflate(R.layout.task_card, taskListContainer, false);

        // Set task details
        TextView taskTitleView = taskView.findViewById(R.id.task_title);
        TextView taskDateView = taskView.findViewById(R.id.task_date);
        TextView taskStatusView = taskView.findViewById(R.id.task_status);
        Button deleteButton = taskView.findViewById(R.id.btn_delete_task);

        taskTitleView.setText(task.getTitle());
        taskDateView.setText(task.getDate());

        // Determine task status
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date taskDate = sdf.parse(task.getDate());

            if (taskDate != null) {
                Date currentDate = new Date();
                if (taskDate.after(currentDate)) {
                    taskStatusView.setText("Upcoming");
                    taskStatusView.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                } else if (taskDate.before(currentDate)) {
                    taskStatusView.setText("Expired");
                    taskStatusView.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                } else {
                    taskStatusView.setText("Today");
                    taskStatusView.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Handle delete button click
        deleteButton.setOnClickListener(v -> {
            taskListContainer.removeView(taskView);
            taskList.remove(task);
        });

        // Add the task view to the container
        taskListContainer.addView(taskView);
    }

    // Task class
    public static class Task {
        private String title;
        private String date;
        private String time;
        private String status;

        public Task(String title, String date, String time, String status) {
            this.title = title;
            this.date = date;
            this.time = time;
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
