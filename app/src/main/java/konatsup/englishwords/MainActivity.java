package konatsup.englishwords;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainActivity extends Activity {

    private Realm realm;
    ListView listView;
//    ArrayAdapter adapter; //後々CustomAdapterにする必要あり

    List<Word> words;
    WordAdapter wordAdapter;
    EditText enEditText;
    EditText jpEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        sendNotification();

        listView = (ListView)findViewById(R.id.listView);
        words = new ArrayList<Word>();

//        words.add(new Word(1,0,"strawberry","いちご","2018-01-01-12-00-00"));
//        adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1);

        enEditText = (EditText)findViewById(R.id.enEditText);
        jpEditText = (EditText)findViewById(R.id.jpEditText);


        realm = Realm.getDefaultInstance();

        RealmResults<Word> result = realm.where(Word.class).findAll();

        wordAdapter = new WordAdapter(result);
        listView.setAdapter(wordAdapter);

//        realm.beginTransaction();
//        realm.copyToRealm(word);
////        realm.copyToRealmOrUpdate(word);
//        realm.commitTransaction();

        sendNotification();


    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    public void sendNotification(){
        Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        Notification notification = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("title")
                .setContentText("content text")
                .setTicker("ticker text")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.square))
                .setContentIntent(pendingIntent)
                .build();

        manager.notify(0, notification);
    }

    public void add(View v){
        String enText,jpText;
        enText = enEditText.getText().toString();
        jpText = jpEditText.getText().toString();
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
        String dateText  = android.text.format.DateFormat.format("yyyy-MM-dd-kk-mm-ss", Calendar.getInstance()).toString();
//        wordAdapter.add(new Word(1,0,enText,jpText,dateText));

        Word word = new Word();
        word.setId(1);
        word.setLevel(0);
        word.setEn_word(enText);
        word.setJp_word(jpText);
        word.setCreatedAt(dateText);
        realm.beginTransaction();
        realm.copyToRealm(word);
        realm.commitTransaction();

        enEditText.setText("");
        jpEditText.setText("");
    }

}
