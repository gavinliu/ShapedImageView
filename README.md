# ShapedImageView

* 支持``圆形`` ``圆角矩形``
* 支持``显示边框``
* 支持``TransitionDrawable``

## Screenhots

![](/screenhots.png)

## Usage

### dependencies

```
compile 'cn.gavinliu.android.lib:ShapedImageView:0.5'
```

### attr

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>

    <declare-styleable name="ShapedImageView">
        <attr name="shape_mode" format="enum">
            <enum name="round_rect" value="1" />
            <enum name="circle" value="2" />
        </attr>
        <attr name="round_radius" format="dimension" />

        <attr name="stroke_width" format="dimension" />
        <attr name="stroke_color" format="color|reference" />
    </declare-styleable>

</resources>
```

### Circle

```java
<cn.gavinliu.android.lib.shapedimageview.ShapedImageView
    android:id="@+id/image1"
    android:layout_width="200dp"
    android:layout_height="200dp"
    android:layout_centerInParent="true"
    app:shape_mode="circle"
    app:stroke_color="#009688"
    app:stroke_width="3dp" />
```

### Round Rect

```java
<cn.gavinliu.android.lib.shapedimageview.ShapedImageView
    android:id="@+id/image2"
    android:layout_width="200dp"
    android:layout_height="200dp"
    android:layout_centerInParent="true"
    app:round_radius="20dp"
    app:shape_mode="round_rect"
    app:stroke_color="#009688"
    app:stroke_width="3dp" />
```

## TODO

* Support Any Shape (SVG to PathShape)

## License

MIT
