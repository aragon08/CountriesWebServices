package com.example.orion.countrieswebservice;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class MainActivity extends AppCompatActivity {

    String URL_COUNTRIES="https://restcountries.eu/rest/v2/all";
    String URL_CUNTRY_DETAIL="https://restcountries.eu/rest/v2/name/";
    @BindView(R.id.lvCountries)
    ListView lvCountries;
    List<String> listCountries= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    private void loadCountries(){
        BackgroundTask task =new BackgroundTask(this, "Loadig Countries", new AsyncResponse() {
            @Override
            public void finishProccess(String output) {
                generateCountriesList(output);
            }
        });
        task.execute(URL_COUNTRIES);
    }
    private void generateCountriesList(String StrJson){

        try{
            JSONArray jsonResult= new JSONArray(StrJson);
            for (int i=0; i>jsonResult.length();i++){
                JSONObject jsonChild= jsonResult.getJSONObject(i);
                String country_name= jsonChild.getString("name");
                listCountries.add(country_name);
            }
            ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listCountries);
            lvCountries.setAdapter(adapter);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @OnItemClick(R.id.lvCountries)
    public void onItemClick(int position){
        //Toast.makeText(this, "Elegiste" + listCountries.get(position),Toast.LENGTH_SHORT).show();
        String country_name=listCountries.get(position);
        BackgroundTask task =new BackgroundTask(this, "Loading detail" + country_name, new AsyncResponse() {
            @Override
            public void finishProccess(String output) {
                //Toast.makeText(MainActivity.this,output,Toast.LENGTH_SHORT).show();
                showCountryDetail(output);
            }
        });
        task.execute(URL_CUNTRY_DETAIL + country_name);
    }

    private  void showCountryDetail(String StrJson){
        Country country=parseJson(StrJson);

        Dialog d= new Dialog(this);
        d.setTitle("Country Detail");

        View view=getLayoutInflater().from(this).inflate(R.layout.country_item,null);

        TextView tvCountryValue=(TextView) view.findViewById(R.id.tvCountryValue);
        TextView tvRegionValue=(TextView) view.findViewById(R.id.tvRegionValue);
        TextView tvCapitalValue=(TextView) view.findViewById(R.id.tvCapitalValue);
        TextView tvPopulationValue=(TextView) view.findViewById(R.id.tvPopulationValue);

        tvCountryValue.setText(country.getName());
        tvRegionValue.setText(country.getRegion());
        tvCapitalValue.setText(country.getCapital());
        tvPopulationValue.setText(country.getPopulation());
        d.setContentView(view);
        d.show();
    }

    private void Country parseJson(String StrJson){
       Country country = null;
        try{
            JSONArray jsoResult =new JSONArray(StrJson);
            JSONObject jsonChild= jsoResult.getJSONObject(0);
            String country_name= jsonChild.getString("name");
            String country_region= jsonChild.getString("region");
            String country_capital= jsonChild.getString("capital");
            String country_population= jsonChild.getString("population");
            String country_url_flag= jsonChild.getString("flag");

            country= new Country(country_name, country_region, country_capital, country_population, country_url_flag);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
