# ShapedImageView

[![Action](https://action-badges.now.sh/gavinliu/ShapedImageView)](https://github.com/gavinliu/ShapedImageView/actions) [![](https://jitpack.io/v/cn.gavinliu/ShapedImageView.svg)](https://jitpack.io/#cn.gavinliu/ShapedImageView)

* Support ``circle`` & ``round rect`` shaped
* Support ``stroke``
* Support ``TransitionDrawable``
* ***New*** **Support custom PathExtension**

## Screenshots

![](/screenshots1.png) ![](/screenshots2.png)

## Usage

### dependencies

Step 1. Add the JitPack repository to your build file

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Step 2. Add the dependency

```
dependencies {
    implementation 'cn.gavinliu:ShapedImageView:0.8.7'
}
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
