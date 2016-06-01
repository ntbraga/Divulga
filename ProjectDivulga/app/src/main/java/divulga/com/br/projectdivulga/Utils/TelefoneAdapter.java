package divulga.com.br.projectdivulga.Utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import divulga.com.br.projectdivulga.ModelDB.Cities;
import divulga.com.br.projectdivulga.R;
import divulga.com.br.projectdivulga.ModelDB.TelefoneUtil;

/**
 * Created by Taty Braga on 31/05/2016.
 */
public class TelefoneAdapter extends RecyclerView.Adapter<TelefoneAdapter.MyViewHolder>{
    private List<TelefoneUtil> utils;
    private Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, tel;
        public ImageView ico;
        public FloatingActionButton fab;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tel_name);
            tel = (TextView) view.findViewById(R.id.tel_num);
            ico = (ImageView) view.findViewById(R.id.tel_icon);
            fab = (FloatingActionButton) view.findViewById(R.id.tel_fab_call);
        }
    }

    public TelefoneAdapter(List<TelefoneUtil> utils, Context context){
        this.utils = utils;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tel_util_rec_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final TelefoneUtil util = utils.get(position);
        holder.name.setText(util.getName());
        holder.tel.setText(util.getTel());
        holder.ico.setImageDrawable(util.getIcon());
        holder.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "ligar para "+util.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return utils.size();
    }

}
