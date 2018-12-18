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

public class main extends Activity implements B4AActivity{
	public static main mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "collection.app.lock", "collection.app.lock.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
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
		activityBA = new BA(this, layout, processBA, "collection.app.lock", "collection.app.lock.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "collection.app.lock.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
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
		return main.class;
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
        BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (main) Resume **");
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
public static anywheresoftware.b4a.objects.preferenceactivity.PreferenceManager _pre = null;
public collection.app.lock.mydb _db1 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtval = null;
public collection.app.lock.actsplash _actsplash = null;
public collection.app.lock.actlist _actlist = null;
public collection.app.lock.actsetting _actsetting = null;
public collection.app.lock.actcontact _actcontact = null;
public collection.app.lock.actlock _actlock = null;
public collection.app.lock.handlepassword _handlepassword = null;
public collection.app.lock.force_close _force_close = null;
public collection.app.lock.handlesmspassword _handlesmspassword = null;
public collection.app.lock.mylibrary _mylibrary = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (actsplash.mostCurrent != null);
vis = vis | (actlist.mostCurrent != null);
vis = vis | (actsetting.mostCurrent != null);
vis = vis | (actcontact.mostCurrent != null);
vis = vis | (actlock.mostCurrent != null);
return vis;}
public static String  _activity_create(boolean _firsttime) throws Exception{
int _count = 0;
 //BA.debugLineNum = 24;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 25;BA.debugLine="Activity.LoadLayout(\"frmkeypad\")";
mostCurrent._activity.LoadLayout("frmkeypad",mostCurrent.activityBA);
 //BA.debugLineNum = 26;BA.debugLine="db1.Initialize";
mostCurrent._db1._initialize(processBA);
 //BA.debugLineNum = 28;BA.debugLine="StartService(handlePassword)";
anywheresoftware.b4a.keywords.Common.StartService(mostCurrent.activityBA,(Object)(mostCurrent._handlepassword.getObject()));
 //BA.debugLineNum = 29;BA.debugLine="StartService(handleSMSPassword)";
anywheresoftware.b4a.keywords.Common.StartService(mostCurrent.activityBA,(Object)(mostCurrent._handlesmspassword.getObject()));
 //BA.debugLineNum = 31;BA.debugLine="txtval.Color = Colors.Transparent";
mostCurrent._txtval.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 33;BA.debugLine="If pre.GetBoolean(\"first\") = False Then";
if (_pre.GetBoolean("first")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 34;BA.debugLine="StartService(force_close)";
anywheresoftware.b4a.keywords.Common.StartService(mostCurrent.activityBA,(Object)(mostCurrent._force_close.getObject()));
 //BA.debugLineNum = 35;BA.debugLine="pre.SetBoolean(\"first\",True)";
_pre.SetBoolean("first",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 36;BA.debugLine="pre.SetBoolean(\"service\",True)";
_pre.SetBoolean("service",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 37;BA.debugLine="pre.SetString(\"password\",\"00\")";
_pre.SetString("password","00");
 //BA.debugLineNum = 38;BA.debugLine="pre.setBoolean(\"lock_alert\",True)";
_pre.SetBoolean("lock_alert",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 39;BA.debugLine="ProgressDialogShow(\"بار گذاری اولیه...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"بار گذاری اولیه...");
 //BA.debugLineNum = 40;BA.debugLine="MyLibrary.LoadPackageList";
mostCurrent._mylibrary._loadpackagelist(mostCurrent.activityBA);
 //BA.debugLineNum = 41;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 }else {
 //BA.debugLineNum = 43;BA.debugLine="Try";
try { //BA.debugLineNum = 45;BA.debugLine="Dim count As Int";
_count = 0;
 //BA.debugLineNum = 46;BA.debugLine="count = File.ReadString(File.DirInternal,\"count";
_count = (int)(Double.parseDouble(anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"countPK")));
 //BA.debugLineNum = 48;BA.debugLine="If count <> MyLibrary.getPackageListCount Then";
if (_count!=mostCurrent._mylibrary._getpackagelistcount(mostCurrent.activityBA)) { 
 //BA.debugLineNum = 49;BA.debugLine="ProgressDialogShow(\"بارگذاری جدید برنامه ها...";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"بارگذاری جدید برنامه ها...");
 //BA.debugLineNum = 50;BA.debugLine="MyLibrary.LoadPackageList";
mostCurrent._mylibrary._loadpackagelist(mostCurrent.activityBA);
 //BA.debugLineNum = 51;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 };
 } 
       catch (Exception e32) {
			processBA.setLastException(e32); };
 };
 //BA.debugLineNum = 59;BA.debugLine="If pre.GetBoolean(\"service\") = True Then";
if (_pre.GetBoolean("service")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 60;BA.debugLine="StartService(force_close)";
anywheresoftware.b4a.keywords.Common.StartService(mostCurrent.activityBA,(Object)(mostCurrent._force_close.getObject()));
 };
 //BA.debugLineNum = 63;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 73;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 74;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 75;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 69;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 70;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 71;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 65;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 67;BA.debugLine="End Sub";
return "";
}
public static String  _btnback_click() throws Exception{
 //BA.debugLineNum = 113;BA.debugLine="Sub btnback_Click";
 //BA.debugLineNum = 114;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 115;BA.debugLine="End Sub";
return "";
}
public static String  _btndelete_click() throws Exception{
 //BA.debugLineNum = 85;BA.debugLine="Sub btndelete_Click";
 //BA.debugLineNum = 86;BA.debugLine="Try";
try { //BA.debugLineNum = 87;BA.debugLine="txtval.Text = txtval.Text.SubString2(0,txtval.Te";
mostCurrent._txtval.setText((Object)(mostCurrent._txtval.getText().substring((int) (0),(int) (mostCurrent._txtval.getText().length()-1))));
 } 
       catch (Exception e56) {
			processBA.setLastException(e56); };
 //BA.debugLineNum = 90;BA.debugLine="End Sub";
return "";
}
public static String  _btnlogin_click() throws Exception{
 //BA.debugLineNum = 92;BA.debugLine="Sub btnlogin_Click";
 //BA.debugLineNum = 94;BA.debugLine="If txtval.Text.Length = 0 Then";
if (mostCurrent._txtval.getText().length()==0) { 
 //BA.debugLineNum = 95;BA.debugLine="ToastMessageShow(\"خطا : رمز عبور به برنامه را وا";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("خطا : رمز عبور به برنامه را وارد کنید",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 96;BA.debugLine="txtval.RequestFocus";
mostCurrent._txtval.RequestFocus();
 //BA.debugLineNum = 97;BA.debugLine="txtval.Text = \"\"";
mostCurrent._txtval.setText((Object)(""));
 //BA.debugLineNum = 98;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 101;BA.debugLine="If pre.GetString(\"password\") = txtval.Text Then";
if ((_pre.GetString("password")).equals(mostCurrent._txtval.getText())) { 
 //BA.debugLineNum = 102;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 103;BA.debugLine="StartActivity(actSplash)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._actsplash.getObject()));
 }else {
 //BA.debugLineNum = 105;BA.debugLine="ToastMessageShow(\"خطا : رمز عبور اشتباه است.دوبا";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("خطا : رمز عبور اشتباه است.دوباره تلاش کنید",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 106;BA.debugLine="txtval.RequestFocus";
mostCurrent._txtval.RequestFocus();
 //BA.debugLineNum = 107;BA.debugLine="txtval.Text = \"\"";
mostCurrent._txtval.setText((Object)(""));
 };
 //BA.debugLineNum = 110;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
actsplash._process_globals();
actlist._process_globals();
actsetting._process_globals();
actcontact._process_globals();
actlock._process_globals();
handlepassword._process_globals();
force_close._process_globals();
handlesmspassword._process_globals();
mylibrary._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _globals() throws Exception{
 //BA.debugLineNum = 19;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 20;BA.debugLine="Private db1 As myDB";
mostCurrent._db1 = new collection.app.lock.mydb();
 //BA.debugLineNum = 21;BA.debugLine="Private txtval As EditText";
mostCurrent._txtval = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 22;BA.debugLine="End Sub";
return "";
}
public static String  _numbers_click() throws Exception{
anywheresoftware.b4a.objects.ImageViewWrapper _img1 = null;
 //BA.debugLineNum = 78;BA.debugLine="Sub numbers_Click";
 //BA.debugLineNum = 79;BA.debugLine="Dim img1 As ImageView";
_img1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 80;BA.debugLine="img1 = Sender";
_img1.setObject((android.widget.ImageView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 81;BA.debugLine="txtval.Text = txtval.Text & img1.Tag";
mostCurrent._txtval.setText((Object)(mostCurrent._txtval.getText()+BA.ObjectToString(_img1.getTag())));
 //BA.debugLineNum = 82;BA.debugLine="MyLibrary.animationView(img1,False,700)";
mostCurrent._mylibrary._animationview(mostCurrent.activityBA,(anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(_img1.getObject())),anywheresoftware.b4a.keywords.Common.False,(int) (700));
 //BA.debugLineNum = 83;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 15;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 16;BA.debugLine="Dim pre As PreferenceManager";
_pre = new anywheresoftware.b4a.objects.preferenceactivity.PreferenceManager();
 //BA.debugLineNum = 17;BA.debugLine="End Sub";
return "";
}
}
