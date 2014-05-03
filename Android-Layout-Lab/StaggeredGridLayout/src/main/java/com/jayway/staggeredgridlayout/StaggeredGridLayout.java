/*
 * (C) Copyright 2013 Jayway AB (http://www.jayway.com/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
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

}
