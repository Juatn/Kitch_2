package moreno.juan.kitch.controlador;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import moreno.juan.kitch.modelo.Receta;
import moreno.juan.kitch.modelo.Usuario;

public class Utils {

    public static ArrayList<Usuario>usuarios=new ArrayList<>();
    public static ArrayList<Receta>recetas =new ArrayList<Receta>();
    public static final String FIREBASE_BDD_RECETAS="recetas";
    public static final String FIREBASE_BDD_COMENTARIOS="comentarios";

    public static String[] categorias={"Bebidas","Carnes","Ensaladas","Mariscos",
            "Legumbres y Cereales","Guarniciones","Pan y Pizzas", "Frutas y Verduras","Sopas y Cremas"
    ,"Pasta"};

    public static  String getImageData(Bitmap bmp) {

        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, bao); // bmp is bitmap from user image file
        bmp.recycle();
        byte[] byteArray = bao.toByteArray();
        String imageB64 = Base64.encodeToString(byteArray, Base64.DEFAULT);


        return imageB64;
        //  store & retrieve this string to firebase
    }

    public static Bitmap StringToBitMap(String image){
        try{
            byte [] encodeByte=Base64.decode(image,Base64.DEFAULT);

            InputStream inputStream  = new ByteArrayInputStream(encodeByte);
            Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
}
