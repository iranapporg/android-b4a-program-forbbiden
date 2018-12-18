package collection.app.lock;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class mylibrary {
private static mylibrary mostCurrent = new mylibrary();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 public anywheresoftware.b4a.keywords.Common __c = null;
public collection.app.lock.main _main = null;
public collection.app.lock.actsplash _actsplash = null;
public collection.app.lock.actlist _actlist = null;
public collection.app.lock.actsetting _actsetting = null;
public collection.app.lock.actcontact _actcontact = null;
public collection.app.lock.actlock _actlock = null;
public collection.app.lock.handlepassword _handlepassword = null;
public collection.app.lock.force_close _force_close = null;
public collection.app.lock.handlesmspassword _handlesmspassword = null;
public static String  _animationview(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.ConcreteViewWrapper _view1,boolean _blnpersist,int _duration) throws Exception{
flm.b4a.animationplus.AnimationPlusWrapper _ani = null;
 //BA.debugLineNum = 7;BA.debugLine="Sub AnimationView(View1 As View,blnPersist As Bool";
 //BA.debugLineNum = 8;BA.debugLine="Dim ani As AnimationPlus";
_ani = new flm.b4a.animationplus.AnimationPlusWrapper();
 //BA.debugLineNum = 9;BA.debugLine="ani.InitializeAlpha(\"ani\",0,1.0)";
_ani.InitializeAlpha(_ba,"ani",(float) (0),(float) (1.0));
 //BA.debugLineNum = 10;BA.debugLine="ani.duration = duration";
_ani.setDuration((long) (_duration));
 //BA.debugLineNum = 11;BA.debugLine="ani.PersistAfter = blnPersist";
_ani.setPersistAfter(_blnpersist);
 //BA.debugLineNum = 12;BA.debugLine="ani.Start(View1)";
_ani.Start((android.view.View)(_view1.getObject()));
 //BA.debugLineNum = 13;BA.debugLine="End Sub";
return "";
}
public static boolean  _filterpackage(anywheresoftware.b4a.BA _ba,String _package) throws Exception{
String[] _filter = null;
int _i = 0;
 //BA.debugLineNum = 15;BA.debugLine="Sub FilterPackage(Package As String) As Boolean";
 //BA.debugLineNum = 16;BA.debugLine="Dim filter(3) As String";
_filter = new String[(int) (3)];
java.util.Arrays.fill(_filter,"");
 //BA.debugLineNum = 17;BA.debugLine="filter(0) = \"com.android.browser\"";
_filter[(int) (0)] = "com.android.browser";
 //BA.debugLineNum = 18;BA.debugLine="filter(1) = \"com.android.gallery3d\"";
_filter[(int) (1)] = "com.android.gallery3d";
 //BA.debugLineNum = 19;BA.debugLine="filter(2) = \"com.android.settings\"";
_filter[(int) (2)] = "com.android.settings";
 //BA.debugLineNum = 21;BA.debugLine="For i = 0 To filter.Length - 1";
{
final int step14 = 1;
final int limit14 = (int) (_filter.length-1);
for (_i = (int) (0); (step14 > 0 && _i <= limit14) || (step14 < 0 && _i >= limit14); _i = ((int)(0 + _i + step14))) {
 //BA.debugLineNum = 22;BA.debugLine="If filter(i) = Package.ToLowerCase Then";
if ((_filter[_i]).equals(_package.toLowerCase())) { 
 //BA.debugLineNum = 23;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 }
};
 //BA.debugLineNum = 27;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 29;BA.debugLine="End Sub";
return false;
}
public static int  _getpackagelistcount(anywheresoftware.b4a.BA _ba) throws Exception{
anywheresoftware.b4a.objects.collections.List _listpk = null;
anywheresoftware.b4a.phone.PackageManagerWrapper _pk1 = null;
 //BA.debugLineNum = 82;BA.debugLine="Sub getPackageListCount As Int";
 //BA.debugLineNum = 83;BA.debugLine="Dim listpk As List";
_listpk = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 84;BA.debugLine="Dim pk1 As PackageManager";
_pk1 = new anywheresoftware.b4a.phone.PackageManagerWrapper();
 //BA.debugLineNum = 86;BA.debugLine="listpk.Initialize";
_listpk.Initialize();
 //BA.debugLineNum = 87;BA.debugLine="listpk = pk1.GetInstalledPackages";
_listpk = _pk1.GetInstalledPackages();
 //BA.debugLineNum = 89;BA.debugLine="Return listpk.Size";
if (true) return _listpk.getSize();
 //BA.debugLineNum = 90;BA.debugLine="End Sub";
return 0;
}
public static String  _loadpackagelist(anywheresoftware.b4a.BA _ba) throws Exception{
anywheresoftware.b4a.objects.collections.List _listpk = null;
String _except = "";
anywheresoftware.b4a.phone.PackageManagerWrapper _pk1 = null;
String _mypackagename = "";
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
anywheresoftware.b4a.student.PackageType _pt = null;
collection.app.lock.mydb _db = null;
int _i = 0;
 //BA.debugLineNum = 39;BA.debugLine="Sub LoadPackageList";
 //BA.debugLineNum = 41;BA.debugLine="Dim listpk        As List";
_listpk = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 42;BA.debugLine="Dim except	   As String = \"'\"";
_except = "'";
 //BA.debugLineNum = 43;BA.debugLine="Dim pk1           As PackageManager";
_pk1 = new anywheresoftware.b4a.phone.PackageManagerWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Dim myPackageName As String";
_mypackagename = "";
 //BA.debugLineNum = 45;BA.debugLine="Dim r             As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 46;BA.debugLine="Dim pt            As PackageType";
_pt = new anywheresoftware.b4a.student.PackageType();
 //BA.debugLineNum = 47;BA.debugLine="Dim db            As myDB";
_db = new collection.app.lock.mydb();
 //BA.debugLineNum = 49;BA.debugLine="listpk.Initialize";
_listpk.Initialize();
 //BA.debugLineNum = 50;BA.debugLine="db.Initialize";
_db._initialize((_ba.processBA == null ? _ba : _ba.processBA));
 //BA.debugLineNum = 52;BA.debugLine="listpk = pk1.GetInstalledPackages";
_listpk = _pk1.GetInstalledPackages();
 //BA.debugLineNum = 54;BA.debugLine="myPackageName = r.GetStaticField(\"anywheresoftwar";
_mypackagename = BA.ObjectToString(_r.GetStaticField("anywheresoftware.b4a.BA","packageName"));
 //BA.debugLineNum = 56;BA.debugLine="For i = 0 To listpk.Size - 1";
{
final int step40 = 1;
final int limit40 = (int) (_listpk.getSize()-1);
for (_i = (int) (0); (step40 > 0 && _i <= limit40) || (step40 < 0 && _i >= limit40); _i = ((int)(0 + _i + step40))) {
 //BA.debugLineNum = 58;BA.debugLine="If listpk.get(i) = myPackageName Then Continue";
if ((_listpk.Get(_i)).equals((Object)(_mypackagename))) { 
if (true) continue;};
 //BA.debugLineNum = 60;BA.debugLine="If pt.IsSystemApp(listpk.get(i)) = False OR Filt";
if (_pt.IsSystemApp((_ba.processBA == null ? _ba : _ba.processBA),BA.ObjectToString(_listpk.Get(_i)))==anywheresoftware.b4a.keywords.Common.False || _filterpackage(_ba,BA.ObjectToString(_listpk.Get(_i)))) { 
 //BA.debugLineNum = 61;BA.debugLine="db.AddPackage2Table(listpk.get(i))";
_db._addpackage2table(BA.ObjectToString(_listpk.Get(_i)));
 //BA.debugLineNum = 62;BA.debugLine="except = except & listpk.Get(i) & \"','\"";
_except = _except+BA.ObjectToString(_listpk.Get(_i))+"','";
 };
 //BA.debugLineNum = 65;BA.debugLine="DoEvents";
anywheresoftware.b4a.keywords.Common.DoEvents();
 }
};
 //BA.debugLineNum = 69;BA.debugLine="except = except.SubString2(0,except.Length -2)";
_except = _except.substring((int) (0),(int) (_except.length()-2));
 //BA.debugLineNum = 71;BA.debugLine="db.ClearTable(except)";
_db._cleartable(_except);
 //BA.debugLineNum = 73;BA.debugLine="Try";
try { //BA.debugLineNum = 74;BA.debugLine="File.WriteString(File.DirInternal,\"countPK\",list";
anywheresoftware.b4a.keywords.Common.File.WriteString(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"countPK",BA.NumberToString(_listpk.getSize()));
 //BA.debugLineNum = 75;BA.debugLine="Log(listpk.Size)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(_listpk.getSize()));
 } 
       catch (Exception e54) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e54); };
 //BA.debugLineNum = 80;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 5;BA.debugLine="End Sub";
return "";
}
public static String  _showhomescreen(anywheresoftware.b4a.BA _ba) throws Exception{
anywheresoftware.b4a.objects.IntentWrapper _i = null;
 //BA.debugLineNum = 31;BA.debugLine="Sub ShowHomeScreen";
 //BA.debugLineNum = 32;BA.debugLine="Dim i As Intent";
_i = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 33;BA.debugLine="i.Initialize(i.ACTION_MAIN, \"\")";
_i.Initialize(_i.ACTION_MAIN,"");
 //BA.debugLineNum = 34;BA.debugLine="i.AddCategory(\"android.intent.category.HOME\")";
_i.AddCategory("android.intent.category.HOME");
 //BA.debugLineNum = 35;BA.debugLine="i.Flags = 0x10000000";
_i.setFlags((int) (0x10000000));
 //BA.debugLineNum = 36;BA.debugLine="StartActivity(i)";
anywheresoftware.b4a.keywords.Common.StartActivity(_ba,(Object)(_i.getObject()));
 //BA.debugLineNum = 37;BA.debugLine="End Sub";
return "";
}
}
