package com.example.homesecuritymain.Login.Activity.Guard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.homesecuritymain.Admin.Model.GuardDetailsModel;
import com.example.homesecuritymain.CommonClasses.ClassCommon.CommonClass;
import com.example.homesecuritymain.CommonClasses.ClassCommon.DateAndTimeClass;
import com.example.homesecuritymain.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class NewGuardActivity extends AppCompatActivity {
    private TextInputLayout tilPassword, tilReEnterPassword;
    private TextInputEditText tiEdPassword, tiEdReEnterPassword;
    private Button btn;
    private String password,reEnteredPassword,id;

    private CommonClass object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_guard);

        initialize();

        object = new CommonClass();

        id = getIntent().getStringExtra("idGuard");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTextFromEditText();
                btn.setEnabled(false);

                if (password.equals(reEnteredPassword)){
                    //set Date
                    object.mUserDatabaseGuardLogin.child(id).child("dateJoined").setValue(new DateAndTimeClass().getCurrentDate());
                    //update password
                    object.mUserDatabaseGuardLogin.child(id).child("password").setValue(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            object.mUserDatabaseGuardLogin.child(id).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    GuardDetailsModel model = snapshot.getValue(GuardDetailsModel.class);
                                    Intent intent = new Intent(NewGuardActivity.this,NewGuardActivitySplashScreenActivity.class);
                                    intent.putExtra("modelGuardAll",model);
                                    startActivity(intent);
                                    finish();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    });
                }else {
                    btn.setEnabled(true);
                    tilPassword.setError("check the password");
                    tilReEnterPassword.setError("check the password");
                    Toast.makeText(NewGuardActivity.this,"The passwords does not match", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void getTextFromEditText() {
        password = tiEdPassword.getText().toString().trim();
        reEnteredPassword = tiEdReEnterPassword.getText().toString().trim();
    }

    private void initialize() {
        tiEdPassword = findViewById(R.id.TiEt_NewGuardActivity_Password);
        tiEdReEnterPassword = findViewById(R.id.TiEt_NewGuardActivity_PasswordRepeat);
        tilPassword = findViewById(R.id.Til_NewGuardActivity_Password);
        tilReEnterPassword = findViewById(R.id.Til_NewGuardActivity_PasswordRepeat);

        btn = findViewById(R.id.Btn_NewGuardActivity_Next);
    }
}