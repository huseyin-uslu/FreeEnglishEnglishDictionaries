package com.huseyinuslu.freeenglishenglishdictionariesapp.screen;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.content.ClipboardManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.huseyinuslu.freeenglishenglishdictionariesapp.R;
import com.huseyinuslu.freeenglishenglishdictionariesapp.adapter.DictionaryListAdapter;
import com.huseyinuslu.freeenglishenglishdictionariesapp.data.DictionaryData;
import com.huseyinuslu.freeenglishenglishdictionariesapp.databinding.FragmentDictionaryBinding;
import com.huseyinuslu.freeenglishenglishdictionariesapp.viewmodel.DictionaryFragmentViewModel;

import java.util.Objects;

public class DictionaryFragment extends Fragment {

    private DictionaryFragmentViewModel viewModel;
    private FragmentDictionaryBinding binding;
    private WebView webV;

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
        webViewerSettings();
        bindedDataMovements();
    }


    private void bindedDataMovements(){

        String dictionaryName = requireActivity().getResources().getString(viewModel.selectedDictionaryItem().getValue().getName());

        DictionaryListAdapter adapter = new DictionaryListAdapter(requireActivity(), dictionaryName, new DictionaryListAdapter.OnDictionaryClicked() {
            @Override
            public void onClick(int selectedIndex) {
                viewModel.setIndexNumber(selectedIndex);
            }
        });

        //this is for the the edit text which gives you suggestions for you finding the correct word.
        ArrayAdapter<String> wordAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,viewModel.wordList);
        binding.edittextYouCanSearch.setAdapter(wordAdapter);

        binding.setFragment(this);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        //dictionary list with recyclerview
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setItemViewCacheSize(DictionaryData.getData().length);
        binding.recyclerView.setAdapter(adapter);

        setDictionariesList(viewModel.dictionaryListState.getValue());
        viewModel.dictionaryListState.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                setDictionariesList(aBoolean);
            }
        });

        clipboardListener();
    }

    public void searchWord(){
        if(Objects.requireNonNull(binding.edittextYouCanSearch.getText()).toString().isEmpty()){
            setError(true);
        }else{
            setError(false);
            viewModel.setDictionaryListState(false);
            viewModel.refreshLink();
        }
    }

    public void backHeadButtonWebviewer(){
       if(webV.canGoBack()){
           webV.goBack();
       }
    }

    public void refreshButtonWebviewer(){
        System.out.println("orjinal URL: " + webV.getOriginalUrl());
        binding.refreshButtonOnWebViewer.setVisibility(View.GONE);
        binding.refreshIndicator.setVisibility(View.VISIBLE);
        countDownTimer(3000, 1000, new VoidParameter() {
            @Override
            public void voidParameter() {
                binding.refreshButtonOnWebViewer.setVisibility(View.VISIBLE);
                binding.refreshIndicator.setVisibility(View.GONE);
            }
        });
        webV.reload();
    }

    public void setDictionariesList(boolean canBeSeen){
        if(canBeSeen){
            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.webViewerIndicator.setVisibility(View.GONE);
            binding.backHeaderToDictionaryListButton.setVisibility(View.GONE);
        }else{
            binding.backHeaderToDictionaryListButton.setVisibility(View.VISIBLE);
            binding.webViewerIndicator.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.GONE);
        }
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

    private void webViewerSettings(){
        webV = binding.webViewer;
        webV.setWebViewClient(new WebViewClient());

        WebSettings settings = webV.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setDomStorageEnabled(true);
    }

    private void countDownTimer(long millis, long countDownInterval,VoidParameter method) {
        new CountDownTimer(millis,countDownInterval) {
            @Override
            public void onTick(long l) {
                //nothing
            }

            @Override
            public void onFinish() {
                method.voidParameter();
            }
        }.start();
    }

    private interface VoidParameter {
        void voidParameter();
    }

    private void alertDialog(String title,String message){
        new AlertDialog.Builder(requireActivity())
                .setTitle(title)
                .setMessage(message)
                .setIcon(R.drawable.logo_icon)
                .show();
    }

    private void clipboardListener(){
        final ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
        clipboard.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
          CharSequence text;
            public void onPrimaryClipChanged() {
                android.content.ClipData primaryClip = clipboard.getPrimaryClip();

                if(primaryClip.getItemCount() > 0){
                    if(!primaryClip.getItemAt(0).getText().toString().isEmpty()){
                        text = clipboard.getPrimaryClip().getItemAt(0).getText();
                        viewModel.setWord(text.toString());
                    }
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        webV.clearFormData();
        webV.clearHistory();
        webV.clearMatches();
        webV.clearSslPreferences();
        webV.destroy();
        binding = null;
    }
}