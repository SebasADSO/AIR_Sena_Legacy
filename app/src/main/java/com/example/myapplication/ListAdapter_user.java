package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter_user extends RecyclerView.Adapter<ListAdapter_user.ViewHolder> {
    // Variables inicializadas
    private List<ListElement_User> mData;
    private LayoutInflater mInflater;
    private Context context;
    final ListAdapter_user.OnItemClickListener listener;

    // Se crea un evento click para el adactador
    public interface OnItemClickListener {
        void onItemClick(ListElement_User item);
    }

    // Se establece Los paramentros necesarios para el adaptador
    public ListAdapter_user(List<ListElement_User> itemList, Context context, ListAdapter_user.OnItemClickListener listener) {
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
    @NonNull
    @Override
    // Se enlaza con una con una vista xml
    public ListAdapter_user.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.user_maneger, parent, false);
        return new ViewHolder(view);
    }
    @Override
    // vincula los datos con la vista
    public void onBindViewHolder(final ListAdapter_user.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));
    }

    public void setItems(List<ListElement_User> items) {
        mData = items;
    }
    // Se crea un metodo que se extiende hacia a los elementos RecycleView y se asignan los elementos en la vista
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView name, city, status;
        ViewHolder(View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.imageView999);
            name = itemView.findViewById(R.id.coduser_txt);
            city = itemView.findViewById(R.id.nombreuser_txt);
            status = itemView.findViewById(R.id.estadouser_txt);
        }
        void bindData(final ListElement_User item) {
            // Se viculan de acuerdo a los elementos del ListElement
            name.setText(item.getCod_usuario());
            city.setText(item.getCedula_usuario());
            if (item.getEstado().equals("ESTADO: INACTIVO")) {
                status.setTextColor(Color.parseColor("#ff0000"));
                status.setText(item.getEstado());
            }
            else if (item.getEstado().equals("ESTADO: ACTIVO")) {
                status.setTextColor(Color.parseColor("#35cf06"));
                status.setText(item.getEstado());
            }
            else {
                Log.d("Color test:", "No se recibio el color: "+item.getEstado());
                status.setText(item.getEstado());
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
