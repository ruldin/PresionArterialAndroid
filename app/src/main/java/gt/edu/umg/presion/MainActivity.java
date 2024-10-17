package gt.edu.umg.presion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button btnIngresarRegistro;
    private Button btnVerHistorial;
    private Button btnExportarDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar los botones
        btnIngresarRegistro = findViewById(R.id.btnIngresarRegistro);
        btnVerHistorial = findViewById(R.id.btnVerHistorial);
        btnExportarDatos = findViewById(R.id.btnExportarDatos);

        // Configurar los listeners para los botones
        btnIngresarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, IngresarRegistroActivity.class);
                startActivity(intent);
            }
        });

        btnVerHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HistorialActivity.class);
                startActivity(intent);
            }
        });

        btnExportarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ExportarActivity.class);
                startActivity(intent);
            }
        });
    }
}