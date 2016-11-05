# ShapedImageView

* 支持``圆形`` ``圆角矩形``
* 支持``显示边框``
* 支持``TransitionDrawable``
* ***New*** **支持自定义Path扩展**

## Screenshots

![](/screenshots1.png) ![](/screenshots2.png)

## Usage

### dependencies

```
compile 'cn.gavinliu.android.lib:ShapedImageView:0.8.1'
```

### Circle

```java
<cn.gavinliu.android.lib.shapedimageview.ShapedImageView
    ...
    app:shape_mode="circle"

    app:stroke_color="#009688"
    app:stroke_width="3dp" />
```

### Round Rect

```java
<cn.gavinliu.android.lib.shapedimageview.ShapedImageView
    ...
    app:shape_mode="round_rect"
    app:round_radius="20dp"

    app:stroke_color="#009688"
    app:stroke_width="3dp" />
```

### PathExtension

```java

image1.setExtension(new CDPathExtension());

class CDPathExtension implements ShapedImageView.PathExtension {

    @Override
    public void onLayout(Path path, int width, int height) {
        path.reset();
        path.addCircle(width / 2, height / 2, width / 8, Path.Direction.CW);
    }
}
```

## TODO

* Support Any Shape (SVG to PathShape)

## License

MIT
