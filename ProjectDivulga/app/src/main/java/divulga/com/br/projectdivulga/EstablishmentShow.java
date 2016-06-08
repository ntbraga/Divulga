package divulga.com.br.projectdivulga;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import divulga.com.br.projectdivulga.Adapters.ContactAdapter;
import divulga.com.br.projectdivulga.ModelDB.Categories;
import divulga.com.br.projectdivulga.ModelDB.Cities;
import divulga.com.br.projectdivulga.ModelDB.Contacts;
import divulga.com.br.projectdivulga.ModelDB.Establishments;
import divulga.com.br.projectdivulga.Utils.ClickHelper;
import divulga.com.br.projectdivulga.Utils.DividerItemDecoration;
import divulga.com.br.projectdivulga.Utils.Utils;
import divulga.com.br.projectdivulga.rest.CallWithProgressBar;
import divulga.com.br.projectdivulga.rest.RealmController;
import divulga.com.br.projectdivulga.rest.RestApi;
import retrofit2.Call;
import retrofit2.Response;

public class EstablishmentShow extends BaseActivity {
    private Establishments establishments;
    private List<Contacts> contactsMail;
    private List<Contacts> contactsPhone;
    private ImageView available;
    private TextView descricao, endereco, funcionamento;
    private RecyclerView telRecyclerView, mailRecyclerView;
    private ContactAdapter mailAdapter, telAdapter;
    private CardView card_contact;
    private LinearLayout phoneLayout, mailLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishment_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        establishments = MainActivity.mainActivity.selectedEstablishment;
        toolbar.setSubtitle(RealmController.getInstance().get(Cities.class, establishments.getId_city()).getCity_name()
                +" - "+RealmController.getInstance().get(Categories.class, establishments.getId_cat()).getCat_name());


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(establishments.getEstab_name());
        contactsMail = new ArrayList<>();
        contactsPhone = new ArrayList<>();
        setViews();
        prepareContactData();
    }

    private void setViews() {
        available = (ImageView) findViewById(R.id.ic_est_func);
        descricao = (TextView) findViewById(R.id.estab_desc);
        endereco = (TextView) findViewById(R.id.estab_address);
        funcionamento = (TextView) findViewById(R.id.estab_funcionamento);
        telRecyclerView = (RecyclerView) findViewById(R.id.tel_rec);
        mailRecyclerView = (RecyclerView) findViewById(R.id.mail_rec);
        card_contact = (CardView) findViewById(R.id.card_content_contact);
        phoneLayout = (LinearLayout) findViewById(R.id.tel_content_linear);
        mailLayout = (LinearLayout) findViewById(R.id.mail_content_linear);
        LinearLayout layout = (LinearLayout)findViewById(R.id.showLocationL);
        if (layout != null) {
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(EstablishmentShow.this, MapsActivity.class);
                    intent.putExtra("id", establishments.getId());
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
        Call<Establishments> call = RestApi.getApiInterface().getEstablishment(establishments.getId());
        new CallWithProgressBar<Establishments>().doCall(call, MainActivity.mainActivity, Establishments.class, new CallWithProgressBar.ProgressCallBack<Establishments>() {
            @Override
            public void onResponse(Call<Establishments> call, Response<Establishments> response) {
                establishments = response.body();
                RealmController.getInstance().setEstablishment(establishments);
                descricao.setText(Html.fromHtml(establishments.getEstab_description()));
                endereco.setText(getAddressConcat());
                funcionamento.setText("Das "+establishments.getOpening_time()+" às "+establishments.getClosing_time());
                available.setImageDrawable(Utils.getAvailableIcon(EstablishmentShow.this, establishments.getOpening_time(), establishments.getClosing_time()));

                if(!establishments.getContacts().isEmpty()){
                    for (Contacts contacts : establishments.getContacts()) {
                        if (contacts.getType().equals("mail")) {
                            contactsMail.add(contacts);
                        } else contactsPhone.add(contacts);
                    }

                    if(contactsPhone.isEmpty()){
                        phoneLayout.setVisibility(View.GONE);
                    }else{
                        phoneLayout.setVisibility(View.VISIBLE);
                        telAdapter.notifyDataSetChanged();
                    }
                    if(contactsMail.isEmpty()){
                        mailLayout.setVisibility(View.GONE);
                    }else{
                        mailLayout.setVisibility(View.VISIBLE);
                        mailAdapter.notifyDataSetChanged();
                    }
                    card_contact.setVisibility(View.VISIBLE);
                }else{
                    card_contact.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Establishments> call, Throwable t) {
                establishments = RealmController.getInstance().get(Establishments.class, establishments.getId());
                descricao.setText(Html.fromHtml(establishments.getEstab_description()));
                endereco.setText(getAddressConcat());
                funcionamento.setText("Das "+establishments.getOpening_time()+" às "+establishments.getClosing_time());
                available.setImageDrawable(Utils.getAvailableIcon(EstablishmentShow.this, establishments.getOpening_time(), establishments.getClosing_time()));
                if(!establishments.getContacts().isEmpty()){
                    for (Contacts contacts : establishments.getContacts()) {
                        if (contacts.getType().equals("mail")) {
                            contactsMail.add(contacts);
                        } else contactsPhone.add(contacts);
                    }

                    if(contactsPhone.isEmpty()){
                        phoneLayout.setVisibility(View.GONE);
                    }else{
                        phoneLayout.setVisibility(View.VISIBLE);
                        telAdapter.notifyDataSetChanged();
                    }
                    if(contactsMail.isEmpty()){
                        mailLayout.setVisibility(View.GONE);
                    }else{
                        mailLayout.setVisibility(View.VISIBLE);
                        mailAdapter.notifyDataSetChanged();
                    }
                    card_contact.setVisibility(View.VISIBLE);
                }else{
                    card_contact.setVisibility(View.GONE);
                }
            }
        });
    }

    private String getAddressConcat(){
        return establishments.getAddress_street()+" "+
                establishments.getAddress_number()+" "+
                establishments.getAddress_district()+" "+
                establishments.getAddress_zip();
    }

}
