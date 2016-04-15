# ShapedImageView

* 简洁，不到***100***行代码
* 支持``圆形`` ``圆角矩形``
* 支持``TransitionDrawable``

## Screenhots

![](/screenhots.png)

## Usage

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
    app:shape_mode="circle" />
```

### Round Rect

```java
<cn.gavinliu.android.lib.shapedimageview.ShapedImageView
    android:id="@+id/image2"
    android:layout_width="200dp"
    android:layout_height="200dp"
    android:layout_centerInParent="true"
    app:round_radius="20dp"
    app:shape_mode="round_rect" />
```

## License

MIT