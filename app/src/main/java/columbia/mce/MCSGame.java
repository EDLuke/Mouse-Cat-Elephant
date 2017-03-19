package columbia.mce;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by lukez_000 on 03/16/2017.
 */

public class MCSGame extends View{
  static final int FRAME_RATE = 30;
  static final int RESET_RATE = 2500;
  static final int RESTART_RATE = 3500;

  Drawable dCat;
  Drawable dMouse;
  Drawable dElephant;
  Drawable dBackground;
  Drawable dGemEmpty;
  Drawable dGemPlayer;
  Drawable dGemOpponent;
  Drawable dBack;

  Resources res;
  DisplayMetrics dsm;
  Context ctx;

  int screenWidth;
  int screenHeight;

  public MCSGame(Context context){
    super(context);

    ctx = context;
    res = ctx.getResources();
    dsm = res.getSystem().getDisplayMetrics();

    dCat      = getResources().getDrawable(R.drawable.ic_cat,       context.getTheme());
    dMouse    = getResources().getDrawable(R.drawable.ic_mouse,     context.getTheme());
    dElephant = getResources().getDrawable(R.drawable.ic_elephant,  context.getTheme());
    dBack     = getResources().getDrawable(R.drawable.ic_back,      context.getTheme());
    dBackground = getResources().getDrawable(R.drawable.ic_background,  context.getTheme());
    dGemEmpty   = getResources().getDrawable(R.drawable.ic_gem_empty,  context.getTheme());
    dGemPlayer  = getResources().getDrawable(R.drawable.ic_gem_player,  context.getTheme());
    dGemOpponent = getResources().getDrawable(R.drawable.ic_gem_opponent,  context.getTheme());

    screenWidth  = dsm.widthPixels;
    screenHeight = dsm.heightPixels;
  }

  public void finishGame(){
    ((Activity)ctx).finish();
  }

}
