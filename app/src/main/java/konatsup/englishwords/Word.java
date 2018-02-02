package konatsup.englishwords;


//import io.realm.RealmObject;

import io.realm.RealmObject;


//public class Word extends RealmObject{
public class Word{
//    @PrimaryKey
    int id;
    int level; //復習レベル
    String en_word; //英単語
    String jp_word; //日本語約
    String createdAt; //

    Word(int id,int l, String ew, String jw, String c){
        this.id = id;
        this.level = l;
        this.en_word = ew;
        this.jp_word = jw;
        this.createdAt = c;
    }


    public int getId(){return id;}
    public void setId(int id) { this.id = id; }

    public int getLevel(){return level;}
    public void setLevel(int level) { this.level = level ;}

    public String getEn_word(){return en_word;}
    public void setEn_word(String en_word) { this.en_word = en_word; }


    public String getJp_word() { return jp_word; }
    public void setJp_word(String jp_word) { this.jp_word = jp_word; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }


}
