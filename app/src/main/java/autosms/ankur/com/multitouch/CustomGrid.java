package autosms.ankur.com.multitouch;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomGrid extends BaseAdapter{
    private Context mContext;
    private ArrayList<String> numberlist ;


    public CustomGrid(Context c,ArrayList<String> numberlist) {
        mContext = c;
        this.numberlist = numberlist;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return numberlist.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid = null;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_item_layout, null);
            grid.setMinimumHeight((int) (MainActivity.height/Math.sqrt(numberlist.size())) - 10);
            grid.setBackgroundColor(0xff00ffff);
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}