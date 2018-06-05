package moreno.juan.kitch.controlador;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.List;

import moreno.juan.kitch.R;
import moreno.juan.kitch.modelo.Receta;

public class RecyclerViewAdaptor extends RecyclerView.Adapter<RecyclerViewAdaptor.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView nombre_receta;
        private TextView nombre_creador;
        ImageView foto_receta;
        RatingBar puntos_receta;

        public ViewHolder(View itemView){
            super(itemView);

            nombre_receta=(TextView)itemView.findViewById(R.id.tvNombreReceta);
            foto_receta=(ImageView) itemView.findViewById(R.id.imgReceta);
            puntos_receta=(RatingBar)itemView.findViewById(R.id.ratingReceta);
            nombre_creador=(TextView)itemView.findViewById(R.id.txtnombre_creador_receta);


        }



    }
    public static List<Receta> recetario;
    public RecyclerViewAdaptor(List<Receta> recetario){

        this.recetario=recetario;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receta,parent,false);
        ViewHolder holder=new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       Receta nueva = (Receta) recetario.get(position);
       float nota=0f;





        holder.nombre_receta.setText(nueva.getS_nombre());
        // foto
        Picasso.get().load(nueva.getImg().toString()).into(holder.foto_receta);
        holder.puntos_receta.setRating(nueva.getPuntuacion());
        holder.nombre_creador.setText("Autor: "+nueva.getCreador_receta());



    }

    @Override
    public int getItemCount() {
        return recetario.size();
    }

}

