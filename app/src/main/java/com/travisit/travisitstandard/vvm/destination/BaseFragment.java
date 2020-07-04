package com.travisit.travisitstandard.vvm.destination;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    public boolean isLengthMoreThanSix(String text){
        return (text.length() > 6) ? true : false;
    }

}
