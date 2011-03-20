/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.smartphone;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.maps.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import org.me.smartphone.application.Road;
import org.me.smartphone.application.RoadProvider;

/**
 *
 * @author jeremie
 */
import org.me.smartphone.util.MessageBox;

public class MapRouteActivity extends MapActivity {

    LinearLayout linearLayout;
    MapView mapView;
    Button buttonBack;
    private Road mRoad;
    double fromLat, fromLon;
    MessageBox messageBox = new MessageBox();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        mapView = (MapView) findViewById(R.id.mapview);
        buttonBack = (Button) findViewById(R.id.buttonBack);
        mapView.setBuiltInZoomControls(true);

//        new Thread()  {
//
//            @Override
//            public void run() {

        try {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                fromLon = location.getLongitude();
//                fromLat = location.getLatitude();
            fromLon = 2.3;
            fromLat = 48.83;

            double toLat = 48.833, toLon = 2.333;
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
            String url = RoadProvider.getUrl(fromLat, fromLon, toLat, toLon);
            InputStream is = getConnection(url);
            mRoad = RoadProvider.getRoute(is);
            mHandler.sendEmptyMessage(0);
        } catch (Exception e) {
            messageBox.Show("Erreur", "Impossible d'atteindre le serveur", this);
        }
//            }
//        }.start();

        buttonBack.setOnClickListener(new View.OnClickListener()    {

            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
    Handler mHandler = new Handler()   {

        public void handleMessage(android.os.Message msg) {
            TextView textView = (TextView) findViewById(R.id.description);
            textView.setText(mRoad.mName + " - " + mRoad.mDescription);
            MapOverlay mapOverlay = new MapOverlay(mRoad, mapView);
            List<Overlay> listOfOverlays = mapView.getOverlays();
            listOfOverlays.clear();
            listOfOverlays.add(mapOverlay);
            mapView.invalidate();
        }
    ;

    };

        private InputStream getConnection(String url) {
        InputStream is = null;
        try {
            URLConnection conn = new URL(url).openConnection();
            is = conn.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
    private final LocationListener locationListener = new LocationListener()   {

        public void onLocationChanged(Location location) {
            fromLon = location.getLongitude();
            fromLat = location.getLatitude();
        }

        public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
        }

        public void onProviderEnabled(String arg0) {
        }

        public void onProviderDisabled(String arg0) {
        }
    };
}

class MapOverlay extends com.google.android.maps.Overlay {

    Road mRoad;
    ArrayList<GeoPoint> mPoints;

    public MapOverlay(Road road, MapView mv) {
        mRoad = road;
        if (road.mRoute.length > 0) {
            mPoints = new ArrayList<GeoPoint>();
            for (int i = 0; i < road.mRoute.length; i++) {
                mPoints.add(new GeoPoint((int) (road.mRoute[i][1] * 1000000),
                        (int) (road.mRoute[i][0] * 1000000)));
            }
            int moveToLat = (mPoints.get(0).getLatitudeE6() + (mPoints.get(
                    mPoints.size() - 1).getLatitudeE6() - mPoints.get(0).getLatitudeE6()) / 2);
            int moveToLong = (mPoints.get(0).getLongitudeE6() + (mPoints.get(
                    mPoints.size() - 1).getLongitudeE6() - mPoints.get(0).getLongitudeE6()) / 2);
            GeoPoint moveTo = new GeoPoint(moveToLat, moveToLong);

            MapController mapController = mv.getController();
            mapController.animateTo(moveTo);
            mapController.setZoom(12);
        }
    }

    @Override
    public boolean draw(Canvas canvas, MapView mv, boolean shadow, long when) {
        super.draw(canvas, mv, shadow);
        drawPath(mv, canvas);
        return true;
    }

    public void drawPath(MapView mv, Canvas canvas) {
        int x1 = -1, y1 = -1, x2 = -1, y2 = -1;
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        for (int i = 0; i < mPoints.size(); i++) {
            Point point = new Point();
            mv.getProjection().toPixels(mPoints.get(i), point);
            x2 = point.x;
            y2 = point.y;
            if (i > 0) {
                canvas.drawLine(x1, y1, x2, y2, paint);
            }
            x1 = x2;
            y1 = y2;
        }
    }
}