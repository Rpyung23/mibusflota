package com.vigitrackecuador.mibusflota;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.itextpdf.text.Paragraph;
import com.vigitrackecuador.mibusflota.POO.cContrles;
import com.vigitrackecuador.mibusflota.POO.cIdBus;
import com.vigitrackecuador.mibusflota.Views.MenuActivity;
import com.vigitrackecuador.mibusflota.Views.Vueltas_PDF_Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    JsonArrayRequest jsonArrayRequestIdBuses;
    RequestQueue requestQueueIdBuses;
    ArrayList<cIdBus> arrayIdBuses ;
    public static ArrayList<String> arrayStringIdBus ;
    cIdBus oIdBus;

    ProgressDialog progressDialog;
    ImageButton imageButtonConfig;
    Spinner oSpinner;
    Button btn_Ingresar;
    String user_name_xml;
    String pass_name_xml;
    TextView textViewUser;
    TextView textViewPass;
    TextView textViewEmpresa;
    RequestQueue requestQueue; //permite la conccion con el webService
    JsonArrayRequest jsonObjectRequest; //permite la conccion con el webService

    public static ArrayList<cContrles> oControlArrayList;
    cContrles oCtrl;
    JsonArrayRequest jsonArrayRequestControl;
    RequestQueue requestQueueControl;

    String empresa;
    String empresaLeer;
    public static String url_login;
    public static String url_mapa;
    public static String url_mapa_gps;
    public static String url_despacho_horas;
    public static String url_despacho;
    public static String url_despacho_pdf;
    public static String url_control_mapa;//los relojes del mata;
    public static String url_ConteoCliente_id;//obtengo las id de los conteo
    public static String url_ConteoCliente_valor;//obtengo la informacion despues de obtner las id con url_Conteo_id
    public static String Codi_observador;
    public static String UserLeer;
    public static String PassLeer;
    boolean banControl=false;
    boolean banIdBus=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        imageButtonConfig = findViewById(R.id.config_login);
        oSpinner = findViewById(R.id.spinnerEmpresas);
        ArrayAdapter<String> oEmpresas = new ArrayAdapter<>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.Empresas));
        oEmpresas.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        oSpinner.setAdapter(oEmpresas);
        textViewEmpresa = findViewById(R.id.empresaSP);
        textViewUser = findViewById(R.id.txtusuarioLogin);
        textViewPass = findViewById(R.id.txtPassLogin);
        cargarsharedPreferendEMpre();
        if (empresaLeer.equals("ErrorE") || UserLeer.equals("ErrorU") || PassLeer.equals("ErrorP")) {
            oSpinner.setEnabled(true);
        } else {
            oSpinner.setEnabled(false);
            empresa=empresaLeer;
            if (empresaLeer.equals("Liribamba")) {
                url_login = "http://www.vigitrackecuador.com/webservice/liribamba/verificarLoginWebService.php";
                url_mapa = "http://www.vigitrackecuador.com/webservice/liribamba/mapaFlotaWebService.php";//obtiene las id de los buses
                url_mapa_gps = "http://www.vigitrackecuador.com/webservice/liribamba/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                url_despacho = "http://www.vigitrackecuador.com/webservice/liribamba/despachoWebService.php";
                url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/liribamba/despachoPdfwebservice.php";
                url_control_mapa = "http://www.vigitrackecuador.com/webservice/liribamba/controlesWebService.php";
                url_despacho_horas = "http://www.vigitrackecuador.com/webservice/liribamba/despachoHorasWebService.php";
            }
            if (empresaLeer.equals("Cooperativa Salitre")) {
                url_login = "http://www.vigitrackecuador.com/webservice/rsalitrena/verificarLoginWebService.php";
                url_mapa = "http://www.vigitrackecuador.com/webservice/rsalitrena/mapaFlotaWebService.php";//obtiene las id de los buses
                url_mapa_gps = "http://www.vigitrackecuador.com/webservice/rsalitrena/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                url_despacho = "http://www.vigitrackecuador.com/webservice/rsalitrena/despachoWebService.php";
                url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/rsalitrena/despachoPdfwebservice.php";
                url_control_mapa = "http://www.vigitrackecuador.com/webservice/rsalitrena/controlesWebService.php";
                url_despacho_horas = "http://www.vigitrackecuador.com/webservice/rsalitrena/despachoHorasWebService.php";
            }
            if (empresaLeer.equals("Puruha")) {
                url_login = "http://www.vigitrackecuador.com/webservice/puruha/verificarLoginWebService.php";
                url_mapa = "http://www.vigitrackecuador.com/webservice/puruha/mapaFlotaWebService.php";//obtiene las id de los buses
                url_mapa_gps = "http://www.vigitrackecuador.com/webservice/puruha/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                url_despacho = "http://www.vigitrackecuador.com/webservice/puruha/despachoWebService.php";
                url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/puruha/despachoPdfwebservice.php";
                url_control_mapa = "http://www.vigitrackecuador.com/webservice/puruha/controlesWebService.php";
                url_despacho_horas = "http://www.vigitrackecuador.com/webservice/puruha/despachoHorasWebService.php";
            }
            if (empresaLeer.equals("Horizonte")) {
                url_login = "http://www.vigitrackecuador.com/webservice/horizonte/verificarLoginWebService.php";
                url_mapa = "http://www.vigitrackecuador.com/webservice/horizonte/mapaFlotaWebService.php";//obtiene las id de los buses
                url_mapa_gps = "http://www.vigitrackecuador.com/webservice/horizonte/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                url_despacho = "http://www.vigitrackecuador.com/webservice/horizonte/despachoWebService.php";
                url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/horizonte/despachoPdfwebservice.php";
                url_control_mapa = "http://www.vigitrackecuador.com/webservice/horizonte/controlesWebService.php";
                url_despacho_horas = "http://www.vigitrackecuador.com/webservice/horizonte/despachoHorasWebService.php";
            }
            if (empresaLeer.equals("Trunsa"))
            {
                url_login = "http://www.vigitrackecuador.com/webservice/trunsa/verificarLoginWebService.php";
                url_mapa = "http://www.vigitrackecuador.com/webservice/trunsa/mapaFlotaWebService.php";//obtiene las id de los buses
                url_mapa_gps = "http://www.vigitrackecuador.com/webservice/trunsa/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                url_despacho = "http://www.vigitrackecuador.com/webservice/trunsa/despachoWebService.php";
                url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/trunsa/despachoPdfwebservice.php";
                url_control_mapa = "http://www.vigitrackecuador.com/webservice/trunsa/controlesWebService.php";
                url_despacho_horas = "http://www.vigitrackecuador.com/webservice/trunsa/despachoHorasWebService.php";
            }
            if (empresaLeer.equals("Citim"))
            {
                url_login = "http://www.vigitrackecuador.com/webservice/citim/verificarLoginWebService.php";
                url_mapa = "http://www.vigitrackecuador.com/webservice/citim/mapaFlotaWebService.php";//obtiene las id de los buses
                url_mapa_gps = "http://www.vigitrackecuador.com/webservice/citim/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                url_despacho = "http://www.vigitrackecuador.com/webservice/citim/despachoWebService.php";
                url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/citim/despachoPdfwebservice.php";
                url_control_mapa = "http://www.vigitrackecuador.com/webservice/citim/controlesWebService.php";
                url_despacho_horas = "http://www.vigitrackecuador.com/webservice/citim/despachoHorasWebService.php";
            }
            if (empresaLeer.equals("Orquidia Amazonica"))
            {
                url_login = "http://www.vigitrackecuador.com/webservice/oamazonica/verificarLoginWebService.php";
                url_mapa = "http://www.vigitrackecuador.com/webservice/oamazonica/mapaFlotaWebService.php";//obtiene las id de los buses
                url_mapa_gps = "http://www.vigitrackecuador.com/webservice/oamazonica/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                url_despacho = "http://www.vigitrackecuador.com/webservice/oamazonica/despachoWebService.php";
                url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/oamazonica/despachoPdfwebservice.php";
                url_control_mapa = "http://www.vigitrackecuador.com/webservice/oamazonica/controlesWebService.php";
                url_despacho_horas = "http://www.vigitrackecuador.com/webservice/oamazonica/despachoHorasWebService.php";

            }
            if (empresaLeer.equals("Rio Tigre"))
            {
                url_login = "http://www.vigitrackecuador.com/webservice/rtigre/verificarLoginWebService.php";
                url_mapa = "http://www.vigitrackecuador.com/webservice/rtigre/mapaFlotaWebService.php";//obtiene las id de los buses
                url_mapa_gps = "http://www.vigitrackecuador.com/webservice/rtigre/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                url_despacho = "http://www.vigitrackecuador.com/webservice/rtigre/despachoWebService.php";
                url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/rtigre/despachoPdfwebservice.php";
                url_control_mapa = "http://www.vigitrackecuador.com/webservice/rtigre/controlesWebService.php";
                url_despacho_horas = "http://www.vigitrackecuador.com/webservice/rtigre/despachoHorasWebService.php";

            }
            if (empresaLeer.equals("Marcelino"))
            {
                Toast.makeText(this, "Sin ip", Toast.LENGTH_SHORT).show();
            }
            if (empresaLeer.equals("Prado-Eco"))
            {
                url_login = "http://www.vigitrackecuador.com/webservice/prado-eco/verificarLoginWebService.php";
                url_mapa = "http://www.vigitrackecuador.com/webservice/prado-eco/mapaFlotaWebService.php";//obtiene las id de los buses
                url_mapa_gps = "http://www.vigitrackecuador.com/webservice/prado-eco/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                url_despacho = "http://www.vigitrackecuador.com/webservice/prado-eco/despachoWebService.php";
                url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/prado-eco/despachoPdfwebservice.php";
                url_control_mapa = "http://www.vigitrackecuador.com/webservice/prado-eco/controlesWebService.php";
                url_despacho_horas = "http://www.vigitrackecuador.com/webservice/prado-eco/despachoHorasWebService.php";

            }
            if (empresaLeer.equals("Consorcio Rem"))
            {
                url_login = "http://www.vigitrackecuador.com/webservice/consorcio-r/verificarLoginWebService.php";
                url_mapa = "http://www.vigitrackecuador.com/webservice/consorcio-r/mapaFlotaWebService.php";//obtiene las id de los buses
                url_mapa_gps = "http://www.vigitrackecuador.com/webservice/consorcio-r/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                url_despacho = "http://www.vigitrackecuador.com/webservice/consorcio-r/despachoWebService.php";
                url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/consorcio-r/despachoPdfwebservice.php";
                url_control_mapa = "http://www.vigitrackecuador.com/webservice/consorcio-r/controlesWebService.php";
                url_despacho_horas = "http://www.vigitrackecuador.com/webservice/consorcio-r/despachoHorasWebService.php";

            }
            if (empresaLeer.equals("Sultana de Cotopaxi"))
            {
                url_login = "http://www.vigitrackecuador.com/webservice/cotopaxi/verificarLoginWebService.php";
                url_mapa = "http://www.vigitrackecuador.com/webservice/cotopaxi/mapaFlotaWebService.php";//obtiene las id de los buses
                url_mapa_gps = "http://www.vigitrackecuador.com/webservice/cotopaxi/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                url_despacho = "http://www.vigitrackecuador.com/webservice/cotopaxi/despachoWebService.php";
                url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/cotopaxi/despachoPdfwebservice.php";
                url_control_mapa = "http://www.vigitrackecuador.com/webservice/cotopaxi/controlesWebService.php";
                url_despacho_horas = "http://www.vigitrackecuador.com/webservice/cotopaxi/despachoHorasWebService.php";

            }
            if (empresaLeer.equals("Citibus"))
            {
                url_login = "http://www.vigitrackecuador.com/webservice/citibus/verificarLoginWebService.php";
                url_mapa = "http://www.vigitrackecuador.com/webservice/citibus/mapaFlotaWebService.php";//obtiene las id de los buses
                url_mapa_gps = "http://www.vigitrackecuador.com/webservice/citibus/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                url_despacho = "http://www.vigitrackecuador.com/webservice/citibus/despachoWebService.php";
                url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/citibus/despachoPdfwebservice.php";
                url_control_mapa = "http://www.vigitrackecuador.com/webservice/citibus/controlesWebService.php";
                url_despacho_horas = "http://www.vigitrackecuador.com/webservice/citibus/despachoHorasWebService.php";

            }
            if (empresaLeer.equals("Santa Lucia"))
            {
                url_login = "http://www.vigitrackecuador.com/webservice/slucia/verificarLoginWebService.php";
                url_mapa = "http://www.vigitrackecuador.com/webservice/slucia/mapaFlotaWebService.php";//obtiene las id de los buses
                url_mapa_gps = "http://www.vigitrackecuador.com/webservice/slucia/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                url_despacho = "http://www.vigitrackecuador.com/webservice/slucia/despachoWebService.php";
                url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/slucia/despachoPdfwebservice.php";
                url_control_mapa = "http://www.vigitrackecuador.com/webservice/slucia/controlesWebService.php";
                url_despacho_horas = "http://www.vigitrackecuador.com/webservice/slucia/despachoHorasWebService.php";

            }
            if (empresaLeer.equals("Ruta Portovejense"))
            {
                url_login = "http://www.vigitrackecuador.com/webservice/rportov/verificarLoginWebService.php";
                url_mapa = "http://www.vigitrackecuador.com/webservice/rportov/mapaFlotaWebService.php";//obtiene las id de los buses
                url_mapa_gps = "http://www.vigitrackecuador.com/webservice/rportov/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                url_despacho = "http://www.vigitrackecuador.com/webservice/rportov/despachoWebService.php";
                url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/rportov/despachoPdfwebservice.php";
                url_control_mapa = "http://www.vigitrackecuador.com/webservice/rportov/controlesWebService.php";
                url_despacho_horas = "http://www.vigitrackecuador.com/webservice/rportov/despachoHorasWebService.php";

            }
            if (empresaLeer.equals("Santa Elisa"))
            {
                url_login = "http://www.vigitrackecuador.com/webservice/selisa/verificarLoginWebService.php";
                url_mapa = "http://www.vigitrackecuador.com/webservice/selisa/mapaFlotaWebService.php";//obtiene las id de los buses
                url_mapa_gps = "http://www.vigitrackecuador.com/webservice/selisa/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                url_despacho = "http://www.vigitrackecuador.com/webservice/selisa/despachoWebService.php";
                url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/selisa/despachoPdfwebservice.php";
                url_control_mapa = "http://www.vigitrackecuador.com/webservice/selisa/controlesWebService.php";
                url_despacho_horas = "http://www.vigitrackecuador.com/webservice/selisa/despachoHorasWebService.php";

            }
            if (empresaLeer.equals("Yaguachi"))
            {
                url_login = "http://www.vigitrackecuador.com/webservice/yaguachi/verificarLoginWebService.php";
                url_mapa = "http://www.vigitrackecuador.com/webservice/yaguachi/mapaFlotaWebService.php";//obtiene las id de los buses
                url_mapa_gps = "http://www.vigitrackecuador.com/webservice/yaguachi/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                url_despacho = "http://www.vigitrackecuador.com/webservice/yaguachi/despachoWebService.php";
                url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/yaguachi/despachoPdfwebservice.php";
                url_control_mapa = "http://www.vigitrackecuador.com/webservice/yaguachi/controlesWebService.php";
                url_despacho_horas = "http://www.vigitrackecuador.com/webservice/yaguachi/despachoHorasWebService.php";

            }
            if (empresaLeer.equals("Santa Clara"))
            {
                url_login = "http://www.vigitrackecuador.com/webservice/sclara/verificarLoginWebService.php";
                url_mapa = "http://www.vigitrackecuador.com/webservice/sclara/mapaFlotaWebService.php";//obtiene las id de los buses
                url_mapa_gps = "http://www.vigitrackecuador.com/webservice/sclara/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                url_despacho = "http://www.vigitrackecuador.com/webservice/sclara/despachoWebService.php";
                url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/sclara/despachoPdfwebservice.php";
                url_control_mapa = "http://www.vigitrackecuador.com/webservice/sclara/controlesWebService.php";
                url_despacho_horas = "http://www.vigitrackecuador.com/webservice/sclara/despachoHorasWebService.php";

            }
            if (empresaLeer.equals("Intra-Puyo"))
            {
                url_login = "http://www.vigitrackecuador.com/webservice/intra-puyo/verificarLoginWebService.php";
                url_mapa = "http://www.vigitrackecuador.com/webservice/intra-puyo/mapaFlotaWebService.php";//obtiene las id de los buses
                url_mapa_gps = "http://www.vigitrackecuador.com/webservice/intra-puyo/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                url_despacho = "http://www.vigitrackecuador.com/webservice/intra-puyo/despachoWebService.php";
                url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/intra-puyo/despachoPdfwebservice.php";
                url_control_mapa = "http://www.vigitrackecuador.com/webservice/intra-puyo/controlesWebService.php";
                url_despacho_horas = "http://www.vigitrackecuador.com/webservice/intra-puyo/despachoHorasWebService.php";

            }
            if (empresaLeer.equals("Trujama SA"))
            {
                url_login = "http://www.vigitrackecuador.com/webservice/trujamasa/verificarLoginWebService.php";
                url_mapa = "http://www.vigitrackecuador.com/webservice/trujamasa/mapaFlotaWebService.php";//obtiene las id de los buses
                url_mapa_gps = "http://www.vigitrackecuador.com/webservice/trujamasa/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                url_despacho = "http://www.vigitrackecuador.com/webservice/trujamasa/despachoWebService.php";
                url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/trujamasa/despachoPdfwebservice.php";
                url_control_mapa = "http://www.vigitrackecuador.com/webservice/trujamasa/controlesWebService.php";
                url_despacho_horas = "http://www.vigitrackecuador.com/webservice/trujamasa/despachoHorasWebService.php";

            }
            if(empresaLeer.equals("Sucua"))
            {
                url_login = "http://www.vigitrackecuador.com/webservice/csucua/verificarLoginWebService.php";
                url_mapa = "http://www.vigitrackecuador.com/webservice/csucua/mapaFlotaWebService.php";//obtiene las id de los buses
                url_mapa_gps = "http://www.vigitrackecuador.com/webservice/csucua/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                url_despacho = "http://www.vigitrackecuador.com/webservice/csucua/despachoWebService.php";
                url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/csucua/despachoPdfwebservice.php";
                url_control_mapa = "http://www.vigitrackecuador.com/webservice/csucua/controlesWebService.php";
                url_despacho_horas = "http://www.vigitrackecuador.com/webservice/csucua/despachoHorasWebService.php";

            }
            if (empresaLeer.equals("Otavalo-Lagos"))
            {
                url_login = "http://www.vigitrackecuador.com/webservice/otavalo-lag/verificarLoginWebService.php";
                url_mapa = "http://www.vigitrackecuador.com/webservice/otavalo-lag/mapaFlotaWebService.php";//obtiene las id de los buses
                url_mapa_gps = "http://www.vigitrackecuador.com/webservice/otavalo-lag/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                url_despacho = "http://www.vigitrackecuador.com/webservice/otavalo-lag/despachoWebService.php";
                url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/otavalo-lag/despachoPdfwebservice.php";
                url_control_mapa = "http://www.vigitrackecuador.com/webservice/otavalo-lag/controlesWebService.php";
                url_despacho_horas = "http://www.vigitrackecuador.com/webservice/otavalo-lag/despachoHorasWebService.php";

            }
            if (empresaLeer.equals("Transfransa"))
            {
                url_login = "http://www.vigitrackecuador.com/webservice/transfransa/verificarLoginWebService.php";
                url_mapa = "http://www.vigitrackecuador.com/webservice/transfransa/mapaFlotaWebService.php";//obtiene las id de los buses
                url_mapa_gps = "http://www.vigitrackecuador.com/webservice/transfransa/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                url_despacho = "http://www.vigitrackecuador.com/webservice/transfransa/despachoWebService.php";
                url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/transfransa/despachoPdfwebservice.php";
                url_control_mapa = "http://www.vigitrackecuador.com/webservice/transfransa/controlesWebService.php";
                url_despacho_horas = "http://www.vigitrackecuador.com/webservice/transfransa/despachoHorasWebService.php";

            }
            if (empresaLeer.equals("Tungurahua"))
            {
                url_login = "http://www.vigitrackecuador.com/webservice/tungurahua/verificarLoginWebService.php";
                url_mapa = "http://www.vigitrackecuador.com/webservice/tungurahua/mapaFlotaWebService.php";//obtiene las id de los buses
                url_mapa_gps = "http://www.vigitrackecuador.com/webservice/tungurahua/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                url_despacho = "http://www.vigitrackecuador.com/webservice/tungurahua/despachoWebService.php";
                url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/tungurahua/despachoPdfwebservice.php";
                url_control_mapa = "http://www.vigitrackecuador.com/webservice/tungurahua/controlesWebService.php";
                url_despacho_horas = "http://www.vigitrackecuador.com/webservice/tungurahua/despachoHorasWebService.php";

            }
            if (empresaLeer.equals("Santa Martha"))
            {
                url_login = "http://www.vigitrackecuador.com/webservice/smartha/verificarLoginWebService.php";
                url_mapa = "http://www.vigitrackecuador.com/webservice/smartha/mapaFlotaWebService.php";//obtiene las id de los buses
                url_mapa_gps = "http://www.vigitrackecuador.com/webservice/smartha/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                url_despacho = "http://www.vigitrackecuador.com/webservice/smartha/despachoWebService.php";
                url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/smartha/despachoPdfwebservice.php";
                url_control_mapa = "http://www.vigitrackecuador.com/webservice/smartha/controlesWebService.php";
                url_despacho_horas = "http://www.vigitrackecuador.com/webservice/smartha/despachoHorasWebService.php";

            }
            if (empresaLeer.equals("Miraflores"))
            {
                url_login = "http://www.vigitrackecuador.com/webservice/viaflores/verificarLoginWebService.php";
                url_mapa = "http://www.vigitrackecuador.com/webservice/viaflores/mapaFlotaWebService.php";//obtiene las id de los buses
                url_mapa_gps = "http://www.vigitrackecuador.com/webservice/viaflores/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                url_despacho = "http://www.vigitrackecuador.com/webservice/viaflores/despachoWebService.php";
                url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/viaflores/despachoPdfwebservice.php";
                url_control_mapa = "http://www.vigitrackecuador.com/webservice/viaflores/controlesWebService.php";
                url_despacho_horas = "http://www.vigitrackecuador.com/webservice/viaflores/despachoHorasWebService.php";

            }
        }
        oSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    empresa = parent.getItemAtPosition(position).toString();
                }
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        url_login = "http://www.vigitrackecuador.com//webservice/liribamba/verificarLoginWebService.php";
                        url_mapa = "http://www.vigitrackecuador.com//webservice/liribamba/mapaFlotaWebService.php";//obtiene las id de los buses
                        url_mapa_gps = "http://www.vigitrackecuador.com/webservice/liribamba/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                        url_despacho = "http://www.vigitrackecuador.com/webservice/liribamba/despachoWebService.php";
                        url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/liribamba/despachoPdfwebservice.php";
                        url_control_mapa = "http://www.vigitrackecuador.com/webservice/liribamba/controlesWebService.php";
                        url_despacho_horas = "http://www.vigitrackecuador.com/webservice/liribamba/despachoHorasWebService.php";
                        break;
                    case 2:
                        url_login = "http://www.vigitrackecuador.com/webservice/rsalitrena/verificarLoginWebService.php";
                        url_mapa = "http://www.vigitrackecuador.com/webservice/rsalitrena/mapaFlotaWebService.php";//obtiene las id de los buses
                        url_mapa_gps = "http://www.vigitrackecuador.com/webservice/rsalitrena/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                        url_despacho = "http://www.vigitrackecuador.com/webservice/rsalitrena/despachoWebService.php";
                        url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/rsalitrena/despachoPdfwebservice.php";
                        url_control_mapa = "http://www.vigitrackecuador.com/webservice/rsalitrena/controlesWebService.php";
                        url_despacho_horas = "http://www.vigitrackecuador.com/webservice/rsalitrena/despachoHorasWebService.php";
                        break;
                    case 3:
                        url_login = "http://www.vigitrackecuador.com/webservice/puruha/verificarLoginWebService.php";
                        url_mapa = "http://www.vigitrackecuador.com/webservice/puruha/mapaFlotaWebService.php";//obtiene las id de los buses
                        url_mapa_gps = "http://www.vigitrackecuador.com/webservice/puruha/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                        url_despacho = "http://www.vigitrackecuador.com/webservice/puruha/despachoWebService.php";
                        url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/puruha/despachoPdfwebservice.php";
                        url_control_mapa = "http://www.vigitrackecuador.com/webservice/puruha/controlesWebService.php";
                        url_despacho_horas = "http://www.vigitrackecuador.com/webservice/puruha/despachoHorasWebService.php";
                        break;
                    case 4:
                        url_login = "http://www.vigitrackecuador.com/webservice/horizonte/verificarLoginWebService.php";
                        url_mapa = "http://www.vigitrackecuador.com/webservice/horizonte/mapaFlotaWebService.php";//obtiene las id de los buses
                        url_mapa_gps = "http://www.vigitrackecuador.com/webservice/horizonte/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                        url_despacho = "http://www.vigitrackecuador.com/webservice/horizonte/despachoWebService.php";
                        url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/horizonte/despachoPdfwebservice.php";
                        url_control_mapa = "http://www.vigitrackecuador.com//webservice/horizonte/controlesWebService.php";
                        url_despacho_horas = "http://www.vigitrackecuador.com/webservice/horizonte/despachoHorasWebService.php";
                        break;
                    case 5:
                        url_login = "http://www.vigitrackecuador.com/webservice/trunsa/verificarLoginWebService.php";
                        url_mapa = "http://www.vigitrackecuador.com/webservice/trunsa/mapaFlotaWebService.php";//obtiene las id de los buses
                        url_mapa_gps = "http://www.vigitrackecuador.com/webservice/trunsa/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                        url_despacho = "http://www.vigitrackecuador.com/webservice/trunsa/despachoWebService.php";
                        url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/trunsa/despachoPdfwebservice.php";
                        url_control_mapa = "http://www.vigitrackecuador.com/webservice/trunsa/controlesWebService.php";
                        url_despacho_horas = "http://www.vigitrackecuador.com/webservice/trunsa/despachoHorasWebService.php";
                        break;
                    case 6:
                        url_login = "http://www.vigitrackecuador.com/webservice/citim/verificarLoginWebService.php";
                        url_mapa = "http://www.vigitrackecuador.com/webservice/citim/mapaFlotaWebService.php";//obtiene las id de los buses
                        url_mapa_gps = "http://www.vigitrackecuador.com/webservice/citim/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                        url_despacho = "http://www.vigitrackecuador.com/webservice/citim/despachoWebService.php";
                        url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/citim/despachoPdfwebservice.php";
                        url_control_mapa = "http://www.vigitrackecuador.com/webservice/citim/controlesWebService.php";
                        url_despacho_horas = "http://www.vigitrackecuador.com/webservice/citim/despachoHorasWebService.php";
                        break;
                    case 7:
                        url_login = "http://www.vigitrackecuador.com/webservice/oamazonica/verificarLoginWebService.php";
                        url_mapa = "http://www.vigitrackecuador.com/webservice/oamazonica/mapaFlotaWebService.php";//obtiene las id de los buses
                        url_mapa_gps = "http://www.vigitrackecuador.com/webservice/oamazonica/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                        url_despacho = "http://www.vigitrackecuador.com/webservice/oamazonica/despachoWebService.php";
                        url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/oamazonica/despachoPdfwebservice.php";
                        url_control_mapa = "http://www.vigitrackecuador.com/webservice/oamazonica/controlesWebService.php";
                        url_despacho_horas = "http://www.vigitrackecuador.com/webservice/oamazonica/despachoHorasWebService.php";
                        break;
                    case 8:
                        url_login = "http://www.vigitrackecuador.com/webservice/rtigre/verificarLoginWebService.php";
                        url_mapa = "http://www.vigitrackecuador.com/webservice/rtigre/mapaFlotaWebService.php";//obtiene las id de los buses
                        url_mapa_gps = "http://www.vigitrackecuador.com/webservice/rtigre/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                        url_despacho = "http://www.vigitrackecuador.com/webservice/rtigre/despachoWebService.php";
                        url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/rtigre/despachoPdfwebservice.php";
                        url_control_mapa = "http://www.vigitrackecuador.com/webservice/rtigre/controlesWebService.php";
                        url_despacho_horas = "http://www.vigitrackecuador.com/webservice/rtigre/despachoHorasWebService.php";
                        break;
                    case 9:

                        break;
                    case 10:
                        url_login = "http://www.vigitrackecuador.com/webservice/prado-eco/verificarLoginWebService.php";
                        url_mapa = "http://www.vigitrackecuador.com/webservice/prado-eco/mapaFlotaWebService.php";//obtiene las id de los buses
                        url_mapa_gps = "http://www.vigitrackecuador.com/webservice/prado-eco/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                        url_despacho = "http://www.vigitrackecuador.com/webservice/prado-eco/despachoWebService.php";
                        url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/prado-eco/despachoPdfwebservice.php";
                        url_control_mapa = "http://www.vigitrackecuador.com/webservice/prado-eco/controlesWebService.php";
                        url_despacho_horas = "http://www.vigitrackecuador.com/webservice/prado-eco/despachoHorasWebService.php";
                        break;
                    case 11:
                        url_login = "http://www.vigitrackecuador.com/webservice/consorcio-r/verificarLoginWebService.php";
                        url_mapa = "http://www.vigitrackecuador.com/webservice/consorcio-r/mapaFlotaWebService.php";//obtiene las id de los buses
                        url_mapa_gps = "http://www.vigitrackecuador.com/webservice/consorcio-r/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                        url_despacho = "http://www.vigitrackecuador.com/webservice/consorcio-r/despachoWebService.php";
                        url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/consorcio-r/despachoPdfwebservice.php";
                        url_control_mapa = "http://www.vigitrackecuador.com/webservice/consorcio-r/controlesWebService.php";
                        url_despacho_horas = "http://www.vigitrackecuador.com/webservice/consorcio-r/despachoHorasWebService.php";
                        break;
                    case 12:
                        url_login = "http://www.vigitrackecuador.com/webservice/cotopaxi/verificarLoginWebService.php";
                        url_mapa = "http://www.vigitrackecuador.com/webservice/cotopaxi/mapaFlotaWebService.php";//obtiene las id de los buses
                        url_mapa_gps = "http://www.vigitrackecuador.com/webservice/cotopaxi/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                        url_despacho = "http://www.vigitrackecuador.com/webservice/cotopaxi/despachoWebService.php";
                        url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/cotopaxi/despachoPdfwebservice.php";
                        url_control_mapa = "http://www.vigitrackecuador.com/webservice/cotopaxi/controlesWebService.php";
                        url_despacho_horas = "http://www.vigitrackecuador.com/webservice/cotopaxi/despachoHorasWebService.php";
                        break;
                    case 13:
                        url_login = "http://www.vigitrackecuador.com/webservice/citibus/verificarLoginWebService.php";
                        url_mapa = "http://www.vigitrackecuador.com/webservice/citibus/mapaFlotaWebService.php";//obtiene las id de los buses
                        url_mapa_gps = "http://www.vigitrackecuador.com/webservice/citibus/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                        url_despacho = "http://www.vigitrackecuador.com/webservice/citibus/despachoWebService.php";
                        url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/citibus/despachoPdfwebservice.php";
                        url_control_mapa = "http://www.vigitrackecuador.com/webservice/citibus/controlesWebService.php";
                        url_despacho_horas = "http://www.vigitrackecuador.com/webservice/citibus/despachoHorasWebService.php";
                        break;
                    case 14:
                        url_login = "http://www.vigitrackecuador.com/webservice/slucia/verificarLoginWebService.php";
                        url_mapa = "http://www.vigitrackecuador.com/webservice/slucia/mapaFlotaWebService.php";//obtiene las id de los buses
                        url_mapa_gps = "http://www.vigitrackecuador.com/webservice/slucia/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                        url_despacho = "http://www.vigitrackecuador.com/webservice/slucia/despachoWebService.php";
                        url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/slucia/despachoPdfwebservice.php";
                        url_control_mapa = "http://www.vigitrackecuador.com/webservice/slucia/controlesWebService.php";
                        url_despacho_horas = "http://www.vigitrackecuador.com/webservice/slucia/despachoHorasWebService.php";
                        break;
                    case 15:
                        url_login = "http://www.vigitrackecuador.com/webservice/rportov/verificarLoginWebService.php";
                        url_mapa = "http://www.vigitrackecuador.com/webservice/rportov/mapaFlotaWebService.php";//obtiene las id de los buses
                        url_mapa_gps = "http://www.vigitrackecuador.com/webservice/rportov/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                        url_despacho = "http://www.vigitrackecuador.com/webservice/rportov/despachoWebService.php";
                        url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/rportov/despachoPdfwebservice.php";
                        url_control_mapa = "http://www.vigitrackecuador.com/webservice/rportov/controlesWebService.php";
                        url_despacho_horas = "http://www.vigitrackecuador.com/webservice/rportov/despachoHorasWebService.php";
                        break;
                    case 16:
                        url_login = "http://www.vigitrackecuador.com/webservice/selisa/verificarLoginWebService.php";
                        url_mapa = "http://www.vigitrackecuador.com/webservice/selisa/mapaFlotaWebService.php";//obtiene las id de los buses
                        url_mapa_gps = "http://www.vigitrackecuador.com/webservice/selisa/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                        url_despacho = "http://www.vigitrackecuador.com/webservice/selisa/despachoWebService.php";
                        url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/selisa/despachoPdfwebservice.php";
                        url_control_mapa = "http://www.vigitrackecuador.com/webservice/selisa/controlesWebService.php";
                        url_despacho_horas = "http://www.vigitrackecuador.com/webservice/selisa/despachoHorasWebService.php";
                        break;
                    case 17:
                        url_login = "http://www.vigitrackecuador.com/webservice/yaguachi/verificarLoginWebService.php";
                        url_mapa = "http://www.vigitrackecuador.com/webservice/yaguachi/mapaFlotaWebService.php";//obtiene las id de los buses
                        url_mapa_gps = "http://www.vigitrackecuador.com/webservice/yaguachi/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                        url_despacho = "http://www.vigitrackecuador.com/webservice/yaguachi/despachoWebService.php";
                        url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/yaguachi/despachoPdfwebservice.php";
                        url_control_mapa = "http://www.vigitrackecuador.com/webservice/yaguachi/controlesWebService.php";
                        url_despacho_horas = "http://www.vigitrackecuador.com/webservice/yaguachi/despachoHorasWebService.php";
                        break;
                    case 18:
                        url_login = "http://www.vigitrackecuador.com/webservice/sclara/verificarLoginWebService.php";
                        url_mapa = "http://www.vigitrackecuador.com/webservice/sclara/mapaFlotaWebService.php";//obtiene las id de los buses
                        url_mapa_gps = "http://www.vigitrackecuador.com/webservice/sclara/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                        url_despacho = "http://www.vigitrackecuador.com/webservice/sclara/despachoWebService.php";
                        url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/sclara/despachoPdfwebservice.php";
                        url_control_mapa = "http://www.vigitrackecuador.com/webservice/sclara/controlesWebService.php";
                        url_despacho_horas = "http://www.vigitrackecuador.com/webservice/sclara/despachoHorasWebService.php";
                        break;
                    case 19:
                        url_login = "http://www.vigitrackecuador.com/webservice/intra-puyo/verificarLoginWebService.php";
                        url_mapa = "http://www.vigitrackecuador.com/webservice/intra-puyo/mapaFlotaWebService.php";//obtiene las id de los buses
                        url_mapa_gps = "http://www.vigitrackecuador.com/webservice/intra-puyo/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                        url_despacho = "http://www.vigitrackecuador.com/webservice/intra-puyo/despachoWebService.php";
                        url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/intra-puyo/despachoPdfwebservice.php";
                        url_control_mapa = "http://www.vigitrackecuador.com/webservice/intra-puyo/controlesWebService.php";
                        url_despacho_horas = "http://www.vigitrackecuador.com/webservice/intra-puyo/despachoHorasWebService.php";
                        break;
                    case 20:
                        url_login = "http://www.vigitrackecuador.com/webservice/csucua/verificarLoginWebService.php";
                        url_mapa = "http://www.vigitrackecuador.com/webservice/csucua/mapaFlotaWebService.php";//obtiene las id de los buses
                        url_mapa_gps = "http://www.vigitrackecuador.com/webservice/csucua/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                        url_despacho = "http://www.vigitrackecuador.com/webservice/csucua/despachoWebService.php";
                        url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/csucua/despachoPdfwebservice.php";
                        url_control_mapa = "http://www.vigitrackecuador.com/webservice/csucua/controlesWebService.php";
                        url_despacho_horas = "http://www.vigitrackecuador.com/webservice/csucua/despachoHorasWebService.php";
                        break;
                    case 21:
                        url_login = "http://www.vigitrackecuador.com/webservice/trujamasa/verificarLoginWebService.php";
                        url_mapa = "http://www.vigitrackecuador.com/webservice/trujamasa/mapaFlotaWebService.php";//obtiene las id de los buses
                        url_mapa_gps = "http://www.vigitrackecuador.com/webservice/trujamasa/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                        url_despacho = "http://www.vigitrackecuador.com/webservice/trujamasa/despachoWebService.php";
                        url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/trujamasa/despachoPdfwebservice.php";
                        url_control_mapa = "http://www.vigitrackecuador.com/webservice/trujamasa/controlesWebService.php";
                        url_despacho_horas = "http://www.vigitrackecuador.com/webservice/trujamasa/despachoHorasWebService.php";
                        break;
                    case 22:
                        url_login = "http://www.vigitrackecuador.com/webservice/otavalo-lag/verificarLoginWebService.php";
                        url_mapa = "http://www.vigitrackecuador.com/webservice/otavalo-lag/mapaFlotaWebService.php";//obtiene las id de los buses
                        url_mapa_gps = "http://www.vigitrackecuador.com/webservice/otavalo-lag/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                        url_despacho = "http://www.vigitrackecuador.com/webservice/otavalo-lag/despachoWebService.php";
                        url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/otavalo-lag/despachoPdfwebservice.php";
                        url_control_mapa = "http://www.vigitrackecuador.com/webservice/otavalo-lag/controlesWebService.php";
                        url_despacho_horas = "http://www.vigitrackecuador.com/webservice/otavalo-lag/despachoHorasWebService.php";
                        break;
                    case 23:
                        url_login = "http://www.vigitrackecuador.com/webservice/transfransa/verificarLoginWebService.php";
                        url_mapa = "http://www.vigitrackecuador.com/webservice/transfransa/mapaFlotaWebService.php";//obtiene las id de los buses
                        url_mapa_gps = "http://www.vigitrackecuador.com/webservice/transfransa/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                        url_despacho = "http://www.vigitrackecuador.com/webservice/transfransa/despachoWebService.php";
                        url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/transfransa/despachoPdfwebservice.php";
                        url_control_mapa = "http://www.vigitrackecuador.com/webservice/transfransa/controlesWebService.php";
                        url_despacho_horas = "http://www.vigitrackecuador.com/webservice/transfransa/despachoHorasWebService.php";
                        break;
                    case 24:
                        url_login = "http://www.vigitrackecuador.com/webservice/tungurahua/verificarLoginWebService.php";
                        url_mapa = "http://www.vigitrackecuador.com/webservice/tungurahua/mapaFlotaWebService.php";//obtiene las id de los buses
                        url_mapa_gps = "http://www.vigitrackecuador.com/webservice/tungurahua/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                        url_despacho = "http://www.vigitrackecuador.com/webservice/tungurahua/despachoWebService.php";
                        url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/tungurahua/despachoPdfwebservice.php";
                        url_control_mapa = "http://www.vigitrackecuador.com/webservice/tungurahua/controlesWebService.php";
                        url_despacho_horas = "http://www.vigitrackecuador.com/webservice/tungurahua/despachoHorasWebService.php";
                        break;

                    case 25:
                        url_login = "http://www.vigitrackecuador.com/webservice/smartha/verificarLoginWebService.php";
                        url_mapa = "http://www.vigitrackecuador.com/webservice/smartha/mapaFlotaWebService.php";//obtiene las id de los buses
                        url_mapa_gps = "http://www.vigitrackecuador.com/webservice/smartha/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                        url_despacho = "http://www.vigitrackecuador.com/webservice/smartha/despachoWebService.php";
                        url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/smartha/despachoPdfwebservice.php";
                        url_control_mapa = "http://www.vigitrackecuador.com/webservice/smartha/controlesWebService.php";
                        url_despacho_horas = "http://www.vigitrackecuador.com/webservice/smartha/despachoHorasWebService.php";
                        break;
                    case 26:
                        url_login = "http://www.vigitrackecuador.com/webservice/viaflores/verificarLoginWebService.php";
                        url_mapa = "http://www.vigitrackecuador.com/webservice/viaflores/mapaFlotaWebService.php";//obtiene las id de los buses
                        url_mapa_gps = "http://www.vigitrackecuador.com/webservice/viaflores/rastreoflotaWebService.php";//obtiene las posiciones de los buses
                        url_despacho = "http://www.vigitrackecuador.com/webservice/viaflores/despachoWebService.php";
                        url_despacho_pdf = "http://www.vigitrackecuador.com/webservice/viaflores/despachoPdfwebservice.php";
                        url_control_mapa = "http://www.vigitrackecuador.com/webservice/viaflores/controlesWebService.php";
                        url_despacho_horas = "http://www.vigitrackecuador.com/webservice/viaflores/despachoHorasWebService.php";
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(LoginActivity.this, "Seleccione una Empresa", Toast.LENGTH_SHORT).show();
            }
        });

        btn_Ingresar = findViewById(R.id.btn_Ingresar);
        btn_Ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ComprobarPermisos())
                {
                    user_name_xml = textViewUser.getText().toString();
                    pass_name_xml = textViewPass.getText().toString();
                    cargarWebService();
                }

            }
        });

        imageButtonConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder= new AlertDialog.Builder(LoginActivity.this);
                builder.setMessage("Desea Habilitar el cambio de Empresa");
                builder.setPositiveButton("Activar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        oSpinner.setEnabled(true);
                        Toast.makeText(LoginActivity.this, "Operacion Exitosa", Toast.LENGTH_SHORT).show();
                        textViewEmpresa.setText("");
                        textViewPass.setText("");
                        textViewUser.setText("");
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(LoginActivity.this, "Operacion Cancelada", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }

    private boolean ComprobarPermisos()
    {
        ActivityCompat.requestPermissions(LoginActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        if (ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this, "Permisos no Otorgados", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            {
                return true;
            }
    }

    private void cargarsharedPreferendEMpre() {
        SharedPreferences sharedPreferences = getSharedPreferences("EmpresaSeleccionada", Context.MODE_PRIVATE);
        empresaLeer = sharedPreferences.getString("empresa", "ErrorE");
        UserLeer = sharedPreferences.getString("usuario", "ErrorU");
        PassLeer = sharedPreferences.getString("pass", "ErrorP");
        //Toast.makeText(this, "GSP"+empresaLeer+"-"+ UserLeer+"-"+PassLeer, Toast.LENGTH_LONG).show();
        if (empresaLeer.equals("ErrorE") || UserLeer.equals("ErrorU") || PassLeer.equals("ErrorP"))
        {
            //Toast.makeText(LoginActivity.this,"Error SP",Toast.LENGTH_SHORT);
        } else {
            textViewEmpresa.setText(empresaLeer);
            textViewUser.setText(UserLeer);
            textViewPass.setText(PassLeer);
        }
    }

    private void cargarControlWebservice()
    {
        oControlArrayList = new ArrayList<cContrles>();
        jsonArrayRequestControl = new JsonArrayRequest(url_control_mapa, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response) {
                for (int aux = 0; aux < response.length(); aux++) {
                    oCtrl = new cContrles();
                    try {
                        JSONObject jsonObject = response.getJSONObject(aux);
                        oCtrl.setLatitud1(jsonObject.getDouble("Lati1Ctrl"));
                        oCtrl.setLongitud1(jsonObject.getDouble("Long1Ctrl"));
                        oCtrl.setLatitud2(jsonObject.getDouble("Lati2Ctrl"));
                        oCtrl.setLongitud2(jsonObject.getDouble("Long2Ctrl"));
                        oCtrl.setRadioControl(jsonObject.getInt("RadiCtrl"));
                        oControlArrayList.add(oCtrl);
                    } catch (JSONException e) {
                        Toast.makeText(LoginActivity.this, ":" + e, Toast.LENGTH_SHORT).show();
                    }
                }
                banControl=true;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Error Controles : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueueControl = Volley.newRequestQueue(getApplicationContext());
        requestQueueControl.add(jsonArrayRequestControl);
    }

    private void guardarPreferencia() {
        SharedPreferences sharedPreferences = getSharedPreferences("EmpresaSeleccionada", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //Toast.makeText(this, "GSP"+empresa+"-"+user_name_xml+"-"+pass_name_xml, Toast.LENGTH_LONG).show();
        editor.putString("empresa", empresa);
        editor.putString("usuario", user_name_xml);
        editor.putString("pass", pass_name_xml);
        editor.apply();
    }

    private void cargarWebService()
    {

        String envio1 = "username=" + user_name_xml;
        String envio2 = "clave=" + pass_name_xml;
        String url_login_App = url_login + "?" + envio1 + "&" + envio2;
        Toast.makeText(getApplicationContext(), "Ingresando Espere....", Toast.LENGTH_SHORT).show();
        jsonObjectRequest = new JsonArrayRequest(url_login_App, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = response.getJSONObject(0);
                    Codi_observador = jsonObject.getString("CodiObse");
                    guardarPreferencia();
                    cargarWebServiceIdBuses();
                    cargarControlWebservice();
                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                    startActivity(intent);
                    /*Toast.makeText(LoginActivity.this, "banControl : "+banControl+" *** banIdBus : "+banIdBus, Toast.LENGTH_LONG).show();
                    if (banControl && banIdBus){
                        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                        startActivity(intent);
                    }
                    else
                        {
                            Toast.makeText(LoginActivity.this, "Error de WebService vuelve a ingresar", Toast.LENGTH_SHORT).show();
                        }*/

                } catch (JSONException e)
                {

                    Toast.makeText(LoginActivity.this, "Datos no validos : "+e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errornull="null";
                if (errornull.equals(error.getMessage().toString()))
                {
                    Toast.makeText(LoginActivity.this, "Servidor no Responde", Toast.LENGTH_SHORT).show();
                }
                else
                    {
                        Toast.makeText(LoginActivity.this, "Datos No validos : "+error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }

            }
        });
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);

    }

    private void cargarWebServiceIdBuses() {
        arrayIdBuses=new ArrayList<cIdBus>();
        String envio1 = "id_observador=" + Codi_observador;
        String newUrl = url_mapa + "?" + envio1;
        jsonArrayRequestIdBuses = new JsonArrayRequest(newUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int cont = 0; cont < response.length(); cont++) {
                    oIdBus = new cIdBus();
                    try {
                        JSONObject jsonObject = response.getJSONObject(cont);
                        //Toast.makeText(LoginActivity.this, "id: " + jsonObject.getString("CodiVehiObseVehi"), Toast.LENGTH_SHORT).show();
                        oIdBus.setIdBus(jsonObject.getString("CodiVehiObseVehi"));
                        arrayIdBuses.add(oIdBus);
                    } catch (JSONException error) {
                        Toast.makeText(getApplicationContext(), "JSONException : " + error, Toast.LENGTH_SHORT).show();
                    }
                }
                llenarListaIdString();
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "ErrorREsponse IdBusesLogin : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueueIdBuses = Volley.newRequestQueue(getApplicationContext());
        requestQueueIdBuses.add(jsonArrayRequestIdBuses);
    }
    private void llenarListaIdString() {
        arrayStringIdBus = new ArrayList<String>();
        for (int i = 0; i < arrayIdBuses.size(); i++)
        {
            arrayStringIdBus.add(arrayIdBuses.get(i).getIdBus());
        }
        banIdBus=true;
    }

}