package yobin_he.com.opengles_book.chapter_2;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author : yobin_he
 * @package: yobin_he.com.opengles_book.chapter_2
 * @fileName: HelloTriangleRender
 * @Date : 2019/1/17  17:12
 * @describe : TODO
 * @org scimall
 * @email he.yibin@scimall.org.cn
 */

public class HelloTriangleRender implements GLSurfaceView.Renderer {
    public static final String TAG = "HelloTriangleRender";
    private int mProgramObject;
    private int mWidth;
    private int mHeight;

    private FloatBuffer mVertices;
    private final float[] mVerticesData = { //顶点坐标
            0.0f,0.5f,0.0f,  //上坐标
            -0.5f,-0.5f,0.0f, //左下坐标
            0.5f,-0.5f,0.0f //右下坐标
    };


    private static String vShaderStr =
            "#version 300 es              \n"
            +"in vec4 vPosition;            \n"
            +"void main(){                  \n"
                    + "gl_Position = vPosition;      \n"
                    + "}";


    private static String fShaderStr =
            "#version 300 es       \n"
             +"precision mediump float   \n"
            +"out vec4 fragColor;    \n"
            +"void main(){         \n"
                    +  "fragColor = vec4(1.0,1.0,0.0,1.0);   \n"
                 +   "}"
            ;



    /**
     * 构造器初始化
     * @param context
     */
    public HelloTriangleRender(Context context){

        ByteBuffer bb = ByteBuffer.allocateDirect(mVerticesData.length * 4); // 分配缓存,没个点分配四个字节
        bb.order(ByteOrder.nativeOrder());
        mVertices = bb.asFloatBuffer();  //将分配的内存浮点缓存形式赋给顶点缓存
        mVertices.put(mVerticesData); //将顶点坐标加入缓存
        mVertices.position(0); //设置缓存起始位置
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        int vertexShader; //顶点着色器
        int fragmentShader; //片元着色器
        int programObject; //程序对象
        int[] linked = new int[1];

        vertexShader = loadShader(GLES30.GL_VERTEX_SHADER,vShaderStr);
        fragmentShader = loadShader(GLES30.GL_FRAGMENT_SHADER,fShaderStr);

        programObject = GLES30.glCreateProgram();
        if(programObject == 0){
            return;
        }

        GLES30.glAttachShader(programObject,vertexShader);
        GLES30.glAttachShader(programObject,fragmentShader);

        //绑定vPosition归于0
        GLES30.glBindAttribLocation(programObject,0,"vPosition");
        //链接程序
        GLES30.glLinkProgram(programObject);

        //检查链接状态
        GLES30.glGetProgramiv(programObject, GLES20.GL_LINK_STATUS,linked,0);
        if(linked[0] == 0){
            Log.e(TAG,"Error linking program:");
            Log.e(TAG,GLES30.glGetShaderInfoLog(programObject));
            GLES30.glDeleteProgram(programObject);
            return;
        }

        mProgramObject = programObject;
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        mWidth = width;
        mHeight = height;
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //设置视窗
        GLES30.glViewport(0,0,mWidth,mHeight);
        //清除颜色缓存
        GLES30.glClearColor(1.0f,0.0f,0.0f,0.0f);
        //使用程序对象
        GLES30.glUseProgram(mProgramObject);

        //载入顶点数据
        GLES30.glVertexAttribPointer(0,3,GLES20.GL_FLOAT,false,0,mVertices);
        GLES30.glEnableVertexAttribArray(0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,3);
    }

    /**
     * 创建shader对象，装载着色器源文件，编译着色器
     * @param type
     * @param shaderSrc
     * @return
     */
    private int loadShader(int type,String shaderSrc){
        int shader;
        int[] compiled = new int[1];
        //创建着色器对象
        shader = GLES30.glCreateShader(type);
        if(shader == 0){
            return 0;
        }

        //装载着色器资源
        GLES30.glShaderSource(shader,shaderSrc);

        //编译着色器
        GLES30.glCompileShader(shader);

        //检查编译状态
        GLES30.glGetShaderiv(shader,GLES30.GL_COMPILE_STATUS,compiled,0);

        if(compiled[0] == 0){
            Log.e(TAG,GLES30.glGetShaderInfoLog(shader));
            GLES30.glDeleteShader(shader);
            return 0;
        }
        return shader;
    }
}
