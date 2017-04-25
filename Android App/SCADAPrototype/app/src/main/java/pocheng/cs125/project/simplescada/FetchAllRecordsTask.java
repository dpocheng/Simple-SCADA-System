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

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FetchAllRecordsTask extends AsyncTask<String, Void, ArrayList<DeviceInfo>> {
    private final String LOG_TAG = FetchAllRecordsTask.class.getSimpleName();

    private ArrayList<DeviceInfo> getDeviceDataFromJson(String deviceDataJsonStr) throws JSONException {
        final String SENSOR_ID = "id";
        final String DEVICE_TAG = "device";
        final String DEVICE_AREA = "area";
        final String TEMPERATURE = "val";
        final String TIMESTAMP = "time";

        ArrayList<DeviceInfo> deviceInfoArrayList = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(deviceDataJsonStr);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject deviceObject = jsonArray.getJSONObject(i);
            String device_sensorId = deviceObject.getString(SENSOR_ID);
            String device_deviceTag = deviceObject.getString(DEVICE_TAG);
            String device_deviceArea = deviceObject.getString(DEVICE_AREA);
            String device_temperature = deviceObject.getString(TEMPERATURE);
            String device_timestamp = deviceObject.getString(TIMESTAMP);
            DeviceInfo deviceInfo = new DeviceInfo(device_sensorId, device_deviceTag, device_deviceArea, device_temperature, device_timestamp);
            deviceInfoArrayList.add(deviceInfo);
        }
        return deviceInfoArrayList;
    }

    @Override
    protected ArrayList<DeviceInfo> doInBackground(String... params) {
        if (params.length == 0) {
            return null;
        }

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String deviceDataJsonStr = null;

        try {
            final String BASE_URL = "http://35.167.243.164/getall?";
            final String INDEX_NUMBER = "from";
            final String NUMBER_OF_DEVICES = "size";

            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(INDEX_NUMBER, params[0])
                    .appendQueryParameter(NUMBER_OF_DEVICES, params[1])
                    .build();
            
            URL url = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }

            deviceDataJsonStr = buffer.toString();
        }
        catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (final IOException e) {
                    Log.e(LOG_TAG, e.getMessage(), e);
                }
            }
        }

        try {
            return getDeviceDataFromJson(deviceDataJsonStr);
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<DeviceInfo> result) {
    }
}
