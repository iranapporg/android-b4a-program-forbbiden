package collection.app.lock;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.debug.*;

public class force_close extends android.app.Service {
	public static class force_close_BR extends android.content.BroadcastReceiver {

		@Override
		public void onReceive(android.content.Context context, android.content.Intent intent) {
			android.content.Intent in = new android.content.Intent(context, force_close.class);
			if (intent != null)
				in.putExtra("b4a_internal_intent", intent);
			context.startService(in);
		}

	}
    static force_close mostCurrent;
	public static BA processBA;
    private ServiceHelper _service;
    public static Class<?> getObject() {
		return force_close.class;
	}
	@Override
	public void onCreate() {
        mostCurrent = this;
        if (processBA == null) {
		    processBA = new BA(this, null, null, "collection.app.lock", "collection.app.lock.force_close");
            try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            processBA.loadHtSubs(this.getClass());
            ServiceHelper.init();
        }
        _service = new ServiceHelper(this);
        processBA.service = this;
        processBA.setActivityPaused(false);
        if (BA.isShellModeRuntimeCheck(processBA)) {
			processBA.raiseEvent2(null, true, "CREATE", true, "collection.app.lock.force_close", processBA, _service);
		}
        BA.LogInfo("** Service (force_close) Create **");
        processBA.raiseEvent(null, "service_create");
        processBA.runHook("oncreate", this, null);
    }
		@Override
	public void onStart(android.content.Intent intent, int startId) {
		handleStart(intent);
    }
    @Override
    public int onStartCommand(android.content.Intent intent, int flags, int startId) {
    	handleStart(intent);
        processBA.runHook("onstartcommand", this, new Object[] {intent, flags, startId});
		return android.app.Service.START_NOT_STICKY;
    }
    private void handleStart(android.content.Intent intent) {
    	BA.LogInfo("** Service (force_close) Start **");
    	java.lang.reflect.Method startEvent = processBA.htSubs.get("service_start");
    	if (startEvent != null) {
    		if (startEvent.getParameterTypes().length > 0) {
    			anywheresoftware.b4a.objects.IntentWrapper iw = new anywheresoftware.b4a.objects.IntentWrapper();
    			if (intent != null) {
    				if (intent.hasExtra("b4a_internal_intent"))
    					iw.setObject((android.content.Intent) intent.getParcelableExtra("b4a_internal_intent"));
    				else
    					iw.setObject(intent);
    			}
    			processBA.raiseEvent(null, "service_start", iw);
    		}
    		else {
    			processBA.raiseEvent(null, "service_start");
    		}
    	}
    }
	@Override
	public android.os.IBinder onBind(android.content.Intent intent) {
		return null;
	}
	@Override
	public void onDestroy() {
        BA.LogInfo("** Service (force_close) Destroy **");
		processBA.raiseEvent(null, "service_destroy");
        processBA.service = null;
		mostCurrent = null;
		processBA.setActivityPaused(true);
        processBA.runHook("ondestroy", this, null);
	}
public anywheresoftware.b4a.keywords.Common __c = null;
public static com.rootsoft.oslibrary.OSLibrary _os = null;
public static collection.app.lock.mydb _db = null;
public static anywheresoftware.b4a.objects.preferenceactivity.PreferenceManager _pre = null;
public static String _opener = "";
public static boolean _running = false;
public collection.app.lock.main _main = null;
public collection.app.lock.actsplash _actsplash = null;
public collection.app.lock.actlist _actlist = null;
public collection.app.lock.actsetting _actsetting = null;
public collection.app.lock.actcontact _actcontact = null;
public collection.app.lock.actlock _actlock = null;
public collection.app.lock.handlepassword _handlepassword = null;
public collection.app.lock.handlesmspassword _handlesmspassword = null;
public collection.app.lock.mylibrary _mylibrary = null;
public static String  _getpk(Object _s) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
String _l = "";
 //BA.debugLineNum = 63;BA.debugLine="Sub getpk(s As Object) As String";
 //BA.debugLineNum = 64;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 65;BA.debugLine="r.Target = s";
_r.Target = _s;
 //BA.debugLineNum = 66;BA.debugLine="Dim l As String";
_l = "";
 //BA.debugLineNum = 67;BA.debugLine="l = r.GetField(\"topActivity\")";
_l = BA.ObjectToString(_r.GetField("topActivity"));
 //BA.debugLineNum = 68;BA.debugLine="Return l.SubString2(14,l.IndexOf(\"/\"))";
if (true) return _l.substring((int) (14),_l.indexOf("/"));
 //BA.debugLineNum = 69;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 5;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 6;BA.debugLine="Dim os As OperatingSystem";
_os = new com.rootsoft.oslibrary.OSLibrary();
 //BA.debugLineNum = 7;BA.debugLine="Dim db As myDB";
_db = new collection.app.lock.mydb();
 //BA.debugLineNum = 8;BA.debugLine="Dim pre As PreferenceManager";
_pre = new anywheresoftware.b4a.objects.preferenceactivity.PreferenceManager();
 //BA.debugLineNum = 9;BA.debugLine="Dim opener As String";
_opener = "";
 //BA.debugLineNum = 10;BA.debugLine="Dim running As Boolean";
_running = false;
 //BA.debugLineNum = 11;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 13;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 14;BA.debugLine="os.Initialize(\"os1\")";
_os.Initialize(processBA,"os1");
 //BA.debugLineNum = 15;BA.debugLine="db.Initialize";
_db._initialize(processBA);
 //BA.debugLineNum = 16;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 59;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 61;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
anywheresoftware.b4a.objects.collections.List _c2 = null;
String _pk = "";
 //BA.debugLineNum = 18;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 19;BA.debugLine="Dim c2 As List";
_c2 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 20;BA.debugLine="Dim pk As String";
_pk = "";
 //BA.debugLineNum = 22;BA.debugLine="c2.Initialize";
_c2.Initialize();
 //BA.debugLineNum = 23;BA.debugLine="c2 = os.getRunningTasks(2)";
_c2.setObject((java.util.List)(_os.getRunningTasks((int) (2))));
 //BA.debugLineNum = 24;BA.debugLine="pk = getpk(c2.Get(0)).ToLowerCase";
_pk = _getpk(_c2.Get((int) (0))).toLowerCase();
 //BA.debugLineNum = 26;BA.debugLine="Log(pk & \" and \" & opener)";
anywheresoftware.b4a.keywords.Common.Log(_pk+" and "+_opener);
 //BA.debugLineNum = 28;BA.debugLine="If pk <> opener Then";
if ((_pk).equals(_opener) == false) { 
 //BA.debugLineNum = 29;BA.debugLine="running = False";
_running = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 32;BA.debugLine="If running = True Then";
if (_running==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 34;BA.debugLine="StartServiceAt(\"\",DateTime.Now + 500,False)";
anywheresoftware.b4a.keywords.Common.StartServiceAt(processBA,(Object)(""),(long) (anywheresoftware.b4a.keywords.Common.DateTime.getNow()+500),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 35;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 39;BA.debugLine="If pk.IndexOf(\"phone\") <> -1 Then";
if (_pk.indexOf("phone")!=-1) { 
 //BA.debugLineNum = 41;BA.debugLine="If db.ExistAndLockApp(opener) = True Then";
if (_db._existandlockapp(_opener)==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 42;BA.debugLine="StartServiceAt(\"\",DateTime.Now + 500,False)";
anywheresoftware.b4a.keywords.Common.StartServiceAt(processBA,(Object)(""),(long) (anywheresoftware.b4a.keywords.Common.DateTime.getNow()+500),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 43;BA.debugLine="Return";
if (true) return "";
 };
 };
 //BA.debugLineNum = 47;BA.debugLine="If db.ExistAndLockApp(pk) = True Then";
if (_db._existandlockapp(_pk)==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 48;BA.debugLine="If pre.GetBoolean(\"lock_alert\") = False Then";
if (_pre.GetBoolean("lock_alert")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 49;BA.debugLine="MyLibrary.ShowHomeScreen";
mostCurrent._mylibrary._showhomescreen(processBA);
 }else {
 //BA.debugLineNum = 51;BA.debugLine="StartActivity(actLock)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._actlock.getObject()));
 };
 };
 //BA.debugLineNum = 55;BA.debugLine="StartServiceAt(\"\",DateTime.Now + 500,False)";
anywheresoftware.b4a.keywords.Common.StartServiceAt(processBA,(Object)(""),(long) (anywheresoftware.b4a.keywords.Common.DateTime.getNow()+500),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 57;BA.debugLine="End Sub";
return "";
}
}
