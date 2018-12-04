package cimarronez.org.periodico.settings;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cimarronez.org.periodico.R;

public class DatosActivity extends AppCompatActivity {

    EditText textViewNombre,textViewCorreo;
    private String userChoosenTask;
    private ImageView perfi;
    public boolean flagFoto;
    public static FirebaseAuth mAuth;

    private int REQUEST_CAMERA = 10;
    private int SELECT_FILE = 20;
    public File photoFile = null;
    public String mCurrentPhotoPath;
    SharedPreferences preferences;

    public ProgressBar progressBar33;
    public Button buttonRegistro;
    private int PICK_IMAGE = 3;

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
        buttonRegistro = findViewById(R.id.buttonRegistro);
        progressBar33 = findViewById(R.id.progressBar33);

        perfi = findViewById(R.id.imageViewProfile);
        flagFoto = false;
        //get data from user prifle and show here
        preferences = getSharedPreferences("cimarronez", Context.MODE_PRIVATE);
        textViewNombre.setText(preferences.getString("nombre","null"));
        textViewCorreo.setText(preferences.getString("correo","null"));

        if(!preferences.getString("nombrefoto", "null").equals("null")){
            String filePath = preferences.getString("nombrefoto", "null");//photoFile.getPath();
            //Bitmap bmp = BitmapFactory.decodeFile(filePath);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            Bitmap bmp = BitmapFactory.decodeFile(filePath,options);

            perfi.setImageBitmap(bmp);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            supportFinishAfterTransition();
        }

        return super.onOptionsItemSelected(item);
    }

//===================update data====================================================================
    public void actualizaDatos(View view) {
        //actualizar user firebase, shaeredpreferences

        if(!textViewNombre.getText().toString().equals("") && !textViewCorreo.getText().toString().equals("")){
            enviarInfo enviarInfo = new enviarInfo();
            enviarInfo.execute();
            //actualizao info
            //update user info

            /*preferences.edit().putString("nombre",textViewNombre.getText().toString()).apply();
            preferences.edit().putString("correo",textViewCorreo.getText().toString()).apply();

            mAuth = FirebaseAuth.getInstance();
            final FirebaseUser user = mAuth.getCurrentUser();

            if(flagFoto){
                //update foto

                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReferenceFromUrl("gs://cimarronez.appspot.com/");
                final StorageReference ref = storageRef.child("usuarios/"+mAuth.getUid()+"/perfil.png");

                File image = new File(preferences.getString("nombrefoto", "null"));
                Uri uri = Uri.fromFile(image);

                Bitmap bmp = null;
                try {
                    bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 50, baos);
                    byte[] data = baos.toByteArray();

                    StorageMetadata metadata = new StorageMetadata.Builder()
                            .setContentType("image/png")
                            .build();
                    UploadTask uploadTask = ref.putBytes(data,metadata);

                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            progressBar33.setVisibility(View.GONE);
                            buttonRegistro.setVisibility(View.VISIBLE);
                            Toast.makeText(DatosActivity.this,"Tuvimos un problema al actualizar. Intent más tarde...",Toast.LENGTH_SHORT).show();

                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                            Uri downloadUri = taskSnapshot.getUploadSessionUri();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(textViewNombre.getText().toString())
                                    .setPhotoUri(downloadUri)
                                    .build();

                            user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressBar33.setVisibility(View.GONE);
                                        buttonRegistro.setVisibility(View.VISIBLE);
                                        finish();
                                    }else {
                                        progressBar33.setVisibility(View.GONE);
                                        buttonRegistro.setVisibility(View.VISIBLE);
                                        Toast.makeText(DatosActivity.this,"Tuvimos un problema al actualizar. Intent más tarde...",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                    progressBar33.setVisibility(View.GONE);
                    buttonRegistro.setVisibility(View.VISIBLE);
                    Toast.makeText(DatosActivity.this,"Tuvimos un problema al actualizar. Intent más tarde...",Toast.LENGTH_SHORT).show();
                }
            }else{
             //no actualizamos foto, solo info
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(textViewNombre.getText().toString())
                        //.setPhotoUri(downloadUri)
                        .build();

                user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressBar33.setVisibility(View.GONE);
                            buttonRegistro.setVisibility(View.VISIBLE);
                            finish();
                        }else {
                            progressBar33.setVisibility(View.GONE);
                            buttonRegistro.setVisibility(View.VISIBLE);
                            Toast.makeText(DatosActivity.this,"Tuvimos un problema al actualizar. Intent más tarde...",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }*/
        }else{
            //progressBar33.setVisibility(View.GONE);
            //buttonRegistro.setVisibility(View.VISIBLE);
            Toast.makeText(this,"Favor de llenar los campos...",Toast.LENGTH_SHORT).show();
        }
    }

    public void seleccionarFoto(View view) {
        //abrir galeria o camara para seleccioanr foto
        //mostrar foto y guradr en caso de ...

        final CharSequence[] items = {getString(R.string.tomafoto), getString(R.string.galeria),
                "Cancelar"};

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
                    if (result)
                        galleryIntent();
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

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
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
                Uri photoURI = FileProvider.getUriForFile(this, "cimarronez.org.periodico.fileprovider", photoFile);
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
        //SharedPreferences preferences = getSharedPreferences(getString(R.string.sharedName), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("nombrefoto",mCurrentPhotoPath);
        editor.apply();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE) {
                Log.i("galeria", "gale");
                try {
                    ///onSelectFromGalleryResult(data);
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };

                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 4;
                    //Bitmap bmp = BitmapFactory.decodeFile(filePath,options);
                    Bitmap bitmap = BitmapFactory.decodeFile(picturePath,options);
                    flagFoto = true;
                    perfi.setImageBitmap(bitmap);

                    File path = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)+File.separator);
                    if(!path.exists()){
                        path.mkdirs();
                    }

                    File pathImage = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)+File.separator+"/perfil.jpg");
                    try {
                        FileOutputStream out = new FileOutputStream(pathImage);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out); // bmp is your Bitmap instance
                        // PNG is a lossless format, the compression factor (100) is ignored
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    File image = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)+File.separator+"/perfil.jpg");
                    mCurrentPhotoPath = image.getAbsolutePath();

                    //save name jajaja
                    //SharedPreferences preferences = getSharedPreferences(getString(R.string.sharedName), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("nombrefoto",mCurrentPhotoPath);
                    editor.apply();

                    //salvar la imagen seleccioanda en el archivo creado

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

//***********************Recuperamos foto tomada de la camara y mostramos*********************************
    private void onCaptureImageResult(Intent data) {

        SharedPreferences preferences = getSharedPreferences(getString(R.string.sharedName), Context.MODE_PRIVATE);
        mCurrentPhotoPath = preferences.getString("nombrefoto", "null");

        String filePath = mCurrentPhotoPath;//photoFile.getPath();
        //Bitmap bmp = BitmapFactory.decodeFile(filePath);

        //BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inSampleSize = 4;
        //Bitmap bmp = BitmapFactory.decodeFile(filePath,options);

        Glide.with(DatosActivity.this)
                .load(filePath)
                .apply(new RequestOptions().override(150, 200).centerCrop())//.override(150,200)
                //.override(150,200)
                //.centerCrop()
                .into(perfi);

        flagFoto = true;
        //perfi.setImageBitmap(bmp);
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

//========================= Thread para enviar datos =================================================
    public class enviarInfo extends AsyncTask<Void,Void,Void>{

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressBar33.setVisibility(View.VISIBLE);
        buttonRegistro.setVisibility(View.GONE);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        preferences.edit().putString("nombre",textViewNombre.getText().toString()).apply();
        preferences.edit().putString("correo",textViewCorreo.getText().toString()).apply();

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();

        if(flagFoto){
            //update foto

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReferenceFromUrl("gs://cimarronez.appspot.com/");
            final StorageReference ref = storageRef.child("usuarios/"+mAuth.getUid()+"/perfil.png");

            //File image = new File(preferences.getString("nombrefoto", "null"));
            //Uri uri = Uri.fromFile(image);
            perfi.setDrawingCacheEnabled(true);
            perfi.buildDrawingCache();

            Bitmap bmp = null;
            try {
                bmp = ((BitmapDrawable) perfi.getDrawable()).getBitmap();
                //bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 80, baos);
                byte[] data = baos.toByteArray();

                StorageMetadata metadata = new StorageMetadata.Builder()
                        .setContentType("image/png")
                        .build();
                UploadTask uploadTask = ref.putBytes(data,metadata);

                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        progressBar33.setVisibility(View.GONE);
                        buttonRegistro.setVisibility(View.VISIBLE);
                        Toast.makeText(DatosActivity.this,"Tuvimos un problema al actualizar. Intent más tarde...",Toast.LENGTH_SHORT).show();

                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        Uri downloadUri = taskSnapshot.getUploadSessionUri();
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(textViewNombre.getText().toString())
                                .setPhotoUri(downloadUri)
                                .build();

                        user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    //progressBar33.setVisibility(View.GONE);
                                    //buttonRegistro.setVisibility(View.VISIBLE);
                                    finish();
                                }else {
                                    progressBar33.setVisibility(View.GONE);
                                    buttonRegistro.setVisibility(View.VISIBLE);
                                    Toast.makeText(DatosActivity.this,"Tuvimos un problema al actualizar. Intent más tarde...",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                progressBar33.setVisibility(View.GONE);
                buttonRegistro.setVisibility(View.VISIBLE);
                Toast.makeText(DatosActivity.this,"Tuvimos un problema al actualizar. Intent más tarde...",Toast.LENGTH_SHORT).show();
            }
        }else{
            //no actualizamos foto, solo info
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(textViewNombre.getText().toString())
                    //.setPhotoUri(downloadUri)
                    .build();

            user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        progressBar33.setVisibility(View.GONE);
                        buttonRegistro.setVisibility(View.VISIBLE);
                        finish();
                    }else {
                        progressBar33.setVisibility(View.GONE);
                        buttonRegistro.setVisibility(View.VISIBLE);
                        Toast.makeText(DatosActivity.this,"Tuvimos un problema al actualizar. Intent más tarde...",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        //progressBar33.setVisibility(View.GONE);
        //buttonRegistro.setVisibility(View.VISIBLE);
    }

    }

}
