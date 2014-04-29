package com.jayway.squaregridlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class SquareGridLayout extends ViewGroup {

    /**
     * Class constructor used when programmatically add this component.
     *
     * @param context
     */
    public SquareGridLayout(Context context) {
        super(context);
        init(null);
    }

    /**
     * Class constructor used by the inflater when adding the component in XML.
     *
     * @param context
     * @param attrs
     */
    public SquareGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    /**
     * Class constructor used by the inflater when adding the component in XML and you have added
     * a style with: style="@styles/MyCustomStyle"
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public SquareGridLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    /**
     * Initialization method for extracting the xml attributes.
     *
     * @param attrs
     */
    private void init(AttributeSet attrs) {
        // TODO: Read the custom attributes.
    }

    /**
     * Compute the size of itself and it’s children.
     *
     * {@inheritDoc}
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO: Measure all children and call on setMeasuredDimension with right size.

        setMeasuredDimension(
                MeasureSpec.getSize(widthMeasureSpec),
                MeasureSpec.getSize(heightMeasureSpec)
        );
    }

    /**
     * Compute the bounds of it’s children for drawing.
     *
     * {@inheritDoc}
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        // TODO: Layout all children and give them their bounds.
    }

    /**
     * Modifying the child index to include the initial.
     *
     * @param idx
     * @param width
     * @param initialSize
     *
     * @return
     */
    private final int convertIndex(int idx, int width, int initialSize) {
        if (width == 1) {
            return idx;
        }

        if (idx == 0) {
            return idx;
        }

        for (int i = 0; i < initialSize; ++i) {
            if (idx <= width * i - initialSize * i) {
                return idx + (initialSize * i - 1);
            }
        }

        return idx + initialSize * initialSize - 1;
    }

}
