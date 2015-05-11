package mrt.hellooo;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {



    private SQLiteDatabase deleteSQLite;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private Handler mhHandler = new Handler();
    private Table table;
    private int[] sound = {R.raw.laos, R.raw.brunei, R.raw.cambodia, R.raw.indonesia, R.raw.malysia, R.raw.myanmar, R.raw.philippines, R.raw.singapore, R.raw.thailand, R.raw.vietnam};
    private int[] icon = {R.mipmap.laos, R.mipmap.brunei, R.mipmap.cambodia, R.mipmap.indonesia, R.mipmap.malaysia, R.mipmap.myanmar, R.mipmap.philippines,
            R.mipmap.singapore, R.mipmap.thailand, R.mipmap.vietnam};

    private String[] titles = {"Laos", "Brunei", "Cambodia", "Indonesia", "Malaysia", "Myanmar", "Philippines", "Singapore", "Thailand", "Vietnam"};

    private String[] word = {"Sabaidee", "helo", "Chum reap sour", "Hai!", "Hello", "mingâlaba!", "Hello", "Nǐhǎo！", "Sawat-dee", "Xin chào"};

    private int[] images = {R.mipmap.laos, R.mipmap.brunei, R.mipmap.cambodia, R.mipmap.indonesia, R.mipmap.malaysia, R.mipmap.myanmar, R.mipmap.philippines, R.mipmap.singapore, R.mipmap.thailand, R.mipmap.vietnam};

    String[] ArrayWord,ArrayTitle;

    Integer[] ArrayImages,ArrayIcon, ArraySound;


    int clickCount = 0;


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        DeleteDataInSQLite();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        this.deleteDatabase("hello.db");


        table = new Table(this);





        for (int i = 0; i < word.length; i++) {

            AddDataToSQLite(sound[i],titles[i],word[i],icon[i],images[i]);

        }



        ArrayWord = word().toArray(new String[word().size()]);
        ArrayTitle = title().toArray(new String[title().size()]);
        ArrayIcon = icon().toArray(new Integer[icon().size()]);
        ArrayImages = images().toArray(new Integer[images().size()]);
        ArraySound = sound().toArray(new Integer[sound().size()]);

        Log.d("hello", "Array = " + ArraySound[0]);
        Log.d("hello", "Array = " + ArraySound[1]);
        Log.d("hello", "Array = " + ArraySound[2]);
        Log.d("hello", "Array = " + ArraySound[3]);
        Log.d("hello", "Array = " + ArraySound[4]);




        if (Build.VERSION.SDK_INT > 9) {

            StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(threadPolicy);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerViewAdapter = new RecyclerViewAdapter(this, getData());
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<information> List = getData();

//        for (information item : List) {
//
//            Log.d("item", "item = " + item);
//
//
//        }
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                PlaySound(getApplication(), position);


            }
        }));


    }

    public void ShowImage(int Postion) {


        String currentWord = ArrayWord[Postion];


        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("'" + currentWord + "'");



        int currentImage = ArrayImages[Postion];
        ImageView imageView = (ImageView) findViewById(R.id.img_country);

        imageView.setImageResource(currentImage);


        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        linearLayout.setBackground(getResources().getDrawable(R.mipmap.border));


    }

    public void PlaySound(Context context, final int Position) {


        int currentSound = ArraySound[Position];
        MediaPlayer mp = MediaPlayer.create(context, currentSound);

        // lerm u ni

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                mp.release();

                clickCount = 0;
            }
        });

        // pid u ni

        if (clickCount == 0) {


            mp.start();


            ShowImage(Position);


        }


        if (mp.isPlaying()) {
            clickCount = 1;


        }

    }

    private void AddDataToSQLite(int sound, String titles, String word, int icon, int images) {


        table.addDatatoSQlite(images, word, sound, icon, titles);


    }


    public List<information> getData() {

        List<information> data = new ArrayList<>();


        for (int i = 0; i < ArrayTitle.length && i < icon.length; i++) {

            information current = new information();
            current.title = ArrayTitle[i];
            current.iconID = ArrayIcon[i];
            current.sound = ArraySound[i];

            data.add(current);


        }

        return data;

    }


    private List<String> word() {

        List<String> word = new ArrayList<>();

        String query = "SELECT * FROM Hello_Table";


        OpenHelper openHelper = new OpenHelper(this);
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Log.d("hello", String.valueOf(cursor.getCount()));

        if (cursor.moveToFirst()) {
            do {
                word.add(cursor.getString(cursor.getColumnIndex("word")));

            } while (cursor.moveToNext());


        }

        db.close();

        return word;

    }


    private List<String> title() {

        List<String> title = new ArrayList<>();

        String query = "SELECT * FROM Hello_Table";


        OpenHelper openHelper = new OpenHelper(this);
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Log.d("hello", String.valueOf(cursor.getCount()));

        if (cursor.moveToFirst()) {
            do {
                title.add(cursor.getString(cursor.getColumnIndex("title")));

            } while (cursor.moveToNext());


        }

        db.close();

        return title;

    }




    private List<Integer> images() {

        List<Integer> images = new ArrayList<>();

        String query = "SELECT * FROM Hello_Table";


        OpenHelper openHelper = new OpenHelper(this);
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Log.d("hello", String.valueOf(cursor.getCount()));

        if (cursor.moveToFirst()) {
            do {
                images.add(cursor.getInt(cursor.getColumnIndex("images")));

            } while (cursor.moveToNext());


        }

        db.close();

        return images;

    }

    private List<Integer> sound() {

        List<Integer> sound = new ArrayList<>();

        String query = "SELECT * FROM Hello_Table";


        OpenHelper openHelper = new OpenHelper(this);
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Log.d("hello", String.valueOf(cursor.getCount()));

        if (cursor.moveToFirst()) {
            do {
                sound.add(cursor.getInt(cursor.getColumnIndex("sound")));

            } while (cursor.moveToNext());


        }

        db.close();

        return sound;

    }


    private List<Integer> icon() {

        List<Integer> icon = new ArrayList<>();

        String query = "SELECT * FROM Hello_Table";


        OpenHelper openHelper = new OpenHelper(this);
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Log.d("hello", String.valueOf(cursor.getCount()));

        if (cursor.moveToFirst()) {
            do {
                icon.add(cursor.getInt(cursor.getColumnIndex("icon")));

            } while (cursor.moveToNext());


        }

        db.close();

        return icon;

    }

    private void DeleteDataInSQLite() {

        deleteSQLite = openOrCreateDatabase("hello.db", MODE_PRIVATE, null);

        Cursor objCursor = deleteSQLite.rawQuery("SELECT * FROM Hello_Table", null);

        int intCursor = objCursor.getCount();

        for (int i = 1; i <= intCursor; i++) {

            deleteSQLite.delete("Hello_Table", "_id" + "=" + i, null);

        }

    }   //  end of DeleteDataInSQLite


}
