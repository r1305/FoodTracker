package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends ActionBarActivity {
@Bind(R.id.FbLoginBtn) Button FbLoginBtn;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    ParseAnalytics.trackAppOpenedInBackground(getIntent());

    FbLoginBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        List<String> permissions = Arrays.asList("public_profile", "email");ParseFacebookUtils.logInWithReadPermissionsInBackground(MainActivity.this, permissions, new LogInCallback() {
              @Override
              public void done(ParseUser user, ParseException err) {
                  if (user == null) {
                      Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                  } else if (user.isNew()) {
                      Log.d("MyApp", "User signed up and logged in through Facebook!");
                  } else {
                      Log.d("MyApp", "User logged in through Facebook!");
                  }
              }
          });
      }
    });
  }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
