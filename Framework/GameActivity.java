package org.Framework;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
    	AppManager.getInstance().setActivity(this);
        setContentView(new GameView(getBaseContext()));
   }
	
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		
		if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
		{
//			AppManager.getInstance().setSize(640, 960);
//			getHolder().setFixedSize(640,960);
		}
		else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
		{
//			AppManager.getInstance().setSize(960, 640);
//			getHolder().setFixedSize(960,640);
		}
	}

}