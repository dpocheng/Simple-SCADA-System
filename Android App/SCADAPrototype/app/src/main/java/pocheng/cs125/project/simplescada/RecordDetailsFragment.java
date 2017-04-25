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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecordDetailsFragment extends Fragment {
    @BindView(R.id.record_device_header) TextView mDeviceHeader;
    @BindView(R.id.record_device_tag) TextView mDeviceTag;
    @BindView(R.id.record_sensor_header) TextView mSensorHeader;
    @BindView(R.id.record_sensor_number) TextView mSensorNumber;
    @BindView(R.id.record_area_header) TextView mAreaHeader;
    @BindView(R.id.record_area_number) TextView mAreaNumber;
    @BindView(R.id.record_temperature_header) TextView mTemperatureHeader;
    @BindView(R.id.record_temperature_value) TextView mTemperatureValue;
    @BindView(R.id.record_timestamp_header) TextView mTimeStampHeader;
    @BindView(R.id.record_timestamp_value) TextView mTimeStampValue;

    public RecordDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.record_details_fragment, container, false);

        DeviceInfo parcelableExtra = getActivity().getIntent().getParcelableExtra("device_info");

        ButterKnife.bind(this, rootView);

        mDeviceHeader.setText(R.string.device_header);
        mDeviceTag.setText(parcelableExtra.getDeviceTag());
        mSensorHeader.setText(R.string.sensor_header);
        String sensorType = parcelableExtra.getSensorId();
        if (sensorType.equals("1")) {
            mSensorNumber.setText("Temperature");
        }
        else if (sensorType.equals("2")) {
            mSensorNumber.setText("Light");
        }
        else if (sensorType.equals("3")) {
            mSensorNumber.setText("Sound");
        }
        else {
            mSensorNumber.setText("null");
        }
        mAreaHeader.setText(R.string.area_header);
        mAreaNumber.setText(parcelableExtra.getDeviceArea());
        mTemperatureHeader.setText(R.string.temperature_header);
        mTemperatureValue.setText(parcelableExtra.getTemperature());
        mTimeStampHeader.setText(R.string.timestamp_header);
        mTimeStampValue.setText(parcelableExtra.getTimestamp());

        return rootView;
    }

}
