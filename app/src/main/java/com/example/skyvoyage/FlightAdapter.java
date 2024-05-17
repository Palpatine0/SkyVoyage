package com.example.skyvoyage;

import android.content.Context;
import android.content.Intent;
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
    private Context context;

    public FlightAdapter(Context context, List<Flight> flightList) {
        this.context = context;
        this.flightList = flightList;
    }

    @NonNull
    @Override
    public FlightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_flight_listing, parent, false);
        return new FlightViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightViewHolder holder, int position) {
        Flight flight = flightList.get(position);
        holder.tvFlightTime.setText(flight.getTime());
        holder.tvFlightDuration.setText(flight.getDuration());
        holder.tvFlightRoute.setText(flight.getRoute());
        holder.tvFlightPrice.setText(flight.getPrice());
        holder.tvAirlineName.setText(flight.getAirlineName());
        holder.ivAirlineLogo.setImageResource(flight.getAirlineLogo());
        holder.tvCount.setText(String.valueOf(flight.getCount()));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FlightDetailActivity.class);
            intent.putExtra("FLIGHT_ID", flight.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return flightList.size();
    }

    public static class FlightViewHolder extends RecyclerView.ViewHolder {
        public TextView tvFlightTime, tvFlightDuration, tvFlightRoute, tvFlightPrice, tvAirlineName, tvCount;
        public ImageView ivAirlineLogo;

        public FlightViewHolder(View view) {
            super(view);
            tvFlightTime = view.findViewById(R.id.tvFlightTime);
            tvFlightDuration = view.findViewById(R.id.tvFlightDuration);
            tvFlightRoute = view.findViewById(R.id.tvFlightRoute);
            tvFlightPrice = view.findViewById(R.id.tvFlightPrice);
            tvAirlineName = view.findViewById(R.id.tvAirlineName);
            ivAirlineLogo = view.findViewById(R.id.ivAirlineLogo);
            tvCount = view.findViewById(R.id.tvCount);
        }
    }
}
