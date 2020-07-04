package com.travisit.travisitstandard.vvm.destination;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.travisit.travisitstandard.R;
import com.travisit.travisitstandard.databinding.FragmentRegistrationBinding;
import com.travisit.travisitstandard.model.User;
import com.travisit.travisitstandard.utils.SharedPrefManager;
import com.travisit.travisitstandard.vvm.AppActivity;
import com.travisit.travisitstandard.vvm.vm.RegistrationVM;

public class RegistrationFragment extends Fragment {
    private RegistrationVM vm;
    private FragmentRegistrationBinding binding;
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
                    getFieldText("password").length() == 0 ||
                    getFieldText("name").length() == 0 ||
                    getFieldText("user type").length() == 0){
                binding.fRegistrationBtnSignUp.setEnabled(false);
            } else {
                binding.fRegistrationBtnSignUp.setEnabled(true);
            }
        }
    };
    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ((AppActivity)getActivity()).changeBottomNavVisibility(View.GONE);
        binding = FragmentRegistrationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        preferences = new SharedPrefManager(getActivity());
        vm = ViewModelProviders.of(this).get(RegistrationVM.class);
        String[] userType = new String[] {"Traveller", "Tour Guide"};
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        userType);
        binding.fRegistrationTietUserType.setAdapter(adapter);
        String gotToSignUpText = getResources().getString(R.string.go_to_sign_in);
        binding.fRegistrationTvGoToSignin.setText(Html.fromHtml(gotToSignUpText));
        handleUserInteractions(view);
    }
    private void handleUserInteractions(final View view) {
        binding.fRegistrationTvGoToSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_from_reg_to_auth);
            }
        });
        binding.fRegistrationBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigation.findNavController(view).navigate(R.id.action_from_reg_to_auth);
                vm.signUp(
                        getFieldText("email"),
                        getFieldText("password"),
                        getFieldText("user type"),
                        getFieldText("name"),
                        "01095252203"
                );
                vm.userMutableLiveData.observe(getActivity(), new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        preferences.saveUser(user);
                        NavDirections action = null;
                        if(user.getType().equals("guide")) {
                            action = RegistrationFragmentDirections.actionFromRegToCompleteGuideProfile()
                                .setUser(user);
                        } else if (user.getType().equals("traveler")){
                            action = RegistrationFragmentDirections.actionFromRegToCompleteTravelerProfile()
                                    .setUser(user);
                        }
                        if(action != null) {
                            Navigation.findNavController(view).navigate(action);
                        }
                        //Toast.makeText(getActivity(),business.getId().toString(), Toast.LENGTH_SHORT).show();
//                        vm.business = business;
//                        if (business != null && !business.getId().isEmpty() && business.getId() != null) {
//                            Toast.makeText(getActivity(), "Sign up was successful. You can Login now", Toast.LENGTH_SHORT).show();
//                            Navigation.findNavController(view).navigate(R.id.action_signup_to_signin);
//
                    }
                });
            }
        });
        binding.fRegistrationTietFullName.addTextChangedListener(watcher);
        binding.fRegistrationTietEmailAddress.addTextChangedListener(watcher);
        binding.fRegistrationTietPassword.addTextChangedListener(watcher);
        binding.fRegistrationTietUserType.addTextChangedListener(watcher);

    }
    private String getFieldText(String fieldName){
        switch (fieldName){
            case "email": return binding.fRegistrationTietEmailAddress.getText().toString();
            case "password": return binding.fRegistrationTietPassword.getText().toString();
            case "user type": return binding.fRegistrationTietUserType.getText().toString();
            case "name": return binding.fRegistrationTietFullName.getText().toString();
            default: return "invalid";
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
