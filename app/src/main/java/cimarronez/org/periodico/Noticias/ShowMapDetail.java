package cimarronez.org.periodico.Noticias;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cimarronez.org.periodico.R;

public class ShowMapDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_map_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LinearLayout llBottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);

        // init the bottom sheet behavior
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);

        // change the state of the bottom sheet
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

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

        TextView title = findViewById(R.id.title_sheet);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }
}
