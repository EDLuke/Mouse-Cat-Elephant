package columbia.mce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {
  private static final String LOG_TAG = "MainActivity";
  boolean play = true;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    //Set to full screen
    getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE);

    //Start the background music service
    Intent intent = new Intent(this, BackgroundSoundService.class);
    intent.putExtra("play", play);
    startService(intent);
  }

  public void onClickPlay(View view){
    Log.v(LOG_TAG, "Play");
    Intent intent = new Intent(this, BlindGameActivity.class);
    startActivity(intent);
  }

  public void onClickToggleMusic(View view){
    Log.v(LOG_TAG, "Setting");
    play = !play;
    if(play)
      ((ImageView)view).setImageResource(R.drawable.ic_musicon);
    else
      ((ImageView)view).setImageResource(R.drawable.ic_musicoff);

    Intent intent = new Intent(this, BackgroundSoundService.class);
    intent.putExtra("play", play);
    startService(intent);
  }
}
