package divulga.com.br.projectdivulga.Utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

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
        public ImageView border, image;

        public EstabViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.estab_name);
            border = (ImageView) view.findViewById(R.id.estab_border);
            image = (ImageView) view.findViewById(R.id.estab_img);
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

    private int borders[] = new int[]{R.drawable.linear_border, R.drawable.linear_border2, R.drawable.linear_border3};
    private static int border = 0;
    @Override
    public void onBindViewHolder(EstabViewHolder holder, int position) {
        Establishments estab = estabs.get(position);
        holder.name.setText(estab.getEstab_name());
        Drawable d = context.getDrawable(borders[border]);
        holder.border.setImageDrawable(d);
        switch(border){
            case 0:{
            }
            case 1:{
                border++;
                break;
            }
            case 2:{
                border = 0;
                break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return estabs.size();
    }

}
