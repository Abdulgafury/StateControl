package com.abdulgafur.bersugir.statecontrol.Adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.abdulgafur.bersugir.statecontrol.Products.MonitoringProduct;
import com.abdulgafur.bersugir.statecontrol.R;


import java.util.ArrayList;

public class MonitoringAdapter extends BaseAdapter{
    private ArrayList<MonitoringProduct> list;
    private Context context;


    public MonitoringAdapter(Context context, ArrayList<MonitoringProduct> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder") View v = View.inflate(context, R.layout.custom_list_monitoring, null);
        TextView monitoringName = v.findViewById(R.id.monitoringName);
        TextView monitoringMoisture = v.findViewById(R.id.monitoringMoisture);
        TextView monitoringMoistureState = v.findViewById(R.id.monitoringMoistureState);
        TextView monitoringTemperature = v.findViewById(R.id.monitoringTemperature);
        TextView monitoringTemperatureState = v.findViewById(R.id.monitoringTemperatureState);
        TextView monitoringHumidity = v.findViewById(R.id.monitoringHumidity);
        TextView monitoringHumidityState = v.findViewById(R.id.monitoringHumidityState);

        monitoringName.setText(list.get(i).getMonitoringName());


        monitoringMoisture.setText("Moisture: " + list.get(i).getMonitoringMoisture());
        monitoringMoistureState.setText("State: " + list.get(i).getMonitoringMoistureState());
        monitoringMoistureState.setTextColor(Color.GREEN);

        monitoringTemperature.setText("Temperature: " + list.get(i).getMonitoringTemperature());
        monitoringTemperatureState.setText("State: " + list.get(i).getMonitoringTemperatureState());
        monitoringTemperatureState.setTextColor(Color.GREEN);

        monitoringHumidity.setText("Humidity: " + list.get(i).getMonitoringHumidity());
        monitoringHumidityState.setText("State: " + list.get(i).getMonitoringHumidityState());
        monitoringHumidityState.setTextColor(Color.GREEN);


        /*
        String date = getDate(Long.parseLong(list.get(i).getMonitoringLastModification()), "dd/MM/yyyy hh:mm:ss");
        if (Math.abs(Calendar.getInstance().getTimeInMillis() - Long.parseLong(list.get(i).getMonitoringLastModification())) > 300000) {
        }
        monitoringLastModification.setText("Last update: " + date);
        private static String getDate(long milliSeconds, String dateFormat)
        {
            // Create a DateFormatter object for displaying date in specified format.
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            // Create a calendar object that will convert the date and time value in milliseconds to date.
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliSeconds);
            return formatter.format(calendar.getTime());
        }
         */

        return v;
    }



}
