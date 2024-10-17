package gt.edu.umg.presion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import gt.edu.umg.presion.BaseDatos.Model.RegistroPresion;

public class RegistroAdapter extends RecyclerView.Adapter<RegistroAdapter.ViewHolder>  {

    private ArrayList<RegistroPresion> listaRegistros;

    public RegistroAdapter(ArrayList<RegistroPresion> listaRegistros) {
        this.listaRegistros = listaRegistros;
    }

    @NonNull
    @Override
    public RegistroAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_registro, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RegistroAdapter.ViewHolder holder, int position) {
        RegistroPresion registro = listaRegistros.get(position);

        holder.tvFechaHora.setText(registro.getFechaHora());
        holder.tvSistolica.setText("Sistólica: " + registro.getSistolica() + " mmHg");
        holder.tvDiastolica.setText("Diastólica: " + registro.getDiastolica() + " mmHg");
        holder.tvClasificacion.setText("Clasificación: " + registro.getClasificacion());
    }

    @Override
    public int getItemCount() {
        return listaRegistros.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvFechaHora;
        public TextView tvSistolica;
        public TextView tvDiastolica;
        public TextView tvClasificacion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFechaHora = itemView.findViewById(R.id.tvFechaHora);
            tvSistolica = itemView.findViewById(R.id.tvSistolica);
            tvDiastolica = itemView.findViewById(R.id.tvDiastolica);
            tvClasificacion = itemView.findViewById(R.id.tvClasificacion);

            // Manejar clic en el ítem (opcional)
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Puedes manejar el clic para mostrar detalles adicionales
                    Toast.makeText(view.getContext(), "Registro seleccionado", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}


