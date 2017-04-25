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
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {

    private Toast mToast;
    private String link = "http://io-server.cloudapp.net/action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        LinearLayout buttonList = (LinearLayout) findViewById(R.id.button_list);
        for (int i = 0; i < buttonList.getChildCount(); i++) {
            final Button button = (Button) buttonList.getChildAt(i);
            if (i == 0) {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, BackgroundActivity.class);
                        startActivity(intent);
                    }
                });
            }
            else if (i == 1) {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, ShowAllRecordsActivity.class);
                        startActivity(intent);
                    }
                });
            }
            else {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, SearchKeywordActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_request, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.main_action_request) {
            try {
                SendRequestToSensor sendRequest = new SendRequestToSensor();
                String respond = "Requesting";
                respond = sendRequest.execute().get();
                mToast = Toast.makeText(this, respond, Toast.LENGTH_SHORT);
                mToast.show();
                return true;
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void onClickCreateToast(String toast_msg) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(this, toast_msg, Toast.LENGTH_SHORT);
        mToast.show();
    }
}
