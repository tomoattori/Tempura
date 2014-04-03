package info.tomoattori.tempura.view.SurfaceView;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import info.tomoattori.tempura.R;

public class SurfaceViewExtra extends Activity implements View.OnTouchListener {
    private MySurfaceViewX mMySurfaceViewX;

    // 取り扱うSurfaceView
    private SurfaceView mSurfaceView;

    // 取り扱うSurfaceViewのサイズ
    private int mWidth;
    private int mHeight;

    // Touchイベント発生時の座標
    private float mTouchStartX = 0;
    private float mTouchStartY = 0;

    // Touchイベントで移動中の座標
    private float mTouchMoveX = 0;
    private float mTouchMoveY = 0;

    // 現在の座標
    private float mOffsetX = 0;
    private float mOffsetY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Layoutの設定
        setContentView(R.layout.surface_view_extra);

        // Layoutで定義されたSurfaceViewを独自クラスにセットする
        mSurfaceView = (SurfaceView) findViewById(R.id.svx_surfaceview);
        mMySurfaceViewX = new MySurfaceViewX(mSurfaceView);

        // onTouchEventで処理をさせたくないViewにはonTouchListenerにActivityを設定してしまう
        // (ActivityのonTouchでtrueを返すことでonTouchEventを実行しなくなる)
        findViewById(R.id.svx_textview).setOnTouchListener(this);
    }

    // ViewのサイズはonCreate時には未確定なのでサイズ取得はここで行う
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        mWidth = mSurfaceView.getWidth();
        mHeight = mSurfaceView.getHeight();
    }

    // ActivityのonTouch
    // (setOnTouchListener(this)しているViewで使用)
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        // 何もせずにイベントを消費する
        return true;
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
                float diffX = mOffsetX + (posX - mTouchStartX);
                if(diffX < 0) {
                    mTouchStartX += diffX;
                } else if(diffX > mWidth - mMySurfaceViewX.RECT_WIDTH) {
                    mTouchStartX += diffX - (mWidth - mMySurfaceViewX.RECT_WIDTH);
                }
                mTouchMoveX = posX - mTouchStartX;

                float diffY = mOffsetY + (posY - mTouchStartY);
                if(diffY < 0) {
                    mTouchStartY += diffY;
                } else if(diffY > mHeight - mMySurfaceViewX.RECT_HEIGHT) {
                    mTouchStartY += diffY - (mHeight - mMySurfaceViewX.RECT_HEIGHT);
                }
                mTouchMoveY = posY - mTouchStartY;

                break;

            // タッチ終了時
            case MotionEvent.ACTION_UP:
                // 移動量を確定して新たな座標を設定する
                mOffsetX += (posX - mTouchStartX);
                if(mOffsetX < 0) {
                    mOffsetX = 0;
                } else if(mOffsetX + mMySurfaceViewX.RECT_WIDTH > mWidth) {
                    mOffsetX = mWidth - mMySurfaceViewX.RECT_WIDTH;
                }

                mOffsetY += (posY - mTouchStartY);
                if(mOffsetY < 0) {
                    mOffsetY = 0;
                } else if(mOffsetY + mMySurfaceViewX.RECT_HEIGHT > mHeight) {
                    mOffsetY = mHeight - mMySurfaceViewX.RECT_HEIGHT;
                }

                // タッチ中の移動量を初期化する
                mTouchMoveX = 0;
                mTouchMoveY = 0;

                break;

            default:
                return true;
        }

        // 移動結果を画面に反映させる
        mMySurfaceViewX.doDraw(mOffsetX + mTouchMoveX, mOffsetY + mTouchMoveY);

        // Touchイベントはここで使い切る
        return true;
    }

}

// SurfaceViewを管理するクラス
class MySurfaceViewX implements SurfaceHolder.Callback {
    // 取り扱うSurfaceViewのSurfaceHolder
    private SurfaceHolder mSurfaceHolder;

    // 描画する四角の縦横幅
    public final float RECT_WIDTH = 50;
    public final float RECT_HEIGHT = 50;

    // コンストラクタ
    public MySurfaceViewX(SurfaceView sv) {
        // SurfaceViewへのフォーカスを可能に設定
        sv.setFocusable(true);

        // SurfaceHolderの保存とコールバック設定
        mSurfaceHolder = sv.getHolder();
        mSurfaceHolder.addCallback(this);
    }

    // SurfaceViewが作成された時の処理
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // Canvasの描画
        doDraw(0,0);
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

    // 画面描画処理
    public void doDraw(float offsetX, float offsetY) {
        // Paintオブジェクトの作成
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);

        // Canvasのロックと確保
        Canvas canvas = mSurfaceHolder.lockCanvas();

        // 指定位置に四角を描画する(背景は黒)
        canvas.drawColor(Color.BLACK);
        canvas.drawRect(offsetX, offsetY,
                        offsetX + RECT_WIDTH, offsetY + RECT_HEIGHT,
                        paint);

        // Canvasへの反映とロック解除
        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }
}