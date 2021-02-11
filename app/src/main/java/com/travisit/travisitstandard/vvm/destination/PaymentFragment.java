package com.travisit.travisitstandard.vvm.destination;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.travisit.travisitstandard.R;
import com.travisit.travisitstandard.databinding.FragmentPaymentBinding;
import com.travisit.travisitstandard.vvm.AppActivity;
import com.travisit.travisitstandard.vvm.observer.IOnBackPressed;


public class PaymentFragment extends Fragment implements IOnBackPressed {
    private FragmentPaymentBinding binding;
    public PaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentPaymentBinding.inflate(inflater,container,false);
        View view=binding.getRoot();
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initWebView();
        ((AppActivity)getActivity()).changeBottomNavVisibility(View.GONE,true);
    }
    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView(){
        WebSettings webSettings=binding.paymentWV.getSettings();
        webSettings.setJavaScriptEnabled(true);
        binding.paymentWV.loadUrl(getActivity().getString(R.string.paymentLink));
        binding.paymentWV.setWebViewClient(new WebViewClient());
        if (18 < Build.VERSION.SDK_INT ){
            //18 = JellyBean MR2, KITKAT=19
            binding.paymentWV.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        }
        ((AppActivity)getActivity()).setOnBackPressedListener(this);
    }
    @Override
    public void onBackPressed() {
        if(binding.paymentWV.canGoBack()){
            binding.paymentWV.goBack();
        }else {
            ((AppActivity)getActivity()).setOnBackPressedListener(null);
        }
    }
}