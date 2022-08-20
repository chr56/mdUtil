package util.mddesign.util;

import static util.mddesign.color.ColorStateList.disabledColorStateList;
import static util.mddesign.color.RippleColor.defaultRippleColor;
import static util.mddesign.drawable.DrawableUtil.createTintedDrawable;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Field;

import util.mdcolor.ColorUtil;
import util.mddesign.R;
import util.mddesign.color.MiscKt;
import util.mddesign.viewtint.CheckBoxUtil;
import util.mddesign.viewtint.EditTextUtil;
import util.mddesign.viewtint.ImageViewUtil;
import util.mddesign.viewtint.ProgressBarUtil;
import util.mddesign.viewtint.RadioButtonUtil;
import util.mddesign.viewtint.SeekBarUtil;
import util.mddesign.viewtint.SwitchUtil;

/**
 * @author afollestad, plusCubed
 */
public final class TintHelper {

    @SuppressWarnings("deprecation")
    public static void setTintSelector(@NonNull View view, @ColorInt final int color, final boolean darker, final boolean useDarkTheme) {
        final boolean isColorLight = ColorUtil.isColorLight(color);
        final int disabled = ContextCompat.getColor(view.getContext(), useDarkTheme ? R.color.MD_button_disabled_dark : R.color.MD_button_disabled_light);
        final int pressed = ColorUtil.shiftColor(color, darker ? 0.9f : 1.1f);
        final int activated = ColorUtil.shiftColor(color, darker ? 1.1f : 0.9f);
        final int rippleColor = defaultRippleColor(view.getContext(), isColorLight);
        final int textColor = ContextCompat.getColor(view.getContext(), isColorLight ? R.color.MD_primary_text_light : R.color.MD_primary_text_dark);

        final ColorStateList sl;
        if (view instanceof Button) {
            sl = disabledColorStateList(color, disabled);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                    view.getBackground() instanceof RippleDrawable) {
                RippleDrawable rd = (RippleDrawable) view.getBackground();
                rd.setColor(ColorStateList.valueOf(rippleColor));
            }

            // Disabled text color state for buttons, may get overridden later by ATE tags
            final Button button = (Button) view;
            button.setTextColor(disabledColorStateList(textColor, ContextCompat.getColor(view.getContext(), useDarkTheme ? R.color.MD_button_text_disabled_dark : R.color.MD_button_text_disabled_light)));
        } else if (view instanceof FloatingActionButton) {
            // FloatingActionButton doesn't support disabled state?
            sl = new ColorStateList(new int[][]{
                    new int[]{-android.R.attr.state_pressed},
                    new int[]{android.R.attr.state_pressed}
            }, new int[]{
                    color,
                    pressed
            });

            final FloatingActionButton fab = (FloatingActionButton) view;
            fab.setRippleColor(rippleColor);
            fab.setBackgroundTintList(sl);
            if (fab.getDrawable() != null)
                fab.setImageDrawable(createTintedDrawable(fab.getDrawable(), textColor));
            return;
        } else {
            sl = new ColorStateList(
                    new int[][]{
                            new int[]{-android.R.attr.state_enabled},
                            new int[]{android.R.attr.state_enabled},
                            new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed},
                            new int[]{android.R.attr.state_enabled, android.R.attr.state_activated},
                            new int[]{android.R.attr.state_enabled, android.R.attr.state_checked}
                    },
                    new int[]{
                            disabled,
                            color,
                            pressed,
                            activated,
                            activated
                    }
            );
        }

        Drawable drawable = view.getBackground();
        if (drawable != null) {
            drawable = createTintedDrawable(drawable, sl);
            ViewUtil.setBackgroundCompat(view, drawable);
        }

        if (view instanceof TextView && !(view instanceof Button)) {
            final TextView tv = (TextView) view;
            tv.setTextColor(disabledColorStateList(textColor, ContextCompat.getColor(view.getContext(), isColorLight ? R.color.MD_text_disabled_light : R.color.MD_text_disabled_dark)));
        }
    }

    public static void setTintAuto(final @NonNull View view, final @ColorInt int color,
                                   boolean background) {
        setTintAuto(view, color, background, MiscKt.isWindowBackgroundDark(view.getContext()));
    }

    @SuppressWarnings("deprecation")
    public static void setTintAuto(final @NonNull View view, final @ColorInt int color,
                                   boolean background, final boolean isDark) {
        if (!background) {
            if (view instanceof RadioButton)
                RadioButtonUtil.setTint((RadioButton) view, color, isDark);
            else if (view instanceof SeekBar)
                SeekBarUtil.setTint((SeekBar) view, color, isDark);
            else if (view instanceof ProgressBar)
                ProgressBarUtil.setTint((ProgressBar) view, color);
            else if (view instanceof EditText)
                EditTextUtil.setTint((EditText) view, color, isDark);
            else if (view instanceof CheckBox)
                CheckBoxUtil.setTint((CheckBox) view, color, isDark);
            else if (view instanceof ImageView)
                ImageViewUtil.setDrawableColor((ImageView) view, color);
            else if (view instanceof Switch)
                SwitchUtil.setTint((Switch) view, color, isDark);
            else if (view instanceof SwitchCompat)
                SwitchUtil.setTint((SwitchCompat) view, color, isDark);
            else background = true;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                    !background && view.getBackground() instanceof RippleDrawable) {
                // Ripples for the above views (e.g. when you tap and hold a switch or checkbox)
                RippleDrawable rd = (RippleDrawable) view.getBackground();
                @SuppressLint("PrivateResource") final int unchecked = ContextCompat.getColor(view.getContext(),
                        isDark ? androidx.appcompat.R.color.ripple_material_dark : androidx.appcompat.R.color.ripple_material_light);
                final int checked = ColorUtil.adjustAlpha(color, 0.4f);
                final ColorStateList sl = new ColorStateList(
                        new int[][]{
                                new int[]{-android.R.attr.state_activated, -android.R.attr.state_checked},
                                new int[]{android.R.attr.state_activated},
                                new int[]{android.R.attr.state_checked}
                        },
                        new int[]{
                                unchecked,
                                checked,
                                checked
                        }
                );
                rd.setColor(sl);
            }
        }
        if (background) {
            // Need to tint the background of a view
            if (view instanceof FloatingActionButton || view instanceof Button) {
                setTintSelector(view, color, false, isDark);
            } else if (view.getBackground() != null) {
                Drawable drawable = view.getBackground();
                if (drawable != null) {
                    drawable = createTintedDrawable(drawable, color);
                    ViewUtil.setBackgroundCompat(view, drawable);
                }
            }
        }
    }

    public static void setTint(@NonNull CheckBox box, @ColorInt int color, boolean useDarker) {
        ColorStateList sl = new ColorStateList(new int[][]{
                new int[]{-android.R.attr.state_enabled},
                new int[]{android.R.attr.state_enabled, -android.R.attr.state_checked},
                new int[]{android.R.attr.state_enabled, android.R.attr.state_checked}
        }, new int[]{
                ContextCompat.getColor(box.getContext(), useDarker ? R.color.MD_control_disabled_dark : R.color.MD_control_disabled_light),
                ContextCompat.getColor(box.getContext(), useDarker ? R.color.MD_control_normal_dark : R.color.MD_control_normal_light),
                color
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            box.setButtonTintList(sl);
        } else {
            Drawable drawable = createTintedDrawable(ContextCompat.getDrawable(box.getContext(), androidx.appcompat.R.drawable.abc_btn_check_material), sl);
            box.setButtonDrawable(drawable);
        }
    }
}