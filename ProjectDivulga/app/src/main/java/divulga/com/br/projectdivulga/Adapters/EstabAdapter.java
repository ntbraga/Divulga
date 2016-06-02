package divulga.com.br.projectdivulga.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import divulga.com.br.projectdivulga.EstablishmentShow;
import divulga.com.br.projectdivulga.MainActivity;
import divulga.com.br.projectdivulga.ModelDB.Establishments;
import divulga.com.br.projectdivulga.R;

/**
 * Created by Taty Braga on 31/05/2016.
 */
public class EstabAdapter extends RecyclerView.Adapter<EstabAdapter.EstabViewHolder>{

    private List<Establishments> estabs;
    private Context context;

    public class EstabViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView image;
        public FloatingActionButton fav_fab;
        public RelativeLayout clickLayout;
        public EstabViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.estab_name);
            image = (ImageView) view.findViewById(R.id.estab_img);
            fav_fab = (FloatingActionButton) view.findViewById(R.id.estab_fab_fav);
            clickLayout = (RelativeLayout) view.findViewById(R.id.click_estab);
        }
    }

    public EstabAdapter(List<Establishments> estabs, Context context){
        this.estabs = estabs;
        this.context = context;
    }

    @Override
    public EstabViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.estab_rec_layout, parent, false);

        return new EstabViewHolder(itemView);
    }

    private int[] colors = new int[]{R.color.colorBorder0, R.color.colorBorder1, R.color.colorBorder2};
    private static int color = 0;
    private static final int star_selected = R.drawable.ic_star_selected;
    private static final int star = R.drawable.ic_star;

    @Override
    public void onBindViewHolder(final EstabViewHolder holder, int position) {
        final Establishments estab = estabs.get(position);
        holder.name.setText(estab.getEstab_name());
        holder.fav_fab.setImageDrawable(context.getDrawable(star));
        holder.image.setImageDrawable(context.getDrawable(colors[color]));
        switch(color){
            case 0:
            case 1:{
                color++;
                break;
            }
            case 2: color = 0;
        }
        holder.fav_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estab.setFavorite(!estab.isFavorite());
                if(estab.isFavorite()){
                    holder.fav_fab.setImageDrawable(context.getDrawable(star_selected));
                }else{
                    holder.fav_fab.setImageDrawable(context.getDrawable(star));
                }
            }
        });
        holder.clickLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mainActivity.selectedEstablishment = estab;
                Intent intent = new Intent(context, EstablishmentShow.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return estabs.size();
    }

}
