package com.jayway.staggeredgridlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class StaggeredGridLayout extends ViewGroup {
    private static final int DEFAULT_NUM_OF_COLS  = 2;

    private int mCols;

    private int mColWidth;

    /**
     * Class constructor used when programmatically add this component.
     *
     * @param context
     */
    public StaggeredGridLayout(Context context) {
        super(context);
        init(null);
    }

    /**
     * Class constructor used by the inflater when adding the component in XML.
     *
     * @param context
     * @param attrs
     */
    public StaggeredGridLayout(Context context, AttributeSet attrs) {
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
    public StaggeredGridLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    /**
     * Initialization method for extracting the xml attributes.
     *
     * @param attrs
     */
    private void init(AttributeSet attrs) {
        if ( attrs == null ) {
            mCols = DEFAULT_NUM_OF_COLS;
        } else {
            TypedArray attributes = getContext().obtainStyledAttributes(
                    attrs,
                    R.styleable.SquareGridLayout);

            mCols = attributes.getInt(R.styleable.SquareGridLayout_cols, DEFAULT_NUM_OF_COLS);

            attributes.recycle();
        }
    }

    /**
     * Compute the size of itself and it’s children.
     *
     * {@inheritDoc}
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMeasureSize  = MeasureSpec.getSize(widthMeasureSpec);
        final int heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec);
        final int widthPadding      = getPaddingLeft() + getPaddingRight();

        int[] columns = new int[mCols];

        mColWidth = (widthMeasureSize - widthPadding) / mCols;
        for (int i = 0; i < getChildCount(); ++i) {
            final View child = getChildAt(i);

            measureChildWithMargins(child,
                    MeasureSpec.makeMeasureSpec(widthMeasureSize, MeasureSpec.UNSPECIFIED),  0,
                    MeasureSpec.makeMeasureSpec(heightMeasureSize, MeasureSpec.UNSPECIFIED), 0
            );

            int col     = getLowestColumn(columns);
            float ratio = mColWidth / (float)child.getMeasuredWidth();

            int childHeight = (int) (child.getMeasuredHeight() * ratio);
            columns[col] += childHeight;
        }

        setMeasuredDimension(
                widthMeasureSize,
                columns[getHighestColumn(columns)]
        );
    }



    /**
     * Compute the bounds of it’s children for drawing.
     *
     * {@inheritDoc}
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int pl = getPaddingLeft();
        final int pt = getPaddingTop();

        int[] columns = new int[mCols];
        for (int i = 0; i < getChildCount(); ++i) {
            final View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }

            int col     = getLowestColumn(columns);
            float ratio = mColWidth / (float)child.getMeasuredWidth();

            int childHeight = (int) (child.getMeasuredHeight() * ratio);

            MarginLayoutParams lps = (MarginLayoutParams) child.getLayoutParams();
            child.layout(
                    pl + col       * mColWidth      + lps.leftMargin,
                    pt + columns[col]               + lps.topMargin,
                    pl + (col + 1) * mColWidth      - lps.rightMargin,
                    pt + columns[col] + childHeight - lps.bottomMargin
            );

            columns[col] += childHeight;
        }
    }

    /**
     * Needed to be able to use measureChildWithMargins.
     *
     * @return
     */
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    /**
     * Needed to be able to use measureChildWithMargins.
     *
     * @return
     */
    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    /**
     * Needed to be able to use measureChildWithMargins.
     *
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    /**
     * Returns the column with the lowest value.
     *
     * @param columns
     * @return
     */
    private int getLowestColumn(int[] columns) {
        int lowest = 0;
        for (int i = 1; i < columns.length; ++i) {
            if (columns[i] < columns[lowest]) {
                lowest = i;
            }
        }

        return lowest;
    }

    /**
     * Returns the column with the highest value.
     *
     * @param columns
     * @return
     */
    private int getHighestColumn(int[] columns) {
        int highest = 0;
        for (int i = 1; i < columns.length; ++i) {
            if (columns[i] > columns[highest]) {
                highest = i;
            }
        }

        return highest;
    }
}
