package moreno.juan.kitch.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import moreno.juan.kitch.R;
import moreno.juan.kitch.VisualizarReceta;
import moreno.juan.kitch.controlador.RecyclerViewAdaptor;
import moreno.juan.kitch.controlador.RecyclerViewComentarios;
import moreno.juan.kitch.controlador.Utils;

public class Tab_CategoriasFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Utils.organizarCategorias(Tab_RecetasFragment.recetas);




        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categorias, container, false);





    }


}
