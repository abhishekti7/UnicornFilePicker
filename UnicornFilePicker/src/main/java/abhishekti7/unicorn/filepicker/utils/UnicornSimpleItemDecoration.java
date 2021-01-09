package abhishekti7.unicorn.filepicker.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import abhishekti7.unicorn.filepicker.R;

/**
 * Created by Abhishek Tiwari on 09-01-2021.
 */
public class UnicornSimpleItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider;

    public UnicornSimpleItemDecoration(Context context) {
        this.mDivider = ContextCompat.getDrawable(context, R.drawable.unicorn_item_layout_divider);
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for(int i = 0; i<childCount; i++){
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}
