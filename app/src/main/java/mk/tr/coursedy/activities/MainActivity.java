package mk.tr.coursedy.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mk.tr.coursedy.R;
import mk.tr.coursedy.adapter.CourseAdapter;
import mk.tr.coursedy.models.Course;
import mk.tr.coursedy.models.User;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton add;
    BottomNavigationView bottomNavigationView;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private RecyclerView recyclerView;
    private CourseAdapter courseAdapter;
    private List<Course> courseList;
    private String codeText;
    DatabaseReference studentListRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        //Remove title bar
        if(getSupportActionBar()!=null)
            getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.main_bottom_nav);
        //Course Activity page select
        bottomNavigationView.setSelectedItemId(R.id.nav_courses);
        //Switch to other pages

        add = findViewById(R.id.add_course);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_courses);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        Drawable verticalDivider = ContextCompat.getDrawable(this, R.drawable.vertical_divider);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL);
        assert verticalDivider != null;
        dividerItemDecoration.setDrawable(verticalDivider);
        recyclerView.addItemDecoration(dividerItemDecoration);

        courseList = new ArrayList<>();
        courseAdapter = new CourseAdapter(this,courseList);
        recyclerView.setAdapter(courseAdapter);
        courseAdapter.notifyDataSetChanged();

        switchPage();

        addbtn();

        //courseAdapter.notifyDataSetChanged();
    }



    private void addbtn()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                if(user!=null) {
                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (user.getType().equals("Student")) {
                                openJoinCourseDialog();
                            } else if (user.getType().equals("Faculty")) {
                                openCourseDialog();
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void openCourseDialog() {
        EditText courseName;
        EditText coursePeriod;
        EditText courseCode;
        ImageView close;
        Button add_course;
        FirebaseUser currentUser;
        DatabaseReference courseReference;
        String teacherName;

        Dialog create = new Dialog(MainActivity.this);
        create.setContentView(R.layout.create_dialog);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        courseReference = FirebaseDatabase.getInstance().getReference("Courses");
        courseName = create.findViewById(R.id.dialog_edit_courseName);
        coursePeriod = create.findViewById(R.id.dialog_edit_coursePeriod);
        courseCode = create.findViewById(R.id.dialog_edit_courseCode);
        add_course = create.findViewById(R.id.add_course);
        close = create.findViewById(R.id.close);

        add_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String courseId = courseReference.push().getKey();

                //final String[] tempTeacherName = new String[1];
                DatabaseReference referenceUser = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid());
                referenceUser.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        User user = dataSnapshot.getValue(User.class);

                        assert user != null;
                        String teacherName = user.getNameSurname();
                        Map<String, Object> hashMap = new HashMap<>();

                        hashMap.put("courseId", courseId);
                        hashMap.put("courseName", courseName.getText().toString());
                        hashMap.put("coursePeriod", coursePeriod.getText().toString());
                        hashMap.put("courseCode", courseCode.getText().toString());
                        hashMap.put("courseTeacherId", currentUser.getUid());
                        hashMap.put("courseTeacherName", teacherName);
                        hashMap.put("courseStudentsId", "");
                        assert courseId != null;
                        courseReference.child(courseId).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                create.dismiss();
                            }
                        });


                        //    startActivity(new Intent(getActivity(), ProfileActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create.dismiss();
            }
        });

        create.show();
    }

    private void readCourses() {

        DatabaseReference courseRef = FirebaseDatabase.getInstance().getReference("Courses");
        courseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    final Course course =snapshot.getValue(Course.class);
                    if(course!=null)
                    {
                        final DatabaseReference studentRef = FirebaseDatabase.getInstance()
                                .getReference("StudentListOfTheCourses").child(course.getCourseId());
                        studentRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {


                                if(currentUser.getUid().equals(course.getCourseTeacherId())||
                                        dataSnapshot2.hasChild(currentUser.getUid()))
                                {
                                    courseList.add(course);
                                    courseAdapter.notifyDataSetChanged();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                }
                courseAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void openJoinCourseDialog() {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.join_dialog);
        final EditText courseCode = dialog.findViewById(R.id.dialog_edit_courseCode);
        Button join = dialog.findViewById(R.id.join_course);
        ImageView close = dialog.findViewById(R.id.close);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeText = courseCode.getText().toString();
                readCourseCodeFromDb(codeText);
                dialog.dismiss();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void readCourseCodeFromDb(final String codeInput) {

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Courses");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                int countIfCourseCode=0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    final Course course = snapshot.getValue(Course.class);
                    assert course != null;

                    if(course.getCourseCode().equals(codeInput))
                    {
                        countIfCourseCode++;
                        studentListRef = FirebaseDatabase.getInstance().getReference("StudentListOfTheCourses")
                                .child(course.getCourseId());

                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users")
                                .child(currentUser.getUid());

                        userRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {

                                User user = dataSnapshot2.getValue(User.class);

                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("studentId", currentUser.getUid());
                                assert user != null;
                                hashMap.put("studentName", user.getNameSurname());
                                hashMap.put("studentEmail", user.getEmail());
                                hashMap.put("studentPicture", user.getPictureUrl());
                                hashMap.put("courseId", course.getCourseId());

                                studentListRef.child(currentUser.getUid()).setValue(hashMap);
                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }


                }
                if(countIfCourseCode==0)
                    Toast.makeText(MainActivity.this, "Please enter a valid code.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }



    private void switchPage(){

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.nav_courses:
                        return true;


                    case R.id.nav_assignments:
                        Intent intent = new Intent(getApplicationContext(),AssignmentsActivity.class);
                        //  intent.putExtra("courseId",course.getCourseId());
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_messages:
                        Intent intentMessage = new Intent(getApplicationContext(),MessagesActivity.class);
                        startActivity(intentMessage);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_announcements:
                        Intent intentAnnounce = new Intent(getApplicationContext(),AnnouncementsActivity.class);
                        startActivity(intentAnnounce);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_profile:
                        Intent intentProfile = new Intent(getApplicationContext(),ProfileActivity.class);
                        startActivity(intentProfile);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

}
