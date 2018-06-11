package moreno.juan.kitch.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import moreno.juan.kitch.R;
import moreno.juan.kitch.VisualizarReceta;
import moreno.juan.kitch.controlador.RecyclerViewAdaptor;
import moreno.juan.kitch.controlador.RecyclerViewCategorias;
import moreno.juan.kitch.controlador.Utils;
import moreno.juan.kitch.modelo.Categoria;
import moreno.juan.kitch.modelo.Receta;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class Tab_CategoriasFragment extends Fragment {

    private View rootView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView recyclerViewCategorias;
    private RecyclerViewCategorias adaptador_categorias;
    private List<Categoria>categorias;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        rootView= inflater.inflate(R.layout.fragment_categorias, container, false);
        categorias=new ArrayList<Categoria>();





        // Inflate the layout for this fragment
        return rootView;





    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLayoutManager = new GridLayoutManager(rootView.getContext().getApplicationContext(),2);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categorias=new ArrayList<>();

        cargarCategorias();

        recyclerViewCategorias = (RecyclerView) view.findViewById(R.id.recicler_categorias);
        recyclerViewCategorias.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCategorias.setLayoutManager(mLayoutManager);

        adaptador_categorias = new RecyclerViewCategorias(categorias);
        recyclerViewCategorias.setAdapter(adaptador_categorias);

        final GestureDetector mGestureDetector = new GestureDetector(rootView.getContext().getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        recyclerViewCategorias.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }

            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                try {
                    View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                    if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {



                        return true;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }
        });



    }
    public void cargarCategorias(){

        Categoria bebida=new Categoria(R.drawable.bebidas,"Bebidas");
        Categoria carnes=new Categoria(R.drawable.carnes,"Carnes");
        Categoria ensaladas=new Categoria(R.drawable.ensalada,"Ensaladas");
        Categoria marisco=new Categoria(R.drawable.pescado_marisco,"Pescado y marisco");
        Categoria postres=new Categoria(R.drawable.postres,"Postres");
        Categoria arroces_y_pasta=new Categoria(R.drawable.arroces,"Arroces y Pasta");


        categorias.add(carnes);
        categorias.add(ensaladas);
        categorias.add(marisco);
        categorias.add(arroces_y_pasta);
        categorias.add(postres);
        categorias.add(bebida);


    }
    }

