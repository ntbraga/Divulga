package divulga.com.br.projectdivulga.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import divulga.com.br.projectdivulga.ModelDB.Categories;
import divulga.com.br.projectdivulga.R;

/**
 * Created by Taty Braga on 31/05/2016.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{

    private List<Categories> categories;
    private Context context;

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView image;

        public CategoryViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.cat_name);
            image = (ImageView) view.findViewById(R.id.cat_img);
        }
    }

    public CategoryAdapter(List<Categories> categories, Context context){
        this.categories = categories;
        this.context = context;
    }


    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cat_block_layout, parent, false);

        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        Categories cat = categories.get(position);
        holder.name.setText(cat.getCat_name());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
