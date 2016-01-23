package me.honge.demo.xposedsimchanger;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_READ_PHONE_STATE_CODE = 10;
    private SharedPreferences prefs;
    private EditText ev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences("simChanger", MODE_PRIVATE);

        ev = (EditText) findViewById(R.id.newNumber);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            //如果App的权限申请曾经被用户拒绝过，就需要在这里跟用户做出解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {
                showToast(R.string.need_permision);
            } else {
                //进行权限请求
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
                        REQUEST_READ_PHONE_STATE_CODE);
            }

            return;
        }


        init();
    }


    private void init() {

        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String phone = tm.getLine1Number();

        if (phone != null && !phone.isEmpty() && !phone.trim().equals("")) {
            TextView tv = (TextView) findViewById(R.id.actualNumber);
            tv.setText(phone);
            tv.setTypeface(null, Typeface.NORMAL);
        }

        String number = prefs.getString("number", "");
        ev.setText(number);

        Button b = (Button) findViewById(R.id.changeButton);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String number = ev.getText().toString().trim();

                boolean saved = prefs.edit().putString("number", number).commit();

                if (saved) {
                    showToast(R.string.preferences_saved);
                } else {
                    showToast(R.string.preferences_not_saved);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    init();
                } else {
                    // Permission Denied
                    showToast(R.string.permision_failed);
                    finish();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.hide_appicon:
                hideIcon(true);
                break;
            case R.id.show_appicon:
                hideIcon(false);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (prefs.getBoolean("is_show_appicon", true)) {
            menu.findItem(R.id.hide_appicon).setVisible(true);
            menu.findItem(R.id.show_appicon).setVisible(false);
        } else {
            menu.findItem(R.id.hide_appicon).setVisible(false);
            menu.findItem(R.id.show_appicon).setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void showToast(int resId) {
        Toast.makeText(MainActivity.this, resId, Toast.LENGTH_LONG).show();
    }


    private void hideIcon(boolean hide) {
        PackageManager p = getPackageManager();
        if (hide) {
            // 将app图标隐藏：
            p.setComponentEnabledSetting(new ComponentName(this,"me.honge.demo.xposedsimchanger.DefaultActivity"),
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP);
            prefs.edit().putBoolean("is_show_appicon",false).commit();
            showToast(R.string.hide_appicon);
        } else {
            // 将app图标显示：
            p.setComponentEnabledSetting(new ComponentName(this,"me.honge.demo.xposedsimchanger.DefaultActivity"),
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);
            showToast(R.string.show_appicon);
            prefs.edit().putBoolean("is_show_appicon",true).commit();
        }
        invalidateOptionsMenu();
    }

}
