package com.example.trabalho2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.trabalho2.produtos.dao.ProdutoDAO;
import com.example.trabalho2.produtos.vo.ProdutoVO;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ReadExternalFileByUrl.OnTaskCompleteListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ProdutoDAO produtoDAO = new ProdutoDAO(getApplicationContext());

        String url = "http://192.168.15.72:5500/atividade2Mobile.txt";
        ReadExternalFileByUrl task = new ReadExternalFileByUrl();
        task.setOnTaskCompleteListener(this);
        task.execute(url);

        ProdutoVO p1 = new ProdutoVO();
        p1.setDescricao("Pizza");
        p1.setPreco((float) 10.50);
        p1.setCalorias(200);
        p1.setGluten(1);
        p1.setImagem("https://loja.supermerclick.com.br/image/cachewebp/catalog/produtos-integracao/002693-omie___coca-cola-1500ml__conv-800x800.webp");
        p1.setTitulo("PIZZAZUDA");
        ProdutoVO p2 = new ProdutoVO();
        p2.setDescricao("Hamburguer");
        p2.setGluten(0);
        p2.setImagem("https://s2.glbimg.com/IaEnP49buSdSUDftlMxVrq3-ZDo=/940x523/e.glbimg.com/og/ed/f/original/2019/04/26/loucosporti1.jpg");
        p2.setCalorias(234);
        p2.setTitulo("HAMBURGAO DA MASSA");
        p2.setPreco((float) 22.30);

        RecyclerViewAdapter ca= new RecyclerViewAdapter(
                new ProdutoVO[]{p1, p2}
        );
        RecyclerView cv=this.findViewById(R.id.itensLayout);
        cv.setLayoutManager(new LinearLayoutManager(this));
        cv.setAdapter(ca);
    }

    @Override
    public void onTaskComplete(ArrayList<ProdutoVO> produtos) {
        ProdutoDAO produtoDAO = new ProdutoDAO(getApplicationContext());
        for(int i=0; i < produtos.size(); i++){
            produtoDAO.insertProdutoDAO(produtos.get(i));
        }
    }
}