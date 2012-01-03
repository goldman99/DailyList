package com.goldmanalpha.androidutility;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Aaron
 * Date: 1/2/12
 * Time: 2:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class BackupHelper {


    //path must have proper ending slash
    public void backup(String path, String fileName, String prefix)
    {
                String datePrefix = new SimpleDateFormat("yyyyMMdd.HHmm").format(new Date());
                FileHelper helper = new FileHelper();

                String backupSuffix = ".backup." + fileName;
                String backupPath = path + prefix + datePrefix + backupSuffix ;
                File backupFile = new File(backupPath);

                if (!backupFile.exists()) {
                    try {
                        //todo: show backup issues in the app
                        helper.CopyFile(path + fileName, backupPath);
                    } catch (IOException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }

                    //delete any older files for this day:

                    File dir = new File(path);

                    String dateOnlyPrefix = new SimpleDateFormat("yyyyMMdd.").format(new Date());

                    File[] files = dir.listFiles();
                    for (int i = 0 ; i < files.length; i++)
                    {
                        File f = files[i];

                        if (f.getName().startsWith(dateOnlyPrefix )
                                && f.getName().endsWith(backupSuffix)
                                && !f.getName().startsWith(datePrefix))
                        {
                            f.delete();
                        }
                    }
                }

                //todo: make sure only weeklies then monthly backups

    }


}
