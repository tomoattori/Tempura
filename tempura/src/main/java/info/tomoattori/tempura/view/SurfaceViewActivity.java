package info.tomoattori.tempura.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfaceViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(new MySurfaceView(this));
    }

}

// SurfaceViewクラス
class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    // 描画する四角の縦横幅
    private final float RECT_WIDTH = 50;
    private final float RECT_HEIGHT = 50;

    // Touchイベント発生時の座標
    private float mTouchStartX = 0;
    private float mTouchStartY = 0;

    // Touchイベントで移動中の座標
    private float mTouchMoveX = 0;
    private float mTouchMoveY = 0;

    // 現在の座標
    private float mOffsetX = 0;
    private float mOffsetY = 0;

    // コンストラクタ
    public MySurfaceView(Context context) {
        super(context);

        setFocusable(true);
        getHolder().addCallback(this);
    }

    // SurfaceViewが作成された時の処理
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // Canvasの描画
        doDraw(surfaceHolder);
    }

    // SurfaceViewが変化した時の処理(サイズ変更など)
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        // 今回は特に何もしない
    }

    // SurfaceViewが破棄された時の処理
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        // 今回は特に何もしない
    }

    // Touchイベントの処理
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // イベントから現在の座標を取得
        float posX = event.getX();
        float posY = event.getY();

        switch (event.getAction()) {
            // タッチ開始時
            case MotionEvent.ACTION_DOWN:
                // タッチ開始時の座標を保持しておく
                mTouchStartX = posX;
                mTouchStartY = posY;
                break;
            // タッチ移動時
            case MotionEvent.ACTION_MOVE:
                // タッチ開始時からの移動量を取得する
                // (移動を即時反映させるため)
                mTouchMoveX = posX - mTouchStartX;
                mTouchMoveY = posY - mTouchStartY;
                break;
            // タッチ終了時
            case MotionEvent.ACTION_UP:
                // 移動量を確定して新たな座標を設定する
                mOffsetX += (posX - mTouchStartX);
                mOffsetY += (posY - mTouchStartY);
                // タッチ中の移動量を初期化する
                mTouchMoveX = 0;
                mTouchMoveY = 0;
                break;
            default:
                return true;
        }

        // 移動結果を画面に反映させる
        doDraw(getHolder());

        // Touchイベントはここで使い切る
        return true;
    }

    // 画面描画処理
    private void doDraw(SurfaceHolder holder) {
        // Paintオブジェクトの作成
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);

        // Canvasのロックと確保
        Canvas canvas = holder.lockCanvas();

        // 指定位置に四角を描画する(背景は黒)
        canvas.drawColor(Color.BLACK);
        canvas.drawRect(mOffsetX + mTouchMoveX, mOffsetY + mTouchMoveY,
                        mOffsetX + mTouchMoveX + RECT_WIDTH, mOffsetY + mTouchMoveY + RECT_HEIGHT,
                        paint);

        // Canvasへの反映とロック解除
        holder.unlockCanvasAndPost(canvas);
    }
}