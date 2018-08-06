package fr.indianacroft.wildhunt;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ExpeditionAdapter extends RecyclerView.Adapter<ExpeditionAdapter.ViewHolder> {
    private List<ExpeditionModel> mExpeditions;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ExpeditionAdapter(List<ExpeditionModel> expeditions) {
        mExpeditions = expeditions;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expedition, parent, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ExpeditionModel expedition = mExpeditions.get(position);
        holder.mTvName.setText(expedition.getName());
        holder.mTvDescription.setText(expedition.getDescription());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mExpeditions.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView mTvName;
        TextView mTvDescription;

        ViewHolder(View v) {
            super(v);
            mTvName = v.findViewById(R.id.name);
            mTvDescription = v.findViewById(R.id.description);
        }
    }
}
