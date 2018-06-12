package moreno.juan.kitch.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import moreno.juan.kitch.Drawler;
import moreno.juan.kitch.ProfileActivity;
import moreno.juan.kitch.R;
import moreno.juan.kitch.controlador.Utils;

import static android.app.Activity.RESULT_OK;


public class MiPerfilFragment extends Fragment {
    public static final int CHOOSE_IMAGE = 101;
    private EditText text_nombre_usuario;
    private EditText text_email_usuario;
    private EditText password;
    private ImageView img_usuario;
    private Button btn_guardar_ajustes;
    private Uri uriProfileImage;
    private String profileImageUrl;
    FirebaseUser user;
     View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view=inflater.inflate(R.layout.fragment_tools, container, false);



        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        text_email_usuario =(EditText) view.findViewById(R.id.editText_email_usuario_ajustes);
        text_nombre_usuario = (EditText) view.findViewById(R.id.editText_nombre_usuario_ajustes);
        password =(EditText) view.findViewById(R.id.editText_password_ususario_ajustes);
        img_usuario =(ImageView) view.findViewById(R.id.imagen_perfil_ajustes);
        btn_guardar_ajustes =(Button)view.findViewById(R.id.btn_guardar_ajustes);
        user = FirebaseAuth.getInstance().getCurrentUser();
        cargarVistaAjustes();


        img_usuario.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });

        btn_guardar_ajustes.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null && profileImageUrl != null)  {

                    String nombre= text_nombre_usuario.getText().toString();
                    UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                            .setDisplayName(nombre)
                            .setPhotoUri(Uri.parse(profileImageUrl)).build();



                    user.updateProfile(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Utils.mostrarMensaje("Perfil actualizado",view.getContext().getApplicationContext());
                            Intent intent = new Intent(view.getContext().getApplicationContext(), Drawler.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    })
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        Utils.mostrarMensaje("Perfil actualizado",view.getContext().getApplicationContext());
                                        Intent intent = new Intent(view.getContext().getApplicationContext(), Drawler.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);









                                    }
                                }
                            });
                }

            }
        });
    }

    public void cargarVistaAjustes() {
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName().toString();
            String email = user.getEmail().toString();
            Uri photoUrl = user.getPhotoUrl();



            Picasso.get().load(photoUrl).into(img_usuario);
            text_nombre_usuario.setText(name);
            text_email_usuario.setText(email);







        }


    }
    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriProfileImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(view.getContext().getContentResolver(), uriProfileImage);
                img_usuario.setImageBitmap(bitmap);

                uploadImageToFirebaseStorage();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void uploadImageToFirebaseStorage() {
        StorageReference profileImageRef =
                FirebaseStorage.getInstance().getReference("profilepics/" + System.currentTimeMillis() + ".jpg");

        if (uriProfileImage != null) {

            profileImageRef.putFile(uriProfileImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            profileImageUrl = taskSnapshot.getDownloadUrl().toString();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void showImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecciona una foto de perfil"), CHOOSE_IMAGE);
    }


}
