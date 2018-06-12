package moreno.juan.kitch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.iamhabib.easy_preference.EasyPreference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import moreno.juan.kitch.controlador.RecyclerViewComentarios;
import moreno.juan.kitch.controlador.Utils;
import moreno.juan.kitch.fragments.CestaCompraFragment;
import moreno.juan.kitch.modelo.Comentario;
import moreno.juan.kitch.modelo.Receta;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class VisualizarReceta extends AppCompatActivity implements View.OnClickListener {

    private RatingBar puntuacion_receta; // visualizada
    private TextView nombre_receta;
    private ImageView foto_receta_vista;
    private TextView nombre_autor_receta;
    private TextView elaboracion_receta;
    private TextView ingredientes_receta;
    private TextView categoria_receta;
    private EditText escribir_nuevo_comentario;
    public static List<Comentario> listComentarios;
    public static Receta receta_visualizada;
    private RatingBar puntuacion_receta_comentario;
    private Button btn_enviar_comentario;
    private FirebaseAuth auth;
    LinearLayoutManager mLayoutManager;
    private RecyclerView recycler_comentarios;
    private RecyclerViewComentarios adaptador_comentarios;
    Task<Void> messageRef;
    private DocumentReference mDatabaseRef;
    FirebaseFirestore db;

    private Button btn_cesta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_receta);

        //
        puntuacion_receta = (RatingBar) findViewById(R.id.rating_receta_vista);
        puntuacion_receta.setEnabled(false);
        categoria_receta = (TextView) findViewById(R.id.txt_categoria_receta_vista);
        nombre_receta = (TextView) findViewById(R.id.txt_nombre_receta_vista);
        nombre_autor_receta = (TextView) findViewById(R.id.txt_nombre_autor_receta_vista);
        elaboracion_receta = (TextView) findViewById(R.id.txt_elaboracion_receta_vista);
        ingredientes_receta = (TextView) findViewById(R.id.txt_ingredientes_receta_vista);
        escribir_nuevo_comentario = (EditText) findViewById(R.id.txt_nuevo_comentario);
        foto_receta_vista = (ImageView) findViewById(R.id.imagen_foto_receta_vista);
        puntuacion_receta_comentario = (RatingBar) findViewById(R.id.rating_puntuacion_nuevareceta);
        btn_enviar_comentario = (Button) findViewById(R.id.btn_enviar_comentario);

        btn_cesta = (Button) findViewById(R.id.btn_cesta);


        db = FirebaseFirestore.getInstance();

        auth = FirebaseAuth.getInstance();
        if (receta_visualizada != null) {
            db.collection(Utils.FIREBASE_BDD_RECETAS).document(receta_visualizada.getId().toString()).collection(Utils.FIREBASE_BDD_COMENTARIOS)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                //listComentarios.removeAll(listComentarios);

                                for (DocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());

                                    // convert document to POJO


                                    Comentario comentario = document.toObject(Comentario.class);


                                    listComentarios.add(comentario);

                                    float nota = 0f;
                                    for (Comentario c : listComentarios) {

                                        nota += c.getF_nota_receta();
                                    }
                                    float notafinal = nota / listComentarios.size();
                                    receta_visualizada.setPuntuacion(notafinal);
                                    db.collection(Utils.FIREBASE_BDD_RECETAS).document(receta_visualizada.getId()).update("puntuacion", notafinal).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            mostrarMensaje("exito", "vvv", "ok");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });
                                    puntuacion_receta.setRating(notafinal);


                                }

                            } else {
                                Log.w(TAG, "Error al recoger datos.", task.getException());
                            }
                        }
                    });

        }


        if (receta_visualizada != null) {
            cargarReceta();
        }
        listComentarios = new ArrayList<Comentario>();


        btn_enviar_comentario.setOnClickListener(this);
        btn_cesta.setOnClickListener(this);


        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);


        recycler_comentarios = (RecyclerView) findViewById(R.id.recicler_comentarios);
        recycler_comentarios.setItemAnimator(new DefaultItemAnimator());
        recycler_comentarios.setLayoutManager(mLayoutManager);

        adaptador_comentarios = new RecyclerViewComentarios(listComentarios);
        recycler_comentarios.setAdapter(adaptador_comentarios);


        //


        foto_receta_vista.requestFocus();


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_enviar_comentario:
                enviarComentario();
                break;
            case R.id.btn_cesta:
                cargarCesta();
                break;


        }
    }


    public void cargarCesta() {


        EasyPreference.with(this).addString("cesta", receta_visualizada.getS_ingredientes());


        CestaCompraFragment.lista_compra += receta_visualizada.getS_ingredientes().toString();


        mostrarMensaje("Ingredientes añadidos a la cesta", "Su lista de ingredientes se ha actualizado", "vale").show();


    }

    public void enviarComentario() {


        if (escribir_nuevo_comentario.getText().toString().isEmpty()) {
            escribir_nuevo_comentario.setError("El comentario no puede estar vacio");
            escribir_nuevo_comentario.requestFocus();
            return;
        } else {
            Comentario mensaje = new Comentario();


            mensaje.setNombre_usuario(auth.getCurrentUser().getDisplayName());
            mensaje.setF_nota_receta(puntuacion_receta_comentario.getRating());
            mensaje.setS_mensaje(escribir_nuevo_comentario.getText().toString());
            DatabaseReference f = FirebaseDatabase.getInstance().getReference("recetas");

            Map<String, Object> updateMap = new HashMap();
            updateMap.put("comentarios", mensaje);


            // Get a new write batch
            FirebaseFirestore.getInstance().collection(Utils.FIREBASE_BDD_RECETAS)
                    .document(receta_visualizada.getId().toString()).collection(Utils.FIREBASE_BDD_COMENTARIOS).add(mensaje).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    mostrarMensaje("Enviado", "Comentario publicado", "vale").show();
                    escribir_nuevo_comentario.setText("");
                    puntuacion_receta_comentario.setRating(0f);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        }

    }

    public void crearToast(String mensaje) {

        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
    }


    public void cargarReceta() {

        // puntuacion de la receta
        if (receta_visualizada.getPuntuacion() != 0) {
            puntuacion_receta.setRating(receta_visualizada.getPuntuacion());
        }
        //nombre de la receta
        nombre_receta.setText(receta_visualizada.getS_nombre().toString());
        // nombre autor de la receta
        nombre_autor_receta.setText(receta_visualizada.getCreador_receta().toString());
        // la elaboracion
        elaboracion_receta.setText(receta_visualizada.getS_elaboracion().toString());
        // ingredientes
        ingredientes_receta.setText(receta_visualizada.getS_ingredientes().toString());
        categoria_receta.setText("Categoria => " + receta_visualizada.getS_categoria().toString());
        // ponemos la foto
        Picasso.get().load(receta_visualizada.getImg()).into(foto_receta_vista);

    }


    public SweetAlertDialog mostrarMensaje(String titulo, String mensaje, String confirmtext) {

        SweetAlertDialog nuevo = new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText(titulo)
                .setContentText(mensaje)
                .setConfirmText(confirmtext);


        return nuevo;

    }


    public SweetAlertDialog warningMensaje(String titulo, String contexto, String texto_confirmacion) {

        SweetAlertDialog nuevo = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(titulo)
                .setContentText(contexto)
                .setConfirmText(texto_confirmacion);

        return nuevo;
    }
}
