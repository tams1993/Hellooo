package mrt.hellooo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by MR.T on 5/11/2015.
 */
public class Table {


    private OpenHelper openHelper;
    private SQLiteDatabase writeSQLite,readSQLite;

    public static final String HELLO_TABLE = "Hello_Table";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_IMAGES = "images";
    public static final String COLUMN_WORD = "word";
    public static final String COLUMN_SOUND = "sound";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_ICON = "icon";



    public Table(Context context) {

        openHelper = new OpenHelper(context);
        writeSQLite = openHelper.getReadableDatabase();
        readSQLite = openHelper.getWritableDatabase();


    }

    public long addDatatoSQlite(int images, String word, int sound,int icon,String title) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_IMAGES, images);
        contentValues.put(COLUMN_WORD, word);
        contentValues.put(COLUMN_SOUND, sound);
        contentValues.put(COLUMN_TITLE, title);
        contentValues.put(COLUMN_ICON, icon);

        return writeSQLite.insert(HELLO_TABLE, null, contentValues);

    }


}
