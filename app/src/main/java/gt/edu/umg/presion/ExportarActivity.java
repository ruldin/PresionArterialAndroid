package gt.edu.umg.presion;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import gt.edu.umg.presion.BaseDatos.Helper.DatabaseHelper;
import gt.edu.umg.presion.BaseDatos.Model.RegistroPresion;


public class ExportarActivity extends AppCompatActivity {

    private Button btnExportarCSV;
    private DatabaseHelper databaseHelper;
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exportar);

        btnExportarCSV = findViewById(R.id.btnExportarCSV);
        databaseHelper = new DatabaseHelper(this);

        btnExportarCSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificarPermisosYExportar();
            }
        });
    }

    private void verificarPermisosYExportar() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        } else {
            exportarDatosCSV();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                exportarDatosCSV();
            } else {
                Toast.makeText(this, "Permiso denegado para escribir en el almacenamiento externo", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void exportarDatosCSV() {
        ArrayList<RegistroPresion> listaRegistros = databaseHelper.obtenerTodosLosRegistros();

        if (listaRegistros.isEmpty()) {
            Toast.makeText(this, "No hay registros para exportar.", Toast.LENGTH_SHORT).show();
            return;
        }

        File folder = new File(Environment.getExternalStorageDirectory(), "ExportarPresionArterial");
        if (!folder.exists()) {
            boolean success = folder.mkdirs();
            if (!success) {
                Toast.makeText(this, "No se pudo crear el directorio.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        File file = new File(folder, "registros_presion.csv");

        try {
            FileWriter fw = new FileWriter(file);
            fw.append("ID,Sistólica,Diastólica,Fecha y Hora,Clasificación\n");
            for (RegistroPresion registro : listaRegistros) {
                fw.append(String.valueOf(registro.getId())).append(",");
                fw.append(String.valueOf(registro.getSistolica())).append(",");
                fw.append(String.valueOf(registro.getDiastolica())).append(",");
                fw.append(registro.getFechaHora()).append(",");
                fw.append(registro.getClasificacion()).append("\n");
            }
            fw.flush();
            fw.close();

            Toast.makeText(this, "Datos exportados exitosamente en " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();

            // Opcional: Compartir el archivo
            compartirArchivoCSV(file.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al exportar los datos: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    private void compartirArchivoCSV(String filePath) {
        File archivoCSV = new File(filePath);
        Uri uri = Uri.fromFile(archivoCSV);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/csv");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Registros de Presión Arterial");
        intent.putExtra(Intent.EXTRA_TEXT, "Adjunto los registros de presión arterial.");
        startActivity(Intent.createChooser(intent, "Compartir archivo CSV"));
    }
}