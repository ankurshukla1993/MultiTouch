package autosms.ankur.com.multitouch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class MainActivity extends Activity {
    private String TAG = "GridViewActivity";
    private String result ;
    private Activity activity ;
    public static GridView gridView ;
    private int gridValue ;

    private CustomGrid adapter;
    private String tx ;

    private ArrayList<String> numberList = new ArrayList() ;
    private ImageView im ;
    private Queue<String> q ;

    private DisplayMetrics metrics = new DisplayMetrics();
    static int width ;
    static int height ;
    private int pv ;

    private Set<String> set = new HashSet<String>() ;
    private Set<Integer> num_set = new HashSet<Integer>() ;
    private Random r ;
    private int randnum ;
    private View lv ;
    private int counter = 0 ;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.gview);
        activity=this;
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        height = metrics.heightPixels;

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            result = extras.getString("NUMBER");
            gridValue = Integer.valueOf(result) ;
            gridValue = gridValue * gridValue ;
            //Toast.makeText(MainActivity.this, "Result value = " + result, Toast.LENGTH_SHORT).show();

            for(int i = 0; i < gridValue; i++){
                numberList.add(String.valueOf(i)) ;
            }
        }

        gridView = (GridView) findViewById(R.id.gridView1);


        adapter = new CustomGrid(MainActivity.this,numberList);
        gridView.setAdapter(adapter);
        gridView.setNumColumns(Integer.valueOf(result));
        adapter.notifyDataSetChanged();



        tx = "" ;
        r=new Random();
        randnum = r.nextInt(gridValue);
        num_set.add(randnum) ;
        blink(randnum) ;


        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int gridView, long id) {
                if(set.size() > 4){
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Game Over  !! :(")
                            .setMessage("5 fingers are not allowed")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .show();
                }
                else{
                    if (gridView == randnum || set.contains(String.valueOf(gridView))) {
                        if (!set.contains(String.valueOf(gridView))) {
                            tx = String.valueOf(gridView);
                            Log.v(TAG, "onItemClick:(" + gridView + "," + id + "," + tx + ")");
                            if ("".equals(tx)) return;
                            set.add(tx);
                            //Toast.makeText(MainActivity.this, "Queue = " + set, Toast.LENGTH_SHORT).show();

                            if (v != null) {
                                ColorDrawable colorId = (ColorDrawable) v.getBackground();
                                int color = colorId.getColor();
                                if (color == Color.WHITE)
                                    v.setBackgroundColor(0xff00ffff);
                                else
                                    v.setBackgroundColor(Color.WHITE);
                            }
                            randnum = r.nextInt(gridValue);
                            while (num_set.contains(randnum)) {
                                randnum = r.nextInt(gridValue);
                            }
                            num_set.add(randnum);
                            blink(randnum);
                        }
                    } else {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Game Over  !! :(")
                                .setMessage("Wrong tile is touched")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(MainActivity.this, NumberCollector.class);
                                        startActivity(intent);
                                    }
                                })
                                .show();
                    }
                }

            }
        });

        gridView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                int pointerIndex = ((event.getAction() & MotionEvent.ACTION_POINTER_ID_MASK)
                        >> MotionEvent.ACTION_POINTER_ID_SHIFT);
                int pointerId = event.getPointerId(pointerIndex);

                Log.v(TAG, "onTouch:ACTION_DOWN");

                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        perform_action("ACTION_DOWN", event, pointerId);
                        break;
                    case MotionEvent.ACTION_POINTER_1_DOWN:
                        perform_action("ACTION_POINTER_1_DOWN", event, pointerId);
                        break;
                    case MotionEvent.ACTION_POINTER_2_DOWN:
                        perform_action("ACTION_POINTER_2_DOWN", event, pointerId);
                        break;
                    case MotionEvent.ACTION_POINTER_3_DOWN:
                        perform_action("ACTION_POINTER_3_DOWN", event, pointerId);
                        break;
                    case MotionEvent.ACTION_UP:
                        //Toast.makeText(MainActivity.this, "First pointer is up pointerId = " + pointerId, Toast.LENGTH_SHORT).show();
                        pv = gridView.pointToPosition((int) event.getX(pointerIndex), (int) event.getY(pointerIndex));
                        set.remove(String.valueOf(pv));
                        //Toast.makeText(MainActivity.this, "Queue = " + set, Toast.LENGTH_SHORT).show();
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        //Toast.makeText(MainActivity.this, "ACTION_POINTER_1_UP pointerId = " + pointerId, Toast.LENGTH_SHORT).show();
                        pv = gridView.pointToPosition((int) event.getX(pointerIndex), (int) event.getY(pointerIndex));
                        set.remove(String.valueOf(pv));
                        //Toast.makeText(MainActivity.this, "Queue = " + set, Toast.LENGTH_SHORT).show();
                        break;
                    case MotionEvent.ACTION_POINTER_2_UP:
                        //Toast.makeText(MainActivity.this, "ACTION_POINTER_2_UP pointerId = " + pointerId, Toast.LENGTH_SHORT).show();
                        pv = gridView.pointToPosition((int) event.getX(pointerIndex), (int) event.getY(pointerIndex));
                        set.remove(String.valueOf(pv));
                        //Toast.makeText(MainActivity.this, "Queue = " + set, Toast.LENGTH_SHORT).show();
                        break;
                    case MotionEvent.ACTION_POINTER_3_UP:
                        //Toast.makeText(MainActivity.this, "ACTION_POINTER_3_UP pointerId = " + pointerId, Toast.LENGTH_SHORT).show();
                        pv = gridView.pointToPosition((int) event.getX(pointerIndex), (int) event.getY(pointerIndex));
                        set.remove(String.valueOf(pv));
                        //Toast.makeText(MainActivity.this, "Queue = " + set, Toast.LENGTH_SHORT).show();
                        break;
                    case 0x305:
                    case 0x405:

                        if (Build.VERSION.SDK_INT >= 5) {
                            int pointCount = event.getPointerCount();
                            for (int p = 0; p < pointCount; p++) {
                                int px = (int) event.getX(p);
                                int py = (int) event.getY(p);
                                clickItemView(px, py);
                            }
                        } else {
                            int px = (int) event.getX();
                            int py = (int) event.getY();
                            clickItemView(px, py);
                        }

                        break;

                }

                return true;
            }
        });


    }


    private void blink(final int rand_num){
        final int new_num = rand_num ;
        final Handler handler = new Handler();
        lv = gridView.getChildAt(rand_num);
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeToBlink = 1000;    //in milissegunds
                try{Thread.sleep(timeToBlink);}catch (Exception e) {}
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (lv != null) {
                            ColorDrawable colorId = (ColorDrawable) lv.getBackground();
                            int color = colorId.getColor();
                            if (color == Color.WHITE)
                                lv.setBackgroundColor(0xff00ffff);
                            else
                                lv.setBackgroundColor(Color.WHITE);
                        }
                        if(counter <= 2) {
                            counter++ ;
                            if (lv != null)
                                lv.setBackgroundColor(0xff00ffff);
                            blink(new_num);
                        }
                        else
                            counter = 0 ;
                    }
                });
            }
        }).start();
    }


    private void perform_action(String action, MotionEvent event, int pointerId) {

        int px ;
        int py ;

        if(Build.VERSION.SDK_INT>=5){
            int pointCount = event.getPointerCount();
            for (int p = 0; p < pointCount; p++) {
                px=(int)event.getX(p);
                py=(int)event.getY(p);
                clickItemView(px, py);
            }
        }
        else{
            px=(int)event.getX();
            py=(int)event.getY();
            clickItemView(px, py);
        }

        //Toast.makeText(MainActivity.this, "Action" + action+" pointerId = " + pointerId, Toast.LENGTH_SHORT).show();

    }


    private void clickItemView(int px, int py) {
        int pv = gridView.pointToPosition(px,py);
        long id = gridView.pointToRowId(px,py);
        View lv = gridView.getChildAt(pv);
        Log.v(TAG,"HIT:"+isViewContains(lv,px,py));
        gridView.performItemClick(lv, pv, id);
    }

    private boolean isViewContains(View view, int rx, int ry) {
        if(view==null)return false;
//	    int[] l = new int[2];
//	    view.getLocationOnScreen(l);
        int x = view.getLeft();// l[0];
        int y = view.getTop();//l[1];
        int w = view.getWidth();
        int h = view.getHeight();

        Log.v(TAG,"(x,y,x+w,y+h,rx,ry)=("+x+","+y+","+(x+w)+","+(y+h)+","+rx+","+ry+")");

        if (rx < x || rx > x + w || ry < y || ry > y + h) {
            return false;
        }
        return true;
    }

    protected <T extends View> T _findViewById(final int id){
        return (T)findViewById(id);
    }
}