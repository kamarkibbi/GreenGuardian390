package com.example.greenguardian390.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.greenguardian390.Models.UserProfile;
import com.example.greenguardian390.R;
import com.example.greenguardian390.Views.AddPlantPage;

public class MainPage extends AppCompatActivity {


    private Button button;

    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);



        button = (Button) findViewById(R.id.AddPlantButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Intent intent =new Intent(MainPage.this,PlantPage.class);
                //startActivity(intent);
                openAddPlantPage();


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