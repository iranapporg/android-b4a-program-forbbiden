package collection.app.lock;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.debug.*;

public class handlepassword extends android.app.Service {
	public static class handlepassword_BR extends android.content.BroadcastReceiver {

		@Override
		public void onReceive(android.content.Context context, android.content.Intent intent) {
			android.content.Intent in = new android.content.Intent(context, handlepassword.class);
			if (intent != null)
				in.putExtra("b4a_internal_intent", intent);
			context.startService(in);
		}

	}
    static handlepassword mostCurrent;
	public static BA processBA;
    private ServiceHelper _service;
    public static Class<?> getObject() {
		return handlepassword.class;
	}
	@Override
	public void onCreate() {
        mostCurrent = this;
        if (processBA == null) {
		    processBA = new BA(this, null, null, "collection.app.lock", "collection.app.lock.handlepassword");
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
			processBA.raiseEvent2(null, true, "CREATE", true, "collection.app.lock.handlepassword", processBA, _service);
		}
        BA.LogInfo("** Service (handlepassword) Create **");
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
    	BA.LogInfo("** Service (handlepassword) Start **");
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
        BA.LogInfo("** Service (handlepassword) Destroy **");
		processBA.raiseEvent(null, "service_destroy");
        processBA.service = null;
		mostCurrent = null;
		processBA.setActivityPaused(true);
        processBA.runHook("ondestroy", this, null);
	}
public anywheresoftware.b4a.keywords.Common __c = null;
public static com.rootsoft.broadcastreceiver.BroadCastReceiver _broadcast = null;
public static collection.app.lock.mydb _db1 = null;
public static anywheresoftware.b4a.objects.preferenceactivity.PreferenceManager _pre = null;
public static com.rootsoft.phonestatelistener.PSL _pec = null;
public static anywheresoftware.b4a.phone.PhoneEvents _pe = null;
public static boolean _blnkill = false;
public collection.app.lock.main _main = null;
public collection.app.lock.actsplash _actsplash = null;
public collection.app.lock.actlist _actlist = null;
public collection.app.lock.actsetting _actsetting = null;
public collection.app.lock.actcontact _actcontact = null;
public collection.app.lock.actlock _actlock = null;
public collection.app.lock.force_close _force_close = null;
public collection.app.lock.handlesmspassword _handlesmspassword = null;
public collection.app.lock.mylibrary _mylibrary = null;
public static String  _broadcastreceiver_onreceive(String _action,Object _i) throws Exception{
anywheresoftware.b4a.objects.IntentWrapper _i2 = null;
String _x = "";
String _z = "";
String _y = "";
 //BA.debugLineNum = 49;BA.debugLine="Sub BroadcastReceiver_OnReceive (Action As String,";
 //BA.debugLineNum = 50;BA.debugLine="Try";
try { //BA.debugLineNum = 51;BA.debugLine="Dim i2 As Intent";
_i2 = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 52;BA.debugLine="i2 = i";
_i2.setObject((android.content.Intent)(_i));
 //BA.debugLineNum = 53;BA.debugLine="Dim X As String : X = i2.ExtrasToString";
_x = "";
 //BA.debugLineNum = 53;BA.debugLine="Dim X As String : X = i2.ExtrasToString";
_x = _i2.ExtrasToString();
 //BA.debugLineNum = 54;BA.debugLine="Dim Z As String : Z = X.IndexOf(\"NUMBER=\")";
_z = "";
 //BA.debugLineNum = 54;BA.debugLine="Dim Z As String : Z = X.IndexOf(\"NUMBER=\")";
_z = BA.NumberToString(_x.indexOf("NUMBER="));
 //BA.debugLineNum = 55;BA.debugLine="Dim y As String";
_y = "";
 //BA.debugLineNum = 56;BA.debugLine="y = X.SubString2(Z+7,Z+24)";
_y = _x.substring((int) ((double)(Double.parseDouble(_z))+7),(int) ((double)(Double.parseDouble(_z))+24));
 //BA.debugLineNum = 57;BA.debugLine="y= y.replace(\" \",\"\")";
_y = _y.replace(" ","");
 //BA.debugLineNum = 58;BA.debugLine="y= y.replace(\",\",\"\")";
_y = _y.replace(",","");
 //BA.debugLineNum = 59;BA.debugLine="y= y.replace(\"a\",\"\")";
_y = _y.replace("a","");
 //BA.debugLineNum = 60;BA.debugLine="y= y.replace(\"n\",\"\")";
_y = _y.replace("n","");
 //BA.debugLineNum = 61;BA.debugLine="y= y.replace(\"d\",\"\")";
_y = _y.replace("d","");
 //BA.debugLineNum = 62;BA.debugLine="y= y.replace(\"r\",\"\")";
_y = _y.replace("r","");
 //BA.debugLineNum = 63;BA.debugLine="y= y.replace(\"o\",\"\")";
_y = _y.replace("o","");
 //BA.debugLineNum = 64;BA.debugLine="y= y.replace(\"i\",\"\")";
_y = _y.replace("i","");
 //BA.debugLineNum = 65;BA.debugLine="y= y.replace(\"d\",\"\")";
_y = _y.replace("d","");
 //BA.debugLineNum = 66;BA.debugLine="y= y.replace(\".\",\"\")";
_y = _y.replace(".","");
 //BA.debugLineNum = 67;BA.debugLine="y = y.replace(\"+98\",\"\")";
_y = _y.replace("+98","");
 //BA.debugLineNum = 68;BA.debugLine="y = replaceWord(y)";
_y = _replaceword(_y);
 //BA.debugLineNum = 69;BA.debugLine="Done(y)";
_done(_y);
 } 
       catch (Exception e58) {
			processBA.setLastException(e58); //BA.debugLineNum = 71;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.Log(anywheresoftware.b4a.keywords.Common.LastException(processBA).getMessage());
 };
 //BA.debugLineNum = 74;BA.debugLine="End Sub";
return "";
}
public static String  _deletepasswordlog(String _number) throws Exception{
 //BA.debugLineNum = 169;BA.debugLine="Sub DeletePasswordLog(Number As String)";
 //BA.debugLineNum = 171;BA.debugLine="End Sub";
return "";
}
public static String  _done(String _number) throws Exception{
String _pk = "";
String _password = "";
anywheresoftware.b4a.phone.PackageManagerWrapper _pa = null;
anywheresoftware.b4a.objects.IntentWrapper _in1 = null;
 //BA.debugLineNum = 76;BA.debugLine="Sub Done(number As String)";
 //BA.debugLineNum = 77;BA.debugLine="Dim pk,password As String";
_pk = "";
_password = "";
 //BA.debugLineNum = 79;BA.debugLine="password = pre.GetString(\"password\")";
_password = _pre.GetString("password");
 //BA.debugLineNum = 81;BA.debugLine="If password = number Then";
if ((_password).equals(_number)) { 
 //BA.debugLineNum = 82;BA.debugLine="blnKill = True";
_blnkill = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 83;BA.debugLine="DeletePasswordLog(number)";
_deletepasswordlog(_number);
 //BA.debugLineNum = 84;BA.debugLine="MyLibrary.ShowHomeScreen";
mostCurrent._mylibrary._showhomescreen(processBA);
 //BA.debugLineNum = 85;BA.debugLine="DoEvents";
anywheresoftware.b4a.keywords.Common.DoEvents();
 //BA.debugLineNum = 86;BA.debugLine="StartActivity(actSplash)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._actsplash.getObject()));
 //BA.debugLineNum = 87;BA.debugLine="killThread";
_killthread();
 }else if((_number).equals(_password+"0")) { 
 //BA.debugLineNum = 89;BA.debugLine="StopService(force_close)";
anywheresoftware.b4a.keywords.Common.StopService(processBA,(Object)(mostCurrent._force_close.getObject()));
 //BA.debugLineNum = 90;BA.debugLine="CancelScheduledService(force_close)";
anywheresoftware.b4a.keywords.Common.CancelScheduledService(processBA,(Object)(mostCurrent._force_close.getObject()));
 //BA.debugLineNum = 91;BA.debugLine="pre.SetBoolean(\"service\",False)";
_pre.SetBoolean("service",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 92;BA.debugLine="ToastMessageShow(\"برنامه غیر فعال شد\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("برنامه غیر فعال شد",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 93;BA.debugLine="MyLibrary.ShowHomeScreen";
mostCurrent._mylibrary._showhomescreen(processBA);
 //BA.debugLineNum = 94;BA.debugLine="DoEvents";
anywheresoftware.b4a.keywords.Common.DoEvents();
 //BA.debugLineNum = 95;BA.debugLine="killThread";
_killthread();
 //BA.debugLineNum = 96;BA.debugLine="blnKill = True";
_blnkill = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 97;BA.debugLine="DeletePasswordLog(number & \"0\")";
_deletepasswordlog(_number+"0");
 }else if((_number).equals(_password+"1")) { 
 //BA.debugLineNum = 99;BA.debugLine="StartService(force_close)";
anywheresoftware.b4a.keywords.Common.StartService(processBA,(Object)(mostCurrent._force_close.getObject()));
 //BA.debugLineNum = 100;BA.debugLine="pre.SetBoolean(\"service\",True)";
_pre.SetBoolean("service",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 101;BA.debugLine="ToastMessageShow(\"برنامه فعال شد\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("برنامه فعال شد",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 102;BA.debugLine="MyLibrary.ShowHomeScreen";
mostCurrent._mylibrary._showhomescreen(processBA);
 //BA.debugLineNum = 103;BA.debugLine="DoEvents";
anywheresoftware.b4a.keywords.Common.DoEvents();
 //BA.debugLineNum = 104;BA.debugLine="killThread";
_killthread();
 //BA.debugLineNum = 105;BA.debugLine="blnKill = True";
_blnkill = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 106;BA.debugLine="DeletePasswordLog(number & \"1\")";
_deletepasswordlog(_number+"1");
 }else {
 //BA.debugLineNum = 108;BA.debugLine="pk = db1.getPK(number)";
_pk = _db1._getpk(_number);
 //BA.debugLineNum = 110;BA.debugLine="If pk <> \"\" Then";
if ((_pk).equals("") == false) { 
 //BA.debugLineNum = 111;BA.debugLine="Dim pa As PackageManager";
_pa = new anywheresoftware.b4a.phone.PackageManagerWrapper();
 //BA.debugLineNum = 112;BA.debugLine="Dim in1 As Intent";
_in1 = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 113;BA.debugLine="in1 = pa.GetApplicationIntent(pk)";
_in1 = _pa.GetApplicationIntent(_pk);
 //BA.debugLineNum = 114;BA.debugLine="DeletePasswordLog(number)";
_deletepasswordlog(_number);
 //BA.debugLineNum = 115;BA.debugLine="force_close.opener = pk";
mostCurrent._force_close._opener = _pk;
 //BA.debugLineNum = 116;BA.debugLine="blnKill = True";
_blnkill = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 117;BA.debugLine="MyLibrary.ShowHomeScreen";
mostCurrent._mylibrary._showhomescreen(processBA);
 //BA.debugLineNum = 118;BA.debugLine="DoEvents";
anywheresoftware.b4a.keywords.Common.DoEvents();
 //BA.debugLineNum = 119;BA.debugLine="killThread";
_killthread();
 //BA.debugLineNum = 120;BA.debugLine="force_close.running = True";
mostCurrent._force_close._running = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 121;BA.debugLine="StartActivity(in1)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_in1.getObject()));
 };
 };
 //BA.debugLineNum = 125;BA.debugLine="End Sub";
return "";
}
public static String  _killcall() throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
Object _telephonymanager = null;
Object _telephonyinterface = null;
 //BA.debugLineNum = 148;BA.debugLine="Sub KillCall";
 //BA.debugLineNum = 149;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 150;BA.debugLine="r.Target = r.GetContext";
_r.Target = (Object)(_r.GetContext(processBA));
 //BA.debugLineNum = 151;BA.debugLine="Dim TelephonyManager, TelephonyInterface As Objec";
_telephonymanager = new Object();
_telephonyinterface = new Object();
 //BA.debugLineNum = 152;BA.debugLine="TelephonyManager = r.RunMethod2(\"getSystemService";
_telephonymanager = _r.RunMethod2("getSystemService","phone","java.lang.String");
 //BA.debugLineNum = 153;BA.debugLine="r.Target = TelephonyManager";
_r.Target = _telephonymanager;
 //BA.debugLineNum = 154;BA.debugLine="TelephonyInterface = r.RunMethod(\"getITelephony\")";
_telephonyinterface = _r.RunMethod("getITelephony");
 //BA.debugLineNum = 155;BA.debugLine="r.Target = TelephonyInterface";
_r.Target = _telephonyinterface;
 //BA.debugLineNum = 156;BA.debugLine="r.RunMethod(\"endCall\")";
_r.RunMethod("endCall");
 //BA.debugLineNum = 157;BA.debugLine="End Sub";
return "";
}
public static String  _killmes() throws Exception{
 //BA.debugLineNum = 165;BA.debugLine="Sub killMes";
 //BA.debugLineNum = 166;BA.debugLine="KillCall";
_killcall();
 //BA.debugLineNum = 167;BA.debugLine="End Sub";
return "";
}
public static String  _killthread() throws Exception{
anywheresoftware.b4a.agraham.threading.Threading _t1 = null;
 //BA.debugLineNum = 159;BA.debugLine="Sub killThread";
 //BA.debugLineNum = 160;BA.debugLine="Dim t1 As Thread";
_t1 = new anywheresoftware.b4a.agraham.threading.Threading();
 //BA.debugLineNum = 161;BA.debugLine="t1.Initialise(\"th\")";
_t1.Initialise(processBA,"th");
 //BA.debugLineNum = 162;BA.debugLine="t1.Start(Me,\"killMes\",Null)";
_t1.Start(handlepassword.getObject(),"killMes",(Object[])(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 163;BA.debugLine="End Sub";
return "";
}
public static String  _phoneevent_screenoff(anywheresoftware.b4a.objects.IntentWrapper _intent) throws Exception{
 //BA.debugLineNum = 43;BA.debugLine="Sub phoneevent_ScreenOff (Intent As Intent)";
 //BA.debugLineNum = 44;BA.debugLine="StopService(force_close)";
anywheresoftware.b4a.keywords.Common.StopService(processBA,(Object)(mostCurrent._force_close.getObject()));
 //BA.debugLineNum = 45;BA.debugLine="CancelScheduledService(force_close)";
anywheresoftware.b4a.keywords.Common.CancelScheduledService(processBA,(Object)(mostCurrent._force_close.getObject()));
 //BA.debugLineNum = 46;BA.debugLine="MyLibrary.ShowHomeScreen";
mostCurrent._mylibrary._showhomescreen(processBA);
 //BA.debugLineNum = 47;BA.debugLine="End Sub";
return "";
}
public static String  _phoneevent_screenon(anywheresoftware.b4a.objects.IntentWrapper _intent) throws Exception{
 //BA.debugLineNum = 37;BA.debugLine="Sub phoneevent_ScreenOn (Intent As Intent)";
 //BA.debugLineNum = 38;BA.debugLine="If pre.GetBoolean(\"service\") = True Then";
if (_pre.GetBoolean("service")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 39;BA.debugLine="StartService(force_close)";
anywheresoftware.b4a.keywords.Common.StartService(processBA,(Object)(mostCurrent._force_close.getObject()));
 };
 //BA.debugLineNum = 41;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 5;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 6;BA.debugLine="Dim broadcast As BroadCastReceiver";
_broadcast = new com.rootsoft.broadcastreceiver.BroadCastReceiver();
 //BA.debugLineNum = 7;BA.debugLine="Dim db1 As myDB";
_db1 = new collection.app.lock.mydb();
 //BA.debugLineNum = 8;BA.debugLine="Dim pre As PreferenceManager";
_pre = new anywheresoftware.b4a.objects.preferenceactivity.PreferenceManager();
 //BA.debugLineNum = 9;BA.debugLine="Dim pec As PhoneStateListener";
_pec = new com.rootsoft.phonestatelistener.PSL();
 //BA.debugLineNum = 10;BA.debugLine="Dim pe As PhoneEvents";
_pe = new anywheresoftware.b4a.phone.PhoneEvents();
 //BA.debugLineNum = 11;BA.debugLine="Dim blnKill As Boolean";
_blnkill = false;
 //BA.debugLineNum = 12;BA.debugLine="End Sub";
return "";
}
public static String  _psl_oncallstatechanged(int _state,String _incomingnumber) throws Exception{
 //BA.debugLineNum = 30;BA.debugLine="Sub PSL_onCallStateChanged (State As Int, incoming";
 //BA.debugLineNum = 31;BA.debugLine="If blnKill = True Then";
if (_blnkill==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 32;BA.debugLine="blnKill = False";
_blnkill = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 33;BA.debugLine="killThread";
_killthread();
 };
 //BA.debugLineNum = 35;BA.debugLine="End Sub";
return "";
}
public static String  _replaceword(String _number) throws Exception{
String _s1 = "";
String _n1 = "";
int _i = 0;
 //BA.debugLineNum = 127;BA.debugLine="Sub replaceWord(number As String) As String";
 //BA.debugLineNum = 128;BA.debugLine="Dim s1,n1 As String";
_s1 = "";
_n1 = "";
 //BA.debugLineNum = 129;BA.debugLine="n1 = number";
_n1 = _number;
 //BA.debugLineNum = 131;BA.debugLine="For i = 65 To 90";
{
final int step110 = 1;
final int limit110 = (int) (90);
for (_i = (int) (65); (step110 > 0 && _i <= limit110) || (step110 < 0 && _i >= limit110); _i = ((int)(0 + _i + step110))) {
 //BA.debugLineNum = 132;BA.debugLine="s1 = Chr(i)";
_s1 = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr(_i));
 //BA.debugLineNum = 133;BA.debugLine="s1 = s1.ToLowerCase";
_s1 = _s1.toLowerCase();
 //BA.debugLineNum = 134;BA.debugLine="n1 = n1.Replace(s1,\"\")";
_n1 = _n1.replace(_s1,"");
 }
};
 //BA.debugLineNum = 137;BA.debugLine="Return n1";
if (true) return _n1;
 //BA.debugLineNum = 138;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 14;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 16;BA.debugLine="pe.Initialize(\"phoneevent\")";
_pe.Initialize(processBA,"phoneevent");
 //BA.debugLineNum = 18;BA.debugLine="pec.Initialize(\"PSL\",False)";
_pec.Initialize(processBA,"PSL",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 20;BA.debugLine="db1.Initialize";
_db1._initialize(processBA);
 //BA.debugLineNum = 22;BA.debugLine="broadcast.Initialize(\"BroadcastReceiver\")";
_broadcast.Initialize(processBA,"BroadcastReceiver");
 //BA.debugLineNum = 23;BA.debugLine="broadcast.addAction(\"android.intent.action.NEW_OU";
_broadcast.addAction("android.intent.action.NEW_OUTGOING_CALL");
 //BA.debugLineNum = 24;BA.debugLine="broadcast.SetPriority(2147483647)";
_broadcast.SetPriority((int) (2147483647));
 //BA.debugLineNum = 25;BA.debugLine="broadcast.registerReceiver(\"\")";
_broadcast.registerReceiver("");
 //BA.debugLineNum = 26;BA.debugLine="pec.startListeningForEvent(pec.LISTEN_CALL_STATE)";
_pec.startListeningForEvent(_pec.LISTEN_CALL_STATE);
 //BA.debugLineNum = 28;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 144;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 145;BA.debugLine="broadcast.unregisterReceiver";
_broadcast.unregisterReceiver();
 //BA.debugLineNum = 146;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
 //BA.debugLineNum = 140;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 142;BA.debugLine="End Sub";
return "";
}
}
