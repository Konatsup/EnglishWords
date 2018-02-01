package konatsup.englishwords;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {
    private static final int QUESTION_COUNT = 3;
    String[] questions_en = {"strawberry","apple","orange"};
    String[] questions_jp={"いちご","りんご","みかん"};
    TextView textView;
    EditText answerEditText;
    int answerCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        textView = (TextView)findViewById(R.id.textView);
        answerEditText = (EditText)findViewById(R.id.editText);
        answerCount = 0;

        //問題表示
        textView.setText(questions_en[answerCount] + "");
        answerEditText.setText("");
    }

    public void confirm(View v){
        String answer = answerEditText.getText().toString();

        if(answer.equals(questions_jp[answerCount])) {
            Toast.makeText(this, "正解", Toast.LENGTH_SHORT).show();
            answerCount++;

            if(answerCount==QUESTION_COUNT){
                //終了後の画面への遷移
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }else {
                //次の問題へ
                textView.setText(questions_en[answerCount] + "");
                answerEditText.setText("");
            }

        }else {
            Toast.makeText(this, "不正解", Toast.LENGTH_SHORT).show();
        }


    }

    public void noIdea(View v){

        //リストの追加など

        answerCount++;

        if(answerCount==QUESTION_COUNT){
            //終了後の画面への遷移
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else {
            //次の問題へ
            textView.setText(questions_en[answerCount] + "");
            answerEditText.setText("");
        }
    }
}
