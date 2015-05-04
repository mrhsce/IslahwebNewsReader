package mrhs.jamaapp.inr;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class SdCardHandler {
	private static final boolean LOCAL_SHOW_LOG = true;
	
	String sdcard = Environment.getExternalStorageDirectory().toString()+ File.separator ; 
	
	public boolean prepareSdCard(Context ctx){
		String[] typeList = new String[]{Commons.NEWS,Commons.INTERVIEW,Commons.ANNOUNCE,Commons.ARTICLE};
		if(android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)
    			&& !android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED_READ_ONLY)){
			File dir= new File (sdcard+Commons.APP_ROOT_DIRECTORY+File.separator);
    		if(dir.exists()){
    			for(String file:typeList){
    				dir = new File(sdcard+Commons.APP_ROOT_DIRECTORY+File.separator+file);
    				if(!dir.exists())
    					dir.mkdirs();
    			}
    		}
    		else{
    			dir.mkdirs();    			
    			for(String file:typeList){
    				dir = new File(sdcard+Commons.APP_ROOT_DIRECTORY+File.separator+file);
    				dir.mkdirs();
    			}
    			log("The directories has been created");
    		}
		}
		else{
			log("Unable to access the sdcard");
			Toast.makeText(ctx, "حافظه قابل دسترسی نیست در این حالت امکان ذخیره و نمایش تصویر وجود ندارد", Toast.
					LENGTH_LONG).show();
		}
		
		return false;
	}
	
	public boolean storeImage(String addr,Bitmap bitmap){
		if(!addr.contains("http://")){
			File file = new File(sdcard+addr) ;		
		    if (file.exists())
		    	file.delete();
		    try {
		    	FileOutputStream out = new FileOutputStream(file);
		    	bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
		    	out.flush();
		    	out.close();
		    	return true;
		    } catch (Exception e) {
		    	e.printStackTrace();
		    	return false;
		    }
		}
		return false;
	}
	
	public Bitmap getImage(String addr){
		if(!addr.contains("http://")){
			File file = new File(sdcard+addr) ;
		    if (file.exists()){
		    	return BitmapFactory.decodeFile(file.getAbsolutePath());
		    }	
		}
		return null;
	}
	
	public void deleteImage(String addr){
		File file = new File(sdcard+addr) ;
	    if (file.exists()){
	    	file.delete();
	    }
	}
	
	public Bitmap getImageCenterCropped(Bitmap bitmap){
		if(bitmap!=null){

			if(bitmap.getHeight()>=bitmap.getWidth()){
				bitmap = Bitmap.createBitmap(bitmap,0,(bitmap.getHeight()-bitmap.getWidth())/2,
						bitmap.getWidth(),bitmap.getWidth());
				return Bitmap.createScaledBitmap(bitmap,60,60,false);
			}
			else{
				bitmap = Bitmap.createBitmap(bitmap,(bitmap.getWidth()-bitmap.getHeight())/2,0,
						bitmap.getHeight(),bitmap.getHeight());
				return Bitmap.createScaledBitmap(bitmap,60,60,false);
			}
		}
		return null;
	}
	
	private void log(String message){
		if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
			Log.d(this.getClass().getSimpleName(),message);
	}

}
