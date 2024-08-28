package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    // Variables inicializadas
    private List<ListElement> mData;
    private LayoutInflater mInflater;
    private Context context;
    final ListAdapter.OnItemClickListener listener;

    // Se crea un evento click para el adactador
    public interface OnItemClickListener {
        void onItemClick(ListElement item);
    }

    // Se establece Los paramentros necesarios para el adaptador
    public ListAdapter(List<ListElement> itemList, Context context, ListAdapter.OnItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.listener = listener;
    }
    @Override
    // Obtiene la longitud de los elementos del adaptador
    public int getItemCount() {
        return  mData.size();
    }
    @Override
    // Se enlaza con una con una vista xml
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_element, null);
        return  new ListAdapter.ViewHolder(view);
    }
    @Override
    // vincula los datos con la vista
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));
    }

    public  void setItems(List<ListElement> items) {
        mData = items;
    }
    // Se crea un metodo que se extiende hacia a los elementos RecycleView y se asignan los elementos en la vista
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView name, city, status, revision;
        ViewHolder(View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.imageView6);
            name = itemView.findViewById(R.id.nameTextView);
            city = itemView.findViewById(R.id.cityTextView);
            status = itemView.findViewById(R.id.statusTextView);
            revision = itemView.findViewById(R.id.RevisionTextView);
        }

        void bindData(final ListElement item) {
            // Se viculan de acuerdo a los elementos del ListElement
            name.setText(item.getId_reporte());
            city.setText(item.getEncabezado_reporte());
            status.setText(item.getFecha_hora_reporte());
            if (item.getEstado().equals("PENDIENTE")) {
                revision.setTextColor(Color.parseColor("#ff0000"));
                revision.setText(item.getEstado());
            }
            else if (item.getEstado().equals("REVISADO")) {
                revision.setTextColor(Color.parseColor("#35cf06"));
                revision.setText(item.getEstado());
            }
            else {
                revision.setText(item.getEstado());
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
