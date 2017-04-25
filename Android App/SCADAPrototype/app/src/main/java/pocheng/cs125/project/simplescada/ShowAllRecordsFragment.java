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

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ShowAllRecordsFragment extends Fragment {
    private AppCompatActivity mActivity;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public ShowAllRecordsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.show_all_records_refresh, menu);
        inflater.inflate(R.menu.show_all_records_settings, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //if (id == R.id.show_all_records_action_refresh) {
        //    return true;
        //}
        if(id == R.id.show_all_records_action_settings) {
            mActivity = (AppCompatActivity) getActivity();
            startActivity(new Intent(mActivity, ShowAllRecordsSettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.show_all_records_fragment, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.device_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager;
        mActivity = (AppCompatActivity) getActivity();
        mLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mEditor = mSharedPreferences.edit();
        mEditor.apply();
        mAdapter = new DeviceAdapter(getActivity(), new ArrayList<DeviceInfo>());
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    private void updateDevice() {
        FetchAllRecordsTask devicesTask = new FetchAllRecordsTask();
        String index = mSharedPreferences.getString(getString(R.string.pref_index_key), getString(R.string.pref_index_default));
        String size = mSharedPreferences.getString(getString(R.string.pref_size_key), getString(R.string.pref_size_default));
        try {
            mAdapter = new DeviceAdapter(getActivity(), devicesTask.execute(index, size).get());
            mRecyclerView.setAdapter(mAdapter);
            Log.d("updateDevice()", "In FetchAllRecordsTask");
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        updateDevice();
    }
}
