package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

public class menu_reportar extends AppCompatActivity {
    // Llamado de los elementos textview, edittext, button y creacion de string
    String ndoc, report_id, cod_user, fecha;
    ImageButton btt_camera;
    ImageView view;
    Bitmap bitmap;
    int PICK_IMAGE_REQUEST = 1;
    RequestQueue requestQueue;
    EditText txt_suceso, txt_descrpcion, txt_lugar;

    String ip = app_config.ip_server;
    String change = "localhost";

    Button btt_siguiente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_reportar);
        Random rand = new Random();
        int id_r = rand.nextInt(800);
        // Se llaman los elemntos del xml
        report_id = Integer.toString(id_r);
        txt_suceso = findViewById(R.id.txt_suceso);
        txt_descrpcion = findViewById(R.id.txt_descrpcion);
        txt_lugar = findViewById(R.id.txt_lugar);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:ss:mm");
        fecha = df.format(c);
        // Se obtiene un bundle con la informacion de la anterior activity
        Bundle extras = this.getIntent().getExtras();
        ndoc = extras.getString("doc");
        view = findViewById(R.id.imageView4);
        //Toast.makeText(getApplicationContext(), ndoc, Toast.LENGTH_SHORT).show();
        btt_camera = findViewById(R.id.btt_camera);
        // Se crea un evento al boton del soporte
        btt_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), fecha, Toast.LENGTH_SHORT).show();
                showFileChooser();
            }
        });
        // Evento de cierre de sesion
        ImageButton logout = findViewById(R.id.btt_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menu_home_back = new Intent(menu_reportar.this, MainActivity.class);
                startActivity(menu_home_back);
                finishAffinity();
            }
        });
        // Evento de continuar y enviar
        btt_siguiente = findViewById(R.id.btt_siguiente);
        btt_siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarid("http://localhost/AIR_Database/reportar_buscarid.php?cedula_usuario=".replace(change, ip)+ndoc+"");
                if (cod_user != null) {
                    //Toast.makeText(getApplicationContext(), cod_user, Toast.LENGTH_SHORT).show();
                    uploadImage("http://localhost/AIR_Database/reportar_upload.php".replace(change, ip));
                }
                else {
                    Toast.makeText(getApplicationContext(), "Vuelva a intentarlo", Toast.LENGTH_LONG).show();
                    buscarid("http://localhost/AIR_Database/reportar_buscarid.php?cedula_usuario=".replace(change, ip)+ndoc+"");
                }
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
    /**

     * Convierte un objeto Bitmap en una cadena codificada en Base64.

     *

     * @param bmp El objeto Bitmap de entrada que se convertirá.

     * @return Una cadena codificada en Base64 que representa el objeto Bitmap de entrada.

     */
    public String getStringImagen(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    // Codigo para manejo de estado
    final int CAPTURA_IMAGEN = 1;
    /**

     * Realiza una solicitud HTTP de tipo POST a la dirección URL proporcionada, enviando parámetros específicos y mostrando un diálogo de progreso mientras se realiza la solicitud.

     *

     * @param URL La dirección URL del servidor donde se realizará la solicitud HTTP.

     */
    public void uploadImage(String URL) {
        final ProgressDialog loading = ProgressDialog.show(this, "Subiendo...", "Espere por favor");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        Log.d("datos", cod_user + "---"+ ndoc);
                        revision("http://localhost/AIR_Database/revision_inicial.php".replace(change, ip));
                        Toast.makeText(menu_reportar.this, response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Log.d("datos", cod_user + "---"+ ndoc);
                Toast.makeText(menu_reportar.this, "Error en cargar imagen.", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String imagen = getStringImagen(bitmap);
                Map<String, String> params = new Hashtable<String, String>();
                params.put("id_reporte", report_id);
                params.put("cod_usuario_fk", cod_user);
                params.put("encabezado_reporte", txt_suceso.getText().toString());
                params.put("descripcion_reporte", txt_descrpcion.getText().toString());
                params.put("ubicacion", txt_lugar.getText().toString());
                params.put("fecha_hora_reporte", fecha);
                params.put("soporte_reporte", imagen);
                return params;
            }
        };

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    /**

     * Muestra un selector de archivos para que el usuario pueda seleccionar una imagen.

     */
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleciona imagen"), PICK_IMAGE_REQUEST);
    }
    /**

     * Maneja el resultado de la actividad lanzada para seleccionar una imagen.

     *

     * @param requestCode El código de solicitud de la actividad que se lanzó.

     * @param resultCode El código de resultado de la actividad que se lanzó.

     * @param data El objeto Intent que contiene los datos devueltos por la actividad.

     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == CAPTURA_IMAGEN && resultCode == RESULT_OK) {
                Uri filePath = data.getData();
                try {
                    //Obtener el mapa de bits de la CAMARA
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    //Configuración del mapa de bits en ImageView
                    view.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**

     * Realiza una solicitud HTTP de tipo POST a la dirección URL proporcionada, enviando parámetros específicos y mostrando un diálogo de progreso mientras se realiza la solicitud.

     *

     * @param URL La dirección URL del servidor donde se realizará la solicitud HTTP.

     */
    private void buscarid(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        cod_user = jsonObject.getString("cod_usuario");
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
    /**

     * Realiza una solicitud HTTP de tipo POST a la dirección URL proporcionada, enviando parámetros específicos y mostrando un diálogo de progreso mientras se realiza la solicitud.

     *

     * @param URL La dirección URL del servidor donde se realizará la solicitud HTTP.

     */
    private  void revision(String URL) {
        final ProgressDialog loading = ProgressDialog.show(this, "Subiendo...", "Espere por favor");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(getApplicationContext(), "Correcto", Toast.LENGTH_SHORT).show();
                loading.dismiss();
                Intent camera = new Intent(menu_reportar.this, reportar_camera.class);
                camera.putExtra("doc", ndoc);
                startActivity(camera);
                finishAffinity();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id_reporte_fk", report_id);
                parametros.put("tipo_peligro", "DESCONOCIDO");
                parametros.put("nivel_peligro", "DESCONOCIDO");
                parametros.put("fecha_revision", "0000-00-00 00:00:00");
                parametros.put("estado", "PENDIENTE");
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}