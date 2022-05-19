package com.example.webservices_jueves;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Ventas extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {


    EditText codigo_venta, fecha, identificacion_usuario, valor_venta;
    Button ingresar, consultar, eliminar, limpiar, regresar;
    RequestQueue rd;
    JsonRequest xt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventas);
        getSupportActionBar().hide();
        codigo_venta = findViewById(R.id.etcodigo_venta);
        fecha = findViewById(R.id.etfecha);
        identificacion_usuario = findViewById(R.id.etidentificacion_usuario);
        valor_venta = findViewById(R.id.etvalor_venta);
        ingresar = findViewById(R.id.btningresar);
        consultar = findViewById(R.id.btnconsultar);
        eliminar = findViewById(R.id.btneliminar);
        limpiar = findViewById(R.id.btnlimpiar);
        regresar = findViewById(R.id.btnregresar);
        rd = Volley.newRequestQueue(this);

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar_venta();
            }
        });

        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consultar_venta();
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminar_venta();
            }
        });
        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Limpiar_campos();
            }
        });

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regresar_inicio();
            }
        });

    }


    private void registrar_venta() {
        String url = "http://172.16.59.28:81/usuarios/adicionarVenta.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Limpiar_campos();
                        Toast.makeText(getApplicationContext(), "Registro de codigo realizado correctamente!", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Registro de codigo incorrecto!", Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("codigo_venta", codigo_venta.getText().toString().trim());
                params.put("fecha", fecha.getText().toString().trim());
                params.put("identificacion_usuario", identificacion_usuario.getText().toString().trim());
                params.put("valor_venta", valor_venta.getText().toString().trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
    }

    private void consultar_venta() {
        if (codigo_venta.getText().toString().isEmpty()) {
            Toast.makeText(this, "El codigo es requerido para consultar", Toast.LENGTH_SHORT).show();
            codigo_venta.requestFocus();
        } else {
            String url = "http://172.16.59.28:81/usuarios/consultarVenta.php?codigo_venta=" + codigo_venta.getText().toString();
            xt = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            rd.add(xt);
        }
    }

    private void eliminar_venta() {
        String url = "http://172.16.59.28:81/usuarios/eliminarVenta.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Limpiar_campos();
                        Toast.makeText(getApplicationContext(), "Registro de codigo venta anulado correctamente!", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error anulando codigo!", Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("codigo_venta", codigo_venta.getText().toString().trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);

        codigo_venta.setText("");
        fecha.setText("");
        identificacion_usuario.setText("");
        valor_venta.setText("");
        codigo_venta.requestFocus();
    }

    private void Limpiar_campos() {
        codigo_venta.setText("");
        fecha.setText("");
        identificacion_usuario.setText("");
        valor_venta.setText("");
        codigo_venta.requestFocus();
    }

    public void regresar_inicio() {
        Intent Regresar = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(Regresar);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "codigo no registrado", Toast.LENGTH_SHORT).show();
        codigo_venta.requestFocus();
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray jsonArray = response.optJSONArray("datos");
        JSONObject jsonObject = null;
        try {
            jsonObject = jsonArray.getJSONObject(0);//posicion 0 del arreglo....
            fecha.setText(jsonObject.optString("fecha"));
            identificacion_usuario.setText(jsonObject.optString("identificacion_usuario"));
            valor_venta.setText(jsonObject.optString("valor_venta"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
