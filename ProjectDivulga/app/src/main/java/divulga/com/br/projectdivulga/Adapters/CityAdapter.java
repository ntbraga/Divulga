package divulga.com.br.projectdivulga.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import divulga.com.br.projectdivulga.ModelDB.Cities;
import divulga.com.br.projectdivulga.R;

/**
 * Created by Taty Braga on 30/05/2016.
 */
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.MyViewHolder> {
    private List<Cities> cities;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, state;
        public ImageView border;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.city_name);
            state = (TextView) view.findViewById(R.id.city_state);
            border = (ImageView) view.findViewById(R.id.city_border);
        }
    }

    public CityAdapter(List<Cities> cities, Context context){
        this.cities = cities;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_rec_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    private int borders[] = new int[]{R.drawable.linear_border, R.drawable.linear_border2, R.drawable.linear_border3};
    private static int border = 0;
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Cities city = cities.get(position);
        holder.name.setText(city.getCity_name());
        holder.state.setText(city.getCity_state());
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
        return cities.size();
    }

}
