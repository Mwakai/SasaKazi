package com.example.sasakazi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddUser extends AppCompatActivity {

    EditText editTextName, editTextEmail;
    Button button;
    String name, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        editTextName = findViewById(R.id.name);
        editTextEmail = findViewById(R.id.email);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = editTextName.getText().toString();
                email = editTextEmail.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    editTextName.setError("Please Enter a Name");
                }else if (TextUtils.isEmpty(email)) {
                    editTextEmail.setError("Please Enter an Email");
                }else {
                    addData(name, email);
                }
            }
        });
    }

    private void addData(String name, String email) {
        String url ="http://192.168.100.140:84/Sasakazi/addUser.php";

        RequestQueue queue = Volley.newRequestQueue(AddUser.this);

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    Log.e("TAG", "RESPONSE IS" + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Toast.makeText(AddUser.this, jsonObject.toString(Integer.parseInt("message")), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    editTextName.setText("");
                    editTextEmail.setText("");
                }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddUser.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);

                return params;
            }
        };

        queue.add(request);
    }
}