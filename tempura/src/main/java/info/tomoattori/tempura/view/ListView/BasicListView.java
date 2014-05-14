package info.tomoattori.tempura.view.ListView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import info.tomoattori.tempura.R;

public class BasicListView extends Activity
        // ListViewのOnItemClickListener、OnItemLongClickListenerはActivity側に実装する
        // (もちろんSimpleの時と同様にListViewの無名クラスとして定義してもOK)
        implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_list_view);

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
        ListView listView = (ListView) findViewById(R.id.basicListViewBody);

        // アイテム選択時／長押し時のリスナーをActivity自身にセットする
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

        // ListViewにデータを渡すためのアダプタを準備する
        // (今回はデータが独自クラスなので専用のカスタムアダプタを準備)
        CustomAdapter adapter = new CustomAdapter(this, listViewData);

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
        Toast.makeText(BasicListView.this, "クリックされました : " + item.getCodeName(), Toast.LENGTH_SHORT).show();
    }

    // ListViewのOnItemLongClickListenerを定義する
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
        // 対象となるListViewそのものを確保する
        ListView selectedView = (ListView) adapterView;

        // ListViewから長押しされたアイテムを取得する
        String item = (String) selectedView.getItemAtPosition(position);

        // とりあえずToastで表示しておく
        Toast.makeText(BasicListView.this, "長押しされました : " + item, Toast.LENGTH_SHORT).show();

        // ここでイベントを消化し切るか否かをreturnで返却する
        return true;        // 長押し終了後にonItemClickを発生させない
        // return false;    // 長押し終了後にonItemClickを発生させる
    }

    // カスタムアダプタ
    private class CustomAdapter extends ArrayAdapter<ListViewData> {

        // コンストラクタ
        public CustomAdapter(Context context, List<ListViewData> objects) {
            super(context, R.layout.basic_list_view_row, objects);
        }

        // getViewで取得したViewを格納するためのクラス(いわゆるViewHolder)
        // 単なるクラスなのでViewHolderという名称は必須ではない
        private class ViewHolder {
            TextView versionNo;
            TextView codeName;
        }

        // ListViewの行を生成するための処理を記述(行が表示される時に呼び出される)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // ViewHolderを準備しておく
            ViewHolder holder;

            // 行のViewが存在しない場合
            if (convertView == null) {
                // 行のViewそのものをinflateする
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.basic_list_view_row, null);

                // 行内部のViewをfindViewByIdで取得し、ViewHolderにセットする
                holder = new ViewHolder();
                holder.versionNo = (TextView) convertView.findViewById(R.id.basicListViewRowNumber);
                holder.codeName = (TextView) convertView.findViewById(R.id.basicListViewRowCodename);

                // ViewHolderを行のViewオブジェクトのtagとしてセットする
                // (あとで再利用するため)
                convertView.setTag(holder);

            // 行のViewが存在する場合
            } else {
                // 処理的に高コストなViewのinflate/findViewByIdを行わず、前回取得したものを再利用する
                holder = (ViewHolder) convertView.getTag();
            }

            // アダプタからデータを取得
            ListViewData item = getItem(position);

            // Viewに対して必要なデータを設定
            holder.versionNo.setText(item.getVerNo());
            holder.codeName.setText(item.getCodeName());

            // Viewを返却する
            return convertView;
        }
    }

    // データ保持用のクラス
    // (ListViewそのものの実装とは無関係なので適当)
    private class ListViewData {
        String verNo;
        String codeName;

        public ListViewData(String vno, String code) {
            this.verNo = vno;
            this.codeName = code;
        }

        public String getVerNo() {
            return this.verNo;
        }

        public String getCodeName() {
            return this.codeName;
        }
    }
}
