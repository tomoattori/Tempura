package info.tomoattori.tempura.device.camera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import info.tomoattori.tempura.R;

public class GetImageFromCamera extends Activity {
    private final int CAMERA_INTENT = 1;
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd_HHmmss");

    private File mPhotoPath = null;
    private File mPhotoFile = null;
    private File mPhotoFileResized = null;
    private Uri mImageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_image_from_camera);

        // 写真を保存するディレクトリを設定
        File extStorageDir = Environment.getExternalStorageDirectory();
        if (!extStorageDir.canWrite()) {
            Toast.makeText(this, "外部ストレージへの書き込みができません", Toast.LENGTH_SHORT).show();
        } else {
            mPhotoPath = new File(extStorageDir.getPath() + "/" + getPackageName());
            if (!mPhotoPath.exists()) {
                mPhotoPath.mkdirs();
            }
        }

        Button btn = (Button) findViewById(R.id.getImageFromCameraBtn);
        if (mPhotoPath == null) {
            btn.setEnabled(false);
        } else {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // ファイル保存先のURIを生成
                    String nowDate = DATE_FORMAT.format(new Date(System.currentTimeMillis()));
                    mPhotoFile = new File(mPhotoPath.getPath() + "/GetImageFromCamera_" + nowDate + ".jpg");
                    mPhotoFileResized = new File(mPhotoPath.getPath() + "/GetImageFromCamera_" + nowDate + "_resized.jpg");
                    mImageUri = Uri.fromFile(mPhotoFile);

                    // カメラを呼び出す
                    Intent intent = new Intent();
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                    startActivityForResult(intent, CAMERA_INTENT);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_INTENT) {
            if (resultCode == RESULT_OK) {
                // 写真データが大きいと取り扱いに困るのでサイズを確認して必要に応じて縮小する
                try {
                    // まずはファイルサイズを確認する(InputStreamをOpenしてるけどファイルそのものは読み込まない)
                    InputStream in = getApplicationContext().getContentResolver().openInputStream(mImageUri);
                    BitmapFactory.Options imageOpts = new BitmapFactory.Options();
                    imageOpts.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(in, null, imageOpts);
                    in.close();

                    // 画像を縮小するための設定をする
                    final int MAX_WIDTH = 1024;
                    final int MAX_HEIGHT = 768;
                    int imageWidth = imageOpts.outWidth;
                    int imageHeight = imageOpts.outHeight;
                    int inSampleSize = 1;

                    if (imageWidth > MAX_WIDTH || imageHeight > MAX_HEIGHT) {
                        if (imageWidth > imageHeight) {
                            inSampleSize = Math.round((float)imageHeight / (float)MAX_HEIGHT);
                        } else {
                            inSampleSize = Math.round((float)imageWidth / (float)MAX_WIDTH);
                        }
                    }
                    imageOpts.inSampleSize = inSampleSize;

                    // サイズ指定して画像を読み込む
                    imageOpts.inJustDecodeBounds = false;
                    in = getApplicationContext().getContentResolver().openInputStream(mImageUri);
                    Bitmap bitmapImage = BitmapFactory.decodeStream(in, null, imageOpts);
                    in.close();

                    // 取得した画像をImageViewにセットする
                    ImageView imageView = (ImageView) findViewById(R.id.get_image_from_camera_image);
                    imageView.setImageBitmap(bitmapImage);

                    // リサイズ済みの画像を保存する
                    if (inSampleSize != 1) {
                        FileOutputStream out = new FileOutputStream(mPhotoFileResized);
                        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, out);
                        out.close();
                    }

                } catch (Exception e) {
                    // TODO
                }
            }
        }
    }
}
