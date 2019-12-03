package com.deepjose.ateneanote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    private Button btnReg, btnBack;
    private EditText name, surname, email, pass ;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnReg = findViewById(R.id.btnRegReg);
        btnBack = findViewById(R.id.btnBackReg);
        email = findViewById(R.id.userEmailReg);
        pass = findViewById(R.id.passwordReg);
        name = findViewById(R.id.userNameReg);
        surname = findViewById(R.id.userSurnameReg);


        mAuth = FirebaseAuth.getInstance();


        email.setText("joserobles98.21@gmail.com");
        pass.setText("contraseña");
        name.setText("José");
        surname.setText("Robles Bastidas");

        btnBack.setOnClickListener(v -> {
            setResult(RESULT_CANCELED) ;
            finish() ;
            return ;
        });

        btnReg.setOnClickListener(view -> {

            final String nam = getField(name) ;
            final String surnam = getField(surname) ;
            final String ema = getField(email) ;
            final String password = getField(pass);

            mAuth.createUserWithEmailAndPassword(ema, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                user.sendEmailVerification();
                                mAuth.signOut();
                                new MaterialAlertDialogBuilder(Register.this)
                                        .setTitle(getResources().getString(R.string.email_dialog_title))
                                        .setMessage(getResources().getString(R.string.email_dialog_body))
                                        .setPositiveButton(getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                setResult(RESULT_CANCELED) ;
                                                finish() ;
                                                return ;
                                            }
                                        })
                                        .show();
                            } else {
                                Toast.makeText(Register.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
        });

    }
    private String getField(EditText edit)
    {
        return edit.getText().toString().trim() ;
    }
}
