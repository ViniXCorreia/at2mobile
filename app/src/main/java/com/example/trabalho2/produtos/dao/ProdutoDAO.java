package com.example.trabalho2.produtos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.trabalho2.produtos.vo.ProdutoVO;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO extends SQLiteOpenHelper {
    public ProdutoDAO(Context context){
        super(context, "banco", null, 2);
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {
        String sql = "CREATE TABLE produto (id INTEGER PRIMARY KEY, imagem TEXT, titulo TEXT, descricao TEXT, preco REAL, gluten INTEGER, calorias INTEGER)";
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
        dados.put("id", produtoVO.getId());
        dados.put("titulo", produtoVO.getTitulo());
        dados.put("descricao", produtoVO.getDescricao());
        dados.put("preco", produtoVO.getPreco());
        dados.put("gluten", produtoVO.getGluten());
        dados.put("calorias", produtoVO.getCalorias());
        dados.put("imagem", produtoVO.getImagem());
        db.insert("produto", null, dados);
        return "Produto inserido com sucesso";
    }

    public String updateProdutoDAO(@NonNull ProdutoVO produtoVO){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = new ContentValues();
        dados.put("titulo", produtoVO.getTitulo());
        dados.put("descricao", produtoVO.getDescricao());
        dados.put("preco", produtoVO.getPreco());
        dados.put("gluten", produtoVO.getGluten());
        dados.put("calorias", produtoVO.getCalorias());
        dados.put("imagem", produtoVO.getImagem());

        String[] args = new String[] { String.valueOf(produtoVO.getId()) };

        db.update("produto", dados, "id = ?", args);

        return "Produto atualizado com sucesso";
    }

    public ArrayList<ProdutoVO> buscarProdutos() throws Exception{
        try{
            SQLiteDatabase db = getReadableDatabase();
            String sql = "SELECT * FROM produto";
            Cursor c = db.rawQuery(sql, null);
            ArrayList<ProdutoVO> produtos = new ArrayList<ProdutoVO>();
            while (c.moveToNext()){
                ProdutoVO produtoVO = new ProdutoVO();
                produtoVO.setTitulo(c.getString(c.getColumnIndexOrThrow("titulo")));
                produtoVO.setDescricao(c.getString(c.getColumnIndexOrThrow("descricao")));
                produtoVO.setPreco(Float.parseFloat(c.getString(c.getColumnIndexOrThrow("preco"))));
                produtoVO.setGluten(Integer.parseInt(c.getString(c.getColumnIndexOrThrow("gluten"))));
                produtoVO.setCalorias(Integer.parseInt(c.getString(c.getColumnIndexOrThrow("calorias"))));
                produtoVO.setImagem(c.getString((c.getColumnIndexOrThrow("imagem"))));
                produtos.add(produtoVO);
            }
            c.close();
            db.close();
            return produtos;

        }catch (Exception e){
            Log.d("Erro SQL", "Erro ao buscarProdutos");
        }

        return null;
    }

    public void deletaProdutos() {
        SQLiteDatabase db = getWritableDatabase();
        String tabela = "produto";
        db.delete(tabela, null, null);
        db.close();
    }

    public ProdutoVO buscaProdutoPorTitulo(String titulo){
        try{
            SQLiteDatabase db = getReadableDatabase();
            String sql = "SELECT * FROM produto WHERE titulo = '" + titulo + "';";
            Cursor c = db.rawQuery(sql, null);
            ProdutoVO produtoVO = null;
            if(c.moveToNext()){
                produtoVO = new ProdutoVO();
                produtoVO.setTitulo(c.getString(c.getColumnIndexOrThrow("titulo")));
                produtoVO.setDescricao(c.getString(c.getColumnIndexOrThrow("descricao")));
                produtoVO.setPreco(Float.parseFloat(c.getString(c.getColumnIndexOrThrow("preco"))));
                produtoVO.setGluten(Integer.parseInt(c.getString(c.getColumnIndexOrThrow("gluten"))));
                produtoVO.setCalorias(Integer.parseInt(c.getString(c.getColumnIndexOrThrow("calorias"))));
                produtoVO.setImagem(c.getString((c.getColumnIndexOrThrow("imagem"))));
            }

            c.close();
            db.close();
            return produtoVO;
        } catch (Exception e){
            Log.d("Erro SQL", "Erro ao buscarProdutoPorTitulo");
        }
        return null;
    }

}
