package com.example.greenguardian390.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.greenguardian390.Models.Plant;
import com.example.greenguardian390.Models.UserProfile;
import com.example.greenguardian390.R;
import com.example.greenguardian390.Views.AddPlantPage;

import java.util.ArrayList;

public class MainPage extends AppCompatActivity {

    static final int REQUEST_CODE = 1;
    private NotificationManager notificationManager;
    private int notificationId;
    private Button button;

    private UserProfile currentuser;

    ListView listView;


//    UserProfile currentuser=(UserProfile) getIntent().getSerializableExtra("currentProfile");;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        //finish();

        //notificationId = getIntent().getIntExtra("notificationId",0);
        //notificationManager=(NotificationManager) getSystemService(Context.)


        listView=findViewById(R.id.userPlants);

        currentuser=(UserProfile) getIntent().getSerializableExtra("currentProfile");


        ArrayList<Plant> currentUserPlants=currentuser.getUserPlants();

        ArrayList<String> plantNames=new ArrayList<>();

        if(currentUserPlants.size()>0)
        {
            for(Plant userPlant : currentUserPlants)
            {
                String plantName;
                plantNames.add(userPlant.getPlantName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, plantNames);
            listView.setAdapter(adapter);
        }

        button = (Button) findViewById(R.id.AddPlantButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Intent intent =new Intent(MainPage.this,PlantPage.class);
                //startActivity(intent);
                openAddPlantPage();


            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedPlantName = (String) adapterView.getItemAtPosition(i);
                Plant selectedPlant=new Plant();
                for (Plant userPlant : currentUserPlants)
                {
                    if(userPlant.getPlantName().equals(selectedPlantName))
                    {
                        selectedPlant=userPlant;
                        break;
                    }
                }
                Intent intent=new Intent(MainPage.this,PlantPage.class);
                intent.putExtra("CurrentUser",currentuser);
                intent.putExtra("plantClicked", selectedPlant);
                startActivity(intent);

            }
        });
    }



    public void openAddPlantPage() {
        UserProfile currentuser=(UserProfile) getIntent().getSerializableExtra("currentProfile");
        System.out.println(currentuser.getUsername());
        Intent intent = new Intent(this, AddPlantPage.class);
        intent.putExtra("CurrentUser", currentuser);
        startActivity(intent);
        //listView = findViewById(R.id.listview);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type name of plant here");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

}