package com.huseyinuslu.freeenglishenglishdictionariesapp.screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
            alertDialog(getString(R.string.title_of_alert),getString(R.string.message_of_alert));
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
        viewModel.initialization(requireActivity());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DictionaryListAdapter adapter = new DictionaryListAdapter(requireActivity(),viewModel.getIndexNumber(),(index) -> {
            viewModel.setSelectedDictionary(index);
        });

        binding.setViewModel(viewModel);
        binding.setFragment(this);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setHasFixedSize(true);

        webViewerSettings();
    }

    public void searchWord(){
        if(Objects.requireNonNull(binding.edittextYouCanSearch.getText()).toString().isEmpty()){
            setError(true);
        }else{
            setError(false);
            setDictionariesList(false);
            binding.webViewer.loadUrl(viewModel.getLink());
        }
    }

    private void webViewerSettings(){
        binding.webViewer.setWebViewClient(new WebViewClient());
        WebSettings settings = binding.webViewer.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setSupportZoom(true);
        settings.setDomStorageEnabled(true);
    }

    public void setDictionariesList(boolean canBeSeen){
        if(canBeSeen){
            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.webViewer.setVisibility(View.GONE);
            binding.backHeaderToDictionaryListButton.setVisibility(View.GONE);
        }else{
            binding.backHeaderToDictionaryListButton.setVisibility(View.VISIBLE);
            binding.webViewer.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.GONE);
        }
    }

    private void alertDialog(String title,String message){
        new AlertDialog.Builder(requireActivity())
                .setTitle(title)
                .setMessage(message)
                .setIcon(R.drawable.logo_icon)
                .show();
    }

    public void updateWord(){
        setError(false);
        viewModel.setWord(Objects.requireNonNull(binding.edittextYouCanSearch.getText()).toString());
    }

    private void setError(boolean error){
        if(error){
            binding.textInputLayout.setError(Objects.requireNonNull(getString(R.string.error_text)));
            binding.textInputLayout.setErrorEnabled(true);
        }else{
            binding.textInputLayout.setError(null);
            binding.textInputLayout.setErrorEnabled(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        WebView webV = binding.webViewer;
        webV.clearFormData();
        webV.clearHistory();
        webV.clearMatches();
        webV.clearSslPreferences();
        webV.destroy();
    }
}