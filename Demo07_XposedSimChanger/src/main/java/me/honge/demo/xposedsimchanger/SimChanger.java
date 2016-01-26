package me.honge.demo.xposedsimchanger;

import android.content.Context;
import android.os.Environment;
import android.telephony.TelephonyManager;

import java.io.File;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by hong on 16/1/22.
 */
public class SimChanger implements IXposedHookLoadPackage {
    private static final String MY_PACKAGE_NAME = SimChanger.class.getPackage().getName();
    Class<?> hookClass;

//    @Override
//    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
//        File prefFile = new File(Environment.getDataDirectory(), "data/" + MY_PACKAGE_NAME + "/shared_prefs/" + "MainActivity.xml");
//
//        XSharedPreferences prefs;
//
//        if (!prefFile.exists()) {
//            return;
//        }
//        prefs = new XSharedPreferences(MY_PACKAGE_NAME, "MainActivity");
//
//
//        final String number = prefs.getString("number", null);
//        XposedHelpers.findAndHookMethod(TelephonyManager.class, "getLine1Number",Integer.class, new XC_MethodHook() {
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                XposedBridge.log("getLine1Number");
//
//                if (number == null)
//                    return;
//
//                param.setResult(number);
//                super.afterHookedMethod(param);
//
//            }
//        });
//    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (lpparam.packageName.equals("com.android.phone")) {

            try {
                hookClass = XposedHelpers.findClass("com.android.phone.PhoneGlobals", lpparam.classLoader);
                XposedBridge.log("PhoneGlobals");
            } catch (XposedHelpers.ClassNotFoundError e) {
                try {
                    hookClass = XposedHelpers.findClass("com.android.phone.PhoneApp", lpparam.classLoader);
                    XposedBridge.log("PhoneApp");
                } catch (XposedHelpers.ClassNotFoundError ex) {
                    XposedBridge.log("Classes don't exist - version not supported");
                    return;
                }
            }

            File prefFile = new File(Environment.getDataDirectory(), "data/" + MY_PACKAGE_NAME + "/shared_prefs/" + "simChanger.xml");

            XSharedPreferences prefs;

            if (!prefFile.exists()) {
                return;
            }
            prefs = new XSharedPreferences(MY_PACKAGE_NAME, "simChanger");


            final String number = prefs.getString("number", null);

            if (number == null)
                return;


            XposedHelpers.findAndHookMethod(hookClass, "onCreate", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    final Object phone;
                    try {
                        phone = XposedHelpers.callStaticMethod(hookClass, "getPhone");
                    } catch (Exception e) {
                        XposedBridge.log("getPhone method not invoked!");
                        return;
                    }

                    if (phone == null) {
                        XposedBridge.log("Phone was null, it wasn't supposed!");
                        return;
                    }

                    final TelephonyManager tm = (TelephonyManager) ((Context) param.thisObject).getSystemService("phone");

                    new Thread(new Runnable() {
                        public void run() {

                            for (int simState = tm.getSimState(); simState != TelephonyManager.SIM_STATE_READY; simState = tm.getSimState()) {
                                try {
                                    XposedBridge.log("sleep");
                                    Thread.sleep(5000);
                                } catch (InterruptedException e) {
                                }
                            }

                            String tag = (String) XposedHelpers.callMethod(phone, "getLine1AlphaTag");

                            if (tag == null || tag.isEmpty() || tag.trim().equals("")) {
                                tag = "Line 1";
                                XposedBridge.log("Tag was empty - 'Line 1' now");
                            }

                            String actualNumber = (String) XposedHelpers.callMethod(phone, "getLine1Number");

                            XposedBridge.log("Actual Number is: " + actualNumber);

                            if (number.equals(actualNumber)) {
                                return;
                            }

                            XposedBridge.log("Going to write SIM number (" + number + ") now!");

                            XposedHelpers.callMethod(phone, "setLine1Number", tag, number, null);

                            XposedBridge.log("write SIM number (" + number + ") done!");
                        }
                    }).start();
                }
            });
        }
    }
}
