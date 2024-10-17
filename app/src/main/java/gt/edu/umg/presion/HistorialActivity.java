package gt.edu.umg.presion;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import gt.edu.umg.presion.BaseDatos.Helper.DatabaseHelper;
import gt.edu.umg.presion.BaseDatos.Model.RegistroPresion;

public class HistorialActivity extends AppCompatActivity {

    private ListView listView;
    private RegistroListAdapter registroListAdapter;
    private ArrayList<RegistroPresion> listaRegistros;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        listView = findViewById(R.id.listViewHistorial);

        databaseHelper = new DatabaseHelper(this);

        // Obtener los registros de la base de datos
        listaRegistros = databaseHelper.obtenerTodosLosRegistros();

        // Configurar el adaptador
        registroListAdapter = new RegistroListAdapter(this, listaRegistros);
        listView.setAdapter(registroListAdapter);

        // Opcional: Manejar clics en los ítems de la lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                RegistroPresion registroSeleccionado = (RegistroPresion) adapterView.getItemAtPosition(position);
                Toast.makeText(HistorialActivity.this, "Registro seleccionado: " + registroSeleccionado.getFechaHora(), Toast.LENGTH_SHORT).show();
                // Aquí puedes abrir una nueva actividad para mostrar detalles adicionales
            }
        });
    }
}
