package com.example.monsheeppractica.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.icu.text.CaseMap;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monsheeppractica.GetterAndSetter.Carrito;
import com.example.monsheeppractica.GetterAndSetter.Productos;
import com.example.monsheeppractica.R;
import com.example.monsheeppractica.adaptadores.AdaptadorGraph;
import com.example.monsheeppractica.adaptadores.AdaptadorNegocioPerfil;
import com.example.monsheeppractica.sqlite.registros.ConsultarTabla;
import com.google.zxing.common.StringUtils;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;

import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MainActivityEstadisticas extends AppCompatActivity {
    SharedPreferences preferences;
    String NombreUser, idUser, idFotoUser, tipoUser, idNegocio;
    GraphView graphView;
    ArrayList<Carrito> carritolista = new ArrayList<>();
    ArrayList arrayObj = new ArrayList();

    List<String> lista = new ArrayList<>();
    List<Integer> listaCantidad = new ArrayList<>();
    List<Integer> listaColores = new ArrayList<>();
    RecyclerView rvColor;
    int cont = 0;
    PieChart pieChart;
    public static String colorRosa = "#";
    TextView tvDestallesGraph, tvDetalleGraph2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_estadisticas);
        preferences = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        idUser = preferences.getString("idusers", "et_pass.getText().toString()");
        NombreUser = preferences.getString("NombreUser", "et_pass.getText().toString()");
        idFotoUser = preferences.getString("idFotoUser", "et_pass.getText().toString()");
        tipoUser = preferences.getString("tipouser", "et_pass.getText().toString()");
        idNegocio = preferences.getString("idNegocio", "et_pass.getText().toString()");
        pieChart = findViewById(R.id.piechart);
        tvDestallesGraph = findViewById(R.id.tvDetalleGraph);
        tvDetalleGraph2 = findViewById(R.id.tvDetalleGraph2);
        rvColor = findViewById(R.id.rvColor);

        listaColores.clear();
        listaCantidad.clear();
        lista.clear();
        carritolista.clear();
        ConsultarTabla consultarTabla = new ConsultarTabla(this);
        consultarTabla.CarritoConsulta(carritolista, "Estadistica", "" + idNegocio, "", "");

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvColor.setLayoutManager(layoutManager);

        String[] arreglo = new String[carritolista.size()];
        int sum = 0;
        int r = 256, g = 256, b = 256;
        for (int i = 0; i < arreglo.length; i++) {
            Random rand = new Random();
            r = (int)(Math.random()*256);
            g = (int)(Math.random()*256);
            b = (int)(Math.random()*256);

            System.out.println(i + ": " + carritolista.get(i).getProductos());
            if (i == 0) {
                sum += Integer.parseInt(carritolista.get(i).getCantidad());
                System.out.println("------" + carritolista.get(i).getProductos() + sum);
            } else if (carritolista.get(i).getIdProducto().equals(carritolista.get(i - 1).getIdProducto())) {
                sum += Integer.parseInt(carritolista.get(i).getCantidad());

            } else {

                if (!carritolista.get(i).getIdProducto().equals(carritolista.get(i - 1).getIdProducto())) {
                    lista.add(carritolista.get(i - 1).getProductos() + ": " + sum);
                    listaCantidad.add(sum);
                    listaColores.add(Color.rgb(r, g, b));
                    System.out.println("------" + carritolista.get(i).getProductos() + sum);

                    sum = 0;
                }
                sum += Integer.parseInt(carritolista.get(i).getCantidad());
            }

        }
        lista.add(carritolista.get(carritolista.size() - 1).getProductos() + ": " + sum);
        listaCantidad.add(sum);
        listaColores.add(Color.rgb(r, g, b));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            lista.forEach(obj -> {
                arrayObj.add("" + obj);

            });

            int n = 0;
            for (int i = 0; i < lista.size(); i++) {
                cont = cont + 1;
                rvColor.setAdapter(new AdaptadorGraph(this, arrayObj, listaColores, this));
                System.out.println("" + listaColores.get(i) + " <---- " + listaCantidad.get(i) + "--> " + lista.get(i));

                n = String.valueOf(listaCantidad.get(i)).length();
                System.out.println(getLastN(lista.get(i), n));
                if (Collections.max(listaCantidad).equals(Integer.parseInt(getLastN(lista.get(i), n)))) {
                    tvDestallesGraph.setText("" + lista.get(i));
                }
                if (Collections.min(listaCantidad).equals(Integer.parseInt(getLastN(lista.get(i), n)))) {
                    tvDetalleGraph2.setText("" + lista.get(i));
                }
                PieModel pieModel  = new PieModel(
                        "venta: " + lista.get(i),
                        listaCantidad.get(i),
                        listaColores.get(i));

                pieChart.addPieSlice(pieModel);

           }

            pieChart.setShowDecimal(true);
            pieChart.setLegendTextSize(20);
            pieChart.setLegendColor(Color.parseColor("#F58564"));
            pieChart.startAnimation();

        }

    }

    public static String getLastN(String s, int n) {
        if (s == null || n > s.length()) {
            return s;
        }
        return s.substring(s.length() - n);
    }

    void Graficar() {
        // on below line we are adding data to our graph view.
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                // on below line we are adding
                // each point on our x and y axis.
                new DataPoint(0, 1),
                new DataPoint(1, 3),
                new DataPoint(2, 4),
                new DataPoint(3, 19),
                new DataPoint(4, 6),
                new DataPoint(5, 3),
                new DataPoint(6, 6),
                new DataPoint(7, 1),
                new DataPoint(8, 2)

        });

        // after adding data to our line graph series.
        // on below line we are setting
        // title for our graph view.
        graphView.setTitle("My Graph View");

        // on below line we are setting
        // text color to our graph view.
        graphView.setTitleColor(R.color.purple_700);

        // on below line we are setting
        // our title text size.
        graphView.setTitleTextSize(26);

        // on below line we are adding
        // data series to our graph view.
        graphView.addSeries(series);
    }

    void BarGRaph() {
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 1),
                new DataPoint(1, 3),
                new DataPoint(2, 4),
                new DataPoint(3, 19),
                new DataPoint(4, 6),
                new DataPoint(5, 3),
                new DataPoint(6, 6),
                new DataPoint(7, 1),
                new DataPoint(8, 2)
        });


        graphView.addSeries(series);

        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX() * 255 / 4, (int) data.getY() * 255 / 6, 100);
            }
        });

        series.setDataWidth(1d);
        series.setSpacing(20);
        series.setAnimated(true);
        series.setDrawValuesOnTop(true);
        series.setTitle("BarGraph");
        series.setColor(Color.argb(255, 60, 200, 128));
        series.setValuesOnTopColor(Color.BLUE);
    }

    void CircleBar() {
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 1),
                new DataPoint(1, 3),
                new DataPoint(2, 4),
                new DataPoint(3, 19),
                new DataPoint(4, 6),
                new DataPoint(5, 3),
                new DataPoint(6, 6),
                new DataPoint(7, 1),
                new DataPoint(8, 2)
        });
        graphView.getViewport().setScrollable(true);

        graphView.addSeries(series);


        series.setAnimated(true);
        series.setTitle("Curve");
        series.setColor(Color.argb(255, 60, 200, 128));

    }

}