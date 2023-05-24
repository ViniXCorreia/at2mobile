package com.example.trabalho2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.trabalho2.produtos.vo.ProdutoVO;

import java.text.DecimalFormat;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";
    private ProdutoVO[] mDataSet;

    private DecimalFormat df = new DecimalFormat("R$ #,##0.00");
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView descricao, calorias, titulo, preco, gluten;
        private ImageView imagemItem;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            descricao = (TextView) v.findViewById(R.id.descricaoItem);
            gluten = (TextView) v.findViewById(R.id.glutenItem);
            titulo = (TextView) v.findViewById(R.id.tituloItem);
            calorias = (TextView) v.findViewById(R.id.caloriasItem);
            preco = (TextView) v.findViewById(R.id.precoItem);
            imagemItem = (ImageView) v.findViewById(R.id.item_view);
        }

        public TextView getCalorias() {
            return calorias;
        }

        public TextView getPreco() {
            return preco;
        }

        public TextView getGluten() {
            return gluten;
        }

        public TextView getTitulo() {
            return titulo;
        }

        public TextView getDescricao() {
            return descricao;
        }

        public ImageView getImagemItem() {
            return imagemItem;
        }
    }

    public RecyclerViewAdapter(ProdutoVO[] dataSet) {
        mDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_cardapio, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        ProdutoVO p = mDataSet[position];
        Log.d(TAG, "Element " + position + " set.");
        viewHolder.getTitulo().setText(p.getTitulo());
        viewHolder.getDescricao().setText(p.getDescricao());
        viewHolder.getCalorias().setText(String.valueOf(p.getCalorias()));
        viewHolder.getPreco().setText(df.format(p.getPreco()));
        viewHolder.getGluten().setText(p.getGluten() == 1 ? "Possui Glúten" : "Sem Glúten");

    }

    @Override
    public int getItemCount() {
        return mDataSet.length;
    }
}