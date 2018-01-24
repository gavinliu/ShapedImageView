# ShapedImageView

[![CircleCI](https://circleci.com/gh/gavinliu/ShapedImageView/tree/master.svg?style=svg)](https://circleci.com/gh/gavinliu/ShapedImageView/tree/master)

* Support ``circle`` & ``round rect`` shaped
* Support ``stroke``
* Support ``TransitionDrawable``
* ***New*** **Support custom PathExtension**

## Screenshots

![](/screenshots1.png) ![](/screenshots2.png)

## Usage

### dependencies

```
compile 'cn.gavinliu.android.lib:ShapedImageView:0.8.5'
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

### Java Api

```java

public void setShape(int shapeMode, float radius);

public void setShapeMode(int shapeMode); // SHAPE_MODE_ROUND_RECT | SHAPE_MODE_CIRCLE

public void setShapeRadius(float radius);

public void setStroke(int strokeColor, float strokeWidth);

public void setStrokeColor(int strokeColor);

public void setStrokeWidth(float strokeWidth);


```

## TODO

* Support Any Shape (SVG to PathShape)

## License

MIT
