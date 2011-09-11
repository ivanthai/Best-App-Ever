package net.twitmtjt.RestaRand;

import android.app.*;
import android.os.Bundle;

import android.widget.*;
import android.view.*;
import android.content.*;
import java.io.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;

public class RestaRandActivity extends Activity {
    /** Called when the activity is first created. */
    
	public static Scanner start;
	ArrayList<String> masterArray = new ArrayList<String>();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        try {
        	saveArray();
        	loadArray();
        }
        catch (IOException e) {
        	Toast.makeText(getBaseContext(), "UH OH", Toast.LENGTH_LONG).show();
        }
        
        //parse txt file of names and push into masterArray
        //masterArray = 
        
		Button goBtn = (Button) findViewById(R.id.go_button);
		goBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Random randomGenerator = new Random();
				int index = randomGenerator.nextInt(masterArray.size());
				Toast.makeText(getBaseContext(), masterArray.get(index).toString(),
						Toast.LENGTH_SHORT).show();
	        }
        });	
    }
	
	private void saveArray() throws IOException{
        try {
        FileOutputStream fOut = openFileOutput("restarand.txt", MODE_WORLD_READABLE);
        OutputStreamWriter osw = new OutputStreamWriter(fOut);
        osw.write("Gypsy's\nThai Basil\nCheeseboard\n");
			osw.flush();
            osw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private void loadArray() throws IOException {
		try {
			FileInputStream fIn = openFileInput("restarand.txt");
			InputStreamReader isr = new InputStreamReader(fIn);
			char[] inputBuffer = new char[100];
			String s = "";

			int charRead;
			while ((charRead = isr.read(inputBuffer)) > 0) {
				String readString = String
						.copyValueOf(inputBuffer, 0, charRead);
				s += readString;
				inputBuffer = new char[100];
			}
			String[] array = s.split("\n");
			for(int i = 0; i < array.length; i++) {
				masterArray.add(array[i].toString());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		CreateMenu(menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return MenuChoice(item);
	}
	
	protected void onActivityResult (int requestCode, int resultCode, Intent data) {
		//masterArray = data.getStringArrayListExtra("arrayListExtra");
		masterArray = data.getStringArrayListExtra("arrayListExtra");
	}
	
	private void CreateMenu(Menu menu) {
		MenuItem mnu1 = menu.add(0, 0, 0, "Add Restaurant Names"); {
			mnu1.setAlphabeticShortcut('a');
			mnu1.setIcon(R.drawable.icon);
		}
	}
    
    private boolean MenuChoice(MenuItem item) {
    	switch (item.getItemId()) {
    	case 0:
    	    Bundle b = new Bundle();
    	    b.putStringArrayList("arrayListExtra", masterArray);
    	    
    		Intent mintent = new Intent(this, Activity2.class);
    		mintent.putExtras(b);
    		startActivityForResult(mintent, 1);
    		return true;
    	}
    	return false;
    }
}