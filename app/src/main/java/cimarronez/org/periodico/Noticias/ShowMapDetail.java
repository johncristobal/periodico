package cimarronez.org.periodico.Noticias;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cimarronez.org.periodico.R;

import static cimarronez.org.periodico.Noticias.Fragments.Notafragment.modelostatisco;

public class ShowMapDetail extends AppCompatActivity {

    public boolean flag;
    public WebView imageView13;
    TextView textPueblo,textLengua,textPoblacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_map_detail);

        imageView13 = findViewById(R.id.imageView13);
        textPueblo = findViewById(R.id.textPueblo);
        textLengua = findViewById(R.id.textLengua);
        textPoblacion = findViewById(R.id.textPoblacion);

        Intent extras = getIntent();
        int imagen = extras.getIntExtra("imagen",0);
        String name = getResources().getResourceEntryName(imagen);
        String[] datos = extras.getStringExtra("texto").split(",");
        textPueblo.setText(datos[0]);
        textLengua.setText(datos[1]);
        textPoblacion.setText(datos[2]);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(datos[0]);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NestedScrollView llBottomSheet = findViewById(R.id.design_bottom_sheet);

        // init the bottom sheet behavior
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);

        // change the state of the bottom sheet
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        flag = false;

        imageView13.loadDataWithBaseURL("file:///android_res/drawable/", "<img src='"+name+ "' />", "text/html", "utf-8", null);
        imageView13.getSettings().setBuiltInZoomControls(true);
        imageView13.setInitialScale(120);
        imageView13.getSettings().setDisplayZoomControls(false);

        // set the peek height
        //bottomSheetBehavior.setPeekHeight(340);

        // set hideable or not
        bottomSheetBehavior.setHideable(false);

        // set callback for changes
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        final TextView title = findViewById(R.id.title_sheet);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag) {
                    title.setText("Ver detalles");
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                else {
                    title.setText("Ocultar");
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }

                flag = !flag;
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
