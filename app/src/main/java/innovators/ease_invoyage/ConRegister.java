package innovators.ease_invoyage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ConRegister extends AppCompatActivity implements View.OnClickListener{


    //defining view objects
    private EditText conEmail;
    private EditText conPassword;
    private Button conSignup;
    private TextView conViewSignin;
    private ProgressDialog progressDialog;

    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_con_register);
        Intent reg = getIntent();

        //initializing views
        conEmail = (EditText) findViewById(R.id.conEmail);
        conPassword = (EditText) findViewById(R.id.conPassword);

        conSignup = (Button) findViewById(R.id.conSignup);
        conViewSignin = (TextView) findViewById(R.id.conViewSignin);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        conSignup.setOnClickListener(this);
        conViewSignin.setOnClickListener(this);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //if getCurrentUser does not returns null
        if(firebaseAuth.getCurrentUser() != null){
            //that means user is already logged in
            //so close this activity
            finish();

            //and open profile activity
            startActivity(new Intent(getApplicationContext(), ViewData.class));
        }


    }

    private void registerUser(){

        //getting email and password from edit texts
        String email = conEmail.getText().toString().trim();
        String password  = conPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            //display some message here
                            Toast.makeText(ConRegister.this,"Successfully registered",Toast.LENGTH_LONG).show();
                        }else{
                            //display some message here
                            Toast.makeText(ConRegister.this,"Registration Error",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onClick(View view) {
        //calling register method on click
        if(view == conSignup){
            registerUser();
        }

        if(view == conViewSignin){
            //open login activity when user taps on the already registered textview
            startActivity(new Intent(this, AdminLogin.class));
        }
    }
}
