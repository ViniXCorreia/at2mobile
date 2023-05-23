package com.example.trabalho2.produtos.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

public class ProdutoDAO extends SQLiteOpenHelper {
    public ProdutoDAO(Context context){
        super(context, "banco", null, 1);
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {
        String sql = "CREATE TABLE produto (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, descricao TEXT, preco REAL, gluten INTEGER, calorias INTEGER)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS produto";
        db.execSQL(sql);
    }
}
