package com.example.simpleparadox.listycity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {
    private EditText cityName;
    private EditText provinceName;
    private OnFragmentInteractionListener listener;
    private City city;

    public AddCityFragment(){}

    public AddCityFragment(City city) {
        this.city = city;
    }

    public interface OnFragmentInteractionListener {
        void onOkPressed(City newCity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        }
        else {
            throw new RuntimeException(context.toString() + "must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_city_fragment_layout, null);
        cityName = view.findViewById(R.id.city_name_editText);
        provinceName = view.findViewById(R.id.province_editText);

        if(city != null) {
            cityName.setText(city.getCityName());
            provinceName.setText(city.getProvinceName());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view).setTitle("Add/edit city")
                .setNegativeButton("Cancel", null).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String city = cityName.getText().toString();
                        String province = provinceName.getText().toString();
                        listener.onOkPressed(new City(city, province));
                    }
                }).create();
    }
}