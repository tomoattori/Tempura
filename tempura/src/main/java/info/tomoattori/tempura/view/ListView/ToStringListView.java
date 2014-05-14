package info.tomoattori.tempura.view.ListView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import info.tomoattori.tempura.R;

public class ToStringListView extends Activity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tostring_list_view);

        // ListViewに表示するデータを定義しておく
        List<ListViewData> listViewData = new ArrayList<ListViewData>();

        // データは単純なStringではなく独自クラスで保持する
        listViewData.add(new ListViewData("1.5", "Cupcake"));
        listViewData.add(new ListViewData("1.6", "Donut"));
        listViewData.add(new ListViewData("2.0/2.1", "Eclair"));
        listViewData.add(new ListViewData("2.2", "Froyo"));
        listViewData.add(new ListViewData("2.3", "Gingerbread"));
        listViewData.add(new ListViewData("3.x", "Honeycomb"));
        listViewData.add(new ListViewData("4.0", "Ice Cream Sandwich"));
        listViewData.add(new ListViewData("4.1/4.2/4.3", "Jerry Bean"));
        listViewData.add(new ListViewData("4.4", "KitKat"));

        // Layoutで定義されたListViewを確保する
        ListView listView = (ListView) findViewById(R.id.toStringListViewBody);

        // アイテム選択時のリスナーをActivity自身にセットする
        listView.setOnItemClickListener(this);

        // ListViewにデータを渡すためのアダプタを準備する
        // (データが独自クラスだけどカスタムアダプタを準備しなくてもOKなのがミソ)
        ArrayAdapter<ListViewData> adapter = new ArrayAdapter<ListViewData>(this, android.R.layout.simple_list_item_1, listViewData);

        // ListViewにアダプタをセットする
        listView.setAdapter(adapter);

    }

    // ListViewのOnItemClickListenerを定義する
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        // 対象となるListViewそのものを確保する
        ListView selectedView = (ListView) adapterView;

        // ListViewからクリックされたアイテムを取得する
        // (データ保持用のクラスが取得される)
        ListViewData item = (ListViewData) selectedView.getItemAtPosition(position);

        // とりあえずToastで表示しておく
        Toast.makeText(ToStringListView.this, "クリックされました : " + item.getCodeName(), Toast.LENGTH_SHORT).show();
    }

    // データ保持用のクラス
    private class ListViewData {
        String verNo;
        String codeName;

        public ListViewData(String vno, String code) {
            this.verNo = vno;
            this.codeName = code;
        }

//        public String getVerNo() {
//            return this.verNo;
//        }

        public String getCodeName() {
            return this.codeName;
        }

        // ToStringメソッドをOverrideしてあげるとListViewにその内容を出力してくれる
        // (ArrayAdapterは渡されたObjectのtoStringの結果をリストに表示する)
        @Override
        public String toString() {
            return this.verNo + " : " + this.codeName;
        }
    }
}
