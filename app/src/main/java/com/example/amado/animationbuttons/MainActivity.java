package com.example.amado.animationbuttons;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.CheckBox;


public class MainActivity extends ActionBarActivity {

   private ObjectAnimator mAnimator;
    private Button mRunButton;
    private Button mEdgeButton;
    private CheckBox mRotateBox;
    private Button mGhostButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRunButton = (Button)findViewById(R.id.run_button);
        mEdgeButton = (Button)findViewById(R.id.edge_button);
        mRotateBox = (CheckBox)findViewById(R.id.rotate_box);
        mGhostButton = (Button)findViewById(R.id.ghost_button);


        mRunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimator = ObjectAnimator.ofFloat(mRunButton, "translationX", 0f, 500f);
                mAnimator.setDuration(300);
                if(mRotateBox.isChecked()){

                    final AnimatorSet withRotate = new AnimatorSet();
                    ObjectAnimator rotate = ObjectAnimator.ofFloat(mRunButton, "rotation", 0f, 360f);
                    withRotate.playTogether(mAnimator, rotate);
                    withRotate.start();
                    withRotate.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {

                            withRotate.setInterpolator(new ReverseInterpolator());
                            withRotate.start();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });

                }else {
                    mAnimator.start();
                    mAnimator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {

                            mAnimator.reverse();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                }

            }
        });

        mEdgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callEdgeAnimation();
            }
        });

        mGhostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ObjectAnimator ghost = ObjectAnimator.ofFloat(mGhostButton, "alpha", 1f, 0f);
                ghost.setDuration(1000);
                if (mRotateBox.isChecked()) {
                    AnimatorSet rotateGhost = new AnimatorSet();
                    ObjectAnimator rotate = ObjectAnimator.ofFloat(mGhostButton, "rotation", 0f, 720f);
                    rotate.setDuration(600);
                    rotateGhost.playTogether(ghost, rotate);
                    rotateGhost.start();
                    ghost.reverse();
                } else {
                    ghost.start();
                    ghost.reverse();
                }

            }




        });

    }

    public class ReverseInterpolator implements Interpolator{
        @Override
        public float getInterpolation(float input) {
            return Math.abs(input -1f);
        }
    }

    private void callEdgeAnimation() {
        AnimatorSet edgeScreen = new AnimatorSet();
        ObjectAnimator maxX = ObjectAnimator.ofFloat(mEdgeButton, "translationX", 0f, 500f);
        ObjectAnimator minY = ObjectAnimator.ofFloat(mEdgeButton, "translationY", 0f, 600f);
        ObjectAnimator minX = ObjectAnimator.ofFloat(mEdgeButton, "translationX", 500f, 0f);
        ObjectAnimator maxY = ObjectAnimator.ofFloat(mEdgeButton, "translationY", 600f, 0f);
        ObjectAnimator rotate = ObjectAnimator.ofFloat(mEdgeButton, "rotation", 0f, 1440f);
        rotate.setDuration(1300);
        edgeScreen.playSequentially(maxX, minY, minX, maxY);
        if(mRotateBox.isChecked()){
            AnimatorSet edgeRotation = new AnimatorSet();
            edgeRotation.playTogether(edgeScreen, rotate);
            edgeRotation.start();
        }else{
            edgeScreen.start();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
