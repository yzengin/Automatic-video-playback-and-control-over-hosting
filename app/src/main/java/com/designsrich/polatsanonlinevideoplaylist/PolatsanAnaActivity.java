package com.designsrich.polatsanonlinevideoplaylist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Yakup ZENGİN - yakup [at] designsrich [dot] com
 * @version 0.0.3
 * @since 0.0.3
 *
 */

public class PolatsanAnaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ProgressDialog pDialog;
    VideoView videoView;
    ArrayList<String> arrayList = new ArrayList<>( Arrays.asList( "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4" ) );
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_polatsan );

        this.getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );

        videoView = findViewById( R.id.videoView );
        final MediaController mediacontroller = new MediaController( this );
        mediacontroller.setAnchorView( videoView );

        // Create a progressbar
        pDialog = new ProgressDialog( PolatsanAnaActivity.this );

        // Set progressbar title
        pDialog.setTitle( "Polatsan Video Oynatıcı" );

        // Set progressbar message
        pDialog.setMessage( "Video Yükleniyor, İyi Seyirler..." );
        pDialog.setIndeterminate( false );
        pDialog.setCancelable( false );
        // Show progressbar
        pDialog.show();
        Handler handler = new Handler();
        handler.postDelayed( new Runnable() {
            public void run() {
                pDialog.dismiss();
            }
        }, 4000 );
        videoView.setVideoURI( Uri.parse( arrayList.get( index ) ) );
        videoView.setMediaController( mediacontroller );
        videoView.requestFocus();
        videoView.start();

        videoView.setOnPreparedListener( new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnVideoSizeChangedListener( new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        videoView.setMediaController( mediacontroller );
                        mediacontroller.setAnchorView( videoView );

                    }
                } );
            }
        } );

        videoView.setOnCompletionListener( new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText( getApplicationContext(), "Video Bitti.", Toast.LENGTH_SHORT ).show();
                if (index++ == arrayList.size()) {
                    Toast.makeText( getApplicationContext(), "Biraz Bekleyin, Video Başlıyor...", Toast.LENGTH_LONG ).show();
                    index = 0;
                    mp.release();
                    Toast.makeText( getApplicationContext(), "Video Bitti.", Toast.LENGTH_SHORT ).show();
                } else if (index == 1) {
                    videoView.setVideoURI( Uri.parse( arrayList.get( index ) ) );
                    Toast.makeText( getApplicationContext(), "Biraz Bekleyin, Video Başlıyor...", Toast.LENGTH_LONG ).show();
                    videoView.start();
                } else {
                    videoView.setVideoURI( Uri.parse( arrayList.get( 0 ) ) );
                    Toast.makeText( getApplicationContext(), "Biraz Bekleyin, Video Başlıyor...", Toast.LENGTH_LONG ).show();
                    videoView.start();
                    index = 0;
                }


            }
        } );

        videoView.setOnErrorListener( new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.d( "API123", "What " + what + " extra " + extra );
                return false;
            }
        } );


        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );


        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();

        NavigationView navigationView = findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.belediye) {
            Toast.makeText( PolatsanAnaActivity.this, "Lütfen Bekleyin. Belediye Sayfasına Yönlendiriliyorsunuz...", Toast.LENGTH_SHORT ).show();

            Intent intent = new Intent( PolatsanAnaActivity.this, MainActivity.class );
            startActivity( intent );

            // Handle the camera action
        }

        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }
}
