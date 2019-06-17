package kr.hs.dgsw.adeyeo.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import kr.hs.dgsw.adeyeo.R;
import kr.hs.dgsw.adeyeo.domain.ResultValues;

public class RecyclerAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private ArrayList<ResultValues> rvList = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_item, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int position) {
        itemViewHolder.onBind(rvList.get(position));
    }

    @Override
    public int getItemCount() {
        return rvList.size();
    }

    public void addItem(ResultValues resultValues){
        rvList.add(resultValues);
    }
}
