package columbia.mce;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;

public class BlindGameActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(new BlindGameGraphicsView(this));
  }

  static public class BlindGameGraphicsView extends MCSGame {
    int BUTTON_TOP = 200;
    int BUTTON_CAT_LEFT = 0;
    int BUTTON_MOUSE_LEFT = 500;
    int BUTTON_ELEPHANT_LEFT = 700;


    public BlindGameGraphicsView(Context context){
      super(context);
    }

    @Override
    protected void onDraw(Canvas canvas){
      Paint cPaint = new Paint();
      cPaint.setColor(Color.GRAY);

      Path path = new Path();
      path.addCircle(300, 200, 150, Path.Direction.CW);

      canvas.drawPath(path, cPaint);

      dCat.setBounds(BUTTON_CAT_LEFT, BUTTON_TOP, BUTTON_CAT_LEFT + 100, BUTTON_TOP + 100);
      dCat.draw(canvas);

      dMouse.setBounds(BUTTON_MOUSE_LEFT, BUTTON_TOP, BUTTON_MOUSE_LEFT + 100, BUTTON_TOP + 100);
      dMouse.draw(canvas);

      dElephant.setBounds(BUTTON_ELEPHANT_LEFT, BUTTON_TOP, BUTTON_ELEPHANT_LEFT + 100, BUTTON_TOP + 100);
      dElephant.draw(canvas);
    }
  }
}
