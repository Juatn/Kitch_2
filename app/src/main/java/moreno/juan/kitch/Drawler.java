package moreno.juan.kitch;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import moreno.juan.kitch.fragments.GalleryFragment;
import moreno.juan.kitch.fragments.ImportFragment;
import moreno.juan.kitch.fragments.SlideshowFragment;
import moreno.juan.kitch.fragments.Tab_RecetasFragment;
import moreno.juan.kitch.fragments.ToolsFragment;

public class Drawler extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private TextView nameuser;
    private TextView emailuser;
    private FirebaseAuth mAuth;
    private ImageView userImg;
    private String profileImageUrl;
    private Uri uriImg;
    FirebaseUser user;
    public static boolean centinela;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_drawler);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),CrearReceta.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


////////////////



        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.contenedor, new Tab_RecetasFragment()).commit();
        user = FirebaseAuth.getInstance().getCurrentUser();

        Toast.makeText(this,"Sesión iniciada como "+ user.getDisplayName().toString(),Toast.LENGTH_LONG).show();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawler, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_camera) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new ImportFragment()).commit();
        } else if (id == R.id.nav_gallery) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new GalleryFragment()).commit();

        } else if (id == R.id.nav_slideshow) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new SlideshowFragment()).commit();
        } else if (id == R.id.nav_manage) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new ToolsFragment()).commit();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nameuser = (TextView) findViewById(R.id.txt_nombre_ususario);
        emailuser = (TextView) findViewById(R.id.txt_email_usuario);
        userImg = (ImageView)findViewById(R.id.imagen_perfil);
        cargaUsuario();

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void cargaUsuario() {



        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName().toString();
            String email = user.getEmail().toString();
            Uri photoUrl = user.getPhotoUrl();


            Picasso.get().load(photoUrl).into(userImg);

            nameuser.setText(name);
            emailuser.setText(email);


        }


    }

    public static Drawable getDrawable(Context mContext, String name) {
        int resourceId = mContext.getResources().getIdentifier(name, "Kitch", mContext.getPackageName());
        return mContext.getResources().getDrawable(resourceId);
    }



}
