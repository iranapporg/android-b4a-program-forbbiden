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

public class actlist extends Activity implements B4AActivity{
	public static actlist mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "collection.app.lock", "collection.app.lock.actlist");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (actlist).");
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
		activityBA = new BA(this, layout, processBA, "collection.app.lock", "collection.app.lock.actlist");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "collection.app.lock.actlist", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (actlist) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (actlist) Resume **");
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
		return actlist.class;
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
        BA.LogInfo("** Activity (actlist) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (actlist) Resume **");
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
public static boolean _internal = false;
public anywheresoftware.b4a.objects.ScrollViewWrapper _sv1 = null;
public static boolean _blnfinishanimation = false;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitle = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgicon = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgmore = null;
public flm.b4a.animationplus.AnimationPlusWrapper _ani1 = null;
public flm.b4a.animationplus.AnimationPlusWrapper _ani2 = null;
public anywheresoftware.b4a.objects.PanelWrapper _currentclick = null;
public static int _currentoffset = 0;
public anywheresoftware.b4a.objects.PanelWrapper _pnlitem = null;
public anywheresoftware.b4a.objects.collections.Map _manimation = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpass = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrun = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnloverlay = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtval = null;
public collection.app.lock.mydb _db1 = null;
public anywheresoftware.b4a.objects.SlidingMenuWrapper _sm = null;
public anywheresoftware.b4a.phone.PackageManagerWrapper _pk1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imglock = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _currentlockimage = null;
public static String _currentpass = "";
public anywheresoftware.b4a.objects.ImageViewWrapper _imgicon_1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnback = null;
public anywheresoftware.b4a.objects.preferenceactivity.PreferenceManager _pre = null;
public static int _top = 0;
public anywheresoftware.b4a.objects.collections.List _apk = null;
public static int _offsetlist = 0;
public collection.app.lock.main _main = null;
public collection.app.lock.actsplash _actsplash = null;
public collection.app.lock.actsetting _actsetting = null;
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
public static String  _a1_animationend() throws Exception{
 //BA.debugLineNum = 200;BA.debugLine="Sub a1_AnimationEnd";
 //BA.debugLineNum = 201;BA.debugLine="If blnFinishAnimation = False Then";
if (_blnfinishanimation==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 202;BA.debugLine="currentClick.Left = currentClick.Left-150dip";
mostCurrent._currentclick.setLeft((int) (mostCurrent._currentclick.getLeft()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (150))));
 //BA.debugLineNum = 203;BA.debugLine="blnFinishAnimation = True";
_blnfinishanimation = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 204;BA.debugLine="mAnimation.Put(currentOffset,True)";
mostCurrent._manimation.Put((Object)(_currentoffset),(Object)(anywheresoftware.b4a.keywords.Common.True));
 };
 //BA.debugLineNum = 206;BA.debugLine="End Sub";
return "";
}
public static String  _a2_animationend() throws Exception{
 //BA.debugLineNum = 209;BA.debugLine="Sub a2_AnimationEnd";
 //BA.debugLineNum = 210;BA.debugLine="If blnFinishAnimation = True Then";
if (_blnfinishanimation==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 211;BA.debugLine="currentClick.Left = currentClick.Left+150dip";
mostCurrent._currentclick.setLeft((int) (mostCurrent._currentclick.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (150))));
 //BA.debugLineNum = 212;BA.debugLine="blnFinishAnimation = False";
_blnfinishanimation = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 213;BA.debugLine="mAnimation.Put(currentOffset,False)";
mostCurrent._manimation.Put((Object)(_currentoffset),(Object)(anywheresoftware.b4a.keywords.Common.False));
 };
 //BA.debugLineNum = 215;BA.debugLine="End Sub";
return "";
}
public static String  _activity_create(boolean _firsttime) throws Exception{
int _i = 0;
 //BA.debugLineNum = 46;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 47;BA.debugLine="Activity.LoadLayout(\"frmlist\")";
mostCurrent._activity.LoadLayout("frmlist",mostCurrent.activityBA);
 //BA.debugLineNum = 50;BA.debugLine="mAnimation.Initialize";
mostCurrent._manimation.Initialize();
 //BA.debugLineNum = 51;BA.debugLine="APK.Initialize";
mostCurrent._apk.Initialize();
 //BA.debugLineNum = 52;BA.debugLine="db1.Initialize";
mostCurrent._db1._initialize(processBA);
 //BA.debugLineNum = 54;BA.debugLine="txtval.Color = Colors.Transparent";
mostCurrent._txtval.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 56;BA.debugLine="createMenu";
_createmenu();
 //BA.debugLineNum = 58;BA.debugLine="APK = db1.GetPackageList";
mostCurrent._apk = mostCurrent._db1._getpackagelist();
 //BA.debugLineNum = 60;BA.debugLine="If APK.IsInitialized = False Then";
if (mostCurrent._apk.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 61;BA.debugLine="ToastMessageShow(\"خطا : در دستگاه گوشی شما برنام";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("خطا : در دستگاه گوشی شما برنامه ای پیدا نشد",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 62;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 65;BA.debugLine="Try";
try { //BA.debugLineNum = 66;BA.debugLine="For i = 0 To 15";
{
final int step44 = 1;
final int limit44 = (int) (15);
for (_i = (int) (0); (step44 > 0 && _i <= limit44) || (step44 < 0 && _i >= limit44); _i = ((int)(0 + _i + step44))) {
 //BA.debugLineNum = 67;BA.debugLine="addItem(i)";
_additem(_i);
 }
};
 } 
       catch (Exception e48) {
			processBA.setLastException(e48); };
 //BA.debugLineNum = 72;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 74;BA.debugLine="offsetList = 16";
_offsetlist = (int) (16);
 //BA.debugLineNum = 76;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 354;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 355;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 356;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else if(_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_MENU) { 
 //BA.debugLineNum = 358;BA.debugLine="If sm.Visible = False Then";
if (mostCurrent._sm.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 359;BA.debugLine="sm.ShowMenu";
mostCurrent._sm.ShowMenu();
 }else {
 //BA.debugLineNum = 361;BA.debugLine="sm.HideMenus";
mostCurrent._sm.HideMenus();
 };
 };
 //BA.debugLineNum = 364;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 153;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 154;BA.debugLine="If UserClosed = False Then";
if (_userclosed==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 155;BA.debugLine="If internal = False Then";
if (_internal==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 156;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else {
 //BA.debugLineNum = 158;BA.debugLine="internal = False";
_internal = anywheresoftware.b4a.keywords.Common.False;
 };
 };
 //BA.debugLineNum = 161;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 149;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 151;BA.debugLine="End Sub";
return "";
}
public static String  _additem(int _i) throws Exception{
collection.app.lock.mydb._package _package1 = null;
anywheresoftware.b4a.objects.PanelWrapper _p1 = null;
anywheresoftware.b4a.object.RippleViewWrapper _rip = null;
 //BA.debugLineNum = 78;BA.debugLine="Sub addItem(i As Int)";
 //BA.debugLineNum = 79;BA.debugLine="Dim package1 As Package";
_package1 = new collection.app.lock.mydb._package();
 //BA.debugLineNum = 80;BA.debugLine="Dim p1 As Panel";
_p1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 82;BA.debugLine="package1.Initialize";
_package1.Initialize();
 //BA.debugLineNum = 83;BA.debugLine="p1.Initialize(\"\")";
_p1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 85;BA.debugLine="package1 = APK.Get(i)";
_package1 = (collection.app.lock.mydb._package)(mostCurrent._apk.Get(_i));
 //BA.debugLineNum = 87;BA.debugLine="Try";
try { //BA.debugLineNum = 88;BA.debugLine="pk1.GetApplicationLabel(package1.sTitle)";
mostCurrent._pk1.GetApplicationLabel(_package1.sTitle);
 } 
       catch (Exception e61) {
			processBA.setLastException(e61); };
 //BA.debugLineNum = 93;BA.debugLine="SV1.Panel.AddView(p1,0,top,100%x,95dip)";
mostCurrent._sv1.getPanel().AddView((android.view.View)(_p1.getObject()),(int) (0),_top,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (95)));
 //BA.debugLineNum = 94;BA.debugLine="p1.LoadLayout(\"frmtemplate\")";
_p1.LoadLayout("frmtemplate",mostCurrent.activityBA);
 //BA.debugLineNum = 95;BA.debugLine="lbltitle.Text = pk1.GetApplicationLabel(package";
mostCurrent._lbltitle.setText((Object)(mostCurrent._pk1.GetApplicationLabel(_package1.sTitle)));
 //BA.debugLineNum = 96;BA.debugLine="imgicon.Background = pk1.GetApplicationIcon(pac";
mostCurrent._imgicon.setBackground(mostCurrent._pk1.GetApplicationIcon(_package1.sTitle));
 //BA.debugLineNum = 97;BA.debugLine="imgmore.Tag = Array(i,pnlitem,imglock)";
mostCurrent._imgmore.setTag((Object)(new Object[]{(Object)(_i),(Object)(mostCurrent._pnlitem.getObject()),(Object)(mostCurrent._imglock.getObject())}));
 //BA.debugLineNum = 98;BA.debugLine="top = top + 100dip";
_top = (int) (_top+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)));
 //BA.debugLineNum = 99;BA.debugLine="mAnimation.Put(i,False)";
mostCurrent._manimation.Put((Object)(_i),(Object)(anywheresoftware.b4a.keywords.Common.False));
 //BA.debugLineNum = 101;BA.debugLine="lblpass.Tag = i";
mostCurrent._lblpass.setTag((Object)(_i));
 //BA.debugLineNum = 102;BA.debugLine="lblrun.Tag = i";
mostCurrent._lblrun.setTag((Object)(_i));
 //BA.debugLineNum = 104;BA.debugLine="If db1.getPasswordApp(package1.sTitle).Length >";
if (mostCurrent._db1._getpasswordapp(_package1.sTitle).length()>0) { 
 //BA.debugLineNum = 105;BA.debugLine="imglock.Visible = True";
mostCurrent._imglock.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 109;BA.debugLine="Dim rip As RippleView";
_rip = new anywheresoftware.b4a.object.RippleViewWrapper();
 //BA.debugLineNum = 110;BA.debugLine="rip.Initialize(lblpass,Colors.White,300,True)";
_rip.Initialize(mostCurrent.activityBA,(android.view.View)(mostCurrent._lblpass.getObject()),anywheresoftware.b4a.keywords.Common.Colors.White,(int) (300),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 111;BA.debugLine="rip.Initialize(lblrun,Colors.White,300,True)";
_rip.Initialize(mostCurrent.activityBA,(android.view.View)(mostCurrent._lblrun.getObject()),anywheresoftware.b4a.keywords.Common.Colors.White,(int) (300),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 113;BA.debugLine="SV1.Panel.Height = top";
mostCurrent._sv1.getPanel().setHeight(_top);
 //BA.debugLineNum = 114;BA.debugLine="End Sub";
return "";
}
public static String  _animationview(int _offset,anywheresoftware.b4a.objects.PanelWrapper _p1) throws Exception{
 //BA.debugLineNum = 181;BA.debugLine="Sub animationView(offset As Int,p1 As Panel)";
 //BA.debugLineNum = 182;BA.debugLine="Try";
try { //BA.debugLineNum = 183;BA.debugLine="If mAnimation.Get(offset) = False Then Then";
if ((mostCurrent._manimation.Get((Object)(_offset))).equals((Object)(anywheresoftware.b4a.keywords.Common.False))) { 
 //BA.debugLineNum = 184;BA.debugLine="ani1.InitializeTranslate(\"a1\",0,0,-150dip,0)";
mostCurrent._ani1.InitializeTranslate(mostCurrent.activityBA,"a1",(float) (0),(float) (0),(float) (-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (150))),(float) (0));
 //BA.debugLineNum = 185;BA.debugLine="ani1.Duration = 400";
mostCurrent._ani1.setDuration((long) (400));
 //BA.debugLineNum = 186;BA.debugLine="ani1.SetInterpolator(ani1.INTERPOLATOR_BOUNCE)";
mostCurrent._ani1.SetInterpolator(mostCurrent._ani1.INTERPOLATOR_BOUNCE);
 //BA.debugLineNum = 187;BA.debugLine="ani1.start(p1)";
mostCurrent._ani1.Start((android.view.View)(_p1.getObject()));
 }else {
 //BA.debugLineNum = 190;BA.debugLine="ani2.InitializeTranslate(\"a2\",0,0,150dip,0)";
mostCurrent._ani2.InitializeTranslate(mostCurrent.activityBA,"a2",(float) (0),(float) (0),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (150))),(float) (0));
 //BA.debugLineNum = 191;BA.debugLine="ani2.Duration = 400";
mostCurrent._ani2.setDuration((long) (400));
 //BA.debugLineNum = 192;BA.debugLine="ani2.SetInterpolator(ani2.INTERPOLATOR_BOUNCE)";
mostCurrent._ani2.SetInterpolator(mostCurrent._ani2.INTERPOLATOR_BOUNCE);
 //BA.debugLineNum = 193;BA.debugLine="ani2.start(p1)";
mostCurrent._ani2.Start((android.view.View)(_p1.getObject()));
 };
 } 
       catch (Exception e142) {
			processBA.setLastException(e142); };
 //BA.debugLineNum = 198;BA.debugLine="End Sub";
return "";
}
public static String  _btnback_click() throws Exception{
 //BA.debugLineNum = 370;BA.debugLine="Sub btnback_Click";
 //BA.debugLineNum = 371;BA.debugLine="If currentPass <> txtval.Text Then";
if ((mostCurrent._currentpass).equals(mostCurrent._txtval.getText()) == false) { 
 //BA.debugLineNum = 372;BA.debugLine="If Msgbox2(\"رمز شما تغییر پیدا کرده است. آیا قصد";
if (anywheresoftware.b4a.keywords.Common.Msgbox2("رمز شما تغییر پیدا کرده است. آیا قصد ذخیره تغییرات جدید را دارید؟","توجه","آری","خیر","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 373;BA.debugLine="btnlogin_Click";
_btnlogin_click();
 //BA.debugLineNum = 374;BA.debugLine="Return";
if (true) return "";
 };
 };
 //BA.debugLineNum = 378;BA.debugLine="animationView(currentOffset,currentClick)";
_animationview(_currentoffset,mostCurrent._currentclick);
 //BA.debugLineNum = 379;BA.debugLine="pnloverlay.SetVisibleAnimated(800,False)";
mostCurrent._pnloverlay.SetVisibleAnimated((int) (800),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 380;BA.debugLine="txtval.Text = \"\"";
mostCurrent._txtval.setText((Object)(""));
 //BA.debugLineNum = 381;BA.debugLine="End Sub";
return "";
}
public static String  _btndelete_click() throws Exception{
 //BA.debugLineNum = 259;BA.debugLine="Sub btndelete_Click";
 //BA.debugLineNum = 260;BA.debugLine="Try";
try { //BA.debugLineNum = 261;BA.debugLine="txtval.Text = txtval.Text.SubString2(0,txtval.Te";
mostCurrent._txtval.setText((Object)(mostCurrent._txtval.getText().substring((int) (0),(int) (mostCurrent._txtval.getText().length()-1))));
 } 
       catch (Exception e193) {
			processBA.setLastException(e193); };
 //BA.debugLineNum = 264;BA.debugLine="End Sub";
return "";
}
public static String  _btnlogin_click() throws Exception{
 //BA.debugLineNum = 266;BA.debugLine="Sub btnlogin_Click";
 //BA.debugLineNum = 268;BA.debugLine="If txtval.Text.Length = 0 Then";
if (mostCurrent._txtval.getText().length()==0) { 
 //BA.debugLineNum = 269;BA.debugLine="db1.newPass(pnloverlay.Tag,txtval.Text)";
mostCurrent._db1._newpass(BA.ObjectToString(mostCurrent._pnloverlay.getTag()),mostCurrent._txtval.getText());
 }else {
 //BA.debugLineNum = 271;BA.debugLine="If pre.GetString(\"password\") = txtval.Text Then";
if ((mostCurrent._pre.GetString("password")).equals(mostCurrent._txtval.getText())) { 
 //BA.debugLineNum = 272;BA.debugLine="ToastMessageShow(\"خطا : این رمز برای ورود به بر";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("خطا : این رمز برای ورود به برنامه اصلی تعین شده است",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 273;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 276;BA.debugLine="If db1.PassExist(txtval.Text) > 0 Then";
if (mostCurrent._db1._passexist(mostCurrent._txtval.getText())>0) { 
 //BA.debugLineNum = 277;BA.debugLine="ToastMessageShow(\"رمزی که وارد کرده اید، قبلا ا";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("رمزی که وارد کرده اید، قبلا استفاده شده است. لطفا رمز دیگری را انتخاب کنید.",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 278;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 281;BA.debugLine="db1.newPass(pnloverlay.Tag,txtval.Text)";
mostCurrent._db1._newpass(BA.ObjectToString(mostCurrent._pnloverlay.getTag()),mostCurrent._txtval.getText());
 };
 //BA.debugLineNum = 284;BA.debugLine="If txtval.Text.Length = 0 Then";
if (mostCurrent._txtval.getText().length()==0) { 
 //BA.debugLineNum = 285;BA.debugLine="ToastMessageShow(\"رمز شما با موفقیت حذف شد\",Fals";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("رمز شما با موفقیت حذف شد",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 286;BA.debugLine="currentLockImage.SetVisibleAnimated(800,False)";
mostCurrent._currentlockimage.SetVisibleAnimated((int) (800),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 288;BA.debugLine="ToastMessageShow(\"رمز جدید با موفقیت ثبت شد\",Fal";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("رمز جدید با موفقیت ثبت شد",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 289;BA.debugLine="currentLockImage.SetVisibleAnimated(800,True)";
mostCurrent._currentlockimage.SetVisibleAnimated((int) (800),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 292;BA.debugLine="animationView(currentOffset,currentClick)";
_animationview(_currentoffset,mostCurrent._currentclick);
 //BA.debugLineNum = 293;BA.debugLine="pnloverlay.SetVisibleAnimated(800,False)";
mostCurrent._pnloverlay.SetVisibleAnimated((int) (800),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 294;BA.debugLine="txtval.Text = \"\"";
mostCurrent._txtval.setText((Object)(""));
 //BA.debugLineNum = 296;BA.debugLine="End Sub";
return "";
}
public static String  _createmenu() throws Exception{
int _offset = 0;
anywheresoftware.b4a.objects.ListViewWrapper _lv1 = null;
 //BA.debugLineNum = 301;BA.debugLine="Sub createMenu";
 //BA.debugLineNum = 302;BA.debugLine="sm.Initialize(\"Menu\")";
mostCurrent._sm.Initialize(mostCurrent.activityBA,"Menu");
 //BA.debugLineNum = 303;BA.debugLine="Dim offset As Int = 120dip";
_offset = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (120));
 //BA.debugLineNum = 304;BA.debugLine="sm.BehindOffset = offset";
mostCurrent._sm.setBehindOffset(_offset);
 //BA.debugLineNum = 305;BA.debugLine="sm.Mode = sm.RIGHT";
mostCurrent._sm.setMode(mostCurrent._sm.RIGHT);
 //BA.debugLineNum = 306;BA.debugLine="Dim lv1 As ListView";
_lv1 = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 308;BA.debugLine="lv1.Initialize(\"PanelMenu\")";
_lv1.Initialize(mostCurrent.activityBA,"PanelMenu");
 //BA.debugLineNum = 309;BA.debugLine="sm.Menu.Color = Colors.rgb(231,76,60)";
mostCurrent._sm.getMenu().setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (231),(int) (76),(int) (60)));
 //BA.debugLineNum = 310;BA.debugLine="lv1.TwoLinesAndBitmap.Label.Gravity = Gravity.R";
_lv1.getTwoLinesAndBitmap().Label.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
 //BA.debugLineNum = 311;BA.debugLine="lv1.TwoLinesAndBitmap.Label.Width = 52%x";
_lv1.getTwoLinesAndBitmap().Label.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (52),mostCurrent.activityBA));
 //BA.debugLineNum = 312;BA.debugLine="lv1.TwoLinesAndBitmap.ImageView.Width = 8%x";
_lv1.getTwoLinesAndBitmap().ImageView.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (8),mostCurrent.activityBA));
 //BA.debugLineNum = 313;BA.debugLine="lv1.TwoLinesAndBitmap.ImageView.Height = lv1.Tw";
_lv1.getTwoLinesAndBitmap().ImageView.setHeight(_lv1.getTwoLinesAndBitmap().ImageView.getWidth());
 //BA.debugLineNum = 314;BA.debugLine="lv1.TwoLinesAndBitmap.ImageView.Left = 53%x";
_lv1.getTwoLinesAndBitmap().ImageView.setLeft(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (53),mostCurrent.activityBA));
 //BA.debugLineNum = 315;BA.debugLine="lv1.TwoLinesAndBitmap.ImageView.top = 16dip";
_lv1.getTwoLinesAndBitmap().ImageView.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (16)));
 //BA.debugLineNum = 316;BA.debugLine="lv1.TwoLinesAndBitmap.Label.Left = 0";
_lv1.getTwoLinesAndBitmap().Label.setLeft((int) (0));
 //BA.debugLineNum = 317;BA.debugLine="lv1.TwoLinesAndBitmap.Label.top = 15dip";
_lv1.getTwoLinesAndBitmap().Label.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15)));
 //BA.debugLineNum = 318;BA.debugLine="lv1.TwoLinesAndBitmap.Label.Typeface = Typeface";
_lv1.getTwoLinesAndBitmap().Label.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
 //BA.debugLineNum = 319;BA.debugLine="lv1.TwoLinesAndBitmap.Label.Typeface = Typeface";
_lv1.getTwoLinesAndBitmap().Label.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("byekan.ttf"));
 //BA.debugLineNum = 321;BA.debugLine="lv1.AddTwoLinesAndBitmap2(\"قفل مخفی\",\"\",LoadBit";
_lv1.AddTwoLinesAndBitmap2("قفل مخفی","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"icon.png").getObject()),(Object)("about"));
 //BA.debugLineNum = 323;BA.debugLine="lv1.AddTwoLinesAndBitmap2(\"درباره ما\",\"\",LoadBi";
_lv1.AddTwoLinesAndBitmap2("درباره ما","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"about.png").getObject()),(Object)("about"));
 //BA.debugLineNum = 324;BA.debugLine="lv1.AddTwoLinesAndBitmap2(\"تنظیمات\",\"\",LoadBitm";
_lv1.AddTwoLinesAndBitmap2("تنظیمات","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"setting.png").getObject()),(Object)("setting"));
 //BA.debugLineNum = 325;BA.debugLine="lv1.AddTwoLinesAndBitmap2(\"خروج از برنامه\",\"\",L";
_lv1.AddTwoLinesAndBitmap2("خروج از برنامه","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"exit.png").getObject()),(Object)("exit"));
 //BA.debugLineNum = 327;BA.debugLine="sm.Menu.AddView(lv1, 0, 0, 100%x - offset, 100%";
mostCurrent._sm.getMenu().AddView((android.view.View)(_lv1.getObject()),(int) (0),(int) (0),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-_offset),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 328;BA.debugLine="End Sub";
return "";
}
public static collection.app.lock.mydb._package  _getpackageinfo(int _i) throws Exception{
collection.app.lock.mydb._package _p1 = null;
 //BA.debugLineNum = 388;BA.debugLine="Sub GetPackageInfo(i As Int) As Package";
 //BA.debugLineNum = 390;BA.debugLine="Dim p1 As Package";
_p1 = new collection.app.lock.mydb._package();
 //BA.debugLineNum = 391;BA.debugLine="p1.Initialize";
_p1.Initialize();
 //BA.debugLineNum = 393;BA.debugLine="p1 = APK.Get(i)";
_p1 = (collection.app.lock.mydb._package)(mostCurrent._apk.Get(_i));
 //BA.debugLineNum = 395;BA.debugLine="Return p1";
if (true) return _p1;
 //BA.debugLineNum = 397;BA.debugLine="End Sub";
return null;
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 10;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 11;BA.debugLine="Private SV1 As ScrollView";
mostCurrent._sv1 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 12;BA.debugLine="Private blnFinishAnimation As Boolean";
_blnfinishanimation = false;
 //BA.debugLineNum = 13;BA.debugLine="Private lbltitle As Label";
mostCurrent._lbltitle = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Private imgicon As ImageView";
mostCurrent._imgicon = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Private imgmore As ImageView";
mostCurrent._imgmore = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Dim ani1,ani2 As AnimationPlus";
mostCurrent._ani1 = new flm.b4a.animationplus.AnimationPlusWrapper();
mostCurrent._ani2 = new flm.b4a.animationplus.AnimationPlusWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Dim currentClick As Panel";
mostCurrent._currentclick = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Dim currentOffset As Int";
_currentoffset = 0;
 //BA.debugLineNum = 21;BA.debugLine="Private pnlitem As Panel";
mostCurrent._pnlitem = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Dim mAnimation As Map";
mostCurrent._manimation = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 23;BA.debugLine="Private lblpass As Label";
mostCurrent._lblpass = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private lblrun As Label";
mostCurrent._lblrun = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private pnloverlay As Panel";
mostCurrent._pnloverlay = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private txtval As EditText";
mostCurrent._txtval = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Dim db1 As myDB";
mostCurrent._db1 = new collection.app.lock.mydb();
 //BA.debugLineNum = 30;BA.debugLine="Dim sm As SlidingMenu";
mostCurrent._sm = new anywheresoftware.b4a.objects.SlidingMenuWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Dim pk1 As PackageManager";
mostCurrent._pk1 = new anywheresoftware.b4a.phone.PackageManagerWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private imglock,currentLockImage As ImageView";
mostCurrent._imglock = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._currentlockimage = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Dim currentPass As String";
mostCurrent._currentpass = "";
 //BA.debugLineNum = 34;BA.debugLine="Private imgicon_1 As ImageView";
mostCurrent._imgicon_1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private btnback As Button";
mostCurrent._btnback = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Dim pre As PreferenceManager";
mostCurrent._pre = new anywheresoftware.b4a.objects.preferenceactivity.PreferenceManager();
 //BA.debugLineNum = 39;BA.debugLine="Dim top As Int : 5dip";
_top = 0;
 //BA.debugLineNum = 39;BA.debugLine="Dim top As Int : 5dip";
anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5));
 //BA.debugLineNum = 41;BA.debugLine="Dim APK As List";
mostCurrent._apk = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 43;BA.debugLine="Dim offsetList As Int";
_offsetlist = 0;
 //BA.debugLineNum = 44;BA.debugLine="End Sub";
return "";
}
public static String  _imgmenu_click() throws Exception{
 //BA.debugLineNum = 366;BA.debugLine="Sub imgmenu_Click";
 //BA.debugLineNum = 367;BA.debugLine="sm.ShowMenu";
mostCurrent._sm.ShowMenu();
 //BA.debugLineNum = 368;BA.debugLine="End Sub";
return "";
}
public static String  _imgmore_click() throws Exception{
anywheresoftware.b4a.objects.ImageViewWrapper _img = null;
Object[] _ob = null;
int _offset = 0;
 //BA.debugLineNum = 163;BA.debugLine="Sub imgmore_Click";
 //BA.debugLineNum = 164;BA.debugLine="Dim img As ImageView";
_img = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 165;BA.debugLine="img = Sender";
_img.setObject((android.widget.ImageView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 167;BA.debugLine="Dim ob(2) As Object";
_ob = new Object[(int) (2)];
{
int d0 = _ob.length;
for (int i0 = 0;i0 < d0;i0++) {
_ob[i0] = new Object();
}
}
;
 //BA.debugLineNum = 168;BA.debugLine="ob = img.Tag";
_ob = (Object[])(_img.getTag());
 //BA.debugLineNum = 170;BA.debugLine="Dim offset As Int";
_offset = 0;
 //BA.debugLineNum = 171;BA.debugLine="offset = ob(0)";
_offset = (int)(BA.ObjectToNumber(_ob[(int) (0)]));
 //BA.debugLineNum = 173;BA.debugLine="currentOffset = offset";
_currentoffset = _offset;
 //BA.debugLineNum = 174;BA.debugLine="currentClick = ob(1)";
mostCurrent._currentclick.setObject((android.view.ViewGroup)(_ob[(int) (1)]));
 //BA.debugLineNum = 175;BA.debugLine="currentLockImage = ob(2)";
mostCurrent._currentlockimage.setObject((android.widget.ImageView)(_ob[(int) (2)]));
 //BA.debugLineNum = 177;BA.debugLine="animationView(offset,currentClick)";
_animationview(_offset,mostCurrent._currentclick);
 //BA.debugLineNum = 178;BA.debugLine="End Sub";
return "";
}
public static String  _lblpass_click() throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _lb = null;
String _pass = "";
 //BA.debugLineNum = 234;BA.debugLine="Sub lblpass_Click";
 //BA.debugLineNum = 235;BA.debugLine="Dim lb As Label";
_lb = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 236;BA.debugLine="lb = Sender";
_lb.setObject((android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 238;BA.debugLine="Dim pass As String";
_pass = "";
 //BA.debugLineNum = 239;BA.debugLine="pass = db1.getPasswordApp( GetPackageInfo(lb.Tag)";
_pass = mostCurrent._db1._getpasswordapp(_getpackageinfo((int)(BA.ObjectToNumber(_lb.getTag()))).sTitle);
 //BA.debugLineNum = 240;BA.debugLine="pnloverlay.SetVisibleAnimated(800,True)";
mostCurrent._pnloverlay.SetVisibleAnimated((int) (800),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 241;BA.debugLine="imgicon_1.Background = pk1.GetApplicationIcon( Ge";
mostCurrent._imgicon_1.setBackground(mostCurrent._pk1.GetApplicationIcon(_getpackageinfo((int)(BA.ObjectToNumber(_lb.getTag()))).sTitle));
 //BA.debugLineNum = 242;BA.debugLine="pnloverlay.Tag =  GetPackageInfo(lb.Tag).sTitle";
mostCurrent._pnloverlay.setTag((Object)(_getpackageinfo((int)(BA.ObjectToNumber(_lb.getTag()))).sTitle));
 //BA.debugLineNum = 244;BA.debugLine="If pass <> Null Then";
if (_pass!= null) { 
 //BA.debugLineNum = 245;BA.debugLine="txtval.Text = pass";
mostCurrent._txtval.setText((Object)(_pass));
 //BA.debugLineNum = 246;BA.debugLine="currentPass = pass";
mostCurrent._currentpass = _pass;
 };
 //BA.debugLineNum = 249;BA.debugLine="End Sub";
return "";
}
public static String  _lblrun_click() throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _b1 = null;
 //BA.debugLineNum = 219;BA.debugLine="Sub lblrun_Click";
 //BA.debugLineNum = 220;BA.debugLine="Dim b1 As Label";
_b1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 221;BA.debugLine="b1 = Sender";
_b1.setObject((android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 223;BA.debugLine="Try";
try { //BA.debugLineNum = 224;BA.debugLine="force_close.opener = GetPackageInfo(b1.Tag).sTit";
mostCurrent._force_close._opener = _getpackageinfo((int)(BA.ObjectToNumber(_b1.getTag()))).sTitle;
 //BA.debugLineNum = 225;BA.debugLine="force_close.running = True";
mostCurrent._force_close._running = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 226;BA.debugLine="StartActivity(pk1.GetApplicationIntent(GetPackag";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._pk1.GetApplicationIntent(_getpackageinfo((int)(BA.ObjectToNumber(_b1.getTag()))).sTitle).getObject()));
 //BA.debugLineNum = 227;BA.debugLine="animationView(currentOffset,currentClick)";
_animationview(_currentoffset,mostCurrent._currentclick);
 } 
       catch (Exception e167) {
			processBA.setLastException(e167); //BA.debugLineNum = 229;BA.debugLine="ToastMessageShow(\"اخطار : اين برنامه امکان اجرا";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("اخطار : اين برنامه امکان اجرا ندارد",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 232;BA.debugLine="End Sub";
return "";
}
public static String  _numbers_click() throws Exception{
anywheresoftware.b4a.objects.ImageViewWrapper _img1 = null;
 //BA.debugLineNum = 252;BA.debugLine="Sub numbers_Click";
 //BA.debugLineNum = 253;BA.debugLine="Dim img1 As ImageView";
_img1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 254;BA.debugLine="img1 = Sender";
_img1.setObject((android.widget.ImageView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 255;BA.debugLine="txtval.Text = txtval.Text & img1.Tag";
mostCurrent._txtval.setText((Object)(mostCurrent._txtval.getText()+BA.ObjectToString(_img1.getTag())));
 //BA.debugLineNum = 256;BA.debugLine="MyLibrary.animationView(img1,False,700)";
mostCurrent._mylibrary._animationview(mostCurrent.activityBA,(anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(_img1.getObject())),anywheresoftware.b4a.keywords.Common.False,(int) (700));
 //BA.debugLineNum = 257;BA.debugLine="End Sub";
return "";
}
public static String  _panelmenu_itemclick(int _position,Object _value) throws Exception{
anywheresoftware.b4a.agraham.dialogs.InputDialog.CustomDialog _c1 = null;
anywheresoftware.b4a.objects.PanelWrapper _p1 = null;
 //BA.debugLineNum = 330;BA.debugLine="Sub PanelMenu_ItemClick (Position As Int, Value As";
 //BA.debugLineNum = 331;BA.debugLine="Select Value";
switch (BA.switchObjectToInt(_value,(Object)("contact"),(Object)("about"),(Object)("setting"),(Object)("exit"))) {
case 0:
 //BA.debugLineNum = 333;BA.debugLine="internal = True";
_internal = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 334;BA.debugLine="StartActivity(actContact)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._actcontact.getObject()));
 break;
case 1:
 //BA.debugLineNum = 336;BA.debugLine="Dim c1 As CustomDialog";
_c1 = new anywheresoftware.b4a.agraham.dialogs.InputDialog.CustomDialog();
 //BA.debugLineNum = 337;BA.debugLine="Dim p1 As Panel";
_p1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 338;BA.debugLine="p1.Initialize(\"\")";
_p1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 339;BA.debugLine="c1.AddView(p1,0,0,85%x,300)";
_c1.AddView((android.view.View)(_p1.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (85),mostCurrent.activityBA),(int) (300));
 //BA.debugLineNum = 340;BA.debugLine="p1.LoadLayout(\"frmabout\")";
_p1.LoadLayout("frmabout",mostCurrent.activityBA);
 //BA.debugLineNum = 341;BA.debugLine="c1.Show(\"درباره ما\",\"اکی\",\"\",\"\",Null)";
_c1.Show("درباره ما","اکی","","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 break;
case 2:
 //BA.debugLineNum = 343;BA.debugLine="showSetting";
_showsetting();
 break;
case 3:
 //BA.debugLineNum = 345;BA.debugLine="If Msgbox2(\"آیا مایل به خروج هستید؟\",\"خروج\",\"بله";
if (anywheresoftware.b4a.keywords.Common.Msgbox2("آیا مایل به خروج هستید؟","خروج","بله","خیر","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 346;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 break;
}
;
 //BA.debugLineNum = 349;BA.debugLine="sm.HideMenus";
mostCurrent._sm.HideMenus();
 //BA.debugLineNum = 350;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="Dim internal As Boolean";
_internal = false;
 //BA.debugLineNum = 8;BA.debugLine="End Sub";
return "";
}
public static String  _showsetting() throws Exception{
 //BA.debugLineNum = 383;BA.debugLine="Sub showSetting";
 //BA.debugLineNum = 384;BA.debugLine="internal = True";
_internal = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 385;BA.debugLine="StartActivity(actSetting)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._actsetting.getObject()));
 //BA.debugLineNum = 386;BA.debugLine="End Sub";
return "";
}
public static String  _sv1_scrollchanged(int _position) throws Exception{
int _i = 0;
 //BA.debugLineNum = 116;BA.debugLine="Sub sv1_ScrollChanged(Position As Int)";
 //BA.debugLineNum = 118;BA.debugLine="If offsetList = APK.Size Then";
if (_offsetlist==mostCurrent._apk.getSize()) { 
 //BA.debugLineNum = 119;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 122;BA.debugLine="If Position + SV1.Height >= SV1.Panel.Height Then";
if (_position+mostCurrent._sv1.getHeight()>=mostCurrent._sv1.getPanel().getHeight()) { 
 //BA.debugLineNum = 124;BA.debugLine="ProgressDialogShow(\"بارگذاری...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"بارگذاری...");
 //BA.debugLineNum = 125;BA.debugLine="For i = offsetList To offsetList + 16";
{
final int step85 = 1;
final int limit85 = (int) (_offsetlist+16);
for (_i = _offsetlist; (step85 > 0 && _i <= limit85) || (step85 < 0 && _i >= limit85); _i = ((int)(0 + _i + step85))) {
 //BA.debugLineNum = 126;BA.debugLine="If i = APK.Size Then";
if (_i==mostCurrent._apk.getSize()) { 
 //BA.debugLineNum = 127;BA.debugLine="offsetList = APK.Size";
_offsetlist = mostCurrent._apk.getSize();
 //BA.debugLineNum = 128;BA.debugLine="SV1.Panel.Height = SV1.Panel.Height + 55dip";
mostCurrent._sv1.getPanel().setHeight((int) (mostCurrent._sv1.getPanel().getHeight()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (55))));
 //BA.debugLineNum = 129;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 130;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 133;BA.debugLine="Try";
try { //BA.debugLineNum = 134;BA.debugLine="addItem(i)";
_additem(_i);
 } 
       catch (Exception e95) {
			processBA.setLastException(e95); //BA.debugLineNum = 136;BA.debugLine="offsetList = APK.Size";
_offsetlist = mostCurrent._apk.getSize();
 //BA.debugLineNum = 137;BA.debugLine="SV1.Panel.Height = SV1.Panel.Height + 55dip";
mostCurrent._sv1.getPanel().setHeight((int) (mostCurrent._sv1.getPanel().getHeight()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (55))));
 //BA.debugLineNum = 138;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 139;BA.debugLine="Return";
if (true) return "";
 };
 }
};
 //BA.debugLineNum = 143;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 144;BA.debugLine="offsetList = offsetList + 17";
_offsetlist = (int) (_offsetlist+17);
 };
 //BA.debugLineNum = 147;BA.debugLine="End Sub";
return "";
}
}
