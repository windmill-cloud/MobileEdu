/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs185.foliostation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginSignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);

        myToolbar.setTitle("");
        TextView tv = (TextView) findViewById(R.id.toolbar_title);
        tv.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent logInIntent = new Intent(LoginSignupActivity.this,
                        SplashScreenActivity.class);
                LoginSignupActivity.this.startActivity(logInIntent);
                LoginSignupActivity.this.finish();
            }
        });
        setSupportActionBar(myToolbar);

        Intent intent = getIntent();

        String type = intent.getStringExtra("TYPE");
        TextView prompt = (TextView) findViewById(R.id.prompt);
        EditText reEnterPassword = (EditText) findViewById(R.id.password_again);
        Button loginSignupButton = (Button) findViewById(R.id.signup_login_button);

        if (type == null || type.equals("SIGN_UP")) {
            loginSignupButton.setText("Sign up");
        } else if(type.equals("LOG_IN")) {
            prompt.setText("Log in");
            reEnterPassword.setHeight(0);
            reEnterPassword.setVisibility(View.GONE);
            reEnterPassword.setEnabled(false);
            loginSignupButton.setText("Log in");
        }

        loginSignupButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent collectIntent = new Intent(LoginSignupActivity.this,
                        ContainerActivity.class);
                collectIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                LoginSignupActivity.this.startActivity(collectIntent);
                LoginSignupActivity.this.finish();
            }
        });

    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.login_sign_up_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        if(item.getItemId() == android.R.id.home) {
            Intent logInIntent = new Intent(this,
                    SplashScreenActivity.class);
                this.startActivity(logInIntent);
                this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

}
