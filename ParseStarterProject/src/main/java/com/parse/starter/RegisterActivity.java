package com.parse.starter;


import android.content.Intent;
import android.graphics.Bitmap;

import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import com.parse.ParseException;
import com.parse.ParseFile;

import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;


import Utilities.BitmapToByteArray;
import Utilities.DownloadImageTask;
import Utilities.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {
    @Bind(R.id.register_okBtn)
    Button ok;
    @Bind(R.id.register_cancelBtn)
    Button cancel;
    @Bind(R.id.nameTxt)EditText name;
    @Bind(R.id.emailTxt)EditText email;
    @Bind(R.id.user)EditText user;
    @Bind(R.id.passwordTxt)EditText psw;
    @Bind(R.id.sexGroup)RadioGroup sex;
    @Bind(R.id.profile_image) ImageView profile_image;
    @Bind(R.id.progressBar)ProgressBar progressBar;
    private static final int PICK_IMAGE = 2000;

    private void getFbData() {
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        Log.d("FbUserData", object.toString());
                        try {
                            Utils.user.setFbid(object.getString("id"));
                            Utils.user.setNombre(object.getString("name"));

                            Utils.user.setEmail(object.getString("email"));
                            Utils.user.setSexo(object.getString("gender"));
                            Utils.user.setUrlFoto("https://graph.facebook.com/" + Utils.user.getFbid() + "/picture?height=400&width=400");

                            name.setText(Utils.user.getNombre());
                            email.setText(Utils.user.getEmail());
                            switch (Utils.user.getSexo()) {
                                case "male":
                                    sex.check(R.id.masculino);
                                    break;
                                case "female":
                                    sex.check(R.id.femenino);
                                    break;
                            }
                            new DownloadImageTask(profile_image, true).execute(Utils.user.getUrlFoto());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,gender");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        final Bitmap bitmap = ((BitmapDrawable) profile_image.getDrawable()).getBitmap();
        new BitmapToByteArray().execute(bitmap);



        if (Utils.user.isFacebook()) {
            getFbData();
            name.setText(Utils.user.getNombre());
            email.setText(Utils.user.getEmail());
            profile_image.setImageBitmap(bitmap);
        }
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(100);


                String pass = psw.getText().toString();
                if (Utils.parseUser == null) {
                    Utils.parseUser = new ParseUser();
                }
                Utils.parseUser.setUsername(user.getText().toString());
                Utils.parseUser.put("username",user.getText().toString());
                Utils.parseUser.setEmail(email.getText().toString());
                Utils.parseUser.setPassword(pass);
                Utils.parseUser.put("name", name.getText().toString());


                switch (sex.getCheckedRadioButtonId()) {
                    case R.id.masculino:
                        Utils.parseUser.put("sex", "masculino");
                        break;
                    case R.id.femenino:
                        Utils.parseUser.put("sex", "femenino");
                        break;
                }
                final ParseFile foto = new ParseFile("foto.png", Utils.imageBuffer);

                foto.saveInBackground(new SaveCallback() {
                      @Override
                      public void done(ParseException e) {
                          Utils.parseUser.put("foto", foto);
                          if (Utils.user.isFacebook()) {
                              Utils.parseUser.saveInBackground(new SaveCallback() {
                                  @Override
                                  public void done(ParseException e) {
                                  Utils.parseUser.signUpInBackground(new SignUpCallback() {

                                      @Override
                                      public void done(ParseException e) {
                                      Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                      Toast t = Toast.makeText(RegisterActivity.this, "Registro correcto, para validar ingrese denuevo", Toast.LENGTH_LONG);
                                      t.show();
                                      startActivity(intent);
                                      RegisterActivity.this.finish();
                                      Utils.parseUser = null;

                                      }
                                  });
                                  }
                              });
                          } else {
                              Utils.parseUser.saveInBackground(new SaveCallback() {
                                  @Override
                                  public void done(ParseException e) {
                                  Utils.parseUser.signUpInBackground(new SignUpCallback() {

                                      @Override
                                      public void done(ParseException e) {
                                      Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                      Toast t = Toast.makeText(RegisterActivity.this, "Registro correcto, para validar ingrese denuevo", Toast.LENGTH_LONG);
                                      t.show();
                                      startActivity(intent);
                                      RegisterActivity.this.finish();
                                      Utils.parseUser = null;

                                      }
                                  });
                                  }
                              });
                          }
                      }
                  }

                );


                }
            });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void openGallery() {
        Intent gallery =
                new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Utils.parseUser != null) {
            Utils.parseUser.deleteInBackground();
            Utils.parseUser = null;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            profile_image.setImageURI(imageUri);
            Bitmap bitmap = ((BitmapDrawable) profile_image.getDrawable()).getBitmap();
            new BitmapToByteArray().execute(bitmap);
        }
    }

    }
