package moreno.juan.kitch;

import android.service.quicksettings.Tile;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import moreno.juan.kitch.controlador.Utils;
import moreno.juan.kitch.fragments.ImportFragment;
import moreno.juan.kitch.modelo.Receta;
import moreno.juan.kitch.modelo.Usuario;

public class MenuActivity extends AppCompatActivity {

    public static ArrayList<Receta>recetas=new ArrayList<Receta>();
    ListView lv_recetas;
    Firebase myFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);






    }

    public void saveRet(){
        // Mapeamos el usuario a nuestra clase



        // GUARDAR EN BDD
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("recetas");



        DatabaseReference usersRef = ref.child("recetas");

        HashMap<Long,Receta> users=new HashMap<>();

        //users.put(System.currentTimeMillis(),nuevo);



        ref.push();

    }
}
