package me.honge.demo02_intentservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent1 = new Intent();
        intent1.setAction("me.honge.demo02_intentservice.IntentServiceDemo");
        intent1.setPackage(getPackageName());
        intent1.putExtra("op", "op1");
        startService(intent1);


        Intent intent2 = new Intent();
        intent2.setAction("me.honge.demo02_intentservice.IntentServiceDemo");
        intent2.setPackage(getPackageName());
        intent2.putExtra("op","op2");
        startService(intent2);

    }
}
