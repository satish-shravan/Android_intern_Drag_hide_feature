package android.intern.drag_drop_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView img;
   private android.widget.RelativeLayout.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        img = findViewById ( R.id.img );

        img.setOnLongClickListener ( new View.OnLongClickListener () {
            @Override
            public boolean onLongClick(View view) {
                ClipData.Item item = new ClipData.Item((CharSequence) view.getTag ());
                String[] mimetype = {ClipDescription.MIMETYPE_TEXT_PLAIN};

                ClipData dragdata = new ClipData ( view.getTag ().toString (),mimetype,item);
                View.DragShadowBuilder shadow = new View.DragShadowBuilder ( img );

                view.startDrag ( dragdata,shadow,img,0 );
                return true;
            }
        } );

        
        img.setOnDragListener ( new View.OnDragListener () {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {

                switch (dragEvent.getAction ())
                {
                    case DragEvent.ACTION_DRAG_STARTED :
                        layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams ();
                        Toast.makeText ( MainActivity.this, "Action : Drag event started", Toast.LENGTH_SHORT ).show ();
                        break;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        int x_co = (int) dragEvent.getX ();
                        int y_co = (int) dragEvent.getY ();
                        Toast.makeText ( MainActivity.this, "Action : Drag event entered", Toast.LENGTH_SHORT ).show ();
                        break;

                    case DragEvent.ACTION_DRAG_LOCATION:
                         x_co = (int) dragEvent.getX ();
                         y_co = (int) dragEvent.getY ();
                        Toast.makeText ( MainActivity.this, "Action : Drag event LOCATION", Toast.LENGTH_SHORT ).show ();
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                       x_co = (int) dragEvent.getX ();
                       y_co= (int) dragEvent.getY ();
                       layoutParams.leftMargin=x_co;
                       layoutParams.topMargin = y_co;
                       view.setLayoutParams ( layoutParams );
                       Toast.makeText ( MainActivity.this, "Action : Drag event exited", Toast.LENGTH_SHORT ).show ();
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        Toast.makeText ( MainActivity.this, "Action : Drag event ENDED", Toast.LENGTH_SHORT ).show ();
                        break;

                    case DragEvent.ACTION_DROP:
                        Toast.makeText ( MainActivity.this, "Action : Drag event Drop", Toast.LENGTH_SHORT ).show ();
                        break;
                }

                return true;
            }
        } );

        img.setOnTouchListener ( new View.OnTouchListener () {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction () == MotionEvent.ACTION_DOWN)
                {
                    ClipData data = ClipData.newPlainText ( "","" );
                    View.DragShadowBuilder shadow = new View.DragShadowBuilder (img );

                    img.startDrag (data,shadow,img,0 );
                    img.setVisibility ( View.INVISIBLE );
                    return  true;
                }
                else
                {
                    return false;
                }
            }
        } );


    }
}
