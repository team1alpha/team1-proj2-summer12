package mobile.group1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateNewActivity extends Activity {
	
	EditText username;
	EditText password;
	String Username;
	String Password;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_new_account);
		// TODO Auto-generated method stub
		
		username = (EditText) findViewById(R.id.createUsernameTextET);
		password = (EditText) findViewById(R.id.createPasswordTextET);
	}

	public void createAccountHandler(View v) {
		
		Username = username.getText().toString().trim();
		Password = password.getText().toString().trim();
		
		if (Username != null && Password != null){
			//Perform check if username is in used
			//insert into database
			Toast.makeText(getApplicationContext(), Username +" "+ Password, Toast.LENGTH_SHORT).show();
		
		}
	
	}

}
