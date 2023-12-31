package cna.self.qimozuoye;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import cna.self.qimozuoye.data.DataBaseHolder;
import cna.self.qimozuoye.databinding.ActivityMainBinding;
/**
 * 该 App使用了 Navigation 控件
 * Navigation使用时在 Fragment 中不再使用findViewById方法
 * 而是获取对应Fragment 的 binding
 * 通过 binding 获取视图
 * */
public class MainActivity extends AppCompatActivity implements MainHandler{


    private AppBarConfiguration appBarConfiguration;
    private static boolean forQuit = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBaseHolder.RegDB(this);


        cna.self.qimozuoye.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // 菜单键，没设置，就一个没有用的键
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // 启动
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    // 返回键监听
    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager()
                                .findFragmentById(R.id.nav_host_fragment_content_main);
        // 限定在FirstFragment和 LoginFragment中发生变化
        if (fragment != null &&
                (DataTransfer.isOnFirstFragment || DataTransfer.isOnLoginFragment)) {
            if(forQuit){
                // 退出程序
                finish();
            }else {
                // 进入退出准备状态
                forQuit = true;
                Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                Log.e("Quit","0");
                new Thread(() -> {
                    try {
                        // 新线程等待1.2s
                        synchronized (this){
                            wait(1200);
                            // 结束退出准备状态
                            forQuit = false;
                            Log.e("Quit","false");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
            return;
        }
        super.onBackPressed();
    }




}