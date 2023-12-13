package com.example.myapplication.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.globals.ApiCall;
import com.example.myapplication.globals.layout.Main;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Home extends Main {

    // variable for our bar chart
    BarChart barChart;

    // variable for our bar data.
    BarData barData;

    // variable for our bar data set.
    BarDataSet barDataSet;

    // list for storing entries.
    List<BarEntry> barEntriesList;

    //LinearLayout checkboxLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadContent(this, R.layout.home);

        // checkboxLayout = findViewById(R.id.CheckBoxLayout);

        // initializing variable for bar chart.
        barChart = findViewById(R.id.idBarChart);

        // creating a new list for bar entries.
        barEntriesList = new ArrayList<>();

        // creating a new bar data set.
        barDataSet = new BarDataSet(barEntriesList, "Exchange rates against the euro");

        // creating a new bar data and passing our bar data set.
        barData = new BarData(barDataSet);

        // below line is to set data to our bar chart.
        barChart.setData(barData);

        // adding color to our bar data set.
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        // setting text color.
        barDataSet.setValueTextColor(Color.BLACK);

        // setting text size.
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(false);

        // calling method to handle API data.
        handleAPIData();
    }
    private void getBarEntriesFromAPI(JSONObject response) throws JSONException {
        JSONArray devises = response.getJSONObject("result")
                .getJSONObject("result")
                .getJSONArray("devises");

        GridLayout checkboxLayout = findViewById(R.id.checkBoxLayout);
        Spinner entitySpinner = findViewById(R.id.entitySpinner);

        String[] currencies = new String[devises.length()];

        for (int i = 0; i < devises.length(); i++) {
            JSONObject devise = devises.getJSONObject(i);
            String codeISODevise = devise.getString("codeISODevise");
            currencies[i] = codeISODevise;
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currencies);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        entitySpinner.setAdapter(spinnerAdapter);

        List<JSONObject> devisesList = new ArrayList<>();
        List<CheckBox> checkBoxList = new ArrayList<>();
        HashMap<String, JSONObject> stringJsonObject = new HashMap<>();

        for (int i = 0; i < devises.length(); i++) {
            JSONObject devise = devises.getJSONObject(i);
            stringJsonObject.put(devise.getString("codeISODevise"),devise);
            CheckBox checkBox = new CheckBox(this);
            checkBox.setId(View.generateViewId());
            checkBox.setText(devise.getString("codeISODevise"));

            checkBoxList.add(checkBox);

            if (i < 5) {
                checkBox.setChecked(true);
            }

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // Récupérer la valeur de la CheckBox (cochée ou non cochée)
                    if (isChecked) {
                        Toast.makeText(Home.this, checkBox.getText() + " CheckBox Checked", Toast.LENGTH_SHORT).show();
                        addBarToChart(checkBox.getText().toString());

                    } else {
                        Toast.makeText(Home.this, checkBox.getText() + " CheckBox Unchecked", Toast.LENGTH_SHORT).show();
                        removeBarFromChart(checkBox.getText().toString());
                    }
                }
            });
            //Toast.makeText(Home.this, "Entity selected: " + CheckBox, Toast.LENGTH_SHORT).show();

            checkboxLayout.addView(checkBox);

        }

        // Sort the list based on the "taux" value

        for (CheckBox checkBox : checkBoxList) {
            if (checkBox.isChecked()) {
                devisesList.add(stringJsonObject.get(checkBox.getText()));
            }
        }

        Collections.sort(devisesList, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                try {
                    // Compare based on the "taux" values
                    float taux1 = (float) o1.getDouble("taux");
                    float taux2 = (float) o2.getDouble("taux");
                    return Float.compare(taux1, taux2);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });

        System.out.println("devises");
        System.out.println(devises);

        // Clear existing entries
        barEntriesList.clear();

        // Adding new entries
        for (int i = 0; i < 5; i++) {
            JSONObject devise = devisesList.get(i);
            float taux = (float) devise.getDouble("taux");
            String codeISODevise = devise.getString("codeISODevise");
            barEntriesList.add(new BarEntry(i + 0f, taux, codeISODevise));
        }

        System.out.println("Size of barEntriesList: " + barEntriesList.size());

        XAxis xAxis = barChart.getXAxis();

        // Create a new BarDataSet
        barDataSet = new BarDataSet(barEntriesList, "Exchange rates against the euro");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        // Set the data to BarChart
        barData = new BarData(barDataSet);
        barChart.setData(barData);

        // Set custom labels for the legend
        Legend legend = barChart.getLegend();
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setTextSize(12f);
        legend.setTextColor(Color.BLACK);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(true);

        // Set custom labels for x-axis
        xAxis.setLabelCount(getLabels().size());
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getLabels()));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);

        // Refresh the chart
        barChart.invalidate();
    }



    private void handleAPIData() {
        ApiCall.makeApiCall("https://happyapi.fr/api/devises", new ApiCall.ApiCallback() {
            public void onSuccess(JSONObject response) {
                try {
                    System.out.println("response");
                    System.out.println(response);
                    // handle API response and update the BarChart
                    getBarEntriesFromAPI(response);
                } catch (JSONException e) {
                    System.out.println("response2");
                    e.printStackTrace();
                }
            }

            public void onError(String errorMessage) {
                // Handle error
            }
        }, Home.this);
    }

    private List<String> getLabels() {
        List<String> labels = new ArrayList<>();
        for (BarEntry entry : barEntriesList) {
            labels.add(entry.getData().toString());
        }
        System.out.println(labels);
        return labels;
    }

    private void addBarToChart(String codeISODevise) {
        // Vérifier si la barre correspondante n'est pas déjà présente
        for (BarEntry entry : barEntriesList) {
            if (entry.getData().toString().equals(codeISODevise)) {
                return; // La barre est déjà présente, ne rien faire
            }
        }

        // Ajouter la barre correspondante à la liste des entrées de la barre
        JSONObject jsonObject = stringJsonObject.get(codeISODevise);
        float taux = 0f;
        try {
            taux = (float) jsonObject.getDouble("taux");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        barEntriesList.add(new BarEntry(barEntriesList.size(), taux, codeISODevise));

        // Actualiser le graphique
        updateChart();
    }

    private void removeBarFromChart(String codeISODevise) {
        // Retirer la barre correspondante de la liste des entrées de la barre
        for (int i = 0; i < barEntriesList.size(); i++) {
            BarEntry entry = barEntriesList.get(i);
            if (entry.getData().toString().equals(codeISODevise)) {
                barEntriesList.remove(i);
                break;
            }
        }

        // Actualiser le graphique
        updateChart();
    }
}
