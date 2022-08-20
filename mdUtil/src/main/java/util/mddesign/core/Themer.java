package util.mddesign.core;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.WindowInsetsController;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import util.mdcolor.ColorUtil;
import util.mddesign.viewtint.MenuTinter;
import util.mddesign.util.TintHelper;
import util.mddesign.viewtint.ToolbarUtil;

import static android.view.View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;

/**
 * @author Karim Abou Zeid (kabouzeid)
 */
public final class Themer {

    @SuppressLint("CommitPrefEdits")
    public static boolean didThemeValuesChange(@NonNull Context context, long since) {
        return util.mdcolor.pref.ThemeColor.isConfigured(context) && util.mdcolor.pref.ThemeColor.mPreferences(context).getLong(util.mdcolor.pref.ThemeColor.VALUES_CHANGED, -1) > since;
    }

    public static void setStatusbarColorAuto(Activity activity) {
        setStatusbarColor(activity, util.mdcolor.pref.ThemeColor.statusBarColor(activity));
    }

    public static void setStatusbarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(color);
            setLightStatusbarAuto(activity, color);
        }
    }

    public static void setLightStatusbarAuto(Activity activity, int bgColor) {
        setLightStatusbar(activity, ColorUtil.isColorLight(bgColor));
    }

    public static void setLightStatusbar(Activity activity, boolean enabled) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            final WindowInsetsController wic = activity.getWindow().getInsetsController();
            if (wic != null) {
                if (enabled) {
                    wic.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS);
                } else {
                    wic.setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS);
                }
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final View decorView = activity.getWindow().getDecorView();
            final int systemUiVisibility = decorView.getSystemUiVisibility();
            if (enabled) {
                decorView.setSystemUiVisibility(systemUiVisibility | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                decorView.setSystemUiVisibility(systemUiVisibility & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    public static void setLightNavigationbar(Activity activity, boolean enabled) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            final WindowInsetsController wic = activity.getWindow().getInsetsController();
            if (wic != null) {
                if (enabled) {
                    wic.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS, WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS);
                } else {
                    wic.setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS);
                }
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final View decorView = activity.getWindow().getDecorView();
            int systemUiVisibility = decorView.getSystemUiVisibility();
            if (enabled) {
                systemUiVisibility |= SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            } else {
                systemUiVisibility &= ~SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            }
            decorView.setSystemUiVisibility(systemUiVisibility);
        }
    }

    public static void setLightNavigationbarAuto(Activity activity, int bgColor) {
        setLightNavigationbar(activity, ColorUtil.isColorLight(bgColor));
    }

    public static void setNavigationbarColorAuto(Activity activity) {
        setNavigationbarColor(activity, util.mdcolor.pref.ThemeColor.navigationBarColor(activity));
    }

    public static void setNavigationbarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setNavigationBarColor(color);
            setLightNavigationbarAuto(activity, color);
        }
    }

    public static void setActivityToolbarColorAuto(Activity activity, @Nullable Toolbar toolbar) {
        setActivityToolbarColor(activity, toolbar, util.mdcolor.pref.ThemeColor.primaryColor(activity));
    }

    public static void setActivityToolbarColor(Activity activity, @Nullable Toolbar toolbar, int color) {
        if (toolbar != null) {
            toolbar.setBackgroundColor(color);
            MenuTinter.setMenuColor_White(activity, toolbar, toolbar.getMenu());
            ToolbarUtil.setToolbarColorAuto(activity, toolbar, null, color);
        }
    }

    public static void setTaskDescriptionColorAuto(@NonNull Activity activity) {
        setTaskDescriptionColor(activity, util.mdcolor.pref.ThemeColor.primaryColor(activity));
    }

    public static void setTaskDescriptionColor(@NonNull Activity activity, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Task description requires fully opaque color
            color = ColorUtil.stripAlpha(color);
            // Sets color of entry in the system recents page
            activity.setTaskDescription(new ActivityManager.TaskDescription((String) activity.getTitle(), null, color));
        }
    }

    public static void setTint(@NonNull View view, @ColorInt int color) {
        TintHelper.setTintAuto(view, color, false);
    }

    public static void setBackgroundTint(@NonNull View view, @ColorInt int color) {
        TintHelper.setTintAuto(view, color, true);
    }

    private Themer() {
    }
}