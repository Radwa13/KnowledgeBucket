package com.example.radwa.knowledgebucket;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.radwa.knowledgebucket.model.Category;
import com.example.radwa.knowledgebucket.model.NetWorkUtilities;
import com.example.radwa.knowledgebucket.model.Questions;
import com.example.radwa.knowledgebucket.model.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Categories extends AppCompatActivity {
    @BindView(R.id.gridview)
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        ButterKnife.bind(this);
        final Category[] categories = new Category[11];
        categories[0] = new Category(R.drawable.history, NetWorkUtilities.HISTORY_QUESTIONS);
        categories[1] = new Category(R.drawable.sports, NetWorkUtilities.SPORTS_QUESTIONS);
        categories[2] = new Category(R.drawable.general_knowldge, NetWorkUtilities.GENERAL_KNOWLDGE_QUESTIONS);
        categories[3] = new Category(R.drawable.art, NetWorkUtilities.ART_QUESTIONS);
        categories[4] = new Category(R.drawable.math, NetWorkUtilities.MATH_QUESTIONS);
        categories[5] = new Category(R.drawable.geography, NetWorkUtilities.GEOGRAPHY_QUESTIONS);
        categories[6] = new Category(R.drawable.sports, NetWorkUtilities.SPORTS_QUESTIONS);
        categories[7] = new Category(R.drawable.general_knowldge, NetWorkUtilities.GENERAL_KNOWLDGE_QUESTIONS);
        categories[8] = new Category(R.drawable.art, NetWorkUtilities.ART_QUESTIONS);
        categories[9] = new Category(R.drawable.math, NetWorkUtilities.MATH_QUESTIONS);
        categories[10] = new Category(R.drawable.geography, NetWorkUtilities.GEOGRAPHY_QUESTIONS);
        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(this, categories);
        gridView.setAdapter(categoriesAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GetData getData = new GetData();
                getData.execute(categories[i].getName());
            }
        });
    }


    public class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog dialog = new ProgressDialog(Categories.this);

        @Override
        protected String doInBackground(String... urlStr) {
            HttpURLConnection httpURLConnection;
            BufferedReader reader;
            int responseCode;
            if (checkInternet()) {

            try {
                    URL url = new URL(urlStr[0]);
                    URLConnection urlConnection = url.openConnection();
                    httpURLConnection = (HttpURLConnection) urlConnection;
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setDoOutput(true);

                    responseCode = httpURLConnection.getResponseCode();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    if (inputStream != null) {
                        if (responseCode == 200) {
                            reader = new BufferedReader(new InputStreamReader(inputStream));
                            StringBuilder buffer = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null) {
                                buffer.append(line);
                            }

                            return buffer.toString();
                        }
                    }
                } catch(MalformedURLException e){
                    e.printStackTrace();
                } catch(IOException e){
                    e.printStackTrace();
                }
            }


                return null;
            }


        @Override
        protected void onPreExecute() {
            //showProgressDialog
            dialog = new ProgressDialog(Categories.this);
            dialog.setMessage("Loading........");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.show();
        }


        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();

            if (result == null) {
                Toast.makeText(Categories.this, getString(R.string.no_internet), Toast.LENGTH_LONG).show();
            } else {
                ArrayList<Result> results = new ArrayList<>();
                Parcel p = Parcel.obtain();

                try {


                    JSONObject obj = new JSONObject(result);
                    if (obj.has(getString(R.string.result))) {
                        for(int i=0;i<obj.getJSONArray(getString(R.string.result)).length();i++)
                        {                    Result myObject = new Result(p);

                            JSONObject questionObj = obj.getJSONArray(getString(R.string.result)).getJSONObject(i);
                            String questionStr = questionObj.getString(getString(R.string.question_key));
                            String correctStr = questionObj.getString(getString(R.string.correct));
                            String category = questionObj.getString(getString(R.string.category));

                            JSONArray incorrect = questionObj.getJSONArray(getString(R.string.incorrect));
                            List<String> answers = new ArrayList<>();
                            for (int j = 0; j < incorrect.length(); j++) {
                                answers.add(incorrect.getString(j));
                            }
                            answers.add(correctStr);
                            myObject.setQuestion(questionStr);
                            myObject.setCorrectAnswer(correctStr);
                            myObject.settAnswers(answers);
                            myObject.setCategory(category);

                            results.add(myObject);
                        }


                    }

                    Intent intent = new Intent(Categories.this, Questins_Activty.class);
                    intent.putParcelableArrayListExtra("hhhh", results);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    }
    public boolean checkInternet() {
        try {
            int timeoutMs = 1500;
            Socket sock = new Socket();
            SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);

            sock.connect(sockaddr, timeoutMs);
            sock.close();

            return true;
        } catch (IOException e) { return false; }
    }
}
