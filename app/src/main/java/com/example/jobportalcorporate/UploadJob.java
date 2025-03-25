package com.example.jobportalcorporate;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class UploadJob extends AppCompatActivity {

    EditText u_jobid;
    EditText u_jobdesc;
    EditText u_salary;
    EditText u_skills;
    EditText u_location;
    EditText u_contact;
    EditText u_cname;


Button post;
Spinner expspinner,jobtypespinner,ug,pg;

String[] exp={"0-2 Years","2-4 Years","4-6 Years","More than 6 Years"};
String[] jobtype={"IT","NON-IT"};
String[] ugcourse={"None","BTech","BCA","BCom","BBA"};
String[] pgcourse={"None","MTech","MCA","MCom","MBA"};
TextView jobdate;

    FirebaseAuth mAuth;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_job);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        u_cname= findViewById(R.id.et_cname);
        u_jobid= findViewById(R.id.et_jobid);
        u_location=findViewById(R.id.et_joblocation);
        u_jobdesc= findViewById(R.id.et_jobdesc);
        
        u_skills= findViewById(R.id.et_Skills);
        u_salary= findViewById(R.id.et_Salary);
        u_contact= findViewById(R.id.et_contact);
        expspinner= findViewById(R.id.sp_exp);
        jobtypespinner= findViewById(R.id.sp_jobtype);
        ug= findViewById(R.id.sp_ug);
        pg= findViewById(R.id.sp_pg);

        jobdate= findViewById(R.id.et_date);
       ImageView date= findViewById(R.id.date);
        ArrayAdapter<String> expadapter=new ArrayAdapter<>(com.example.jobportalcorporate.UploadJob.this,android.R.layout.simple_list_item_1,exp);
        expspinner.setAdapter(expadapter);
        ArrayAdapter<String> jobtypeadapter=new ArrayAdapter<>(com.example.jobportalcorporate.UploadJob.this,android.R.layout.simple_list_item_1,jobtype);
        jobtypespinner.setAdapter(jobtypeadapter);

        ArrayAdapter<String> ugadapter=new ArrayAdapter<>(com.example.jobportalcorporate.UploadJob.this,android.R.layout.simple_list_item_1,ugcourse);
        ug.setAdapter(ugadapter);


        ArrayAdapter<String> pgadapter=new ArrayAdapter<>(com.example.jobportalcorporate.UploadJob.this,android.R.layout.simple_list_item_1,pgcourse);
        pg.setAdapter(pgadapter);


        post= findViewById(R.id.btn_uploadjob);

        date.setOnClickListener(v -> {
            Calendar calendar= Calendar.getInstance();
            int year=calendar.get(Calendar.YEAR);
            int month=calendar.get(Calendar.MONTH);
            int day=calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog=new DatePickerDialog(UploadJob.this, (view, year1, month1, dayOfMonth) -> {
                String date1 =dayOfMonth+"/"+(month1 +1)+"/"+ year1;
                jobdate.setText(date1);

            },year,month,day);
            dialog.show();
        });

        post.setOnClickListener(v -> {
            FirebaseUser user=mAuth.getCurrentUser();

            String name=u_cname.getText().toString().trim();
            String jobid=u_jobid.getText().toString().trim();
            String location=u_location.getText().toString().trim();
            String jobdescription=u_jobdesc.getText().toString().trim();
            String experience=expspinner.getSelectedItem().toString().trim();
            String jobtype=jobtypespinner.getSelectedItem().toString().trim();
            String undergrad=ug.getSelectedItem().toString().trim();
            String postgrad=pg.getSelectedItem().toString().trim();
            String jobqualification= undergrad +","+ postgrad;
            String skills=u_skills.getText().toString().trim();
            String sa=u_salary.getText().toString().trim();
            String contact=u_contact.getText().toString().trim();
            String emailid = user.getEmail();
            String salary=sa+" "+"L/PA";
            String date2 =jobdate.getText().toString();
            String uid= user.getUid();



            if (name.isEmpty()){
                u_cname.setError("Name Required");
                u_cname.requestFocus();
                return;
            }

            if (jobid.isEmpty()){
                u_jobid.setError("Job Id Required");
                u_jobid.requestFocus();
                return;
            } if (location.isEmpty()){
                u_location.setError("Location Required");
                u_location.requestFocus();
                return;
            }
            if (jobdescription.isEmpty()){
                u_jobdesc.setError("Job Description Required");
                u_jobdesc.requestFocus();
                return;
            }
            if (skills.isEmpty()){
                u_skills.setError("Job Id Required");
                u_skills.requestFocus();
                return;
            }

            if (contact.isEmpty()){
                u_contact.setError("Job Id Required");
                u_contact.requestFocus();
                return;
            }

            PostJob pj=new PostJob(name,jobid,location,jobdescription,experience,jobqualification,skills,salary,contact,emailid,jobtype, date2,uid);


            try {
                databaseReference.child("Jobs").child(jobid).setValue(pj);
            }catch (Exception e){

                Toast.makeText(UploadJob.this, "Error", Toast.LENGTH_SHORT).show();
            }

            Toast.makeText(UploadJob.this, "Job Posted", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(UploadJob.this,ProfileActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}
