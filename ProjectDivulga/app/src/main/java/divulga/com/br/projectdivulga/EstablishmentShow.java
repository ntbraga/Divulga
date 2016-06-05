package divulga.com.br.projectdivulga;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import divulga.com.br.projectdivulga.Adapters.ContactAdapter;
import divulga.com.br.projectdivulga.ModelDB.Contacts;
import divulga.com.br.projectdivulga.ModelDB.Establishments;
import divulga.com.br.projectdivulga.Utils.ClickHelper;
import divulga.com.br.projectdivulga.Utils.DividerItemDecoration;

public class EstablishmentShow extends BaseActivity {
    private Establishments establishments;
    private List<Contacts> contactsAll;
    private List<Contacts> contactsMail;
    private List<Contacts> contactsPhone;
    private TextView descricao, endereco;
    private RecyclerView telRecyclerView, mailRecyclerView;
    private ContactAdapter mailAdapter, telAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishment_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        establishments = MainActivity.mainActivity.selectedEstablishment;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(establishments.getEstab_name());
        contactsAll = new ArrayList<>();
        contactsMail = new ArrayList<>();
        contactsPhone = new ArrayList<>();
        setViews();
        prepareContactData();
    }

    private void setViews() {
        descricao = (TextView) findViewById(R.id.estab_desc);
        endereco = (TextView) findViewById(R.id.estab_address);
        telRecyclerView = (RecyclerView) findViewById(R.id.tel_rec);
        mailRecyclerView = (RecyclerView) findViewById(R.id.mail_rec);

        descricao.setText("Short Description");
        endereco.setText(getAddressConcat());

        LinearLayout layout = (LinearLayout)findViewById(R.id.showLocationL);
        if (layout != null) {
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(EstablishmentShow.this, MapsActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    private void prepareContactData() {
        mailAdapter = new ContactAdapter(contactsMail, this, ContactAdapter.ContactType.EMAIL);
        telAdapter = new ContactAdapter(contactsPhone, this, ContactAdapter.ContactType.TELEPHONE);
        RecyclerView.LayoutManager mailLayout = new LinearLayoutManager(this);
        RecyclerView.LayoutManager telLayout = new LinearLayoutManager(this);

        telRecyclerView.setLayoutManager(mailLayout);
        mailRecyclerView.setLayoutManager(telLayout);

        telRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mailRecyclerView.setItemAnimator(new DefaultItemAnimator());

        telRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mailRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        telRecyclerView.setAdapter(telAdapter);
        mailRecyclerView.setAdapter(mailAdapter);

        telRecyclerView.addOnItemTouchListener(new ClickHelper.RecyclerTouchListener(this, telRecyclerView, new ClickHelper.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                MainActivity.mainActivity.doCall(contactsPhone.get(position).getContact());
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        mailRecyclerView.addOnItemTouchListener(new ClickHelper.RecyclerTouchListener(this, mailRecyclerView, new ClickHelper.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                MainActivity.mainActivity.sendMail(contactsMail.get(position).getContact());
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        prepareData();
    }

    private void prepareData(){

        // TODO

        telAdapter.notifyDataSetChanged();
        mailAdapter.notifyDataSetChanged();
    }

    private String getAddressConcat(){
        return "Address";
    }

}
