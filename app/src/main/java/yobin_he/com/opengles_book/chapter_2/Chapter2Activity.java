package yobin_he.com.opengles_book.chapter_2;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class Chapter2Activity extends AppCompatActivity {
    public static final String TAG = "Chapter2Activity";
    private final int CONTEXT_CLIENT_VERSION = 3 ; //openGl版本
    private GLSurfaceView mGLSurfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGLSurfaceView = new GLSurfaceView(this);
        if(detectOpenGLES30()){
            mGLSurfaceView.setEGLContextClientVersion(CONTEXT_CLIENT_VERSION);
            mGLSurfaceView.setRenderer(new HelloTriangleRender(this));

        }else {
            Log.e(TAG,"OpenGL ES 3.0 not supported on device.Exiting...");
        }

        setContentView(mGLSurfaceView); //添加view
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Ideally a game should implement onResume() and onPause()
        // to take appropriate action when the activity looses focus
        mGLSurfaceView.onPause();
    }

    /**
     * 检测版本opengles版本是否大与3.0
     * @return
     */
    private boolean detectOpenGLES30(){
         ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return (info.reqGlEsVersion >= 0x30000);
    }
}
