package columbia.mce;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by lukez_000 on 03/16/2017.
 */

public class MCSGame extends View{
  Drawable dCat;
  Drawable dMouse;
  Drawable dElephant;
  Resources res;

  public MCSGame(Context context){
    super(context);

    res = context.getResources();

    dCat      = getResources().getDrawable(R.drawable.ic_cat,       context.getTheme());
    dMouse    = getResources().getDrawable(R.drawable.ic_mouse,     context.getTheme());
    dElephant = getResources().getDrawable(R.drawable.ic_elephant,  context.getTheme());
  }

}
