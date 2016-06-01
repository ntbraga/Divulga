package divulga.com.br.projectdivulga.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import divulga.com.br.projectdivulga.ModelDB.Contacts;
import divulga.com.br.projectdivulga.R;

/**
 * Created by Taty Braga on 01/06/2016.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder>{
    public static enum ContactType{
        EMAIL(R.drawable.ic_mail), TELEPHONE(R.drawable.ic_telephone);
        ;
        public int ico;
        private ContactType(int ico){
            this.ico = ico;
        }
    }

    private List<Contacts> contacts;
    private Context context;
    private ContactType type;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView value;
        public ImageView ico;

        public MyViewHolder(View view) {
            super(view);
            value = (TextView) view.findViewById(R.id.contact_value);
            ico = (ImageView) view.findViewById(R.id.contact_icon);
        }
    }

    public ContactAdapter(List<Contacts> contacts, Context context, ContactType type){
        this.contacts = contacts;
        this.context = context;
        this.type = type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_rec_layout, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Contacts contact = contacts.get(position);
        holder.ico.setImageDrawable(context.getDrawable(type.ico));
        switch(type){
            case EMAIL:{
                holder.value.setText(contact.getContact_email());
                break;
            }
            case TELEPHONE:{
                holder.value.setText(contact.getContact_phone());
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

}
