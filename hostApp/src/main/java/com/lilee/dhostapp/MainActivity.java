package com.lilee.dhostapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lilee.plugin.lib.IDynamicRes;
import com.lilee.plugin.lib.IBean;
import com.lilee.plugin.lib.ICallback;
import com.lilee.plugin.utils.RefInvoke;

import java.lang.reflect.Method;


/**
 * 测试sdk版本 android 5.0
 * 4.4 会抛出异常：Class ref in pre-verified class resolved to unexpected implementation
 */
public class MainActivity extends BaseActivity {

    private TextView tv;
    private Button btnNormal;
    private Button btnExtends;
    private Button btnCallBack;
    private Button btnGetResString;

    private Button btnDefault;

    private Button btnFirstPlugin;
    private Button btnSecondPlugin;
    private TextView tvRes;
    private ImageView ivRobot;
    private LinearLayout layout;
    private Button btnStartTestService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);

        btnDefault = findViewById(R.id.btn_default);
        btnNormal = findViewById(R.id.btn_normal);
        btnExtends = findViewById(R.id.btn_extends);
        btnCallBack = findViewById(R.id.btn_callback);
        btnGetResString = findViewById(R.id.btn_getResStr);
        btnFirstPlugin = findViewById(R.id.btn_fist_plugin);
        btnSecondPlugin = findViewById(R.id.btn_second_plugin);
        btnStartTestService = findViewById(R.id.btn_startTestService);

        ivRobot = findViewById(R.id.iv_robot);
        tvRes = findViewById(R.id.tv_res);
        layout = findViewById(R.id.layout);

        btnDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String defaultStr = getResources().getString(R.string.default_string);
                tv.setText(defaultStr);
                Toast.makeText(getApplicationContext(), defaultStr, Toast.LENGTH_LONG).show();
            }
        });

        btnNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class classBean;
                try {
                    classBean = plugins.get(firstApkName).getClassLoader().loadClass("com.lilee.pluginone.Bean");
                    /**
                     * when call at 4.4 will throw exception:
                     * Class ref in pre-verified class resolved to unexpected implementation
                     */
                    Object beanObject = classBean.newInstance();

                    // 方式1
                    Method getName = classBean.getMethod("getName");
                    getName.setAccessible(true);
                    String name = (String) getName.invoke(beanObject);

                    tv.setText(name);
                    Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.w(TAG, "Exception : " + e.getMessage());
                }
            }
        });

        btnExtends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class classBean;
                try {
                    classBean = plugins.get(firstApkName).getClassLoader().loadClass("com.lilee.pluginone.Bean");
                    Object beanObject = classBean.newInstance();

                    //方式2
                    IBean iBean = (IBean) beanObject;
                    iBean.setName("test plugin lib");
                    String name = iBean.getName();

                    tv.setText(name);
                    Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.w(TAG, "Exception : " + e.getMessage());
                }
            }
        });

        btnCallBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class classBean;
                try {
                    classBean = plugins.get(firstApkName).getClassLoader().loadClass("com.lilee.pluginone.Bean");
                    Object beanObject = classBean.newInstance();

                    //方式2
                    IBean iBean = (IBean) beanObject;
                    iBean.setName("test plugin lib callback");


                    iBean.registerCallBack(new ICallback() {
                        @Override
                        public void sendResult(String result) {
                            tv.setText(result);
                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.w(TAG, "Exception : " + e.getMessage());
                }
            }
        });

        btnGetResString.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                loadResources(plugins.get(firstApkName).getDexPath(), new Runnable() {
                    @Override
                    public void run() {
                        Class clazz;
                        //手动标记是加载plugin中的资源还是apk中的资源---这种方式不理想。
                        try {
                            clazz = plugins.get(firstApkName).getClassLoader().loadClass("com.lilee.pluginone.DynamicRes");
                            Object obj = clazz.newInstance();

                            IDynamicRes dynamicRes = (IDynamicRes) obj;

                            String result = dynamicRes.getStringForResId(MainActivity.this);
                            tv.setText(result);
                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.w(TAG, "Exception : " + e.getMessage());
                        }
                    }
                });
            }
        });

        btnFirstPlugin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadResources(plugins.get(firstApkName).getDexPath(), new Runnable() {
                    @Override
                    public void run() {
//                        changeUIFirstStyle(plugins.get(firstApkName).getClassLoader(),"com.lilee.pluginone.UiUtil");
                        changeUISecondStyle(plugins.get(firstApkName).getClassLoader(), "com.lilee.pluginone.R");
                    }
                });
            }
        });

        btnSecondPlugin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadResources(plugins.get(secondApkName).getDexPath(), new Runnable() {
                    @Override
                    public void run() {
//                        changeUIFirstStyle(plugins.get(secondApkName).getClassLoader(),"com.lilee.plugintwo.UiUtil");
                        changeUISecondStyle(plugins.get(secondApkName).getClassLoader(), "com.lilee.plugintwo.R");
                    }
                });
            }
        });


        btnStartTestService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent();
                    String serviceName = plugins.get(firstApkName).getPackageInfo().packageName + ".service.TestService";
                    intent.setClass(MainActivity.this, plugins.get(firstApkName).getClassLoader().loadClass(serviceName));
//                    intent.setClass(MainActivity.this, Class.forName(serviceName));
                    startService(intent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void changeUIFirstStyle(ClassLoader dexClassLoader, String cls) {
        Class clazz = null;
        try {
            clazz = dexClassLoader.loadClass(cls);
            String str = (String) RefInvoke.invokeStaticMethod(clazz, "getTextString", Context.class, this);
            tvRes.setText(str);

            Drawable drawable = (Drawable) RefInvoke.invokeStaticMethod(clazz, "getImageDrawable", Context.class, this);
            ivRobot.setImageDrawable(drawable);

            layout.removeAllViews();
            View view = (View) RefInvoke.invokeStaticMethod(clazz, "getLayout", Context.class, this);
            Log.d(TAG, "View == null ? " + (view == null));
            Toast.makeText(getApplicationContext(), "View == null ? " + (view == null), Toast.LENGTH_LONG).show();
            layout.addView(view);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void changeUISecondStyle(ClassLoader dexClassLoader, String clsR) {
        try {
            Class<?> sClass = dexClassLoader.loadClass(clsR + "$string");
            int resStrId = (int) RefInvoke.getStaticFieldObject(sClass, "hello_message");
            tvRes.setText(resStrId);

            Class<?> dClass = dexClassLoader.loadClass(clsR + "$drawable");
            int resDrawableId = (int) RefInvoke.getStaticFieldObject(dClass, "robot");
            ivRobot.setImageResource(resDrawableId);

            layout.removeAllViews();
            Class<?> layoutClass = dexClassLoader.loadClass(clsR + "$layout");
            int resLayoutId = (int) RefInvoke.getStaticFieldObject(layoutClass, "activity_default");
            layout.addView(LayoutInflater.from(this).inflate(resLayoutId, null));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
