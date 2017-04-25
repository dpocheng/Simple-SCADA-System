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

public class Keyword implements Parcelable {
    private String mKeywordArea;
    private String mKeywordDevice;

    public Keyword(String keywordArea, String keywordDevice) {
        mKeywordArea = keywordArea;
        mKeywordDevice = keywordDevice;
    }

    private Keyword(Parcel in) {
        mKeywordArea = in.readString();
        mKeywordDevice = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "Keyword{" +
                "mKeywordArea='" + mKeywordArea + '\'' +
                ", mKeywordDevice='" + mKeywordDevice + '\'' +
                '}';
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mKeywordArea);
        parcel.writeString(mKeywordDevice);
    }

    public static final Parcelable.Creator<Keyword> CREATOR = new Parcelable.Creator<Keyword>() {
        public Keyword createFromParcel(Parcel parcel) {
            return new Keyword(parcel);
        }

        public Keyword[] newArray(int i) {
            return new Keyword[i];
        }
    };

    public String getmKeywordArea() {
        return mKeywordArea;
    }

    public void setmKeywordArea(String keywordArea) {
        mKeywordArea = keywordArea;
    }

    public String getmKeywordDevice() {
        return mKeywordDevice;
    }

    public void setmKeywordDevice(String keywordDevice) {
        mKeywordDevice = keywordDevice;
    }
}
