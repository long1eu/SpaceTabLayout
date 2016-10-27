package eu.long1.spacebuttombardemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import eu.long1.spacebuttonbar.SpaceButtonView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SpaceButtonView spaceButton = (SpaceButtonView) findViewById(R.id.spaceButton);
        spaceButton.setButtonImage(R.drawable.ic_fail);
    }
}
