package columbia.mce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {
  private static final String LOG_TAG = "MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  public void onClickPlay(View view){
    Log.v(LOG_TAG, "Play");
    Intent intent = new Intent(this, BlindGameActivity.class);
    startActivity(intent);
  }

  public void onClickLeader(View view){
    Log.v(LOG_TAG, "LeaderBoard");

  }

  public void onClickSetting(View view){
    Log.v(LOG_TAG, "Setting");

  }
}
