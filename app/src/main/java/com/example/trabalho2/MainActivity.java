package com.example.trabalho2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.trabalho2.produtos.dao.ProdutoDAO;
import com.example.trabalho2.produtos.vo.ProdutoVO;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ReadExternalFileByUrl.OnTaskCompleteListener, ReadExternalFileByUrl.OnTaskFailedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean conectado = ChecaInternet.isNetworkConnected(getApplicationContext());
        if(conectado){
            String url = "http://192.168.160.1:5500/atividade2Mobile.txt";
            ReadExternalFileByUrl task = new ReadExternalFileByUrl();
            task.setOnTaskCompleteListener(this);
            task.setOnTaskFailedListener(this);
            task.execute(url);
        }else{
            this.carregaDadosOffline();
            Toast.makeText(this, "SEM INTERNET", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onTaskComplete(ArrayList<ProdutoVO> produtos) throws Exception {
        ProdutoDAO produtoDAO = new ProdutoDAO(getApplicationContext());
        for(int i=0; i < produtos.size(); i++){
            ProdutoVO existeProduto = produtoDAO.buscaProdutoPorTitulo(produtos.get(i).getTitulo());
            if(existeProduto == null){
                produtoDAO.insertProdutoDAO(produtos.get(i));
            }
        }
        List<ProdutoVO> getProdutos = produtoDAO.buscarProdutos();
        ProdutoVO[] produtosArray = new ProdutoVO[getProdutos.size()];
        getProdutos.toArray(produtosArray);
        RecyclerViewAdapter ca= new RecyclerViewAdapter(
                produtosArray
        );
        RecyclerView cv=this.findViewById(R.id.itensLayout);
        cv.setLayoutManager(new LinearLayoutManager(this));
        cv.setAdapter(ca);
    }

    @Override
    public void onTaskFailed(boolean failed) {
        if(failed == true) {
            this.carregaDadosOffline();
        }
    }

    public void carregaDadosOffline(){
        Toast.makeText(this, "SEM CONEXÃO COM O SERVIDOR", Toast.LENGTH_SHORT).show();
        ProdutoDAO produtoDAO = new ProdutoDAO(getApplicationContext());
        List<ProdutoVO> getProdutos = null;
        try {
            getProdutos = produtoDAO.buscarProdutos();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ProdutoVO[] produtosArray = new ProdutoVO[getProdutos.size()];
        getProdutos.toArray(produtosArray);
        RecyclerViewAdapter ca= new RecyclerViewAdapter(
                produtosArray
        );
        RecyclerView cv=this.findViewById(R.id.itensLayout);
        cv.setLayoutManager(new LinearLayoutManager(this));
        cv.setAdapter(ca);
    }
}