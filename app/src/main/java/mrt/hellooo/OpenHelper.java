package mrt.hellooo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by MR.T on 5/11/2015.
 */
public class OpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "hello.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE = "CREATE TABLE Hello_Table (_id integer primary key, "+" images VARCHAR(255), word VARVHAR(255), sound VARCHAR(255), title VARCHAR(255), icon VARCHAR(255));";

   public OpenHelper(Context context) {
       super(context,DATABASE_NAME,null,DATABASE_VERSION);
   }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
