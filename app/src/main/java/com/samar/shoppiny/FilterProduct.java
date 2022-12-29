package com.samar.shoppiny;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.Locale;

public class FilterProduct extends Filter {
    private AdapterProduct adapter;
    private ArrayList<ModelProduct> filterList;

    public FilterProduct(AdapterProduct adapter, ArrayList<ModelProduct> filterList) {
        this.adapter = adapter;
        this.filterList = filterList;
    }


    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if(constraint != null && constraint.length() >0){
            constraint = constraint.toString().toUpperCase();
            ArrayList<ModelProduct> filteredModels = new ArrayList<>();
            for(int i = 0; i<filterList.size();i++){
                if(filterList.get(i).getProductTitle().toUpperCase().contains(constraint) ||
                filterList.get(i).getProductCategory().toUpperCase().contains(constraint) ){
                    //add filtered data to list
                    filteredModels.add(filterList.get(i));
                }

            }
            results.count = filteredModels.size();
            results.values = filteredModels;
        }
        else {
            results.count = filterList.size();
            results.values = filterList;
        }
        return null;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.productList = (ArrayList<ModelProduct>) results.values;
        adapter.notifyDataSetChanged();
    }
}
