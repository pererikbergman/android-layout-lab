package com.jayway.squaregridlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class SquareGridLayout extends ViewGroup {
    private static final int DEFAULT_NUM_OF_COLS  = 4;
    private static final int DEFAULT_INITIAL_SIZE = 1;

    private int mCols;
    private int mInitialSize;

    private int mColSize;

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
        if ( attrs == null ) {
            mCols        = DEFAULT_NUM_OF_COLS;
            mInitialSize = DEFAULT_INITIAL_SIZE;
        } else {
            TypedArray attributes = getContext().obtainStyledAttributes(
                    attrs,
                    R.styleable.SquareGridLayout);

            mCols = attributes.getInt(R.styleable.SquareGridLayout_cols, DEFAULT_NUM_OF_COLS);
            mInitialSize = attributes.getInt(R.styleable.SquareGridLayout_initialSize, DEFAULT_INITIAL_SIZE);

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
        final int widthPadding      = getPaddingLeft() + getPaddingRight();
        final int heightPadding     = getPaddingTop()  + getPaddingBottom();

        // Ignoring children that is 'gone'
        int childCount = 0;
        for (int i = 0; i < getChildCount(); ++i) {
            if (getChildAt(i).getVisibility() != GONE) {
                childCount++;
            }
        }

        final int rows = Math.max(mInitialSize-1, (childCount + mInitialSize * mInitialSize - 2) / mCols);

        mColSize = (widthMeasureSize - widthPadding) / mCols;

        int idxComp = 0;
        for (int i = 0; i < getChildCount(); ++i) {
            int idx = i - idxComp;
            int x = i % mCols;
            int y = i / mCols;

            final View child = getChildAt(y * mColSize + x);
            if (child == null) {
                continue;
            }

            if (child.getVisibility() == GONE) {
                idxComp++;
                continue;
            }

            if (idx == 0) {
                measureChildWithMargins(child,
                        MeasureSpec.makeMeasureSpec(mColSize * 2, MeasureSpec.EXACTLY), 0,
                        MeasureSpec.makeMeasureSpec(mColSize * 2, MeasureSpec.EXACTLY), 0
                );
            } else {
                measureChildWithMargins(child,
                        MeasureSpec.makeMeasureSpec(mColSize, MeasureSpec.EXACTLY), 0,
                        MeasureSpec.makeMeasureSpec(mColSize, MeasureSpec.EXACTLY), 0
                );
            }
        }

        setMeasuredDimension(
                widthMeasureSize,
                (rows + 1) * mColSize + heightPadding
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

        int idxComp = 0;
        for (int i = 0; i < getChildCount(); ++i) {
            View view = getChildAt(i);
            if ( view.getVisibility() == GONE) {
                idxComp++;
                continue;
            }
            int idx   = convertIndex(i-idxComp, mCols, mInitialSize);
            int x     = idx % mCols;
            int y     = idx / mCols;
            int size  = mColSize;

            if (idx == 0) {
                size *= mInitialSize;
            }

            SquareGridLayoutParams lps = (SquareGridLayoutParams) view.getLayoutParams();

            view.layout(
                    pl + x       * size + lps.leftMargin,
                    pt + y       * size + lps.topMargin,
                    pl + (x + 1) * size - lps.rightMargin,
                    pt + (y + 1) * size - lps.bottomMargin
            );

            final String toastString = lps.getToastString();
            if ( toastString != null ) {
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), toastString, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    /**
     * Needed to be able to use measureChildWithMargins.
     *
     * @return
     */
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new SquareGridLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    /**
     * Needed to be able to use measureChildWithMargins.
     *
     * @return
     */
    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new SquareGridLayoutParams(p);
    }

    /**
     * Needed to be able to use measureChildWithMargins.
     *
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new SquareGridLayoutParams(getContext(), attrs);
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

    public class SquareGridLayoutParams extends MarginLayoutParams {

        private String mToastString;

        public SquareGridLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            init(attrs);
        }

        public SquareGridLayoutParams(int width, int height) {
            super(width, height);
        }

        public SquareGridLayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public SquareGridLayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        private void init(AttributeSet attrs) {
            if (attrs != null) {
                TypedArray attributes = getContext().obtainStyledAttributes(
                        attrs,
                        R.styleable.SquareGridLayout);

                mToastString = attributes.getString(R.styleable.SquareGridLayout_layout_toast_string);

                attributes.recycle();
            }
        }

        public String getToastString() {
            return mToastString;
        }
    }
}
