package columbia.mce;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class BackgroundSoundService extends Service {
  private static final String LOG_TAG = "BackgroundSoundService";
  MediaPlayer player;

  public BackgroundSoundService() {
  }

  @Override
  public void onCreate(){
    super.onCreate();
    player = MediaPlayer.create(this, R.raw.background_music);
    player.setLooping(true);
    player.setVolume(100, 100);
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId){
    if(intent.getExtras().getBoolean("play"))
      player.start();
    else
      player.pause();

    return START_STICKY;
  }

  @Override
  public void onDestroy(){
    player.stop();
    player.release();
  }

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }
}
