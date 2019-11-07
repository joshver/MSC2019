package com.example.msc2019;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {
    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<ListNames> List = null;
    private ArrayList<ListNames> arraylist;

    public ListViewAdapter(Context context, List<ListNames> List) {
        mContext = context;
        this.List = List;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<ListNames>();
        this.arraylist.addAll(List);
    }

    public class ViewHolder {
        TextView id;
        TextView nombre;
        TextView salario;
    }

    @Override
    public int getCount() {
        return List.size();
    }

    @Override
    public ListNames getItem(int position) {
        return List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_item, null);
            // Locate the TextViews in listview_item.xml
            holder.id = view.findViewById(R.id.ID);
            holder.nombre = view.findViewById(R.id.Nombre);
            holder.salario = view.findViewById(R.id.Salario);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.id.setText(" "+List.get(position).getID());
        holder.nombre.setText(" "+List.get(position).getNombre());
        holder.salario.setText(" "+List.get(position).getSalario());
        // Listen for ListView Item Click
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Send single item click data to SingleItemView Class
                MainActivity.starton = true;
                Intent intent = new Intent(mContext, Modificar.class);
                intent.putExtra("ID",(List.get(position).getID()));
                intent.putExtra("Nombre",(List.get(position).getNombre()));
                intent.putExtra("Salario",(List.get(position).getSalario()));
                mContext.startActivity(intent);
            }
        });
        return view;
    }

    // Filter Class

    public void filterNombre(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        List.clear();
        if (charText.length() == 0) {
            List.addAll(arraylist);
        }
        else
        {
            for (ListNames wp : arraylist)
            {
                if (wp.getNombre().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    List.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
    public void filterId(int charText) {
       // charText = charText.toLowerCase(Locale.getDefault());
        List.clear();
        for (ListNames wp : arraylist)
        {
            if (String.valueOf(wp.getID()).contains(charText+"")) {
                List.add(wp);
            }
        }

        notifyDataSetChanged();
    }
}
