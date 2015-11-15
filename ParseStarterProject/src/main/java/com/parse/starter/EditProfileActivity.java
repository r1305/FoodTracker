package com.parse.starter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import Utilities.BitmapToByteArray;
import Utilities.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;

public class EditProfileActivity extends AppCompatActivity {

    @Bind(R.id.edit_name_perfil)
    EditText name;
    @Bind(R.id.edit_img_perfil)
    ImageView img;
    @Bind(R.id.edit_correo_perfil)
    EditText email;
    @Bind(R.id.edit_user)
    EditText user;
    @Bind(R.id.save_fab)
    FloatingActionButton fab;
    @Bind(R.id.toolbar_edit)
    Toolbar toolbar;

    int PICK_IMAGE=2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);

        final Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
        new BitmapToByteArray().execute(bitmap);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setTitle("Editando");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditProfileActivity.this, InicioActivity.class);
                startActivity(i);
                EditProfileActivity.this.finish();

            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });



        final ParseUser currentUser = ParseUser.getCurrentUser();

        if (currentUser != null) {
            name.setText(currentUser.getString("name"));
            email.setText(currentUser.getEmail());
            user.setText(currentUser.getUsername());
            try {
                ParseFile applicantResume = (ParseFile) currentUser.get("foto");
                applicantResume.getDataInBackground(new GetDataCallback() {
                    public void done(byte[] data, ParseException e) {
                        if (e == null) {

                            img.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));

                        } else {
                            // something went wrong
                            Toast t = Toast.makeText(EditProfileActivity.this, "No se pudo obtener la foto", Toast.LENGTH_SHORT);
                            t.show();

                        }
                    }
                });
            }catch(Exception e){
                Toast t = Toast.makeText(EditProfileActivity.this, "No se pudo obtener la foto", Toast.LENGTH_SHORT);
                t.show();
            }



        } else {
            // show the signup or login screen

        }

         fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ParseFile foto = new ParseFile("foto.png", Utils.imageBuffer);
                foto.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        currentUser.put("foto", foto);
                        currentUser.setEmail(email.getText().toString());
                        currentUser.put("name", name.getText().toString());
                        currentUser.setUsername(user.getText().toString());

                        currentUser.saveInBackground(new SaveCallback() {

                            @Override
                            public void done(ParseException e) {

                                Intent i = new Intent(EditProfileActivity.this, InicioActivity.class);
                                startActivity(i);

                                Toast t = Toast.makeText(EditProfileActivity.this, "Edicion exitosa", Toast.LENGTH_SHORT);
                                t.show();
                                EditProfileActivity.this.finish();

                                Utils.parseUser = null;

                            }
                        });
                    }
                });

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            img.setImageURI(imageUri);
            Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
            new BitmapToByteArray().execute(bitmap);
        }
    }

}
