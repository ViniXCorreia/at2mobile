package com.example.trabalho2;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import com.example.trabalho2.produtos.dao.ProdutoDAO;
import com.example.trabalho2.produtos.vo.ProdutoVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ReadExternalFileByUrl extends AsyncTask<String, Void, JSONObject> {
    public ArrayList produtos = new ArrayList<ProdutoVO>();
    private static final String TAG = "FetchJsonTask";
    private OnTaskCompleteListener taskListener;
    private OnTaskFailedListener taskFailedListener;

    private Context context;

    public ReadExternalFileByUrl (Context context) {
        this.context = context;
    }

    public interface OnTaskCompleteListener{
        void onTaskComplete(ArrayList<ProdutoVO> produtos) throws Exception;
    }

    public interface OnTaskFailedListener{
        void onTaskFailed(boolean failed);
    }

    public void setOnTaskCompleteListener(OnTaskCompleteListener listener){
        taskListener = listener;
    }

    public void setOnTaskFailedListener(OnTaskFailedListener listener){
        taskFailedListener = listener;
    }
    @Override
    protected JSONObject doInBackground(String... urls) {
        String urlString = urls[0];
        JSONObject jsonObject = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(500);
            urlConnection.connect();

            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = urlConnection.getInputStream();
                String jsonString = convertInputStreamToString(inputStream);
                jsonObject = new JSONObject(jsonString);
                JSONArray produtosArray =jsonObject.getJSONArray("produtos");
                if(produtosArray.length() > 0 ){
                    ArrayList<ProdutoVO> produtosFromURL = new ArrayList<ProdutoVO>();
                    for(int i=0; i< produtosArray.length(); i++){
                        JSONObject produto = produtosArray.getJSONObject(i);
                        int id = produto.getInt("id");
                        String imagemURL = produto.getString("imagemURL");
                        String descricao = produto.getString("descricao");
                        double preco = produto.getDouble("preco");
                        String titulo = produto.getString("titulo");
                        int calorias = produto.getInt("calorias");
                        int gluten = produto.getInt("glutem");

                        ProdutoVO produtoVO = new ProdutoVO();
                        produtoVO.setId(id);
                        produtoVO.setDescricao(descricao);
                        produtoVO.setPreco(preco);
                        produtoVO.setTitulo(titulo);
                        produtoVO.setCalorias(calorias);
                        produtoVO.setGluten(gluten);

                        Bitmap bmImage = this.downloadImage(imagemURL);
                        File file = this.saveImage(bmImage, "product_" + id);

                        produtoVO.setImagem(file.getPath());

                        produtos.add(produtoVO);
                    }
                }
            } else {
                Log.e(TAG, "HTTP error code: " + responseCode);
            }
        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error fetching JSON: " + e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return jsonObject;
    }

    private Bitmap downloadImage(String imageUrl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public File saveImage(Bitmap image, String filename) {
        File file = new File(context.getExternalFilesDir("product_images"), filename);

        try {
            FileOutputStream outputStream = new FileOutputStream(file, false);
            image.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return file;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        if (jsonObject != null) {
            if(taskListener != null){
                try {
                    taskListener.onTaskComplete(produtos);
                } catch (Exception e) {
                    Log.e(TAG, "Error executing task: " + e.getMessage());
                }
            }
        }else{
            if(taskFailedListener != null){
                try{
                    taskFailedListener.onTaskFailed(true);
                } catch (Exception e){
                    Log.e(TAG, "Error executing task: " + e.getMessage());
                }
            }
        }
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        bufferedReader.close();
        return stringBuilder.toString();
    }

}
