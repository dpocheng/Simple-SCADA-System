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
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SearchKeywordFragment extends Fragment {
    private String areaInput = "empty";
    private String deviceInput = "empty";

    public SearchKeywordFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_keyword_fragment, container, false);
        final TextView textView_one = (TextView) rootView.findViewById(R.id.textView1);
        final EditText areaEditText = (EditText) rootView.findViewById(R.id.area_editText);
        areaEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_NEXT) {
                    textView_one.setText("");
                    areaInput = textView.getText().toString();
                    if (areaInput.equals("")) {
                        areaInput = "empty";
                        handled = true;
                    }
                }
                return handled;
            }
        });
        EditText deviceEditText = (EditText) rootView.findViewById(R.id.device_editText);
        deviceEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    deviceInput = textView.getText().toString();
                    if (deviceInput.equals("")) {
                        deviceInput = "empty";
                    }
                }
                return false;
            }
        });
        Button searchButton = (Button) rootView.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty(areaEditText)) {
                    textView_one.setText(R.string.area_empty_warning);
                }
                else if (areaInput.equals("empty")) {
                    textView_one.setText(R.string.area_assign_warning);
                }
                else {
                    textView_one.setText("");
                    Keyword keyword = new Keyword(areaInput, deviceInput);
                    Intent intent = new Intent(getActivity(), SearchResultsActivity.class);
                    intent.putExtra("keyword", keyword);
                    startActivity(intent);
                }
            }
        });
        return rootView;
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() <= 0;
    }

}
