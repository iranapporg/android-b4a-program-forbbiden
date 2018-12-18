package collection.app.lock;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class actsetting extends Activity implements B4AActivity{
	public static actsetting mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "collection.app.lock", "collection.app.lock.actsetting");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (actsetting).");
				p.finish();
			}
		}
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
		BA.handler.postDelayed(new WaitForLayout(), 5);

	}
	private static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "collection.app.lock", "collection.app.lock.actsetting");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "collection.app.lock.actsetting", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (actsetting) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (actsetting) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEvent(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return actsetting.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (actsetting) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (actsetting) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}

public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.preferenceactivity.PreferenceManager _pre = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtpass = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rbmessage = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rbhome = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgservice = null;
public collection.app.lock.mydb _db1 = null;
public collection.app.lock.main _main = null;
public collection.app.lock.actsplash _actsplash = null;
public collection.app.lock.actlist _actlist = null;
public collection.app.lock.actcontact _actcontact = null;
public collection.app.lock.actlock _actlock = null;
public collection.app.lock.handlepassword _handlepassword = null;
public collection.app.lock.force_close _force_close = null;
public collection.app.lock.handlesmspassword _handlesmspassword = null;
public collection.app.lock.mylibrary _mylibrary = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 19;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 20;BA.debugLine="Activity.LoadLayout(\"frmsetting\")";
mostCurrent._activity.LoadLayout("frmsetting",mostCurrent.activityBA);
 //BA.debugLineNum = 21;BA.debugLine="txtpass.Color = Colors.Transparent";
mostCurrent._txtpass.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 22;BA.debugLine="txtpass.Text = pre.GetString(\"password\")";
mostCurrent._txtpass.setText((Object)(mostCurrent._pre.GetString("password")));
 //BA.debugLineNum = 24;BA.debugLine="db1.Initialize";
mostCurrent._db1._initialize(processBA);
 //BA.debugLineNum = 26;BA.debugLine="If pre.GetBoolean(\"lock_alert\") = True Then";
if (mostCurrent._pre.GetBoolean("lock_alert")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 27;BA.debugLine="rbmessage.Checked = True";
mostCurrent._rbmessage.setChecked(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 30;BA.debugLine="If IsPaused(force_close) = True Then";
if (anywheresoftware.b4a.keywords.Common.IsPaused(mostCurrent.activityBA,(Object)(mostCurrent._force_close.getObject()))==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 31;BA.debugLine="imgservice.SetBackgroundImage(LoadBitmap(File.Di";
mostCurrent._imgservice.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"toggle-off.png").getObject()));
 }else {
 //BA.debugLineNum = 33;BA.debugLine="imgservice.SetBackgroundImage(LoadBitmap(File.Di";
mostCurrent._imgservice.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"toggle-on.png").getObject()));
 };
 //BA.debugLineNum = 36;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 42;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 43;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 44;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 38;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 40;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 10;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 11;BA.debugLine="Private pre As PreferenceManager";
mostCurrent._pre = new anywheresoftware.b4a.objects.preferenceactivity.PreferenceManager();
 //BA.debugLineNum = 12;BA.debugLine="Private txtpass As EditText";
mostCurrent._txtpass = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 13;BA.debugLine="Private rbmessage As RadioButton";
mostCurrent._rbmessage = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Private rbhome As RadioButton";
mostCurrent._rbhome = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Private imgservice As ImageView";
mostCurrent._imgservice = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private db1 As myDB";
mostCurrent._db1 = new collection.app.lock.mydb();
 //BA.debugLineNum = 17;BA.debugLine="End Sub";
return "";
}
public static String  _imgservice_click() throws Exception{
 //BA.debugLineNum = 81;BA.debugLine="Sub imgservice_Click";
 //BA.debugLineNum = 82;BA.debugLine="If IsPaused(force_close) = True Then";
if (anywheresoftware.b4a.keywords.Common.IsPaused(mostCurrent.activityBA,(Object)(mostCurrent._force_close.getObject()))==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 83;BA.debugLine="imgservice.SetBackgroundImage(LoadBitmap(File.Di";
mostCurrent._imgservice.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"toggle-on.png").getObject()));
 //BA.debugLineNum = 84;BA.debugLine="pre.SetBoolean(\"service\",True)";
mostCurrent._pre.SetBoolean("service",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 85;BA.debugLine="StartService(force_close)";
anywheresoftware.b4a.keywords.Common.StartService(mostCurrent.activityBA,(Object)(mostCurrent._force_close.getObject()));
 //BA.debugLineNum = 86;BA.debugLine="ToastMessageShow(\"برنامه فعال شد\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("برنامه فعال شد",anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 88;BA.debugLine="imgservice.SetBackgroundImage(LoadBitmap(File.Di";
mostCurrent._imgservice.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"toggle-off.png").getObject()));
 //BA.debugLineNum = 89;BA.debugLine="pre.SetBoolean(\"service\",False)";
mostCurrent._pre.SetBoolean("service",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 90;BA.debugLine="StopService(force_close)";
anywheresoftware.b4a.keywords.Common.StopService(mostCurrent.activityBA,(Object)(mostCurrent._force_close.getObject()));
 //BA.debugLineNum = 91;BA.debugLine="CancelScheduledService(force_close)";
anywheresoftware.b4a.keywords.Common.CancelScheduledService(mostCurrent.activityBA,(Object)(mostCurrent._force_close.getObject()));
 //BA.debugLineNum = 92;BA.debugLine="ToastMessageShow(\"برنامه غیر فعال شد\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("برنامه غیر فعال شد",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 94;BA.debugLine="End Sub";
return "";
}
public static String  _lblhome_click() throws Exception{
 //BA.debugLineNum = 61;BA.debugLine="Sub lblhome_Click";
 //BA.debugLineNum = 63;BA.debugLine="End Sub";
return "";
}
public static String  _lblmessage_click() throws Exception{
 //BA.debugLineNum = 65;BA.debugLine="Sub lblmessage_Click";
 //BA.debugLineNum = 67;BA.debugLine="End Sub";
return "";
}
public static String  _lblservice_click() throws Exception{
 //BA.debugLineNum = 77;BA.debugLine="Sub lblservice_Click";
 //BA.debugLineNum = 79;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="End Sub";
return "";
}
public static String  _rbhome_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 69;BA.debugLine="Sub rbhome_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 70;BA.debugLine="pre.SetBoolean(\"lock_alert\",False)";
mostCurrent._pre.SetBoolean("lock_alert",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 71;BA.debugLine="End Sub";
return "";
}
public static String  _rbmessage_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 73;BA.debugLine="Sub rbmessage_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 74;BA.debugLine="pre.SetBoolean(\"lock_alert\",True)";
mostCurrent._pre.SetBoolean("lock_alert",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 75;BA.debugLine="End Sub";
return "";
}
public static String  _tnsavepass_click() throws Exception{
 //BA.debugLineNum = 46;BA.debugLine="Sub tnsavepass_Click";
 //BA.debugLineNum = 47;BA.debugLine="If txtpass.Text.Length < 2 Then";
if (mostCurrent._txtpass.getText().length()<2) { 
 //BA.debugLineNum = 48;BA.debugLine="ToastMessageShow(\"خطا : رمز عبور حداقل باید 2 کا";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("خطا : رمز عبور حداقل باید 2 کاراکتر باشد",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 49;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 52;BA.debugLine="If db1.PassExist(txtpass.Text) > 0 Then";
if (mostCurrent._db1._passexist(mostCurrent._txtpass.getText())>0) { 
 //BA.debugLineNum = 53;BA.debugLine="Msgbox(\"این رمز برای برنامه قفل شده انتخاب شده ا";
anywheresoftware.b4a.keywords.Common.Msgbox("این رمز برای برنامه قفل شده انتخاب شده است"+anywheresoftware.b4a.keywords.Common.CRLF+"لطفا رمز دیگری انتخاب کنید","خطا",mostCurrent.activityBA);
 //BA.debugLineNum = 54;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 57;BA.debugLine="pre.SetString(\"password\",txtpass.Text)";
mostCurrent._pre.SetString("password",mostCurrent._txtpass.getText());
 //BA.debugLineNum = 58;BA.debugLine="ToastMessageShow(\"رمز عبور با موفقیت ثبت شد\",Fals";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("رمز عبور با موفقیت ثبت شد",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 59;BA.debugLine="End Sub";
return "";
}
}
