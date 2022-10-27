package com.example.simpleparadox.listycity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    // Declare the variables so that you will be able to reference it later.
    RecyclerView cityList;
    CustomAdapter cityAdapter;
    ArrayList<City> cityDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String TAG = "Sample";
        Button addCityButton;
        final EditText addCityEditText;
        final EditText addProvinceEditText;
        FirebaseFirestore db;

        db = FirebaseFirestore.getInstance();

        // Get a top level reference to the collection
        final CollectionReference collectionReference = db.collection("Cities");

        addCityButton = findViewById(R.id. add_city_button );
        addCityEditText = findViewById(R.id. add_city_field );
        addProvinceEditText = findViewById(R.id. add_province_edit_text );

        // Get a reference to the ListView and create an object for the city list
        cityList = findViewById(R.id. city_list );
        cityDataList = new ArrayList<>();
        cityAdapter = new CustomAdapter(this, cityDataList);
        cityList.setAdapter(cityAdapter);

        // Update data to database
        addCityButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String cityName = addCityEditText.getText().toString();
                final String provinceName = addProvinceEditText.getText().toString();

                HashMap<String, String> data = new HashMap<>();
                // Retrieving the city name and the province name from the EditText fields
                if (cityName.length()>0 && provinceName.length()>0) {
                    // If there’s some data in the EditText field, then we create a new key-value pair.
                    data.put("Province Name", provinceName);

                    collectionReference
                            .document(cityName)
                            .set(data)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // These are a method which gets executed when the task is succeeded
                                    Log. d (TAG, "Data has been added successfully!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // These are a method which gets executed if there’s any problem
                                    Log. d (TAG, "Data could not be added!" + e.toString());
                                }
                            });

                    // Setting the fields to null so that user can add a new city
                    addCityEditText.setText("");
                    addProvinceEditText.setText("");
                }
            }
        });

        // delete data from database on swipe
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getBindingAdapterPosition();
                collectionReference.document(cityDataList.get(position).getCityName()).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                              @Override
                              public void onFailure(@NonNull Exception e) {
                                  Log.w(TAG, "Error deleting document", e);
                              }
                        });
            }
        });

        itemTouchHelper.attachToRecyclerView(cityList);

        // Load data from database back to app
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
                    FirebaseFirestoreException error) {
                // Clear the old list
                cityDataList.clear();
                assert queryDocumentSnapshots != null;
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots)
                {
                    Log. d (TAG, String. valueOf (doc.getData().get("Province Name")));
                    String city = doc.getId();
                    String province = (String) doc.getData().get("Province Name");
                    cityDataList.add(new City(city, province)); // Adding the cities and provinces from FireStore
                }
                cityAdapter.notifyDataSetChanged(); // Notifying the adapter to render any new data fetched from the cloud
            }
        });
    }
}