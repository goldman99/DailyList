package com.goldmanalpha.dailydo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;
import com.com.goldmanalpha.dailydo.db.DailyDoDatabaseHelper;
import com.goldmanalpha.androidutility.BackupHelper;

/**
 * Created by IntelliJ IDEA.
 * User: Aaron
 * Date: 1/2/12
 * Time: 2:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class BackupService extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public static final String BACKUP_PREFIX = "backup_prefix";
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String prefix = "";

        if (intent.hasExtra(BACKUP_PREFIX))
        {
            prefix = intent.getStringExtra(BACKUP_PREFIX);
        }

        String path = "data/data/" + this.getPackageName() + "/databases/";

        BackupHelper helper = new BackupHelper();
        helper.backup(path, DailyDoDatabaseHelper.DATABASE_NAME, prefix);

        Toast.makeText(this, "DailyDo Backed Up", Toast.LENGTH_SHORT).show();

        stopSelf();

        return START_NOT_STICKY;
    }
}