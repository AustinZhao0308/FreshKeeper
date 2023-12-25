package com.example.freshkeeper;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import androidx.core.app.JobIntentService;

public class MyBackgroundService extends JobIntentService {
    private static final String TAG = "MyBackgroundService";

    // 一个唯一的 ID，用于标识这个服务
    private static final int JOB_ID = 1001;

    // 启动服务的方法
    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, MyBackgroundService.class, JOB_ID, work);
    }

    // 实际执行后台任务的方法
    @Override
    protected void onHandleWork(Intent intent) {
        // 这里添加你的后台任务逻辑，比如检查过期物品并发送通知
        Log.d(TAG, "Background task triggered at " + System.currentTimeMillis());

        // 模拟一个长时间运行的任务
        simulateLongRunningTask();
    }

    private void simulateLongRunningTask() {
        // 模拟一个长时间运行的任务，例如网络请求或复杂计算
        SystemClock.sleep(5000); // 5秒
    }
}
