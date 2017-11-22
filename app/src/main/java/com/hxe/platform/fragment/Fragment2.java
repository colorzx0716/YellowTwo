package com.hxe.platform.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.hxe.platform.R;

/**
 * Created by 张肖肖 on 2017/11/20.
 */

public class Fragment2 extends Fragment {

    private View view;
    private ImageView fg2_img;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getContext(), R.layout.fragment2, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fg2_img = view.findViewById(R.id.fg2_img);
        fg2_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getContext(), "被点击了，要扫码", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
