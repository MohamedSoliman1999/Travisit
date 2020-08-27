package com.travisit.travisitstandard.vvm.destination;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.travisit.travisitstandard.R;
import com.travisit.travisitstandard.databinding.FragmentForgotPasswordBinding;
import com.travisit.travisitstandard.utils.SharedPrefManager;
import com.travisit.travisitstandard.vvm.AppActivity;
import com.travisit.travisitstandard.vvm.vm.AuthenticationVM;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotPasswordFragment extends Fragment {
    private AuthenticationVM vm;
    private FragmentForgotPasswordBinding binding;
    public SharedPrefManager preferences;
    private final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (getFieldText("email").length() == 0) {
                binding.fForgotPasswordBtnSendCode.setEnabled(false);
            } else {
                binding.fForgotPasswordBtnSendCode.setEnabled(true);
            }
        }
    };

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppActivity) getActivity()).changeBottomNavVisibility(View.GONE, false);
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        preferences = new SharedPrefManager(getActivity());
        vm = ViewModelProviders.of(this).get(AuthenticationVM.class);
        handleUserInteractions(view);
    }
    private void handleUserInteractions(final View view) {
        binding.fForgotPasswordIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_from_forgot_password_to_auth);
            }
        });
        binding.fForgotPasswordBtnSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.RequestNewPasswordCode(
                        getFieldText("email")
                );
                vm.newCodeMutableLiveData.observe(getActivity(), new Observer<JsonObject>() {
                    @Override
                    public void onChanged(JsonObject jsonObject) {
                        if (jsonObject.get("result").getAsString().equals("Email sent.")) {
                            //TODO: Change message way
                            Toast.makeText(getActivity(), R.string.reset_code_sent, Toast.LENGTH_SHORT).show();
                            Log.d("here","before");
                            preferences.savePasswordResetCode(jsonObject.get("code").getAsString());
                            Log.d("here","after" + jsonObject.get("code").getAsString());
                            NavDirections action = ForgotPasswordFragmentDirections.actionFromForgotPasswordToCheckPasswordCodeFragment()
                                    .setCode(jsonObject.get("code").getAsString());
                            Navigation.findNavController(view).navigate(action);
                        }
                    }
                });
            }
        });
        binding.fForgotPasswordTvGoToCodePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_from_forgot_password_to_check_password_code_fragment);
            }
        });
        binding.fForgotPasswordTietEmailAddress.addTextChangedListener(watcher);

    }
    private String getFieldText(String fieldName) {
        switch (fieldName) {
            case "email":
                return binding.fForgotPasswordTietEmailAddress.getText().toString();
            default:
                return "invalid";
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
