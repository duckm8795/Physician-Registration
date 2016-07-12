package com.trainingandroidpart1.physicianregistration;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.trainingandroidpart1.physicianregistration.Response.GetDegreeList.DegreeList;
import com.trainingandroidpart1.physicianregistration.Response.GetDegreeList.MedDegreeList;
import com.trainingandroidpart1.physicianregistration.Service.ServiceAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DegreeListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_degree_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final MaterialSpinner materialSpinner = (MaterialSpinner) findViewById(R.id.spinner_degree_list);
        ServiceAPI serviceAPI = ServiceAPI.retrofit.create(ServiceAPI.class);
        Call<DegreeList> call = serviceAPI.getDegreeList("180");
        call.enqueue(new Callback<DegreeList>() {
            @Override
            public void onResponse(Call<DegreeList> call, Response<DegreeList> response) {
                if ( response.body().getSuccess()){
                    List<MedDegreeList> medDegreeListList = response.body().getMedDegreeList();
                    List<String> medDegreeName =  new ArrayList<>();
                    final List<Integer> medDegreeID =  new ArrayList<>();
                    for(int i = 0 ; i < medDegreeListList.size();i++){
                        medDegreeName.add(medDegreeListList.get(i).getName());
                        medDegreeID.add(medDegreeListList.get(i).getMedDegreeID());
                    }
                    materialSpinner.setItems(medDegreeName);
                    materialSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                        @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                            //Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(),medDegreeID.get(position).toString(),Toast.LENGTH_LONG).show();

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<DegreeList> call, Throwable t) {

            }
        });
    }
    public  void next_to_spcialist_activity ( View view){
        Intent intent = new Intent(DegreeListActivity.this,SpecialistActivity.class);
        startActivity(intent);
    }
}
