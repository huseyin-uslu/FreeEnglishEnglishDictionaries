package com.huseyinuslu.freeenglishenglishdictionariesapp.screen;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.huseyinuslu.freeenglishenglishdictionariesapp.R;
import com.huseyinuslu.freeenglishenglishdictionariesapp.viewmodel.DictionaryFragmentViewModel;


public class DictionaryFragment extends Fragment {

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       if(item.getItemId() == R.id.menu_item_about_app){
           //TODO: take a look previos tutorial about making menu... [X]!
       }else{

       }
        return super.onOptionsItemSelected(item);
    }

    private DictionaryFragmentViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        viewModel = new ViewModelProvider(this).get(DictionaryFragmentViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater getLayoutInflater = getLayoutInflater();
        ViewDataBinding binding = DataBindingUtil.inflate(getLayoutInflater,R.layout.fragment_dictionary,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}