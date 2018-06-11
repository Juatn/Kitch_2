package moreno.juan.kitch.controlador;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import moreno.juan.kitch.R;
import moreno.juan.kitch.modelo.Categoria;
import moreno.juan.kitch.modelo.Comentario;
import moreno.juan.kitch.modelo.Receta;

public class RecyclerViewCategorias extends RecyclerView.Adapter<RecyclerViewCategorias.ViewMensaje> {


    public static List<Categoria> categorias;

    public RecyclerViewCategorias(List<Categoria> categorias){

        this.categorias=categorias;



    }

    @NonNull
    @Override
    public ViewMensaje onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categorias,parent,false);
        RecyclerViewCategorias.ViewMensaje holder= new ViewMensaje(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewMensaje holder, int position) {


        Categoria nuevo = categorias.get(position);

        holder.nombre_categoria.setText(nuevo.getNombre_categoria().toString());


        Picasso.get().load(nuevo.getFoto_categoria()).resize(250,250).centerCrop()
                .into(holder.foto);

    }


    @Override
    public int getItemCount() {
        return categorias.size();
    }

    public static class ViewMensaje extends RecyclerView.ViewHolder{

        private TextView nombre_categoria;
        private ImageView foto;



        public ViewMensaje(View itemView){
            super(itemView);


            nombre_categoria=(TextView)itemView.findViewById(R.id.txt_nombre_categoria);
            foto=(ImageView)itemView.findViewById(R.id.img_categoria_item);





        }



    }
}
