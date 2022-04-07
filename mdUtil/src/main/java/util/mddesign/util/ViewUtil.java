package util.mddesign.util;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author Karim Abou Zeid (kabouzeid)
 */
public final class ViewUtil {

    @SuppressWarnings("deprecation")
    public static void removeOnGlobalLayoutListener(View v, ViewTreeObserver.OnGlobalLayoutListener listener) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
//            v.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
//        } else {
            v.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
//        }
    }

    @SuppressWarnings("deprecation")
    public static void setBackgroundCompat(@NonNull View view, @Nullable Drawable drawable) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
//            view.setBackground(drawable);
//        else
            view.setBackgroundDrawable(drawable);
    }

    public static TransitionDrawable setBackgroundTransition(@NonNull View view, @NonNull Drawable newDrawable) {
        TransitionDrawable transition = DrawableUtil.createTransitionDrawable(view.getBackground(), newDrawable);
        setBackgroundCompat(view, transition);
        return transition;
    }

    public static TransitionDrawable setBackgroundColorTransition(@NonNull View view, @ColorInt int newColor) {
        final Drawable oldColor = view.getBackground();

        Drawable start = oldColor != null ? oldColor : new ColorDrawable(view.getSolidColor());
        Drawable end = new ColorDrawable(newColor);

        TransitionDrawable transition = DrawableUtil.createTransitionDrawable(start, end);

        setBackgroundCompat(view, transition);

        return transition;
    }

    private ViewUtil() {
    }
}

/**
 * DrawableUtil
 *
 * @author Karim Abou Zeid (kabouzeid)
 */
final class DrawableUtil {

    public static TransitionDrawable createTransitionDrawable(@ColorInt int startColor, @ColorInt int endColor) {
        return createTransitionDrawable(new ColorDrawable(startColor), new ColorDrawable(endColor));
    }

    public static TransitionDrawable createTransitionDrawable(Drawable start, Drawable end) {
        final Drawable[] drawables = new Drawable[2];

        drawables[0] = start;
        drawables[1] = end;

        return new TransitionDrawable(drawables);
    }

    private DrawableUtil() {
    }
}