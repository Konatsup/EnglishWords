package konatsup.englishwords;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import io.realm.Realm;
import io.realm.RealmResults;
/*
createdAtに対して
レベル0:30分後に通知(60%)
レベル1:90分後に通知(40%)
レベル2:1日後に通知(25%)
レベル3:1週間後に通知(22%)
レベル4:1ヶ月後に通知(20%)
レベル5:もう覚えたはず

*/
public class QuizActivity extends Activity {

    private static final int QUESTION_COUNT = 5; //使わないかも

//    String[] questions_en = {"strawberry","apple","orange"};
//    String[] questions_jp = {"いちご","りんご","みかん"};

    TextView textView;
    EditText answerEditText;
    int answerCount; //カウンター
    int questionLevel; //対象となるクイズのレベル
    int questionMaxNum;
    Word[] words = {};
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        textView = (TextView)findViewById(R.id.textView);
        answerEditText = (EditText)findViewById(R.id.editText);

        questionLevel = 0; //とりあえず0
        realm = Realm.getDefaultInstance();

        RealmResults<Word> result = realm.where(Word.class).equalTo("level",questionLevel).findAll();
        questionMaxNum = result.size();

        //
        if(questionMaxNum == 0){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        questionMaxNum = questionMaxNum < QUESTION_COUNT ? questionMaxNum : QUESTION_COUNT;

        words = result.toArray(new Word[questionMaxNum]);

        answerCount = 0;

        //問題表示
        textView.setText(words[answerCount].getEn_word());
        answerEditText.setText("");
    }


    public void confirm(View v){

        String answer = answerEditText.getText().toString();

        //レベルを1アップする
        realm.beginTransaction();
        Word word = realm.copyToRealm(words[answerCount]);
        word.setLevel(word.getLevel()+1); //レベルアップ
        realm.commitTransaction();

        if(answer.equals(words[answerCount].getJp_word())) {
            Toast.makeText(this, "正解", Toast.LENGTH_SHORT).show();
            answerCount++;

            if(answerCount==questionMaxNum){
                //終了後の画面への遷移
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }else {
                //次の問題へ
                textView.setText(words[answerCount].getEn_word());
                answerEditText.setText("");
            }

        }else {
            Toast.makeText(this, "不正解", Toast.LENGTH_SHORT).show();
        }


    }

    //わからないボタン
    public void noIdea(View v){

        //レベルを0に変更
        realm.beginTransaction();
        Word word = realm.copyToRealm(words[answerCount]);
        word.setLevel(0);
        realm.commitTransaction();

        answerCount++;

        if(answerCount==questionMaxNum){
            //終了後の画面への遷移
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else {
            //次の問題へ
            textView.setText(words[answerCount].getEn_word());
            answerEditText.setText("");
        }
    }

}
