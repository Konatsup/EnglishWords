package konatsup.englishwords;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import java.text.DateFormat;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends Activity {

    private Realm realm;
    ListView listView;
    WordAdapter wordAdapter;
    EditText enEditText;
    EditText jpEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView);
        enEditText = (EditText)findViewById(R.id.enEditText);
        jpEditText = (EditText)findViewById(R.id.jpEditText);

        realm = Realm.getDefaultInstance();
        RealmResults<Word> result = realm.where(Word.class).findAll();


        wordAdapter = new WordAdapter(result);
        listView.setAdapter(wordAdapter);

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
                .setContentTitle("英単語アプリ")
                .setContentText("クイズを解こう!")
                .setTicker("クイズリマインド")
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
        String createdAtText  = android.text.format.DateFormat.format("yyyy-MM-dd-kk-mm-ss", Calendar.getInstance()).toString();

        //データの新規登録
        realm.beginTransaction();
        Word word = realm.createObject(Word.class); // 新たなオブジェクトを作成
        word.setId(1);
        word.setLevel(0); //新規登録時はレベル0
        word.setEn_word(enText);
        word.setJp_word(jpText);
        word.setCreatedAt(createdAtText);
        realm.commitTransaction();

        enEditText.setText("");
        jpEditText.setText("");
    }

    public void allDelete(){
        final RealmResults<Word> result = realm.where(Word.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // すべてのオブジェクトを削除
                result.deleteAllFromRealm();
            }
        });
    }

}
