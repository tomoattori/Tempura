package info.tomoattori.tempura.view.ListView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import info.tomoattori.tempura.R;

public class SimpleListView extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_list_view);

        // ListViewに表示するデータを定義しておく
        String[] listViewData = { "Cupcake", "Donut", "Eclair", "Froyo", "Gingerbread",
                                   "Honeycomb", "Ice Cream Sandwitch", "Jerry Bean", "KitKat"};

        // Layoutで定義されたListViewを確保する
        ListView listView = (ListView) findViewById(R.id.simpleListViewBody);

        // ListViewにデータを渡すためのアダプタを準備する
        // (android.R.layout.simple_list_item_1は個々のアイテムの表示レイアウト。これが定番)
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listViewData);

        // ListViewにアダプタをセットする
        listView.setAdapter(adapter);

        // ListViewのアイテムがクリックされた時に呼び出されるコールバックリスナを登録する
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            // positionとidは同じ値が返ってくる(ArrayAdapterの実装がそうなってるので)

                // 対象となるListViewそのものを確保する
                ListView selectedView = (ListView) adapterView;

                // ListViewからクリックされたアイテムを取得する
                String item = (String) selectedView.getItemAtPosition(position);

                // とりあえずToastで表示しておく
                Toast.makeText(SimpleListView.this, "クリックされました : " + item, Toast.LENGTH_SHORT).show();
            }
        });

        // ListViewのアイテムが長押しされた時に呼び出されるコールバックリスナを登録する
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
            // すべき処理はonItemClickの時とほとんど一緒

                // 対象となるListViewそのものを確保する
                ListView selectedView = (ListView) adapterView;

                // ListViewから長押しされたアイテムを取得する
                String item = (String) selectedView.getItemAtPosition(position);

                // とりあえずToastで表示しておく
                Toast.makeText(SimpleListView.this, "長押しされました : " + item, Toast.LENGTH_SHORT).show();

                // ここでイベントを消化し切るか否かをreturnで返却する
                return true;        // 長押し終了後にonItemClickを発生させない
                // return false;    // 長押し終了後にonItemClickを発生させる
            }
        });

        // ListViewのアイテムが選択された時に呼び出されるコールバックリスナを登録する
        // (トラックボール等がないと選択状態にならないので今となっては必要ないかも)
        // (ListView#setSelection()してもこのコールバックは呼ばれなかった)
        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            // アイテムが選択された時に呼ばれる

                // 対象となるListViewそのものを確保する
                ListView selectedView = (ListView) adapterView;

                // ListViewから選択されたアイテムを取得する
                // (クリック時と違いpositionを意識する必要がない)
                String item = (String) selectedView.getSelectedItem();

                // とりあえずToastで表示しておく
                Toast.makeText(SimpleListView.this, "選択中 : " + item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            // 選択状態のアイテムがなくなった時に呼ばれる

                // とりあえずToastで表示しておく
                Toast.makeText(SimpleListView.this, "何も選択されていません", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
