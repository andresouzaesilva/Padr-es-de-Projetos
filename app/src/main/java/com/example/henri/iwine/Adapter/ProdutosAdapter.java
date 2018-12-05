package com.example.henri.iwine.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.henri.iwine.Entidades.Produtos;
import com.example.henri.iwine.R;

import java.util.ArrayList;

public class ProdutosAdapter extends ArrayAdapter<Produtos> {

    private ArrayList<Produtos> produto;
    private Context context;

    public ProdutosAdapter(Context c, ArrayList<Produtos> objects) {
        super(c, 0, objects);
        this.context = c;
        this.produto = objects;
    }


    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {

        View view = null;

        if (produto != null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.lista_produtos,parent, false);

            TextView tvViewTipo = (TextView) view.findViewById(R.id.tvViewTipo);
            TextView tvViewMarca = (TextView) view.findViewById(R.id.tvViewMarca);
            TextView tvViewAno = (TextView) view.findViewById(R.id.tvViewAno);
            TextView tvViewAcompanhamento = (TextView) view.findViewById(R.id.tvViewAcompanhamento);

            Produtos produtos2 = produto.get(position);

            tvViewTipo.setText(produtos2.getTipo());
            tvViewMarca.setText(produtos2.getMarca());
            tvViewAno.setText(produtos2.getAno());
            tvViewAcompanhamento.setText(produtos2.getAcompanhamento());



        }
        return view;
    }
}
