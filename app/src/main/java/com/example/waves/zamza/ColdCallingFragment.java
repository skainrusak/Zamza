package com.example.waves.zamza;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ColdCallingFragment extends Fragment {
    private RecyclerView callingRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_cold_calling , container , false);
        callingRecyclerView = (RecyclerView)view.findViewById(R.id.cold_calling_rv);
        callingRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
    private class ColdHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ColdHolder( View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
