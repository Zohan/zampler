package djtools.sampler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaScannerConnection;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SamplerActivity extends Activity {
    /** Called when the activity is first created. */
	
	public RadioGroup group;
	public SoundPool pool;
	public AudioManager  mAudioManager;
	Sample [] sample = new Sample[16];
	TextView [] sampleText = new TextView[16];
	Button saveSamples;
	Button loadSamples;
	static int version;
	int startX = 0;
	int startY = 15;
	int id;
	int buttonID;
	int buttonWidth;
	int buttonHeight;
	boolean isLowDPI;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        version = Integer.parseInt(Build.VERSION.RELEASE.substring(0, 1));
        if(version > 2)
        {
        	setContentView(R.layout.main);
        	isLowDPI = false;
        }
        else
        {
        	setContentView(R.layout.main_lowdpi);
        	isLowDPI = true;
        }        
        
        Display display = getWindowManager().getDefaultDisplay(); 
    	int width = display.getWidth();
    	int height = display.getHeight();
    	
    	buttonWidth = (width-100)/16;
    	buttonHeight = (height-10)/16;
    	  
    	group = (RadioGroup) findViewById(R.id.radioGroup1);
        pool = new SoundPool(16, AudioManager.STREAM_MUSIC, 0);
        mAudioManager = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
        
        loadSamples = (Button) findViewById(R.id.button17);
        loadSamples.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loadSampleSet();
            }
        });
        saveSamples = (Button) findViewById(R.id.button18);
        saveSamples.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                saveSampleSet();
            }
        });
        
        initializeButtonArray();
    }
    
    //HOOK EVERYTHING UP
    public void initializeButtonArray()
    {

    	int soundId;
    	ImageView imageButton;
    	
    	imageButton = (ImageView) findViewById(R.id.imageView1);
    	soundId = pool.load(this, R.drawable.yeayuh, 1);
    	sample[0] = new Sample("None", soundId, imageButton);
    	sampleText[0] = (TextView) findViewById(R.id.sampleText1);
    	
    	imageButton = (ImageView) findViewById(R.id.imageView2);
    	soundId = pool.load(this, R.drawable.protosssound1, 1);
    	sample[1] = new Sample("None", soundId, imageButton);
    	sampleText[1] = (TextView) findViewById(R.id.sampleText2);
    	
    	imageButton = (ImageView) findViewById(R.id.imageView3);
    	soundId = pool.load(this, R.drawable.protosssound2, 1);
    	sample[2] = new Sample("None", soundId, imageButton);
    	sampleText[2] = (TextView) findViewById(R.id.sampleText3);
    	
    	imageButton = (ImageView) findViewById(R.id.imageView4);
    	soundId = pool.load(this, R.drawable.protosssound3, 1);
    	sample[3] = new Sample("None", soundId, imageButton);
    	sampleText[3] = (TextView) findViewById(R.id.sampleText4);
    
        imageButton = (ImageView) findViewById(R.id.imageView5);
        sample[4] = new Sample("None", soundId, imageButton);
        sampleText[4] = (TextView) findViewById(R.id.sampleText5);
        
        imageButton = (ImageView) findViewById(R.id.imageView6);
        sample[5] = new Sample("None", soundId, imageButton);
        sampleText[5] = (TextView) findViewById(R.id.sampleText6);
        
        imageButton = (ImageView) findViewById(R.id.imageView7);
        sample[6] = new Sample("None", soundId, imageButton);
        sampleText[6] = (TextView) findViewById(R.id.sampleText7);
        
        imageButton = (ImageView) findViewById(R.id.imageView8);
        sample[7] = new Sample("None", soundId, imageButton);
        sampleText[7] = (TextView) findViewById(R.id.sampleText8);
        
        imageButton = (ImageView) findViewById(R.id.imageView9);
        sample[8] = new Sample("None", soundId, imageButton);
        sampleText[8] = (TextView) findViewById(R.id.sampleText9);
        
        imageButton = (ImageView) findViewById(R.id.imageView10);
        sample[9] = new Sample("None", soundId, imageButton);
        sampleText[9] = (TextView) findViewById(R.id.sampleText10);
        
        imageButton = (ImageView) findViewById(R.id.imageView11);
        sample[10] = new Sample("None", soundId, imageButton);
        sampleText[10] = (TextView) findViewById(R.id.sampleText11);
        
        imageButton = (ImageView) findViewById(R.id.imageView12);
        sample[11] = new Sample("None", soundId, imageButton);
        sampleText[11] = (TextView) findViewById(R.id.sampleText12);
        
        imageButton = (ImageView) findViewById(R.id.imageView13);
        sample[12] = new Sample("None", soundId, imageButton);
        sampleText[12] = (TextView) findViewById(R.id.sampleText13);
        
        imageButton = (ImageView) findViewById(R.id.imageView14);
        sample[13] = new Sample("None", soundId, imageButton);
        sampleText[13] = (TextView) findViewById(R.id.sampleText14);
        
        imageButton = (ImageView) findViewById(R.id.imageView15);
        sample[14] = new Sample("None", soundId, imageButton);
        sampleText[14] = (TextView) findViewById(R.id.sampleText15);
        
        imageButton = (ImageView) findViewById(R.id.imageView16);
        sample[15] = new Sample("None", soundId, imageButton);
        sampleText[15] = (TextView) findViewById(R.id.sampleText16);
        
    }
    
    public void playSample(int sampleNum)
    {
    	float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    	streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    	
    	int streamID = 0;
    	streamID = sample[sampleNum].getStreamID();
    	pool.stop(streamID);
    	streamID = pool.play(sample[sampleNum].getSoundID(), streamVolume, streamVolume, 1, 0, 1f);
    	sample[sampleNum].setStreamID(streamID);
		sample[sampleNum].setButtonColor(1);
		sample[sampleNum].setLoop(false);
    }
    
    public void loopSample(int sampleNum)
    {
    	float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    	streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    	
    	if(!sample[sampleNum].isLooping())
    	{
    		int streamID = pool.play(sample[sampleNum].getSoundID(), streamVolume, streamVolume, 1, -1, 1f);
    		sample[sampleNum].setStreamID(streamID);
    		sample[sampleNum].setButtonColor(1);
    		sample[sampleNum].setLoop(true);
    	}
    	else
    	{
    		int streamID = sample[sampleNum].getStreamID();
    		pool.stop(streamID);
    		sample[sampleNum].setButtonColor(0);
    		sample[sampleNum].setLoop(false);
    	}
    		
    }
    
    public void recordSample(int sampleNum)
    {
    	
    }
    
    public void setSample(int sampleNum, String file)
    {	
    	int soundId;
    	ImageView imageButton;
    	imageButton = sample[sampleNum].getImageButton();
    	soundId = pool.load(file, 1);
    	sample[sampleNum] = new Sample(file, soundId, imageButton);
    }
    
    public void loadSample(int sampleNum)
    {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	File currentDirectory;
    	if(version > 2)
    	{
    		currentDirectory = new File("/storage/sdcard0/Music/zampler");
    		
    	}
    	else
    	{
    		currentDirectory = new File("/mnt");
    	}
    	//File currentDirectory = new File("/sdcard/Music");
    	CharSequence[] items = currentDirectory.list();
    	buttonID = sampleNum;
    	builder.setItems(items, new DialogInterface.OnClickListener() {
    		int sampleNum = buttonID;	
    	    public void onClick(DialogInterface dialog, int item) {
    	    	buttonID = item;
    	    	File currentDirectory;
    	    	if(version > 2)
    	    	{
    	    		currentDirectory = new File("/storage/sdcard0/Music/zampler");
    	    		
    	    	}
    	    	else
    	    	{
    	    		currentDirectory = new File("/mnt/Music/Zampler");
    	    	}
    	    	CharSequence[] items = currentDirectory.list();
    	    	setSample(sampleNum, currentDirectory.toString() + "/" + items[buttonID]);
    	    	sampleText[sampleNum].setText(items[buttonID]);
    	    	
    	    }
    	});
    	AlertDialog alert = builder.create();
    	alert.show();
    }
    
    public int getButton(int x, int y)
    {
    	if(x>865)
    		return 20;
    	int guessX = x/214;
    	int guessY = y-60;
    	if(isLowDPI)
    	{
    		guessY = y/200;
    	}
    	else guessY = Math.abs(guessY/165);
    	int result = guessY*4+guessX;
    	if(result > 15 || guessX > 864)
    		return -1;
    	else return guessY*4+guessX;
    }
    
    public void loadSampleSet()
    {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	File currentDirectory;
    	if(version > 2)
    	{
    		currentDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "/Sets/");
    		
    	}
    	else
    	{
    		currentDirectory = new File("/");
    	}
    	//File currentDirectory = new File("/sdcard/Music");
    	CharSequence[] items = currentDirectory.list();
    	builder.setItems(items, new DialogInterface.OnClickListener() {	
    	    public void onClick(DialogInterface dialog, int item) {
    	    	buttonID = item;
    	    	File currentDirectory;
    	    	if(version > 2)
    	    	{
    	    		currentDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "/Sets/");
    	    		
    	    	}
    	    	else
    	    	{
    	    		currentDirectory = new File("/mnt/Music/Zampler/Sets");
    	    	}
    	    	CharSequence[] items = currentDirectory.list();    	    	
    	    	try 
    	    	{
    				File myFile = new File(currentDirectory.toString() + "/" + items[buttonID]);
    				FileInputStream fIn = new FileInputStream(myFile);
    				BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
    				String aDataRow = "";
    				for(int i = 0; i<16; i++)
    				{
    					aDataRow = myReader.readLine();
    					setSample(i, aDataRow.toString());
    					aDataRow = aDataRow.substring(aDataRow.lastIndexOf("/")+1);
    					sampleText[i].setText(aDataRow);
    				
    				}
    				myReader.close();
    			} 
    	    	catch (Exception e) 
    			{
    			}
    	    	
    	    }
    	});
    	AlertDialog alert = builder.create();
    	alert.show();
    }
    
    //Gross!
    public Context contextForMedia = this;
    
    public void saveSampleSet()
    {
    	LayoutInflater infalter = LayoutInflater.from(this); 
        final View textEntryView = infalter.inflate(R.layout.alert_dialog_text_entry, null);  
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);  
        dialogBuilder.setTitle("Filename");  
        dialogBuilder.setView(textEntryView);  
            dialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener(){  
  
            public void onClick(DialogInterface dialog, int which)   
            {  
            	EditText tempText = (EditText) textEntryView.findViewById(R.id.ALERT_DIALOG_EDIT_TEXT);
                CharSequence text = tempText.getText();
                
                try
                {
                    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
                    File myFile = new File(path, "/Sets/" + text.toString());
                	//myFile.mkdirs();
                	FileWriter writer = new FileWriter(myFile);
                	BufferedWriter out = new BufferedWriter(writer);
                    
	    			if(myFile.canWrite())
	    			{
	    				for(int i = 0; i<16; i++)
	    				{
	    					out.write(sample[i].fileName + "\n");
	    					//out.write("Hello" + "\n");
	    					//writer.append(sample[i].fileName);
	            			//writer.flush();
	    				}
	    			}
	    			out.close();
	    			MediaScannerConnection.scanFile(contextForMedia,
	                        new String[] { myFile.toString() }, null,
	                        new MediaScannerConnection.OnScanCompletedListener() {
	                    public void onScanCompleted(String path, Uri uri) {
	                        Log.i("ExternalStorage", "Scanned " + path + ":");
	                        Log.i("ExternalStorage", "-> uri=" + uri);
	                    }
	                });
    			}
                catch (Exception e) 
                {
                	Toast.makeText(getBaseContext(), e.getMessage(),
        					Toast.LENGTH_SHORT).show();
                }
             }
              
        });
        dialogBuilder.setNegativeButton("Cancel", null);  
        dialogBuilder.create();
        dialogBuilder.show();
    }
    
    @Override
    public boolean onTouchEvent (MotionEvent event) {
    	int action = event.getActionMasked();
    	TextView theView = (TextView) findViewById(R.id.textView1);
    	theView.setText(event.getX() + " " + event.getY());
    	if(action == MotionEvent.ACTION_POINTER_1_DOWN
    			|| action == MotionEvent.ACTION_DOWN)
    	{
	    	int buttonId = group.getCheckedRadioButtonId();
	    	RadioButton selectedButton = (RadioButton) findViewById(buttonId);
	    	String currentMode = selectedButton.getText().toString();
	    	int count=event.getPointerCount();
	    	int ex = -1;
	    	int ey = -1;
	    	if(currentMode.equals("Play"))
	    	{
	    		for(int i = 0; i<count; i++)
	    		{
	        		ex=(int)event.getX(i);
	    			ey=(int)event.getY(i);
	    			int theButton = getButton(ex, ey);
	    			if(theButton >= 0 && theButton < 16)
	    			{
	    				playSample(theButton);
	    			}
	    		}
	    	}
	    	else if(currentMode.equals("Loop"))
	    	{
	    		for(int i = 0; i<count; i++)
	    		{
	        		ex=(int)event.getX(i);
	    			ey=(int)event.getY(i);
	    			int theButton = getButton(ex, ey);
	    			if(theButton >= 0 && theButton < 16)
	    			{
	    				loopSample(theButton);
	    			}
	    		}
	    	}
	    	else if(currentMode.equals("Record"))
	    	{
	    		for(int i = 0; i<count; i++)
	    		{
	        		ex=(int)event.getX(i);
	    			ey=(int)event.getY(i);
	    			int theButton = getButton(ex, ey);
	    			if(theButton >= 0 && theButton < 16)
	    			{
	    				playSample(theButton);
	    			}
	    		}
	    	}
	    	else if(currentMode.equals("Load Samples"))
	    	{
	    		for(int i = 0; i<count; i++)
	    		{
	        		ex=(int)event.getX(i);
	    			ey=(int)event.getY(i);
	    			int theButton = getButton(ex, ey);
	    			if(theButton >= 0 && theButton < 16)
	    			{
	    				loadSample(theButton);
	    			}
	    		}
	    	}
    	}
    	
    	if(action == MotionEvent.ACTION_POINTER_1_UP
    			|| action == MotionEvent.ACTION_UP)
    	{
    		int count=event.getPointerCount();
	    	int ex = -1;
	    	int ey = -1;
	    	for(int i = 0; i<count; i++)
    		{
        		ex=(int)event.getX(i);
    			ey=(int)event.getY(i);
    			int theButton = getButton(ex, ey);
    			if(theButton >= 0 && theButton < 16 && !sample[theButton].isLooping())
    			{
    				sample[theButton].setButtonColor(0);
    			}
    		}
    	}
        return true;
    }
}