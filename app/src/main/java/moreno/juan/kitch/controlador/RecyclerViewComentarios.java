package moreno.juan.kitch.controlador;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import moreno.juan.kitch.R;
import moreno.juan.kitch.VisualizarReceta;
import moreno.juan.kitch.modelo.Comentario;
import moreno.juan.kitch.modelo.Receta;

public class RecyclerViewComentarios  extends RecyclerView.Adapter<RecyclerViewComentarios.ViewMensaje> {


    public static List<Comentario> comentarios;
    public RecyclerViewComentarios(List<Comentario> coment){

        this.comentarios=coment;

    }

    @NonNull
    @Override
    public RecyclerViewComentarios.ViewMensaje onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewMensaje holder, int position) {


            Comentario nuevo = comentarios.get(position);

            holder.autor_comentario.setText(nuevo.getNombre_usuario().toString());
            holder.mensaje.setText(nuevo.getS_mensaje().toString());
            holder.nota.setRating(nuevo.getF_nota_receta());

    }


    @Override
    public int getItemCount() {
        return comentarios.size();
    }

    public static class ViewMensaje extends RecyclerView.ViewHolder{

        private TextView autor_comentario;
        private TextView mensaje;
        private RatingBar nota;


        public ViewMensaje(View itemView){
            super(itemView);

            nota=(RatingBar)itemView.findViewById(R.id.rating_nota_comentario);
            autor_comentario=(TextView)itemView.findViewById(R.id.txt_nombre_autor_comentario);
            mensaje=(TextView)itemView.findViewById(R.id.txt_mensj_creado);




        }



    }
}