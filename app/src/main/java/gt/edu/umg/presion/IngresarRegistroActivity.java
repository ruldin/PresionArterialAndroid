package gt.edu.umg.presion;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import gt.edu.umg.presion.BaseDatos.Helper.DatabaseHelper;
import gt.edu.umg.presion.BaseDatos.Model.RegistroPresion;

public class IngresarRegistroActivity extends AppCompatActivity {

    private EditText etSistolica;
    private EditText etDiastolica;
    private Button btnGuardarRegistro;
    private Button btnCancelar;

    private DatabaseHelper databaseHelper; // Instancia de DatabaseHelper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_registro);

        // Enlazar vistas
        etSistolica = findViewById(R.id.etSistolica);
        etDiastolica = findViewById(R.id.etDiastolica);
        btnGuardarRegistro = findViewById(R.id.btnGuardarRegistro);
        btnCancelar = findViewById(R.id.btnCancelar);

        // Inicializar DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Configurar listeners
        btnGuardarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarRegistro();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Regresa a la actividad anterior
            }
        });
    }

    private void guardarRegistro() {
        // Validar entradas
        String sistolicaStr = etSistolica.getText().toString().trim();
        String diastolicaStr = etDiastolica.getText().toString().trim();

        if (sistolicaStr.isEmpty() || diastolicaStr.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese todos los valores.", Toast.LENGTH_SHORT).show();
            return;
        }

        int sistolica = Integer.parseInt(sistolicaStr);
        int diastolica = Integer.parseInt(diastolicaStr);

        // Obtener fecha y hora actuales
        String fechaHora = obtenerFechaHoraActual();

        // Clasificar la presión arterial
        String clasificacion = clasificarPresion(sistolica, diastolica);

        // Crear un objeto RegistroPresion
        RegistroPresion registro = new RegistroPresion(sistolica, diastolica, fechaHora, clasificacion);

        // Insertar el registro en la base de datos
        long id = databaseHelper.insertarRegistro(registro);

        if (id > 0) {
            Toast.makeText(this, "Registro guardado Calificación:"+clasificacion, Toast.LENGTH_SHORT).show();
            // Limpiar campos
            etSistolica.setText("");
            etDiastolica.setText("");
            // Opcional: Regresar a la actividad anterior
            finish();
        } else {
            Toast.makeText(this, "Error al guardar el registro.", Toast.LENGTH_SHORT).show();
        }
    }

    private String obtenerFechaHoraActual() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    private String clasificarPresion(int sistolica, int diastolica) {
        if (sistolica < 90 || diastolica < 60) {
            return "Baja";
        } else if (sistolica <= 120 && diastolica <= 80) {
            return "Normal";
        } else {
            return "Alta";
        }
    }
}