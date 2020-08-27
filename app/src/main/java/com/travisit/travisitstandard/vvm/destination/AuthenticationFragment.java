package com.travisit.travisitstandard.vvm.destination;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.travisit.travisitstandard.R;
import com.travisit.travisitstandard.data.Client;
import com.travisit.travisitstandard.databinding.FragmentAuthenticationBinding;
import com.travisit.travisitstandard.model.User;
import com.travisit.travisitstandard.utils.SharedPrefManager;
import com.travisit.travisitstandard.vvm.AppActivity;
import com.travisit.travisitstandard.vvm.vm.AuthenticationVM;

public class AuthenticationFragment extends Fragment {
    private AuthenticationVM vm;
    private FragmentAuthenticationBinding binding;
    public SharedPrefManager preferences;
    private final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        { }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {}
        @Override
        public void afterTextChanged(Editable s) {
            if (getFieldText("email").length() == 0 ||
                    getFieldText("password").length() < 6 ||
                    getFieldText("user type").length() == 0){
                binding.fAuthBtnSignIn.setEnabled(false);
            } else {
                binding.fAuthBtnSignIn.setEnabled(true);
            }
        }
    };

    public static AuthenticationFragment newInstance() {
        return new AuthenticationFragment();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ((AppActivity)getActivity()).changeBottomNavVisibility(View.GONE, false);
        binding = FragmentAuthenticationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        preferences = new SharedPrefManager(getActivity());
        vm = ViewModelProviders.of(this).get(AuthenticationVM.class);
        String[] userType = new String[] {"Traveler", "Tour Guide"};
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        userType);
        binding.fAuthTietUserType.setAdapter(adapter);
        String gotToSignUpText = getResources().getString(R.string.go_to_sign_up);
        binding.fAuthTvGoToSignup.setText(Html.fromHtml(gotToSignUpText));
        handleUserInteractions(view);
    }
    private void handleUserInteractions(final View view) {
        binding.fAuthTvGoToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_from_auth_to_reg);
            }
        });
        binding.fAuthTvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_from_auth_to_forgot_password);
            }
        });
        binding.fAuthBtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.signIn(
                        getFieldText("email"),
                        getFieldText("password")
                );
                vm.userMutableLiveData.observe(getActivity(), new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        preferences.saveUser(user);
                        Client.reinstantiateClient(
                                user.getToken()
                        );
                        if(user.getType().contains("guide")){
                            AppActivity.isTourGuide = true;
                        } else {
                            AppActivity.isTourGuide = false;
                        }
                        //TODO: CHECK USER STATUS TO NAVIGATE
                        Navigation.findNavController(view).navigate(R.id.action_from_auth_to_home);
                    }
                });
            }
        });

        binding.fAuthTietEmailAddress.addTextChangedListener(watcher);
        binding.fAuthTietUserType.addTextChangedListener(watcher);
        binding.fAuthTietPassword.addTextChangedListener(watcher);

    }
    private String getFieldText(String fieldName){
        switch (fieldName){
            case "email": return binding.fAuthTietEmailAddress.getText().toString();
            case "password": return binding.fAuthTietPassword.getText().toString();
            case "user type": return binding.fAuthTietUserType.getText().toString();
            default: return "invalid";
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
