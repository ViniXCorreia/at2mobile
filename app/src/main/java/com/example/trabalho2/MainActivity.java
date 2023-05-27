package com.example.trabalho2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.trabalho2.produtos.vo.ProdutoVO;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView teste = findViewById(R.id.testeView);

        String url = "https://loja.supermerclick.com.br/image/cachewebp/catalog/produtos-integracao/002693-omie___coca-cola-1500ml__conv-800x800.webp";

        DownloadImageTask task = new DownloadImageTask(teste);
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
}