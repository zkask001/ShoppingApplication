package com.example.shoppingapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

public class IndoorMapView extends View {
    private static final int MARKER_RADIUS = 10;
    private static final Paint markerPaint = new Paint();

    private static final ProductLocation[] PRODUCT_LOCATIONS = {
            new ProductLocation("Product 1", 100, 200),
            new ProductLocation("Product 2", 150, 250),
            new ProductLocation("Product 3", 200, 300),
    };

    private OnProductClickListener onProductClickListener;

    public IndoorMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the markers for each product
        for (ProductLocation productLocation : PRODUCT_LOCATIONS) {
            canvas.drawCircle(productLocation.x, productLocation.y, MARKER_RADIUS, markerPaint);
        }
    }

    public void setOnProductClickListener(OnProductClickListener onProductClickListener) {
        this.onProductClickListener = onProductClickListener;
    }

    public interface OnProductClickListener {
        void onProductClick(ProductLocation productLocation);
    }

    private static class ProductLocation {
        String productName;
        int x;
        int y;

        ProductLocation(String productName, int x, int y) {
            this.productName = productName;
            this.x = x;
            this.y = y;
        }
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean performLongClick() {
        return super.performLongClick();
    }
}