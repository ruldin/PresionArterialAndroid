package gt.edu.umg.presion.BaseDatos.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import gt.edu.umg.presion.BaseDatos.Model.RegistroPresion;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Constantes de la base de datos
    private static final String DATABASE_NAME = "presion_arterial.db";
    private static final int DATABASE_VERSION = 1;

    // Nombre de la tabla
    private static final String TABLE_REGISTROS = "registros";

    // Columnas de la tabla
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_SISTOLICA = "sistolica";
    private static final String COLUMN_DIASTOLICA = "diastolica";
    private static final String COLUMN_FECHA_HORA = "fecha_hora";
    private static final String COLUMN_CLASIFICACION = "clasificacion";

    // Sentencia SQL para crear la tabla
    private static final String CREATE_TABLE_REGISTROS = "CREATE TABLE " + TABLE_REGISTROS + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_SISTOLICA + " INTEGER, " +
            COLUMN_DIASTOLICA + " INTEGER, " +
            COLUMN_FECHA_HORA + " TEXT, " +
            COLUMN_CLASIFICACION + " TEXT" +
            ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_REGISTROS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Si actualizas la versión de la base de datos, puedes manejar los cambios aquí
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTROS);
        onCreate(db);
    }

    // Método para insertar un nuevo registro
    public long insertarRegistro(RegistroPresion registro) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SISTOLICA, registro.getSistolica());
        values.put(COLUMN_DIASTOLICA, registro.getDiastolica());
        values.put(COLUMN_FECHA_HORA, registro.getFechaHora());
        values.put(COLUMN_CLASIFICACION, registro.getClasificacion());

        long id = db.insert(TABLE_REGISTROS, null, values);
        db.close();
        return id;
    }

    // Método para obtener todos los registros
    public ArrayList<RegistroPresion> obtenerTodosLosRegistros() {
        ArrayList<RegistroPresion> listaRegistros = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_REGISTROS + " ORDER BY " + COLUMN_FECHA_HORA + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                RegistroPresion registro = new RegistroPresion();
                registro.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                registro.setSistolica(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SISTOLICA)));
                registro.setDiastolica(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DIASTOLICA)));
                registro.setFechaHora(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FECHA_HORA)));
                registro.setClasificacion(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLASIFICACION)));

                listaRegistros.add(registro);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listaRegistros;
    }

    // Método para eliminar un registro por ID
    public void eliminarRegistro(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REGISTROS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Método para actualizar un registro existente
    public int actualizarRegistro(RegistroPresion registro) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SISTOLICA, registro.getSistolica());
        values.put(COLUMN_DIASTOLICA, registro.getDiastolica());
        values.put(COLUMN_FECHA_HORA, registro.getFechaHora());
        values.put(COLUMN_CLASIFICACION, registro.getClasificacion());

        // Actualizar fila
        int rowsAffected = db.update(TABLE_REGISTROS, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(registro.getId())});
        db.close();
        return rowsAffected;
    }

    // Método para obtener un registro por ID
    public RegistroPresion obtenerRegistroPorId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_REGISTROS, null, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        RegistroPresion registro = new RegistroPresion();
        registro.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
        registro.setSistolica(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SISTOLICA)));
        registro.setDiastolica(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DIASTOLICA)));
        registro.setFechaHora(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FECHA_HORA)));
        registro.setClasificacion(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLASIFICACION)));

        cursor.close();
        db.close();
        return registro;
    }
}
