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
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class BackgroundActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.background_activity);
        TextView abstractTitle = (TextView) findViewById(R.id.text_abstract_title);
        TextView abstractContent= (TextView) findViewById(R.id.text_abstract_content);
        TextView problemTitle = (TextView) findViewById(R.id.text_problem_title);
        TextView problemContent = (TextView) findViewById(R.id.text_problem_content);
        abstractTitle.setText(R.string.abstract_title);
        abstractContent.setText(R.string.abstract_content);
        problemTitle.setText(R.string.problem_title);
        problemContent.setText(R.string.problem_content);
    }
}
