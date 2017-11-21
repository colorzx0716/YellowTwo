package com.hxe.platform.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hxe.platform.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 张肖肖 on 2017/11/20.
 */

public class Fragment1 extends Fragment {

    private View view;
    private Button fg1_btn;
    private SharedPreferences success;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getContext(), R.layout.fragment1, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fg1_btn = view.findViewById(R.id.fg1_btn);
        fg1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //清除SharedPreferences
                success = getContext().getSharedPreferences("success", MODE_PRIVATE);
                SharedPreferences.Editor edit = success.edit();
                edit.clear();
                edit.commit();
            }
        });
    }
}
