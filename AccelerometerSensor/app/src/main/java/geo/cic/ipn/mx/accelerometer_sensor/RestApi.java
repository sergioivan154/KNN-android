/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geo.cic.ipn.mx.accelerometer_sensor;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author sergio
 */
public class RestApi {

    private static String URL = "http://pruebas.devworms.com/";


    public static String obtenerClase(Float x, Float y, Float z, Integer k){
        String clase = "";
         try {
             // TODO code application logic here

             OkHttpClient client = new OkHttpClient();

             MediaType mediaType = MediaType.parse("application/octet-stream");
             Request request = new Request.Builder()
                     .url(URL+"ObtenerClase.php?x="+Math.abs(x * 1000)+"&y="+Math.abs(y * 1000)+"&z="+Math.abs(z * 1000)+"&k="+k)
                     .get()
                     .addHeader("cache-control", "no-cache")
                     .addHeader("postman-token", "b983b2f6-8cd7-5956-32f5-bc7cf4e53b9f")
                     .build();


             JSONObject jsonObject = new RequestApi().execute(request).get();

             Integer estatus = jsonObject.getInt("estado");

             if (estatus == 1) { // exito
                 clase = jsonObject.getString("clase");

             }

         }catch (JSONException ex) {
             ex.printStackTrace();
        } catch (InterruptedException e) {
             e.printStackTrace();
         } catch (ExecutionException e) {
             e.printStackTrace();
         }
        catch (Exception ex){
            clase = "-1";
        }
        return clase;
    }

    private static class RequestApi extends AsyncTask<Request, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(Request... params) {
            try {

                OkHttpClient client = new OkHttpClient();
                Response response = client.newCall(params[0]).execute();

                String string = response.body().string();
                JSONObject jsonObject = new JSONObject(string);

                return jsonObject;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }
    }






}

