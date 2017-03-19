package columbia.mce;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class BlindGameActivity extends Activity {
  private static final String LOG_TAG = "BlindGameActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(new BlindGameGraphicsView(this));

    getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE);
  }

  public static class BlindGameGraphicsView extends MCSGame {
    private static final String PICK = "Pick an animal!";
    private static final String WIN  = "You win!";
    private static final String LOSE = "Opponent wins!";
    private static final String DRAW = "Draw";
    private static final String WINNER = "Winner";
    private static final String GAMEOVER = "Game Over!";

    enum State{WIN, LOSE, DRAW, WINNER, GAMEOVER, UNDECIDED}

    int BUTTON_TOP = 200;
    int BUTTON_CAT_LEFT = 0;
    int BUTTON_MOUSE_LEFT  = 500;
    int BUTTON_MOUSE_RIGHT = 500;
    int BUTTON_ELEPHANT_LEFT = 700;
    int BUTTON_SIZE = 300;

    int OPPONENT_TOP = 50;
    int OPPONENT_LEFT = 500;
    int OPPONENT_RIGHT = 500;

    int TEXT_TOP;
    int TEXT_LEFT;

    int GEM_SIZE = 125;
    int GEM_LEFT = 500;
    int GEM_TOP  = 500;

    //Game State variables
    Drawable dOpponent;
    State currentState;
    int chosenAnimal = -1; //0 - Cat, 2 - Mouse, 1 - Elephant
    int opponentAnimal = -1;
    int playerWinCount;
    int opponentWinCount;

    Paint textPaint;
    Paint selectionPaint;
    Rect selectionBorder;


    //Animation refresh handler and runnable
    private Handler handler;
    private Runnable refresh = new Runnable() {
      @Override
      public void run() {
        invalidate();
      }
    };
    private Runnable finishGame = new Runnable() {
      @Override
      public void run() {
        finishGame();
      }
    };



    private State decideOutcome(){
      State ret = State.UNDECIDED;

      if(chosenAnimal == opponentAnimal)
        ret = State.DRAW;
      else if(chosenAnimal == 0){
        ret = opponentAnimal == 1 ? State.LOSE : State.WIN;
      }
      else if(chosenAnimal == 1){
        ret = opponentAnimal == 2 ? State.LOSE : State.WIN;
      }
      else // chosenAnimal == 2
        ret = opponentAnimal == 0 ? State.LOSE : State.WIN;

      if(ret == State.WIN)
        playerWinCount++;
      else if(ret == State.LOSE)
        opponentWinCount++;

      if(playerWinCount == 3)
        ret = State.WINNER;
      else if(opponentWinCount == 3)
        ret = State.GAMEOVER;

      return ret;
    }


    public BlindGameGraphicsView(Context context){

      super(context);

      //Initialize three buttons' locations
      BUTTON_MOUSE_LEFT  = screenWidth / 2 - BUTTON_SIZE / 2;
      BUTTON_MOUSE_RIGHT = screenWidth / 2 + BUTTON_SIZE / 2;
      BUTTON_ELEPHANT_LEFT = screenWidth - BUTTON_SIZE;
      BUTTON_TOP = screenHeight / 3 * 2 + 150;

      //Initialize dOpponent's choice
      OPPONENT_TOP = BUTTON_TOP - (int)(BUTTON_SIZE * 3.5);
      OPPONENT_LEFT = screenWidth / 2 - BUTTON_SIZE / 2 * 3;
      OPPONENT_RIGHT = screenWidth / 2 + BUTTON_SIZE / 2 * 3;

      //Initialize text
      TEXT_TOP = BUTTON_TOP - 50;
      TEXT_LEFT = screenWidth / 2;

      //Initialize gem
      GEM_LEFT = screenWidth / 2 - (int)(GEM_SIZE * 2.5);
      GEM_TOP = 75;

      handler = new Handler();
      currentState = State.UNDECIDED;
      playerWinCount = 0;
      opponentWinCount = 0;

      //Initialize paint
      textPaint = new Paint();
      textPaint.setTextSize(50);
      textPaint.setTextAlign(Paint.Align.CENTER);
      textPaint.setColor(Color.BLACK);

      selectionPaint = new Paint();
      selectionPaint.setStyle(Paint.Style.STROKE);
      selectionPaint.setShader(new LinearGradient(0, 0, 0, BUTTON_SIZE, Color.GREEN, Color.BLUE, Shader.TileMode.MIRROR));
      selectionPaint.setStrokeWidth(10);

      selectionBorder = new Rect();
    }

    private void resetRound(){
      currentState = State.UNDECIDED;
      chosenAnimal = -1;
      opponentAnimal = -1;
    }

    @Override
    protected void onDraw(Canvas canvas){
      //Draw background
      dBackground.setBounds(0, 0, screenWidth, screenHeight);
      dBackground.draw(canvas);

      //Draw the buttons
      dCat.setBounds(BUTTON_CAT_LEFT, BUTTON_TOP, BUTTON_CAT_LEFT + BUTTON_SIZE, BUTTON_TOP + BUTTON_SIZE);
      dCat.draw(canvas);

      dMouse.setBounds(BUTTON_MOUSE_LEFT, BUTTON_TOP, BUTTON_MOUSE_RIGHT, BUTTON_TOP + BUTTON_SIZE);
      dMouse.draw(canvas);

      dElephant.setBounds(BUTTON_ELEPHANT_LEFT, BUTTON_TOP, screenWidth, BUTTON_TOP + BUTTON_SIZE);
      dElephant.draw(canvas);

      //Draw exit
      dBack.setBounds(0, GEM_TOP, GEM_SIZE, GEM_TOP + GEM_SIZE);
      dBack.draw(canvas);

      if(currentState != State.UNDECIDED) {
        //Draw dOpponent
        dOpponent.setBounds(OPPONENT_LEFT, OPPONENT_TOP, OPPONENT_RIGHT, OPPONENT_TOP + BUTTON_SIZE * 3);
        dOpponent.draw(canvas);

        //Draw Selection Border
        canvas.drawRect(selectionBorder, selectionPaint);
      }

      //Draw Gems
      for(int i = 0; i < 5; i++){
        if(i < playerWinCount) {
          dGemPlayer.setBounds(GEM_LEFT + i * GEM_SIZE, GEM_TOP, GEM_LEFT + i * GEM_SIZE + GEM_SIZE, GEM_TOP + GEM_SIZE);
          dGemPlayer.draw(canvas);
        }
        else if(4 - i < opponentWinCount) {
          dGemOpponent.setBounds(GEM_LEFT + i * GEM_SIZE, GEM_TOP, GEM_LEFT + i * GEM_SIZE + GEM_SIZE, GEM_TOP + GEM_SIZE);
          dGemOpponent.draw(canvas);
        }
        else{
          dGemEmpty.setBounds(GEM_LEFT + i * GEM_SIZE, GEM_TOP, GEM_LEFT + i * GEM_SIZE + GEM_SIZE, GEM_TOP + GEM_SIZE);
          dGemEmpty.draw(canvas);
        }
      }

      //Draw outcome text
      switch (currentState){
        case UNDECIDED:
          canvas.drawText(PICK, TEXT_LEFT, TEXT_TOP, textPaint);
          handler.postDelayed(refresh, FRAME_RATE);
          break;
        case WIN:
          canvas.drawText(WIN, TEXT_LEFT, TEXT_TOP, textPaint);
          resetRound();
          handler.postDelayed(refresh, RESET_RATE);
          break;
        case LOSE:
          canvas.drawText(LOSE, TEXT_LEFT, TEXT_TOP, textPaint);
          resetRound();
          handler.postDelayed(refresh, RESET_RATE);
          break;
        case DRAW:
          canvas.drawText(DRAW, TEXT_LEFT, TEXT_TOP, textPaint);
          resetRound();
          handler.postDelayed(refresh, RESET_RATE);
          break;
        case WINNER:
          canvas.drawText(WINNER, TEXT_LEFT, TEXT_TOP, textPaint);
          handler.postDelayed(finishGame, RESTART_RATE);
          break;
        case GAMEOVER:
          canvas.drawText(GAMEOVER, TEXT_LEFT, TEXT_TOP, textPaint);
          handler.postDelayed(finishGame, RESTART_RATE);
          break;
      }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
      if(event.getAction() != MotionEvent.ACTION_UP)
        return true;

      int x = (int)(event.getX());
      int y = (int)(event.getY());

      Rect touchRect = new Rect(x - 2, y - 2, x + 2, y + 2);
      if(touchRect.intersect(dCat.copyBounds())){
        Log.v(LOG_TAG, "Cat button touched");
        chosenAnimal = 0;
        selectionBorder.set(dCat.getBounds());
      }
      else if(touchRect.intersect(dElephant.copyBounds())){
        Log.v(LOG_TAG, "Elephant button touched");
        chosenAnimal = 1;
        selectionBorder.set(dElephant.getBounds());
      }
      else if(touchRect.intersect(dMouse.copyBounds())){
        Log.v(LOG_TAG, "Mouse button touched");
        chosenAnimal = 2;
        selectionBorder.set(dMouse.getBounds());
      }else if(touchRect.intersect(dBack.copyBounds())){ //Go back
        finishGame();
      }

      if(chosenAnimal != -1) {
        opponentAnimal = (new Random()).nextInt(3);
        switch(opponentAnimal){
          case 0:
            dOpponent = dCat.mutate().getConstantState().newDrawable();
            break;
          case 2:
            dOpponent = dMouse.mutate().getConstantState().newDrawable();
            break;
          case 1:
            dOpponent = dElephant.mutate().getConstantState().newDrawable();
            break;
        }
        currentState = decideOutcome();
      }

      return true;
    }
  }
}
