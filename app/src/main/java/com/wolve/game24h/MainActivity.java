package com.wolve.game24h;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wolve.libcommon.utils.VNStatusBar;
import com.wolve.game24h.model.Destination;
import com.wolve.game24h.utils.VNAppConfig;
import com.wolve.game24h.utils.VNNavGraphBuilder;
import com.wolve.game24h.view.VNAppBottomBar;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

/**
 * App 主页 入口
 * <p>
 * 1.底部导航栏 使用VNAppBottomBar 承载
 * 2.内容区域 使用VNWindowInsetsNavHostFragment 承载
 * <p>
 * 3.底部导航栏 和 内容区域的 切换联动 使用VNNavController驱动
 * 4.底部导航栏 按钮个数和 内容区域destination个数。由注解处理器NavProcessor来收集,生成assets/destination.json
 */
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private NavController navController;
    private VNAppBottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        VNStatusBar.setStatusBarMode(this, false, R.color.colorEbonyClay);
        //以下代码用于去除阴影
        if(Build.VERSION.SDK_INT>=21){
            getSupportActionBar().setElevation(0);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomBar = findViewById(R.id.nav_view);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = NavHostFragment.findNavController(fragment);
        VNNavGraphBuilder.build(this, fragment.getChildFragmentManager(), navController, fragment.getId());

        bottomBar.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        HashMap<String, Destination> destConfig = VNAppConfig.getDestConfig();
        Iterator<Map.Entry<String, Destination>> iterator = destConfig.entrySet().iterator();
        //遍历 target destination 是否需要登录拦截
//        while (iterator.hasNext()) {
//            Map.Entry<String, Destination> entry = iterator.next();
//            Destination value = entry.getValue();
//            if (value != null && !UserManager.get().isLogin() && value.needLogin && value.id == menuItem.getItemId()) {
//                UserManager.get().login(this).observe(this, new Observer<User>() {
//                    @Override
//                    public void onChanged(User user) {
//                        if (user != null) {
//                            navView.setSelectedItemId(menuItem.getItemId());
//                        }
//                    }
//                });
//                return false;
//            }
//        }
//
//        if (item.getItemId() == 590027178){
//            getSupportActionBar().show();
//        }else{
//            getSupportActionBar().hide();
//        }
        navController.navigate(item.getItemId());
        return !TextUtils.isEmpty(item.getTitle());
    }

    @Override
    public void onBackPressed() {
        //当前正在显示的页面destinationId
        int currentPageId = navController.getCurrentDestination().getId();

        //APP页面路导航结构图  首页的destinationId
        int homeDestId = navController.getGraph().getStartDestination();

        //如果当前正在显示的页面不是首页，而我们点击了返回键，则拦截。
        if (currentPageId != homeDestId) {
            bottomBar.setSelectedItemId(homeDestId);
            return;
        }
        //否则 finish，此处不宜调用onBackPressed。因为navigation会操作回退栈,切换到之前显示的页面。
        finish();
    }

    /**
     * bugfix:
     * 当MainActivity因为内存不足或系统原因 被回收时 会执行该方法。
     * <p>
     * 此时会触发 FragmentManangerImpl#saveAllState的方法把所有已添加的fragment基本信息给存储起来(view没有存储)，以便于在恢复重建时能够自动创建fragment
     * <p>
     * 但是在fragment嵌套fragment的情况下，被内嵌的fragment在被恢复时，生命周期被重新调度，出现了错误。没有重新走onCreateView 方法
     * 从而会触发throw new IllegalStateException("Fragment " + fname did not create a view.");的异常
     * <p>
     * 但是在没有内嵌fragment的情况，没有问题、
     * <p>
     * <p>
     * 那我们为了解决这个问题，网络上也有许多方案，但都不尽善尽美。
     * <p>
     * 此时我们复写onSaveInstanceState，不让 FragmentManangerImpl 保存fragment的基本数据，恢复重建时，再重新创建即可
     *
     * @param outState
     */
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        disableABCShowHideAnimation(getSupportActionBar());
        return super.onCreateOptionsMenu(menu);
    }

    public void disableABCShowHideAnimation(ActionBar actionBar) {
        try {
            actionBar.getClass().getDeclaredMethod("setShowHideAnimationEnabled", boolean.class).invoke(actionBar, false);
        } catch (Exception exception) {
            try {
                Field mActionBarField = actionBar.getClass().getSuperclass().getDeclaredField("mActionBar");
                mActionBarField.setAccessible(true);
                Object icsActionBar = mActionBarField.get(actionBar);
                Field mShowHideAnimationEnabledField = icsActionBar.getClass().getDeclaredField("mShowHideAnimationEnabled");
                mShowHideAnimationEnabledField.setAccessible(true);
                mShowHideAnimationEnabledField.set(icsActionBar,false);
                Field mCurrentShowAnimField = icsActionBar.getClass().getDeclaredField("mCurrentShowAnim");
                mCurrentShowAnimField.setAccessible(true);
                mCurrentShowAnimField.set(icsActionBar,null);
            }catch (Exception e){
            }
        }
    }
}