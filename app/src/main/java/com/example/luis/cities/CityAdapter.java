package com.example.luis.cities;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.luis.cities.model.Data;
import com.example.luis.cities.util.Trie;

import java.util.ArrayList;
import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.MyViewHolder> {


    private Context context;
    private List<Data> citiesList;
    private List<Data> citiesListFiltered;
    private CityListener listener;

    private Trie trie;



    private Handler _handler;

    private boolean pending=false;

    public CityAdapter(Context context, List<Data> cityList, CityListener listener) {
        this.context = context;
        this.listener = listener;
        this.citiesList = cityList;
        this.citiesListFiltered = cityList;

    }


    /**
     * Called when RecyclerView needs a new {@linkViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link#onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see#onBindViewHolder(ViewHolder, int)
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@linkViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link#onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Data d= citiesListFiltered.get(position);
        holder.city.setText(d.getName());
        holder.country.setText(d.getCountry());
    }


    public void initialLoad(final List<Data> data){

        citiesList=data;
        citiesListFiltered=data;
        notifyDataSetChanged();

    }

    public void setTrie(Trie trie){
        this.trie=trie;

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return citiesListFiltered.size();
    }

    public void filter(String query) {

        if(pending)
            return;

        final String prefix=query;

        pending=true;
        if (_handler == null) _handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {

                if (prefix.isEmpty()) {
                    Log.d("filter","empty..");
                    citiesListFiltered = citiesList;

                    Log.d("filter","empty.."+citiesListFiltered.size());

                } else {

                    Log.d("filter", "start filtering..with" + prefix);

                    long s = System.nanoTime();
                    String comp = trie.printSuggestions(trie.rootNode, prefix);
                    Log.d("filter", "--" + comp);
                    double e = (double) (System.nanoTime() - s) / 1000000000.0;
                    Log.d("filter", "finished time==" + e+" size="+trie.lstRes.size());

                    List<Data> filteredList = new ArrayList<>();

                    filteredList.addAll(trie.getListResults());

                    citiesListFiltered=filteredList;

                }

                _handler.post(new Runnable() {
                    @Override
                    public void run() {

                        updateRecyData(citiesListFiltered);

                    }
                });


            }
        }).start();



    }

    private void updateRecyData(List<Data> filteredList) {


        pending=false;
        this.citiesListFiltered = filteredList;
        notifyDataSetChanged();


    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView city;
        public TextView country;

        public MyViewHolder(View itemView) {
            super(itemView);
            city = itemView.findViewById(R.id.city);
            country = itemView.findViewById(R.id.country);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    listener.onCitySelected(citiesListFiltered.get(getAdapterPosition()));

                }
            });

        }
    }


    public interface CityListener {
        void onCitySelected(Data city);
    }


}
