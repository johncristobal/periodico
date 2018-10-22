package cimarronez.org.periodico.settings;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import cimarronez.org.periodico.R;

public class DatosActivity extends AppCompatActivity {

    EditText textViewNombre,textViewCorreo;
    private String userChoosenTask;

    private int REQUEST_CAMERA = 10;
    private int SELECT_FILE = 20;
    public File photoFile = null;
    public String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Actualizar datos...");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        textViewNombre = findViewById(R.id.editNombreDatos);
        textViewCorreo = findViewById(R.id.editCorreoDatos);

        //get data from user prifle and show here
        SharedPreferences preferences = getSharedPreferences("cimarronez", Context.MODE_PRIVATE);
        textViewNombre.setText(preferences.getString("nombre","null"));
        textViewCorreo.setText(preferences.getString("correo","null"));

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            //finish();
            supportFinishAfterTransition();
        }

        return super.onOptionsItemSelected(item);
    }

    public void actualizaDatos(View view) {
        //actualizar user firebase, shaeredpreferences
        if(!textViewNombre.getText().toString().equals("") && !textViewCorreo.getText().toString().equals("")){
            //actualizao info

        }
    }

    public void seleccionarFoto(View view) {
        //abrir galeria o camara para seleccioanr foto
        //mostrar foto y guradr en caso de ...

        final CharSequence[] items = {getString(R.string.tomafoto), getString(R.string.galeria),
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Agregar imagen");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(DatosActivity.this);
                if (items[item].equals(getString(R.string.tomafoto))) {
                    userChoosenTask = getString(R.string.tomafoto);
                    if (result)
                        permisionCamera();
                    //cameraIntent();
                } else if (items[item].equals(getString(R.string.galeria))) {
                    userChoosenTask = getString(R.string.galeria);
                    //if (result)
                        //galleryIntent();
                } else if (items[item].equals("Cancelar")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

        /*String name = "";
        if(name.equals("")){
            Toast.makeText(this,"Ponle un nombre para poder guardar la foto",Toast.LENGTH_SHORT).show();
        }else {
            //Directament abrimos camara
            //permisionCamera();
        }*/
    }

    public void permisionCamera(){
        if (Build.VERSION.SDK_INT < 23) {

            cameraIntent23();
        } else {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
            } else {
                cameraIntent();
            }
        }
    }

    //***********************Abrir camara para tomar foto***********************************************
    private void cameraIntent()
    {
        Intent takepic=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivityForResult(i, FRONT_VEHICLE);
        if (takepic.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            try {
                photoFile = createImageFile("perfil");
            } catch (IOException ex) {
                ex.printStackTrace();
                // Error occurred while creating the File...
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                //Uri photoURI = FileProvider.getUriForFile(VehiclePictures.this, "miituo.com.miituo", photoFile);
                Uri photoURI = FileProvider.getUriForFile(this, "nowoscmexico.com.tradinggames_1.fileprovider", photoFile);
                takepic.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takepic, REQUEST_CAMERA);
            }else{
                Toast.makeText(this,"Tuvimos un problema al tomar la imagen. Intente mas tarde.",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void cameraIntent23()
    {
        Intent takepic=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivityForResult(i, FRONT_VEHICLE);
        if (takepic.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            try {
                photoFile = createImageFile("perfil");
            } catch (IOException ex) {
                ex.printStackTrace();
                // Error occurred while creating the File...
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                //Uri photoURI = FileProvider.getUriForFile(VehiclePictures.this, "miituo.com.miituo", photoFile);
                //Uri photoURI = FileProvider.getUriForFile(this, "nowoscmexico.com.tradinggames_1.fileprovider", photoFile);
                Uri photoURI = Uri.fromFile(photoFile);
                takepic.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takepic, REQUEST_CAMERA);
            }else{
                Toast.makeText(this,"Tuvimos un problema al tomar la imagen. Intente mas tarde.",Toast.LENGTH_SHORT).show();
            }
        }
    }

    //***********************save iage to path folder app***********************************************
    private File createImageFile(String username) throws IOException {
        // Create an image file name
        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        //String imageFileName = username;

        File path = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)+File.separator);
        if(!path.exists()){
            path.mkdirs();
        }

        File image = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)+File.separator+"/"+username+".png");
        /*File image = File.createTempFile(
                username+tag,  // prefix
                ".png",         // suffix
                storageDir      // directory
        );*/

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();

        //save name jajaja
        SharedPreferences preferences = getSharedPreferences(getString(R.string.sharedName), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("nombrefoto",mCurrentPhotoPath);
        editor.apply();
        return image;
    }

    public static class Utility {
        public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

        public static boolean checkPermission(final Context context)
        {
            int currentAPIVersion = Build.VERSION.SDK_INT;
            if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
            {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                        alertBuilder.setCancelable(true);
                        alertBuilder.setTitle("Permission necessary");
                        alertBuilder.setMessage("External storage permission is necessary");
                        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                            }
                        });
                        AlertDialog alert = alertBuilder.create();
                        alert.show();
                    } else {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                    return false;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }
    }
}
