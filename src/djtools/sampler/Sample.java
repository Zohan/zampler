package djtools.sampler;

import android.widget.ImageView;

public class Sample {
	
	private int soundID;
	private int streamID;
	boolean isLooping;
	String fileName;
	ImageView imageButton;
	
	public Sample(String mFileName, int sound, ImageView theButton)
	{
		soundID = sound;
		imageButton = theButton;
		isLooping = false;
		fileName = mFileName;
		streamID = 0;
	}
	
	public int getSoundID()
	{
		return this.soundID;
	}
	
	public int getStreamID()
	{
		return this.streamID;
	}
	
	public ImageView getImageButton()
	{
		return this.imageButton;
	}
	
	public boolean isLooping()
	{
		return this.isLooping;
	}
	
	public void setSoundID(int id)
	{
		this.soundID = id;	
	}
	
	public void setStreamID(int id)
	{
		this.streamID = id;
	}
	
	public void setLoop(boolean value)
	{
		this.isLooping = value;
	}
	
	public void setButtonColor(int value)
	{
		if(value == 0)
		{
			if(SamplerActivity.version > 2)
			{
				imageButton.setImageResource(R.drawable.androidbutton);
			}
			else imageButton.setImageResource(R.drawable.androidbuttonsm);
		}
		else
		{
			if(SamplerActivity.version > 2)
			{
				imageButton.setImageResource(R.drawable.androidbutton2);
			}
			else imageButton.setImageResource(R.drawable.androidbutton2sm);
		}
	}

}
