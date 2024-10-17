package gt.edu.umg.presion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import gt.edu.umg.presion.BaseDatos.Model.RegistroPresion;

public class RegistroListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<RegistroPresion> registros;
    private LayoutInflater inflater;

    public RegistroListAdapter(Context context, ArrayList<RegistroPresion> registros) {
        this.context = context;
        this.registros = registros;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return registros.size();
    }

    @Override
    public Object getItem(int position) {
        return registros.get(position);
    }

    @Override
    public long getItemId(int position) {
        return registros.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_registro, parent, false);
            holder = new ViewHolder();
            holder.tvFechaHora = convertView.findViewById(R.id.tvFechaHora);
            holder.tvSistolica = convertView.findViewById(R.id.tvSistolica);
            holder.tvDiastolica = convertView.findViewById(R.id.tvDiastolica);
            holder.tvClasificacion = convertView.findViewById(R.id.tvClasificacion);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Obtener el registro actual
        RegistroPresion registro = registros.get(position);

        // Establecer los valores en las vistas
        holder.tvFechaHora.setText(registro.getFechaHora());
        holder.tvSistolica.setText("Sistólica: " + registro.getSistolica() + " mmHg");
        holder.tvDiastolica.setText("Diastólica: " + registro.getDiastolica() + " mmHg");
        holder.tvClasificacion.setText("Clasificación: " + registro.getClasificacion());

        return convertView;
    }

    private static class ViewHolder {
        TextView tvFechaHora;
        TextView tvSistolica;
        TextView tvDiastolica;
        TextView tvClasificacion;
    }
}