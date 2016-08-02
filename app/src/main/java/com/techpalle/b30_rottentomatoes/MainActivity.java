package com.techpalle.b30_rottentomatoes;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm=getSupportFragmentManager();
        Frag1 f=new Frag1();
        FragmentTransaction ft=fm.beginTransaction();
        ft.add(R.id.container,f);
        ft.commit();
    }
}
