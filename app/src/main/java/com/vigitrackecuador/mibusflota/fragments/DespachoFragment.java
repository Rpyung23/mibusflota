package com.vigitrackecuador.mibusflota.fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;
import com.vigitrackecuador.mibusflota.POO.cFlotas;
import com.vigitrackecuador.mibusflota.POO.cIdBus;
import com.vigitrackecuador.mibusflota.R;
import com.vigitrackecuador.mibusflota.adapter.cAdapterRecyclerViewDespacho;
import com.vigitrackecuador.mibusflota.adapter.cAdapter_flotas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.vigitrackecuador.mibusflota.LoginActivity.Codi_observador;
import static com.vigitrackecuador.mibusflota.LoginActivity.arrayStringIdBus;
import static com.vigitrackecuador.mibusflota.LoginActivity.url_despacho;
import static com.vigitrackecuador.mibusflota.LoginActivity.url_mapa;
import static com.vigitrackecuador.mibusflota.LoginActivity.url_mapa_gps;

/**
 * A simple {@link Fragment} subclass.
 */
public class DespachoFragment extends Fragment {
    BottomBar bottomBar;

    public DespachoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_despacho, container, false);
        bottomBar = view.findViewById(R.id.bottombar_despacho);
        bottomBar.setDefaultTab(R.id.tab_vueltas);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId) {
                    case R.id.tab_vueltas:
                        despachoVueltasFragment odv = new despachoVueltasFragment();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contenedor_despacho, odv)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        break;
                    case R.id.tab_time:
                        DespachoHorasFragment odh = new DespachoHorasFragment();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contenedor_despacho,odh)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        break;
                }
            }
        });
        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(int tabId)
            {
                switch (tabId) {
                    case R.id.tab_vueltas:
                        despachoVueltasFragment odv = new despachoVueltasFragment();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contenedor_despacho, odv)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        break;
                    case R.id.tab_time:
                        DespachoHorasFragment odh = new DespachoHorasFragment();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contenedor_despacho,odh)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        break;
                }
            }
        });
        return view;
    }
}