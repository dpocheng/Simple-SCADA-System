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

import android.os.Parcel;
import android.os.Parcelable;

public class DeviceInfo implements Parcelable {
    private String sensorId;
    private String deviceTag;
    private String deviceArea;
    private String temperature;
    private String timestamp;

    public DeviceInfo(String sensorId, String deviceTag, String deviceArea, String temperature, String timestamp) {
        this.sensorId = sensorId;
        this.deviceTag = deviceTag;
        this.deviceArea = deviceArea;
        this.temperature = temperature;
        this.timestamp = timestamp;
    }

    private DeviceInfo(Parcel in) {
        sensorId = in.readString();
        deviceTag = in.readString();
        deviceArea = in.readString();
        temperature = in.readString();
        timestamp = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "DeviceInfo{" +
                "sensorId='" + sensorId + '\'' +
                "deviceTag='" + deviceTag + '\'' +
                "deviceArea='" + deviceArea + '\'' +
                "temperature='" + temperature + '\'' +
                "timestamp='" + timestamp + '\'' +
                '}';
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(sensorId);
        parcel.writeString(deviceTag);
        parcel.writeString(deviceArea);
        parcel.writeString(temperature);
        parcel.writeString(timestamp);
    }

    public static final Parcelable.Creator<DeviceInfo> CREATOR = new Parcelable.Creator<DeviceInfo>() {
        public DeviceInfo createFromParcel(Parcel parcel) {
            return new DeviceInfo(parcel);
        }

        public DeviceInfo[] newArray(int i) {
            return new DeviceInfo[i];
        }
    };

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getDeviceTag() {
        return deviceTag;
    }

    public void setDeviceTag(String deviceTag) {
        this.deviceTag = deviceTag;
    }

    public String getDeviceArea() {
        return deviceArea;
    }

    public void setDeviceArea(String deviceArea) {
        this.deviceArea = deviceArea;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
