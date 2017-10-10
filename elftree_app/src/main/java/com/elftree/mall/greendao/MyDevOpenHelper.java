package com.elftree.mall.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by zouhongzhi on 2017/9/20.
 */

public class MyDevOpenHelper extends DaoMaster.DevOpenHelper {
    public MyDevOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MyDevOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }
}
