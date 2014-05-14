package info.tomoattori.tempura.view.ListView;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import info.tomoattori.tempura.R;

public class ActivityListView extends ListActivity {
    // ListViewに表示するデータを定義しておく
    final String[] DATA_EXIST = { "Cupcake", "Donut", "Eclair", "Froyo", "Gingerbread",
            "Honeycomb", "Ice Cream Sandwich", "Jerry Bean", "KitKat"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        // Layoutで定義されたListViewを確保する
        // (ListActivityの場合findViewByIdする必要はなく、getListViewで
        //  idが "android:id/list" であるListViewが取得できる)
        ListView listView = getListView();

        // ListViewにデータを渡すためのアダプタを準備する
        // (コンストラクタでデータを渡してしまうとあとで削除(clear)できなくなってしまうため、
        //  コンストラクタではデータを渡さずにあとからaddAllで追加している)
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        adapter.addAll(DATA_EXIST);

        // ListViewにアダプタをセットする
        // (ListActivityの場合アダプタをセットするListViewを指定する必要がない)
        setListAdapter(adapter);

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
                Toast.makeText(ActivityListView.this, "クリックされました : " + item, Toast.LENGTH_SHORT).show();
            }
        });

        // データの有無を入れ替える
        ToggleButton btn = (ToggleButton) findViewById(R.id.listActivityToggleButton);
        btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // 定義済みのデータを全て追加する
                    adapter.addAll(DATA_EXIST);
                } else {
                    // アダプタが保持しているデータを全て削除する
                    adapter.clear();
                }

                // データ操作の結果を反映する
                adapter.notifyDataSetChanged();
            }
        });
    }

}
