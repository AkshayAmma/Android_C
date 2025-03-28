package com.example.jobportalcorporate;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PostedJob extends AppCompatActivity {

    RecyclerView postedjobrcv;
    PostedjobAdapter postedjobadapter=new PostedjobAdapter();
    ArrayList<com.example.jobportalcorporate.JobList> postedjoblist = new ArrayList<>();
    DatabaseReference myRef;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posted_job);



        mAuth= FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        myRef= FirebaseDatabase.getInstance().getReference("Jobs");
        String uid=user.getUid();
        postedjobrcv= findViewById(R.id.postedjob_rcv);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(com.example.jobportalcorporate.PostedJob.this);
        postedjobrcv.setLayoutManager(manager);

        myRef.orderByChild("uid").equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childDatasnapshot : dataSnapshot.getChildren()) {
                    String contact = childDatasnapshot.child("contact").getValue(String.class);
                    String date=childDatasnapshot.child("date").getValue(String.class);
                    String emailid=childDatasnapshot.child("emailid").getValue(String.class);
                    String experience=childDatasnapshot.child("experience").getValue(String.class);
                    String jobdescription=childDatasnapshot.child("jobdescription").getValue(String.class);
                    String jobid=childDatasnapshot.child("jobid").getValue(String.class);
                    String jobqualification=childDatasnapshot.child("jobqualification").getValue(String.class);
                    String jobtype=childDatasnapshot.child("jobtype").getValue(String.class);
                    String location=childDatasnapshot.child("location").getValue(String.class);
                    String name=childDatasnapshot.child("name").getValue(String.class);
                    String salary=childDatasnapshot.child("salary").getValue(String.class);
                    String skills=childDatasnapshot.child("skills").getValue(String.class);

                    com.example.jobportalcorporate.JobList data = new com.example.jobportalcorporate.JobList();

                    data.setCompany_name(name);
                    data.setContact(contact);
                    data.setEmailid(emailid);
                    data.setExperience(experience);
                    data.setJobdescription(jobdescription);
                    data.setJobid(jobid);
                    data.setJobqualification(jobqualification);
                    data.setSalary(salary);
                    data.setSkills(skills);
                    data.setJobtype(jobtype);
                    data.setLocation(location);
                    data.setDate(date);

                    postedjoblist.add(data);


                    postedjobrcv.setAdapter(postedjobadapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public static class PostedjobViewHolder extends RecyclerView.ViewHolder{

        Button deljob;
        TextView company_name
                ,jobdescription,jobid,experience,jobqualification,salary,skills,jobtype,location,date;
        public PostedjobViewHolder(View itemView) {
            super(itemView);

            company_name=itemView.findViewById(R.id.tv_name);

            experience=itemView.findViewById(R.id.tv_experience);
            jobdescription=itemView.findViewById(R.id.tv_jobdescription);
            jobid=itemView.findViewById(R.id.tv_jobid);
            jobqualification=itemView.findViewById(R.id.tv_qualification);
            salary=itemView.findViewById(R.id.tv_salary);
            skills=itemView.findViewById(R.id.tv_skills);
            jobtype=itemView.findViewById(R.id.tv_jobtype);
            location=itemView.findViewById(R.id.tv_location);
            date=itemView.findViewById(R.id.tv_date);
            deljob=itemView.findViewById(R.id.deljob);

        }
    }
    public class PostedjobAdapter extends RecyclerView.Adapter<PostedjobViewHolder>{

        @NonNull
        @Override
        public PostedjobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater=getLayoutInflater();
            View v=inflater.inflate(R.layout.joblist,parent,false);
            return new PostedjobViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull final PostedjobViewHolder holder, int position) {
            final com.example.jobportalcorporate.JobList list=postedjoblist.get(position);

            holder.company_name.setText(list.getCompany_name());

            holder.experience.setText(list.getExperience());
            holder.jobdescription.setText(list.getJobdescription());
            holder.jobid.setText(list.getJobid());
            holder.jobqualification.setText(list.getJobqualification());
            holder.salary.setText(list.getSalary());
            holder.skills.setText(list.getSkills());
            holder.jobtype.setText(list.getJobtype());
            holder.location.setText(list.getLocation());
            holder.date.setText(list.getDate());

            holder.itemView.setOnClickListener(v -> {
                String jobid=list.getJobid();
                Intent intent=new Intent(PostedJob.this, AppliedCandidates.class);
                intent.putExtra("jobid",jobid);
                startActivity(intent);
            });
            holder.deljob.setOnClickListener(v -> {
                String deljob=list.getJobid();
                myRef.child(deljob).removeValue();
                Toast.makeText(PostedJob.this, "JOB DELETED", Toast.LENGTH_SHORT).show();

            });
        }

        @Override
        public int getItemCount() {

            return postedjoblist.size();
        }
    }
}


