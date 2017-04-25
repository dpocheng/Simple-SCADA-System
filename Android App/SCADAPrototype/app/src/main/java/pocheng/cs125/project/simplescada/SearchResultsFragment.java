/*
 * Copyright 2017 Pok On Cheng
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pocheng.cs125.project.simplescada;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultsFragment extends Fragment {
    private AppCompatActivity mActivity;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private String keywordArea;
    private String keywordDevice;
    @BindView(R.id.results_area_header) TextView mAreaHeader;
    //@BindView(R.id.results_area) TextView mArea;
    @BindView(R.id.results_device_header) TextView mDeviceHeader;
    //@BindView(R.id.results_device) TextView mDevice;

    public SearchResultsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_results_fragment, container, false);

        Keyword keyword = getActivity().getIntent().getParcelableExtra("keyword");
        keywordArea = keyword.getmKeywordArea();
        keywordDevice = keyword.getmKeywordDevice();

        ButterKnife.bind(this, rootView);

        mAreaHeader.setText("Area: " + keywordArea);
        mDeviceHeader.setText("Device: " + keywordDevice);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.device_recyclerview);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new KeywordAdapter(getActivity(), new ArrayList<DeviceInfo>());
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateKeyword();
    }

    private void updateKeyword() {
        FetchKeywordTask keywordTask = new FetchKeywordTask();
        try {
            mAdapter = new KeywordAdapter(getActivity(), keywordTask.execute(keywordArea, keywordDevice).get());
            mRecyclerView.setAdapter(mAdapter);
            Log.d("updateKeyword()", "In FetchKeywordTask");
        }
        catch (ExecutionException| InterruptedException e) {
            e.printStackTrace();
        }
    }

}
