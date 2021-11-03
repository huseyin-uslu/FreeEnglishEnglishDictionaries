package com.huseyinuslu.freeenglishenglishdictionariesapp.screen;

import android.app.Activity;
import android.content.Intent;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.huseyinuslu.freeenglishenglishdictionariesapp.R;
import com.huseyinuslu.freeenglishenglishdictionariesapp.adapter.DictionaryListAdapter;
import com.huseyinuslu.freeenglishenglishdictionariesapp.databinding.FragmentDictionaryBinding;
import com.huseyinuslu.freeenglishenglishdictionariesapp.viewmodel.DictionaryFragmentViewModel;

import java.util.Objects;

public class DictionaryFragment extends Fragment {

    private DictionaryFragmentViewModel viewModel;
    private FragmentDictionaryBinding binding;


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_item_about_app) {
            //TODO: when being clicked , will open a dialog about the app.
        }
        return super.onOptionsItemSelected(item);
    }

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
        binding = DataBindingUtil.inflate(getLayoutInflater, R.layout.fragment_dictionary, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.setSharedPreferences(requireActivity());
        viewModel.initialization();

        DictionaryListAdapter adapter = new DictionaryListAdapter(requireActivity(),viewModel.getIndexNumber(),(index) -> {
            viewModel.setSelectedDictionary(index);
        });

        binding.setViewModel(viewModel);
        binding.setFragment(this);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setHasFixedSize(true);

    }

    public void searchWord(){
        Activity context = requireActivity();
        Intent intent = context.getIntent();
        //must come words as user is typing some vocabulary he doesn't know.

    }

    public void updateWord(){
        viewModel.setWord(Objects.requireNonNull(binding.edittextYouCanSearch.getText()).toString());
    }

    private void setError(){

    }
}