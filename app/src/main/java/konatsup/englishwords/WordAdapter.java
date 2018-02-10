package konatsup.englishwords;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

public class WordAdapter extends RealmBaseAdapter<Word> implements ListAdapter{


    public WordAdapter(OrderedRealmCollection<Word> realmResults){
        super(realmResults);
    }

    public static class ViewHolder{
        TextView enTextView;
        TextView jpTextView;

        public ViewHolder(View view){
            enTextView = (TextView) view.findViewById(R.id.enTextView);
            jpTextView = (TextView) view.findViewById(R.id.jpTextView);
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;

        // Viewを再利用している場合は新たにViewを作らない
        if (convertView == null) {
            convertView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.word, parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(adapterData != null){
        final Word item = adapterData.get(position);

            viewHolder.enTextView.setText(item.en_word);
            viewHolder.jpTextView.setText(item.jp_word);
            viewHolder.enTextView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(viewHolder.enTextView.getText().toString()==""){
                        viewHolder.enTextView.setText(item.en_word);
                    }else {
                        viewHolder.enTextView.setText("");
                    }
                }
            });
            viewHolder.jpTextView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(viewHolder.jpTextView.getText().toString()==""){
                        viewHolder.jpTextView.setText(item.jp_word);
                    }else {
                        viewHolder.jpTextView.setText("");
                    }
                }
            });
        }
        return convertView;
    }

}