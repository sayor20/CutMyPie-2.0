package com.sayor.org.cutmypie.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.sayor.org.cutmypie.R;


public class LoginActivity extends ActionBarActivity {

    Button btLogin,btSignup;
    TextView tvUsername, tvPassword, tvEmail;
    EditText etUsername, etPassword, etEmail;
    ParseUser user;
    ToggleButton btToggle;
    String sUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = new ParseUser();

        tvUsername = (TextView)findViewById(R.id.tvUsername);
        tvPassword = (TextView)findViewById(R.id.tvPassword);
        tvEmail = (TextView)findViewById(R.id.tvEmail);
        etUsername = (EditText)findViewById(R.id.etUsername);
        etPassword = (EditText)findViewById(R.id.etPassword);
        etEmail = (EditText)findViewById(R.id.etEmail);
        btLogin = (Button)findViewById(R.id.btLogin);;
        btToggle = (ToggleButton) findViewById(R.id.btToggle);

        tvEmail.setVisibility(View.INVISIBLE);
        etEmail.setVisibility(View.INVISIBLE);

        onBackPressed();
    }

    public void onToggle(View v){
        if(btToggle.isChecked()){
            tvEmail.setVisibility(View.INVISIBLE);
            etEmail.setVisibility(View.INVISIBLE);
            btLogin.setText("Login");
        }else{
            tvEmail.setVisibility(View.VISIBLE);
            etEmail.setVisibility(View.VISIBLE);
            btLogin.setText("Signup");
        }
    }

    @Override
    public void onBackPressed() {
        // do nothing.
    }

    public void onValidate(View v){
        String login = btLogin.getText().toString();
        if(login.equals("Login")) {
            user.logInInBackground(etUsername.getText().toString(), etPassword.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    if (e == null) {
                        sUserId = ParseUser.getCurrentUser().getObjectId();
                        Intent i =new Intent(LoginActivity.this, MapsActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(LoginActivity.this, "error in login", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else{
            user.setUsername(etUsername.getText().toString());
            user.setPassword(etPassword.getText().toString());
            user.setEmail(etEmail.getText().toString());
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if(e==null){
                        sUserId = ParseUser.getCurrentUser().getObjectId();
                        Intent i =new Intent(LoginActivity.this, MapsActivity.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(LoginActivity.this, "error in signup", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
