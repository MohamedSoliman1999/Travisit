package com.travisit.travisitstandard.vvm.destination;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.travisit.travisitstandard.R;
import com.travisit.travisitstandard.databinding.FragmentGuideCompleteProfileBinding;
import com.travisit.travisitstandard.model.User;
import com.travisit.travisitstandard.utils.PathUtil;
import com.travisit.travisitstandard.vvm.AppActivity;
import com.travisit.travisitstandard.vvm.vm.ProfileVM;

import static android.app.Activity.RESULT_OK;

public class GuideCompleteProfileFragment extends Fragment {
    private static final int REQUEST_IMAGE_PP = 125;
    private ProfileVM vm;
    private String ppPath = "";
    private FragmentGuideCompleteProfileBinding binding;
    private final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        { }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {}
        @Override
        public void afterTextChanged(Editable s) {
            if (getFieldText("full name").length() == 0 ||
                    getFieldText("email").length() == 0 ||
                    getFieldText("phone").length() == 0 ||
                    getFieldText("rate").length() == 0 ||
                    getFieldText("membership number").length() == 0 ||
                    getFieldText("license number").length() == 0 ||
                    getFieldText("education").length() == 0 ||
                    getFieldText("experience").length() == 0){
                binding.fGuideCompleteProfileBtnSubmit.setEnabled(false);
            } else {
                binding.fGuideCompleteProfileBtnSubmit.setEnabled(true);
            }
        }
    };

    public GuideCompleteProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppActivity) getActivity()).changeBottomNavVisibility(View.GONE, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        binding = FragmentGuideCompleteProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = ViewModelProviders.of(this).get(ProfileVM.class);
        handleUserInteractions(view);
    }

    private void handleUserInteractions(View view) {
        binding.fGuideCompleteProfileBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.uploadFile(ppPath);
                vm.filePMutableLiveData.observe(getActivity(), new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        Log.d("YOU", "DID IT");
                    }
                });
                vm.editGuideProfile(
                        getFieldText("full name"),
                        getFieldText("email"),
                        getFieldText("phone"),
                        getFieldText("education"),
                        getFieldText("experience"),
                        Integer.parseInt(getFieldText("rate")),
                        Integer.parseInt(getFieldText("membership number")),
                        Integer.parseInt(getFieldText("license number"))
                );
                vm.profileMutableLiveData.observe(getActivity(), new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        Navigation.findNavController(view).navigate(R.id.action_from_g_complete_profile_to_account_status);
                    }
                });
            }
        });
        binding.fGuideCompleteProfileCivPp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage(REQUEST_IMAGE_PP);
            }
        });
        binding.fGuideCompleteProfileTietFullName.addTextChangedListener(watcher);
        binding.fGuideCompleteProfileTietEmailAddress.addTextChangedListener(watcher);
        binding.fGuideCompleteProfileTietPhone.addTextChangedListener(watcher);
        binding.fGuideCompleteProfileTietExperienceYears.addTextChangedListener(watcher);

    }
    private void pickImage(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, requestCode);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri thumbnail = data.getData();
            PathUtil pathUtil = new PathUtil(getActivity());
            String path = pathUtil.getPath(thumbnail);
            Log.d("CompleteProfile", "Selected Image path: " + path);

            if (requestCode == REQUEST_IMAGE_PP) {
                ppPath = path;
                binding.fGuideCompleteProfileCivPp.setImageURI(thumbnail);
            }
        }
    }
    private String getFieldText(String fieldName){
        switch (fieldName){
            case "full name": return binding.fGuideCompleteProfileTietFullName.getText().toString();
            case "email": return binding.fGuideCompleteProfileTietEmailAddress.getText().toString();
            case "phone": return binding.fGuideCompleteProfileTietPhone.getText().toString();
            case "experience": return binding.fGuideCompleteProfileTietExperienceYears.getText().toString();
            case "education": return binding.fGuideCompleteProfileTietEducation.getText().toString();
            case "rate": return binding.fGuideCompleteProfileTietRate.getText().toString();
            case "license number": return binding.fGuideCompleteProfileTietRate.getText().toString();
            case "membership number": return binding.fGuideCompleteProfileTietRate.getText().toString();
            default: return "invalid";
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}