package moreno.juan.kitch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import moreno.juan.kitch.controlador.RecyclerViewAdaptor;
import moreno.juan.kitch.controlador.RecyclerViewComentarios;
import moreno.juan.kitch.controlador.Utils;
import moreno.juan.kitch.modelo.Comentario;
import moreno.juan.kitch.modelo.Receta;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;
import static moreno.juan.kitch.controlador.Utils.recetas;

public class VisualizarReceta extends AppCompatActivity implements View.OnClickListener{

    private RatingBar puntuacion_receta; // visualizada
    private TextView nombre_receta;
    private ImageView foto_receta_vista;
    private TextView nombre_autor_receta;
    private TextView elaboracion_receta;
    private TextView ingredientes_receta;
    private TextView categoria_receta;
    private EditText escribir_nuevo_comentario;
    public static List<Comentario> listComentarios=new ArrayList<Comentario>();
    public static Receta receta_visualizada;
    private RatingBar puntuacion_receta_comentario;
    private Button btn_enviar_comentario;
    private FirebaseAuth auth;
    LinearLayoutManager mLayoutManager;
    private RecyclerView recycler_comentarios;
    private RecyclerViewComentarios adaptador_comentarios;
    Task<DocumentReference>messageRef;
    FirebaseFirestore db;

    WriteBatch batch;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_receta);

        //
        puntuacion_receta=(RatingBar)findViewById(R.id.rating_receta_vista);
        categoria_receta=(TextView)findViewById(R.id.txt_categoria_receta_vista);
        nombre_receta=(TextView) findViewById(R.id.txt_nombre_receta_vista);
        nombre_autor_receta=(TextView)findViewById(R.id.txt_nombre_autor_receta_vista);
        elaboracion_receta=(TextView) findViewById(R.id.txt_elaboracion_receta_vista);
        ingredientes_receta=(TextView)findViewById(R.id.txt_ingredientes_receta_vista);
        escribir_nuevo_comentario=(EditText)findViewById(R.id.txt_nuevo_comentario);
        foto_receta_vista =(ImageView)findViewById(R.id.imagen_foto_receta_vista);
        puntuacion_receta_comentario=(RatingBar)findViewById(R.id.rating_puntuacion_nuevareceta);
        btn_enviar_comentario=(Button)findViewById(R.id.btn_enviar_comentario);

        db = FirebaseFirestore.getInstance();
        batch=db.batch();
        auth= FirebaseAuth.getInstance();





        if(receta_visualizada!=null) {
            cargarReceta();
        }

        btn_enviar_comentario.setOnClickListener(this);

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);


        recycler_comentarios = (RecyclerView) findViewById(R.id.recicler_comentarios);
        recycler_comentarios.setItemAnimator(new DefaultItemAnimator());
        recycler_comentarios.setLayoutManager(mLayoutManager);

        adaptador_comentarios = new RecyclerViewComentarios(listComentarios);
        recycler_comentarios.setAdapter(adaptador_comentarios);



        //

        db.collection(Utils.FIREBASE_BDD_RECETAS).document(receta_visualizada.getId().toString()).collection(Utils.FIREBASE_BDD_COMENTARIOS)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            listComentarios.removeAll(listComentarios);
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                // convert document to POJO

                                Comentario comentario = document.toObject(Comentario.class);

                                listComentarios.add(comentario);



                            }

                        } else {
                            Log.w(TAG, "Error al recoger datos.", task.getException());
                        }
                    }
                });



    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_enviar_comentario:
                enviarComentario();


        }
    }





    public void enviarComentario(){


        if(escribir_nuevo_comentario.getText().toString().isEmpty()){
            escribir_nuevo_comentario.setError("El comentario no puede estar vacio");
            escribir_nuevo_comentario.requestFocus();
            return;
        }
        else {
            Comentario mensaje = new Comentario();

            mensaje.setNombre_usuario(auth.getCurrentUser().getDisplayName());
            mensaje.setF_nota_receta(puntuacion_receta_comentario.getRating());
            mensaje.setS_mensaje(escribir_nuevo_comentario.getText().toString());

            // Get a new write batch

               messageRef = db
                    .collection(Utils.FIREBASE_BDD_RECETAS).document(receta_visualizada.getId().toString()).collection(Utils.FIREBASE_BDD_COMENTARIOS).add(mensaje);


        }

    }


    public void cargarReceta(){


            // puntuacion de la receta
        if(receta_visualizada.getPuntuacion()!=0)
            puntuacion_receta.setRating(receta_visualizada.getPuntuacion());
            //nombre de la receta
            nombre_receta.setText(receta_visualizada.getS_nombre().toString());
            // nombre autor de la receta
            nombre_autor_receta.setText(receta_visualizada.getCreador_receta().toString());
            // la elaboracion
            elaboracion_receta.setText(receta_visualizada.getS_elaboracion().toString());
            // ingredientes
            ingredientes_receta.setText(receta_visualizada.getS_ingredientes().toString());
            categoria_receta.setText("Categoria => "+receta_visualizada.getS_categoria().toString());
            // ponemos la foto
           Picasso.get().load(receta_visualizada.getImg()).into(foto_receta_vista);




    }
}
