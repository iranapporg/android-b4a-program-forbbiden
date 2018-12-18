package collection.app.lock;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.debug.*;

public class handlesmspassword extends android.app.Service {
	public static class handlesmspassword_BR extends android.content.BroadcastReceiver {

		@Override
		public void onReceive(android.content.Context context, android.content.Intent intent) {
			android.content.Intent in = new android.content.Intent(context, handlesmspassword.class);
			if (intent != null)
				in.putExtra("b4a_internal_intent", intent);
			context.startService(in);
		}

	}
    static handlesmspassword mostCurrent;
	public static BA processBA;
    private ServiceHelper _service;
    public static Class<?> getObject() {
		return handlesmspassword.class;
	}
	@Override
	public void onCreate() {
        mostCurrent = this;
        if (processBA == null) {
		    processBA = new BA(this, null, null, "collection.app.lock", "collection.app.lock.handlesmspassword");
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
			processBA.raiseEvent2(null, true, "CREATE", true, "collection.app.lock.handlesmspassword", processBA, _service);
		}
        BA.LogInfo("** Service (handlesmspassword) Create **");
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
    	BA.LogInfo("** Service (handlesmspassword) Start **");
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
        BA.LogInfo("** Service (handlesmspassword) Destroy **");
		processBA.raiseEvent(null, "service_destroy");
        processBA.service = null;
		mostCurrent = null;
		processBA.setActivityPaused(true);
        processBA.runHook("ondestroy", this, null);
	}
public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.phone.PhoneEvents.SMSInterceptor _sms1 = null;
public static anywheresoftware.b4a.objects.preferenceactivity.PreferenceManager _pre = null;
public static collection.app.lock.mydb _db1 = null;
public collection.app.lock.main _main = null;
public collection.app.lock.actsplash _actsplash = null;
public collection.app.lock.actlist _actlist = null;
public collection.app.lock.actsetting _actsetting = null;
public collection.app.lock.actcontact _actcontact = null;
public collection.app.lock.actlock _actlock = null;
public collection.app.lock.handlepassword _handlepassword = null;
public collection.app.lock.force_close _force_close = null;
public collection.app.lock.mylibrary _mylibrary = null;
public static boolean  _done(String _number) throws Exception{
String _pk = "";
String _password = "";
anywheresoftware.b4a.phone.PackageManagerWrapper _pa = null;
anywheresoftware.b4a.objects.IntentWrapper _in1 = null;
 //BA.debugLineNum = 28;BA.debugLine="Sub Done(number As String) As Boolean";
 //BA.debugLineNum = 29;BA.debugLine="Dim pk,password As String";
_pk = "";
_password = "";
 //BA.debugLineNum = 31;BA.debugLine="password = pre.GetString(\"password\")";
_password = _pre.GetString("password");
 //BA.debugLineNum = 33;BA.debugLine="If password = number Then";
if ((_password).equals(_number)) { 
 //BA.debugLineNum = 34;BA.debugLine="StartActivity(actSplash)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._actsplash.getObject()));
 //BA.debugLineNum = 35;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 37;BA.debugLine="pk = db1.getPK(number)";
_pk = _db1._getpk(_number);
 //BA.debugLineNum = 39;BA.debugLine="If pk <> \"\" Then";
if ((_pk).equals("") == false) { 
 //BA.debugLineNum = 40;BA.debugLine="Dim pa As PackageManager";
_pa = new anywheresoftware.b4a.phone.PackageManagerWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Dim in1 As Intent";
_in1 = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 42;BA.debugLine="in1 = pa.GetApplicationIntent(pk)";
_in1 = _pa.GetApplicationIntent(_pk);
 //BA.debugLineNum = 43;BA.debugLine="StartActivity(in1)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_in1.getObject()));
 //BA.debugLineNum = 44;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 };
 //BA.debugLineNum = 48;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 50;BA.debugLine="End Sub";
return false;
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 5;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 6;BA.debugLine="Dim sms1 As SmsInterceptor";
_sms1 = new anywheresoftware.b4a.phone.PhoneEvents.SMSInterceptor();
 //BA.debugLineNum = 7;BA.debugLine="Dim pre As PreferenceManager";
_pre = new anywheresoftware.b4a.objects.preferenceactivity.PreferenceManager();
 //BA.debugLineNum = 8;BA.debugLine="Dim db1 As myDB";
_db1 = new collection.app.lock.mydb();
 //BA.debugLineNum = 9;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 11;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 12;BA.debugLine="sms1.Initialize2(\"sms1\",999)";
_sms1.Initialize2("sms1",processBA,(int) (999));
 //BA.debugLineNum = 13;BA.debugLine="db1.Initialize";
_db1._initialize(processBA);
 //BA.debugLineNum = 14;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 20;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 21;BA.debugLine="sms1.StopListening";
_sms1.StopListening();
 //BA.debugLineNum = 22;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
 //BA.debugLineNum = 16;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 18;BA.debugLine="End Sub";
return "";
}
public static boolean  _sms1_messagereceived(String _from,String _body) throws Exception{
 //BA.debugLineNum = 24;BA.debugLine="Sub sms1_MessageReceived (From As String, Body As";
 //BA.debugLineNum = 25;BA.debugLine="Return Done(Body)";
if (true) return _done(_body);
 //BA.debugLineNum = 26;BA.debugLine="End Sub";
return false;
}
}
