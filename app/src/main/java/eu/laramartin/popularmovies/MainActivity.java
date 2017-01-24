package eu.laramartin.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import eu.laramartin.popularmovies.api.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetworkUtils.buildUrl("12");
    }
}
