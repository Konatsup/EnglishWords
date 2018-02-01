package konatsup.englishwords;

public class Word {
    int state;
    int id;
    String en_word;
    String jp_word;

    Word(int i,int st, String ew,String jw){
        this.id = i;
        this.state = st;
        this.en_word = ew;
        this.jp_word = jw;
    }


}
