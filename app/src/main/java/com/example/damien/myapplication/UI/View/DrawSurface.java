package com.example.damien.myapplication.UI.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.example.damien.myapplication.Model.Draw.Circle;
import com.example.damien.myapplication.Model.Draw.Dessin;
import com.example.damien.myapplication.Model.Draw.Nyan;
import com.example.damien.myapplication.Model.Draw.Square;
import com.example.damien.myapplication.R;

import java.util.ArrayList;

/**
 * Created by damien on 06/10/2015.
 */
public class DrawSurface extends SurfaceView {

    //region Attributs
    private Paint paint = new Paint();
    private ArrayList<Dessin> dessins;
    private Boolean existCircle;
    private Dessin selectedDessin;
    public int type;
    //endregion

    //region Constructor

    /**
     * Constructeur
     * @param context context
     * @param attrs attribute
     */
    public DrawSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        selectedDessin = new Circle();
        dessins = new ArrayList<>();
        existCircle = false;
        type = 0;
    }
    //endregion

    //region Override
    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.nyan);
        myBitmap = Nyan.getResizedBitmap(myBitmap);

        // Dessiner ici !
        for (Dessin d : dessins) {
            if (d.isNew) {
                paint.setColor(Dessin.colorSelected);
            } else {
                paint.setColor(Dessin.color);
            }
            d.isNew = false;

            if( d instanceof Circle)
            {
                canvas.drawCircle(d.x, d.y, Dessin.size, paint);
            } else if ( d instanceof Square){
                canvas.drawRect(d.x - Dessin.size, d.y - Dessin.size, d.x + Dessin.size, d.y + Dessin.size,paint);
            } else if ( d instanceof Nyan){
                canvas.drawBitmap(myBitmap,d.x,d.y, paint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Gérer les actions ici !
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                selectedDessin.x = event.getX();
                selectedDessin.y = event.getY();
                // relâchement du doigt
                if (!isExistCircle()) {
                    dessins.add(selectedDessin);
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_DOWN:
                switch (type){
                    case 0:
                        selectedDessin = new Circle();
                        break;
                    case 1:
                        selectedDessin = new Square();
                        break;
                    case 2:
                        selectedDessin = new Nyan();
                        break;
                    default:
                        selectedDessin = new Circle();
                        break;
                }
                existCircle = false;

                selectedDessin.x = event.getX();
                selectedDessin.y = event.getY();
                existCircle = isExistCircle();
                selectedDessin.isNew = true;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (existCircle) {
                    selectedDessin.x = event.getX();
                    selectedDessin.y = event.getY();
                    selectedDessin.isNew = true;
                    invalidate();
                }
                break;
            default:
                break;
        }
        return true;
    }
    //endregion

    //region Methods

    /**
     * Regarde s'il n'existe pas déjà un dessin à l'emplacement du nouveau point
     * @return
     */
    private boolean isExistCircle() {
        for (Dessin d : dessins) {
            if (Math.sqrt(Math.pow((selectedDessin.x - d.x),2) + Math.pow((selectedDessin.y - d.y),2)) < Dessin.size) {
                selectedDessin = d;
                return true;
            }
        }
        return false;
    }

    /**
     * Vide la surface de dessin
     */
    public void reset(){
        this.dessins.clear();
        invalidate();
    }
    //endregion
}
