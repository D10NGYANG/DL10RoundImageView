# DL10RoundImageView
Android 自定义控件基本教程之自定义一个圆形ImageView可设置边框和阴影（demo带详细注释）

说明链接：https://blog.csdn.net/sinat_38184748/article/details/88643857

## 使用方法
添加依赖：

Add it in your root build.gradle at the end of repositories:
```java
    allprojects {
    	repositories {
    		...
    		maven { url 'https://jitpack.io' }
    	}
    }
```
Step 2. Add the dependency
```java
	dependencies {
	        implementation 'com.github.D10NGYANG:DL10RoundImageView:1.0.0'
	}
```

在页面布局里添加

```java
<com.dlong.rep.dlroundimageview.DLRoundImageView
        android:id="@+id/img"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_gravity="center"
        android:src="@mipmap/dlong" />
```
带属性的话

```java
<com.dlong.rep.dlroundimageview.DLRoundImageView
        android:id="@+id/img"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_gravity="center"
        android:src="@mipmap/dlong"
        app:borderColor="@android:color/white"
        app:borderWidth="20"
        app:hasShadow="true"
        app:shadowColor="@color/colorAccent"
        app:shadowRadius="30" />
```
代码动态修改属性

```java
public class MainActivity extends AppCompatActivity {
    private DLRoundImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = (DLRoundImageView) findViewById(R.id.img);
        img.setBorderWidth(20);
        img.setBorderColor(Color.WHITE);
        img.setHasShadow(true);
        img.setShadowColor(Color.GRAY);
        img.setShadowRadius(30f);
    }
}
```
