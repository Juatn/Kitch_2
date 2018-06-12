package moreno.juan.kitch.controlador;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import moreno.juan.kitch.modelo.Receta;
import moreno.juan.kitch.modelo.Usuario;

public class Utils {

    public static ArrayList<Usuario>usuarios=new ArrayList<>();
    public static ArrayList<Receta>recetas =new ArrayList<Receta>();
    public static final String FIREBASE_BDD_RECETAS="recetas";
    public static final String FIREBASE_BDD_COMENTARIOS="comentarios";
    public static ArrayList<Receta> bebidas=new ArrayList<>();
    public static ArrayList<Receta> carnes=new ArrayList<>();
    public static ArrayList<Receta> ensaladas=new ArrayList<>();
    public static ArrayList<Receta> mariscos=new ArrayList<>();
    public static ArrayList<Receta> legumbres_y_cereales =new ArrayList<>();
    public static ArrayList<Receta> pasta =new ArrayList<>();
    public static ArrayList<Receta> pan_y_pizzas=new ArrayList<>();
    public static ArrayList<Receta> frutas_verduras=new ArrayList<>();
    public static ArrayList<Receta> sopas_cremas=new ArrayList<>();


    public static String[] categorias={"Bebidas","Carnes","Ensaladas","Pescado y mariscos","Arroces y Pasta","Postres"
    };

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

    public static void organizarCategorias(List<Receta> recetas){


        for(Receta c:recetas){


            if(c.getS_categoria().toUpperCase().equals("BEBIDAS")){
                bebidas.add(c);
            }
            else if(c.getS_categoria().toUpperCase().equals("CARNES")){
                carnes.add(c);
            }
            else if(c.getS_categoria().toUpperCase().equals("ENSALADAS")){
                ensaladas.add(c);
            }
            else if(c.getS_categoria().toUpperCase().equals("MARISCOS")){
                mariscos.add(c);
            }
            else if(c.getS_categoria().toUpperCase().equals("LEGUMBRES Y CEREABLES")){
                legumbres_y_cereales.add(c);
            }
            else if(c.getS_categoria().toUpperCase().equals("PAN Y PIZZAS")){
                pan_y_pizzas.add(c);
            }
            else if(c.getS_categoria().toUpperCase().equals("FRUTAS Y VERDURAS")){
                frutas_verduras.add(c);
            }
            else if(c.getS_categoria().toUpperCase().equals("SOPAS Y CREMAS")){
                sopas_cremas.add(c);
            }
            else if(c.getS_categoria().toUpperCase().equals("PASTA")){
                pasta.add(c);
            }


        }


    }
    public static SweetAlertDialog mostrarMensaje(String mensaje,Context context){

        SweetAlertDialog nuevo=new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)

                .setContentText(mensaje);

        return  nuevo;

    }

}
