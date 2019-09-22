package com.home.sliderecyclerview;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class MainActivityTwo extends AppCompatActivity implements View.OnTouchListener {


    String TAG = MainActivityTwo.class.getSimpleName();
    TextView tvCo, tvXY, tvDifXY;
    ConstraintLayout root;
    float height = 0;
    View line;
    List<String> names = Arrays.asList("Kelvin", "Lionnel", "Cervan", "Ruiz", "Jorge", "Lima", "Cuzco", "1", "2", "3", "4", "5", "6"
            , "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20");
    RecyclerView rvMain;
    RecyclerView.OnScrollListener obs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //final Button btn = findViewById(R.id.btn);
        //btn.setOnTouchListener(this);
        tvCo = findViewById(R.id.tvCo);
        root = findViewById(R.id.root);
        tvXY = findViewById(R.id.tvXY);
        tvDifXY = findViewById(R.id.tvDifXY);
        line = findViewById(R.id.line);
        rvMain = findViewById(R.id.rvMain);

        root.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        // Layout has happened here.
                        height = root.getY();

                        // Don't forget to remove your listener when you are done with it.
                        root.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });

        rvMain.setAdapter(new MainAdapter(names));
        rvMain.setOnTouchListener(this);
        obs = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            int currentScrollPosition = 0;

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                /*int firstVisiblePosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                if (firstVisiblePosition == 0) {
                    Toast.makeText(MainActivityTwo.this, "UP", Toast.LENGTH_LONG).show();
                    rvMain.setOnTouchListener(MainActivityTwo.this);
                }*/
                /*boolean b = recyclerView.canScrollVertically(-1);
                if (!b) {
                    Toast.makeText(MainActivityTwo.this, "UP", Toast.LENGTH_LONG).show();
                    recyclerView.removeOnScrollListener(obs);
                    rvMain.setOnTouchListener(MainActivityTwo.this);
                    recyclerView.animate()
                            //.x(location[0] + diffX)
                            .y(1)
                            .setDuration(0)
                            .start();
                    move = true;
                }*/
                Log.d(TAG, "onScroll");
                currentScrollPosition += dy;

                if (currentScrollPosition == 0) {
                    // We're at the top
                    Toast.makeText(MainActivityTwo.this, "UP", Toast.LENGTH_LONG).show();
                    recyclerView.removeOnScrollListener(obs);
                    rvMain.setOnTouchListener(MainActivityTwo.this);
                }
            }
        };
        rvMain.addOnScrollListener(obs);
    }

    boolean isInTop = false;
    boolean move;
    float dX, dY;
    float cX, cY;
    float iX, iY, diffX, diffY;
    float[] location = new float[2];

    @Override
    public boolean onTouch(View view, MotionEvent event) {


        tvCo.setText(String.format("rx: %s; ry: %s", event.getRawX(), event.getRawY()));
        tvXY.setText(String.format("x: %s; y: %s", view.getX(), view.getY()));
        tvDifXY.setText(String.format("dx: %s; dy: %s", dX, dY));

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                /*dX = view.getX() - event.getRawX();
                dY = view.getY() - event.getRawY();*/
                iX = event.getRawX();
                iY = event.getRawY();
                location[0] = view.getX();
                location[1] = view.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //if((event.getRawY() + dY) < 40)
                /*if (view.getY() < 450) {
                    view.setY(451);
                    return true;
                }*/
                /*view.animate()
                        .x(event.getRawX() + dX)
                        .y(event.getRawY() + dY)
                        .setDuration(0)
                        .start();*/
                diffX = event.getRawX() - iX;
                diffY = event.getRawY() - iY;

                float newPosY = location[1] + diffY;

                if (newPosY < 200) {
                    // se ha alcanzado el límite y no mover más
                    if (isInTop) {
                        rvMain.setOnTouchListener(MainActivityTwo.this);
                        rvMain.removeOnScrollListener(obs);
                        isInTop = true;
                    } else {
                        rvMain.setOnTouchListener(null);
                        rvMain.addOnScrollListener(obs);
                        view.animate()
                                .y(200)
                                .setDuration(0)
                                .start();
                        isInTop = true;
                    }
                    Log.d(TAG, "newY " + newPosY);
                    Log.d(TAG, "isInTop " + isInTop);
                } else {
                    if (isInTop) {
                        rvMain.setOnTouchListener(MainActivityTwo.this);
                        rvMain.removeOnScrollListener(obs);
                        view.animate()
                                .y(newPosY)
                                .setDuration(0)
                                .start();
                        isInTop = false;
                    } else {
                        view.animate()
                                .y(newPosY)
                                .setDuration(0)
                                .start();
                        isInTop = false;
                        rvMain.setOnTouchListener(MainActivityTwo.this);
                        rvMain.removeOnScrollListener(obs);
                    }
                    Log.d(TAG, "newY: " + newPosY);
                    Log.d(TAG, "isInTop " + isInTop);
                    break;
                }
            default:
                return false;
        }
        return true;
    }
}
