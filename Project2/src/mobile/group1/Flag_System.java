package mobile.group1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Flag_System extends Activity {
	
	TextView flagTV;
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.flagpicture);
	    
	    flagTV = (TextView) findViewById(R.id.flagTV);
	
	    // TODO Auto-generated method stub
	}
	public void flagBT(View v){
		flagTV.setText("This Picture is Flag");
		flagTV.setTextColor(0xffff0000);
	}

}
