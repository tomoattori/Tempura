package info.tomoattori.tempura;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import info.tomoattori.tempura.view.ListView.ActivityListView;
import info.tomoattori.tempura.view.ListView.BasicListView;
import info.tomoattori.tempura.view.ListView.SimpleListView;
import info.tomoattori.tempura.view.ListView.ToStringListView;
import info.tomoattori.tempura.view.SurfaceView.SurfaceViewExtra;
import info.tomoattori.tempura.view.SurfaceView.SurfaceViewLayout;
import info.tomoattori.tempura.view.SurfaceView.SurfaceViewOnly;


public class Top extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top);

        findViewById(R.id.button_top_surfaceview_only).setOnClickListener(this);
        findViewById(R.id.button_top_surfaceview_layout).setOnClickListener(this);
        findViewById(R.id.button_top_surfaceview_extra).setOnClickListener(this);
        findViewById(R.id.button_top_simple_listview).setOnClickListener(this);
        findViewById(R.id.button_top_basic_listview).setOnClickListener(this);
        findViewById(R.id.button_top_tostring_listview).setOnClickListener(this);
        findViewById(R.id.button_top_listactivity).setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.button_top_surfaceview_only:
                intent = new Intent(this, SurfaceViewOnly.class);
                startActivity(intent);
                break;
            case R.id.button_top_surfaceview_layout:
                intent = new Intent(this, SurfaceViewLayout.class);
                startActivity(intent);
                break;
            case R.id.button_top_surfaceview_extra:
                intent = new Intent(this, SurfaceViewExtra.class);
                startActivity(intent);
                break;
            case R.id.button_top_simple_listview:
                intent = new Intent(this, SimpleListView.class);
                startActivity(intent);
                break;
            case R.id.button_top_basic_listview:
                intent = new Intent(this, BasicListView.class);
                startActivity(intent);
                break;
            case R.id.button_top_tostring_listview:
                intent = new Intent(this, ToStringListView.class);
                startActivity(intent);
                break;
            case R.id.button_top_listactivity:
                intent = new Intent(this, ActivityListView.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
