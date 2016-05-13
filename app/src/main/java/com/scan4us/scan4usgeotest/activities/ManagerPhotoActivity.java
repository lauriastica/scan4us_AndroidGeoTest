package com.scan4us.scan4usgeotest.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.scan4us.scan4usgeotest.R;

public class ManagerPhotoActivity extends AppCompatActivity {


   final int REQUEST_CAMERA=112;
   final int TAKE_PIC =1;
    Button btonManagerPhoto;
    Bitmap imageBitmap=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_phone);
        btonManagerPhoto=(Button) findViewById(R.id.bton_take_repeat_photo);


    }


    /**
    *Este metodo es el encargado de chequear los permisos que se le piden al usuario para que la
     * aplicación funcione al 100%
     *
     * @param codeRequest este el codigo con el que he pedido el permiso a android
     * @param namePermission este es el nombre del permiso que declare en le manifest para dicha concesión
     */
    public boolean checkpermission(int codeRequest,String namePermission){
        //esta variable me dira si el usuario me ha concedido permiso a la camara
        int permissionCheck = ContextCompat.checkSelfPermission(this,namePermission);
        //valido si el usuario me dio permiso a la camara
        if(permissionCheck<0){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    codeRequest);
            return false;
        }

        return true;
    }


    /**
     * metodo que implementa los clicks que se le hagan a las vistas del contexto actual
     * */
    public void clickBton(View view){

        switch (view.getId()) {

            case R.id.bton_take_repeat_photo:
                //chequeo los permisos que le pedi al usuario

               if(checkpermission(REQUEST_CAMERA,Manifest.permission.CAMERA)){
                  /*cuando el usuario le da click al bton <Tomar Foto> se le saca la interfaz de android
                 *para tomar la foto
                */
                   startActivityForResult( new Intent(MediaStore.ACTION_IMAGE_CAPTURE),TAKE_PIC);
               }

                break;

            case R.id.bton_save_photo:
                 if(imageBitmap!=null){
                     finish();
                     Toast.makeText(getApplicationContext(),getString(R.string.message_saved),
                             Toast.LENGTH_LONG).show();
                 }else{
                     Toast.makeText(getApplicationContext(),getString(R.string.message_not_saved_photo),
                             Toast.LENGTH_LONG).show();
                 }
                break;
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),"Gracias por conceder permiso!..Ahora " +
                            "estas listo para tomarte la foto",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"La aplicación no tiene acceso a la camara" +
                                    ",por favor considerar la concesión de esta autorización para que la aplicación funcione bien.",
                            Toast.LENGTH_LONG).show();
                }

            }
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data)
    {

        //valido haber si el usuario se tomo la foto o no se la tomo
        if (requestCode == TAKE_PIC && resultCode==RESULT_OK){
            //obtengo la imagen que el usuario se tomo con su camara
                    imageBitmap = (Bitmap) data.getExtras().get("data");
            //le seteo a <imagaView_Photo> la imagen que se tomo el usuario
            ((ImageView) findViewById(R.id.imageView_Photo)).setImageBitmap(imageBitmap);
            //le seteo al boton una imagen que indica que puede volver a repeter su foto que se habia tomado
            btonManagerPhoto.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_replay_photo_white_36dp
                    ,0,0);

        }

    }
}
