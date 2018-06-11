package moreno.juan.kitch.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iamhabib.easy_preference.EasyPreference;

import moreno.juan.kitch.R;



public class CestaCompraFragment extends Fragment {
    private String lista_ingredientes;
    private TextView cesta_compra;
    public static String lista_compra="";
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cesta, container, false);

        cesta_compra=(TextView)view.findViewById(R.id.txt_cesta_ingredientes);





        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




        cesta_compra.setText(EasyPreference.with(view.getContext().getApplicationContext()).getString("recetas","").toString());
        cesta_compra.setText(lista_compra);


    }




}
