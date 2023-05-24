package com.example.trabalho2.produtos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import com.example.trabalho2.produtos.vo.ProdutoVO;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO extends SQLiteOpenHelper {
    public ProdutoDAO(Context context){
        super(context, "banco", null, 1);
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {
        String sql = "CREATE TABLE produto (id INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT, descricao TEXT, preco REAL, gluten INTEGER, calorias INTEGER)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS produto";
        db.execSQL(sql);
    }

    public String insertProdutoDAO(@NonNull ProdutoVO produtoVO){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = new ContentValues();
        dados.put("titulo", produtoVO.getTitulo());
        dados.put("descricao", produtoVO.getDescricao());
        dados.put("preco", produtoVO.getPreco());
        dados.put("gluten", produtoVO.getGluten());
        dados.put("calorias", produtoVO.getCalorias());
        db.insert("produto", null, dados);
        return "Produto inserido com sucesso";
    }

    public List<ProdutoVO> buscarProdutos() throws Exception{
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM produtos";
        Cursor c = db.rawQuery(sql, null);
        List produtos = new ArrayList<ProdutoVO>();
        while (c.moveToNext()){
            ProdutoVO produtoVO = new ProdutoVO();
            produtoVO.setTitulo(c.getString(c.getColumnIndexOrThrow("titulo")));
            produtoVO.setDescricao(c.getString(c.getColumnIndexOrThrow("descricao")));
            produtoVO.setPreco(Float.parseFloat(c.getString(c.getColumnIndexOrThrow("preco"))));
            produtoVO.setGluten(Integer.parseInt(c.getString(c.getColumnIndexOrThrow("gluten"))));
            produtoVO.setCalorias(Integer.parseInt(c.getString(c.getColumnIndexOrThrow("calorias"))));
            produtos.add(produtoVO);
        }
        c.close();
        db.close();
        return produtos;
    }

}
