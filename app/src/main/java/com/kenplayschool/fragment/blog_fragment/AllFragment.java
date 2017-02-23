package com.kenplayschool.fragment.blog_fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kenplayschool.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllFragment extends Fragment {

    private View rootView;
    private RecyclerView mRecyclerView;

    public AllFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_all, container, false);
        //recycle view
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.all_list);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLinearLayoutManager.scrollToPosition(0);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
//        BlogsAdapter adapter = new BlogsAdapter();
//        mRecyclerView.setAdapter(adapter);
        return rootView;
    }

}
