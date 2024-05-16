package com.example.skyvoyage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.FlightViewHolder> {
    private List<Flight> flightList;

    public FlightAdapter(List<Flight> flightList) {
        this.flightList = flightList;
    }

    @Override
    public FlightViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_flight_listing, parent, false);
        return new FlightViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FlightViewHolder holder, int position) {
        Flight flight = flightList.get(position);
        holder.tvFlightTime.setText(flight.getTime());
        holder.tvFlightDuration.setText(flight.getDuration());
        holder.tvFlightRoute.setText(flight.getRoute());
        holder.tvFlightPrice.setText(flight.getPrice());
        holder.tvAirlineName.setText(flight.getAirlineName());
        holder.ivAirlineLogo.setImageResource(flight.getAirlineLogo());
    }

    @Override
    public int getItemCount() {
        return flightList.size();
    }

    public class FlightViewHolder extends RecyclerView.ViewHolder {
        public TextView tvFlightTime, tvFlightDuration, tvFlightRoute, tvFlightPrice, tvAirlineName;
        public ImageView ivAirlineLogo;

        public FlightViewHolder(View view) {
            super(view);
            tvFlightTime = view.findViewById(R.id.tvFlightTime);
            tvFlightDuration = view.findViewById(R.id.tvFlightDuration);
            tvFlightRoute = view.findViewById(R.id.tvFlightRoute);
            tvFlightPrice = view.findViewById(R.id.tvFlightPrice);
            tvAirlineName = view.findViewById(R.id.tvAirlineName);
            ivAirlineLogo = view.findViewById(R.id.ivAirlineLogo);
        }
    }
}
