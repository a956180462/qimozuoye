package cna.self.qimozuoye;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public interface MainHandler {
    int REG_RESULT      = 100020;
    Handler handler = new Handler(Looper.getMainLooper(), msg -> {
        switch (msg.what){
            case REG_RESULT:
                if (msg.arg1 < 1){
                    Toast.makeText(
                            (Context) msg.obj,
                            "注册失败",
                            Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(
                            (Context) msg.obj,
                            "注册成功",
                            Toast.LENGTH_SHORT).show();
                }
            break;
        }
        return false;
    });






}
