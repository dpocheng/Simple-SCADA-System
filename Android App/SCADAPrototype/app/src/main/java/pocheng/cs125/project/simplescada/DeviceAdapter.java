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

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {
    private Context mContext;
    private List<DeviceInfo> mDeviceInfoList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.sensor_header) TextView sensorHeader;
        @BindView(R.id.sensor_number) TextView sensorNumber;
        @BindView(R.id.list_item_recoreds_textview) TextView mTextView;
        public View v;
        public ViewHolder(View view) {
            super(view);
            v = view;
            ButterKnife.bind(this, view);
        }
    }

    public DeviceAdapter(Context context, ArrayList<DeviceInfo> deviceInfoList) {
        mContext = context;
        mDeviceInfoList = deviceInfoList;
    }

    @Override
    public DeviceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_records, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTextView.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                DeviceInfo deviceInfo = mDeviceInfoList.get(position);
                Intent intent = new Intent(context, RecordDetailsActivity.class);
                intent.putExtra("device_info", deviceInfo);
                context.startActivity(intent);
            }
        });
        String deviceArea = mDeviceInfoList.get(position).getDeviceArea();
        holder.sensorHeader.setText(deviceArea);
        String sensorId = mDeviceInfoList.get(position).getSensorId();
        if (sensorId.equals("1")) {
            holder.sensorNumber.setText("Temperature");
        }
        else if (sensorId.equals("2")) {
            holder.sensorNumber.setText("Light");
        }
        else if (sensorId.equals("3")) {
            holder.sensorNumber.setText("Sound");
        }
        String timestamp = mDeviceInfoList.get(position).getTimestamp();
        holder.mTextView.setText(timestamp);
    }

    @Override
    public int getItemCount() {
        return mDeviceInfoList.size();
    }
}
