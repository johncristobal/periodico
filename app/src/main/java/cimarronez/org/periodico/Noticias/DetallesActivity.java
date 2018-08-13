package cimarronez.org.periodico.Noticias;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import cimarronez.org.periodico.R;

import static cimarronez.org.periodico.Noticias.Fragments.Notafragment.modelostatisco;
import static cimarronez.org.periodico.Noticias.NoticiasActivity.categorias;

public class DetallesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbardetalles);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TextView titulo = findViewById(R.id.textViewTitulo);
        TextView categoria = findViewById(R.id.textViewCategoria);
        TextView fecha = findViewById(R.id.textViewFecha);
        TextView descripcion = findViewById(R.id.textViewDescripcion);

        titulo.setText(modelostatisco.getTitulo());
        categoria.setText(categorias.get(modelostatisco.getCategoria()));
        fecha.setText(modelostatisco.getFecha());
        descripcion.setText(modelostatisco.getDescripcion());
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
