package com.techpalle.b30_rottentomatoes;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Frag1 extends Fragment {
    Button b1;
    ArrayList<Movies> al;

    //source
    MyAdapter m;//bridge between  source and destination
    ListView lv1;//Destination
    //Create custom Adapter
    private class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return al.size();
        }

        @Override
        public Object getItem(int position) {
            return al.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = getActivity().getLayoutInflater().inflate(R.layout.row,null);
            TextView tv1= (TextView) v.findViewById(R.id.textView);
            TextView tv2= (TextView) v.findViewById(R.id.textView2);
            TextView tv3= (TextView) v.findViewById(R.id.textView3);
            //Get data from arrayList
            Movies m1 = al.get(position);
            String title = m1.getTitle();
            String [] hero= m1.getCast();
            //fill data onto row
            tv1.setText(title);
            tv2.setText(hero[0]);
            tv3.setText(hero[1]);

            //return row
            return v;
        }
    }
        //create AsyncTask
    public class MyTask extends AsyncTask<String,Void,String>{
        //Declare All variable
            URL url;
            HttpURLConnection con;
            InputStream is;
            InputStreamReader ir;
            BufferedReader br;
            StringBuilder sb;
            @Override
            protected String doInBackground(String... params) {
                try {
                    url=new URL(params[0]);
                    con=(HttpURLConnection) url.openConnection();
                    is=con.getInputStream();
                    ir=new InputStreamReader(is);
                    br=new BufferedReader(ir);
            //now read line by line from buffer reader
                    sb=new StringBuilder();
                    String line=br.readLine();
                    while (line!=null) {
                        sb.append(line);
                        line= br.readLine();
                    }

                    return sb.toString();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                if (s == null) {
                    Toast.makeText(getActivity(), "Some Thing went wrong", Toast.LENGTH_LONG).show();
                    return;
                }
                //JSON Parsing
                try {
                    JSONObject jo=new JSONObject(s);
                    JSONArray ja=jo.getJSONArray("movies");

                    for(int i=0;i<=ja.length();i++){
                        Movies movies=new Movies();
                        String[] cast=new String[2];

                        JSONObject jo1 = ja.getJSONObject(i);

                        String Title = jo1.getString("title");
                        // Log.d("title", Title);

                        JSONArray ja1 = jo1.getJSONArray("abridged_cast");

                        for (int j = 0; j <= 1; j++) {
                            JSONObject jo2 = ja1.getJSONObject(j);
                            cast[j] = jo2.getString("name");
                            // Log.d("name", cast[j]);
                            movies.setTitle(Title);
                            movies.setCast(cast);

                        }
                        al.add(movies);
                        m.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                super.onPostExecute(s);
                }
            }


    public Frag1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_frag1,container,false);
        b1= (Button) v.findViewById(R.id.button);
        lv1= (ListView) v.findViewById(R.id.listView);
        al=new ArrayList<Movies>();
                    m = new MyAdapter();
                    lv1.setAdapter(m);
                    b1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            MyTask m = new MyTask();
                            m.execute("http://api.rottentomatoes.com/api/public/v1.0/movies.json?q=titans&apikey=ny97sdcpqetasj8a4v2na8va");
            }


        });

        return v;
    }

}
