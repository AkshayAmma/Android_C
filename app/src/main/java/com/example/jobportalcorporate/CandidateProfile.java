package com.example.jobportalcorporate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class CandidateProfile extends AppCompatActivity {
    TextView tv_name,tv_sex, tv_city,tv_address,
            tv_pincode,tv_10marks,tv_10year,tv_12marks,
            tv_12year,tv_ugmarks,tv_ugyear,tv_ugcourse,
            tv_pgmarks,tv_pgyear,tv_pgcourse,tv_skills,
            tv_achievement,tv_certification,tv_workexp
            ,tv_dob,tv_email;
    Button downloadcv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_profile);
        tv_name= findViewById(R.id.tv_name);
        tv_sex= findViewById(R.id.tv_sex);
        tv_email= findViewById(R.id.tv_email);
        tv_city= findViewById(R.id.tv_city);
        tv_address= findViewById(R.id.tv_address);
        tv_pincode= findViewById(R.id.tv_pincode);
        tv_10marks= findViewById(R.id.tv_10marks);
        tv_10year= findViewById(R.id.tv_10year);
        tv_12marks= findViewById(R.id.tv_12marks);
        tv_12year= findViewById(R.id.tv_12year);
        tv_ugcourse= findViewById(R.id.tv_ugcourse);
        tv_ugmarks= findViewById(R.id.tv_ugmarks);
        tv_ugyear= findViewById(R.id.tv_ugyear);
        tv_pgcourse= findViewById(R.id.tv_pgcourse);
        tv_pgmarks= findViewById(R.id.tv_pgmarks);
        tv_pgyear= findViewById(R.id.tv_pgyear);
        tv_skills= findViewById(R.id.tv_skills);
        tv_achievement= findViewById(R.id.tv_achievement);
        tv_certification= findViewById(R.id.tv_certification);
        tv_workexp= findViewById(R.id.tv_workexp);
        tv_dob= findViewById(R.id.tv_dob);
        downloadcv= findViewById(R.id.btn_downcv);

        String name= Objects.requireNonNull(getIntent().getExtras()).getString("name");
        String sex=getIntent().getExtras().getString("sex");
        String city=getIntent().getExtras().getString("city");
        String address=getIntent().getExtras().getString("address");
        String pincode=getIntent().getExtras().getString("pincode");
        String xmarks=getIntent().getExtras().getString("xmarks");
        String xyear=getIntent().getExtras().getString("xyear");
        String xiimarks=getIntent().getExtras().getString("xiimarks");
        String xiiyear=getIntent().getExtras().getString("xiiyear");
        String ugmarks=getIntent().getExtras().getString("ugmarks");
        String ugyear=getIntent().getExtras().getString("ugyear");
        String ugcourse=getIntent().getExtras().getString("ugcourse");
        String pgmarks=getIntent().getExtras().getString("pgmarks");
        String pgyear=getIntent().getExtras().getString("pgyear");
        String pgcourse=getIntent().getExtras().getString("pgcourse");
        String skills=getIntent().getExtras().getString("skills");
        String achievements=getIntent().getExtras().getString("achievements");
        String certification=getIntent().getExtras().getString("certifications");
        String workexp=getIntent().getExtras().getString("workexp");
        String dob=getIntent().getExtras().getString("dob");
        String email=getIntent().getExtras().getString("email");

        final String cv=getIntent().getExtras().getString("cv");



        tv_name.setText(name);
        tv_sex.setText(sex);
        tv_city.setText(city);
        tv_address.setText(address);
        tv_pincode.setText(pincode);
        tv_10marks.setText(xmarks);
        tv_10year.setText(xyear);
        tv_12marks.setText(xiimarks);
        tv_12year.setText(xiiyear);
        tv_ugcourse.setText(ugcourse);
        tv_ugmarks.setText(ugmarks);
        tv_ugyear.setText(ugyear);
        tv_pgcourse.setText(pgcourse);
        tv_pgmarks.setText(pgmarks);
        tv_pgyear.setText(pgyear);
        tv_skills.setText(skills);
        tv_achievement.setText(achievements);
        tv_certification.setText(certification);
        tv_workexp.setText(workexp);
        tv_dob.setText(dob);
        tv_email.setText(email);


        downloadcv.setOnClickListener(v -> {
                try {

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(cv));
                    startActivity(intent);
                }catch (Exception e){
                    String error="No Cv";
                    Toast.makeText(CandidateProfile.this, error, Toast.LENGTH_SHORT).show();
                }

        });

    }
}
