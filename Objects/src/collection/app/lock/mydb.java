package collection.app.lock;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class mydb extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "collection.app.lock.mydb");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            if (BA.isShellModeRuntimeCheck(ba)) {
			    ba.raiseEvent2(null, true, "CREATE", true, "collection.app.lock.mydb",
                    ba);
                return;
		    }
        }
        ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.sql.SQL _sql1 = null;
public collection.app.lock.main _main = null;
public collection.app.lock.actsplash _actsplash = null;
public collection.app.lock.actlist _actlist = null;
public collection.app.lock.actsetting _actsetting = null;
public collection.app.lock.actcontact _actcontact = null;
public collection.app.lock.actlock _actlock = null;
public collection.app.lock.handlepassword _handlepassword = null;
public collection.app.lock.force_close _force_close = null;
public collection.app.lock.handlesmspassword _handlesmspassword = null;
public collection.app.lock.mylibrary _mylibrary = null;
public static class _package{
public boolean IsInitialized;
public String sTitle;
public String sPassword;
public String sID;
public void Initialize() {
IsInitialized = true;
sTitle = "";
sPassword = "";
sID = "";
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public String  _addpackage2table(String _packagename) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _cu = null;
 //BA.debugLineNum = 149;BA.debugLine="Sub AddPackage2Table(PackageName As String)";
 //BA.debugLineNum = 150;BA.debugLine="Try";
try { //BA.debugLineNum = 151;BA.debugLine="Dim cu As Cursor";
_cu = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 152;BA.debugLine="cu = sql1.ExecQuery(\"SELECT * FROM tbl_package W";
_cu.setObject((android.database.Cursor)(_sql1.ExecQuery("SELECT * FROM tbl_package WHERE sPackageName = '"+_packagename+"'")));
 //BA.debugLineNum = 154;BA.debugLine="If cu.RowCount = 0 Then";
if (_cu.getRowCount()==0) { 
 //BA.debugLineNum = 155;BA.debugLine="sql1.ExecNonQuery(\"INSERT INTO tbl_package(sPac";
_sql1.ExecNonQuery("INSERT INTO tbl_package(sPackageName,sPassword) VALUES('"+_packagename+"','')");
 };
 } 
       catch (Exception e122) {
			ba.setLastException(e122); };
 //BA.debugLineNum = 161;BA.debugLine="End Sub";
return "";
}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 2;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 3;BA.debugLine="Dim sql1 As SQL";
_sql1 = new anywheresoftware.b4a.sql.SQL();
 //BA.debugLineNum = 4;BA.debugLine="Type Package(sTitle As String,sPassword As String";
;
 //BA.debugLineNum = 5;BA.debugLine="End Sub";
return "";
}
public String  _cleartable(String _except) throws Exception{
 //BA.debugLineNum = 110;BA.debugLine="Sub ClearTable(Except As String)";
 //BA.debugLineNum = 111;BA.debugLine="sql1.ExecNonQuery(\"DELETE FROM tbl_package WHERE";
_sql1.ExecNonQuery("DELETE FROM tbl_package WHERE sPackageName NOT IN ("+_except+")");
 //BA.debugLineNum = 112;BA.debugLine="End Sub";
return "";
}
public boolean  _existandlockapp(String _package1) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _cu = null;
 //BA.debugLineNum = 163;BA.debugLine="Sub ExistAndLockApp(Package1 As String) As Boolean";
 //BA.debugLineNum = 165;BA.debugLine="Dim cu As Cursor";
_cu = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 166;BA.debugLine="cu = sql1.ExecQuery(\"SELECT * FROM tbl_package WH";
_cu.setObject((android.database.Cursor)(_sql1.ExecQuery("SELECT * FROM tbl_package WHERE sPackageName ='"+_package1+"'")));
 //BA.debugLineNum = 168;BA.debugLine="If cu.RowCount = 0 Then";
if (_cu.getRowCount()==0) { 
 //BA.debugLineNum = 169;BA.debugLine="Return False";
if (true) return __c.False;
 }else {
 //BA.debugLineNum = 171;BA.debugLine="cu.Position = 0";
_cu.setPosition((int) (0));
 //BA.debugLineNum = 172;BA.debugLine="If cu.GetString(\"sPassword\").Length = 0 Then";
if (_cu.GetString("sPassword").length()==0) { 
 //BA.debugLineNum = 173;BA.debugLine="Return False";
if (true) return __c.False;
 }else {
 //BA.debugLineNum = 175;BA.debugLine="Return True";
if (true) return __c.True;
 };
 };
 //BA.debugLineNum = 179;BA.debugLine="End Sub";
return false;
}
public anywheresoftware.b4a.objects.collections.List  _getpackagelist() throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _cu = null;
anywheresoftware.b4a.objects.collections.List _l1 = null;
int _i = 0;
collection.app.lock.mydb._package _pk = null;
 //BA.debugLineNum = 114;BA.debugLine="Sub GetPackageList As List";
 //BA.debugLineNum = 116;BA.debugLine="Dim cu As Cursor";
_cu = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 117;BA.debugLine="Dim l1 As List";
_l1 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 119;BA.debugLine="l1.Initialize";
_l1.Initialize();
 //BA.debugLineNum = 120;BA.debugLine="cu = sql1.ExecQuery(\"SELECT * FROM tbl_package OR";
_cu.setObject((android.database.Cursor)(_sql1.ExecQuery("SELECT * FROM tbl_package ORDER BY sPassword DESC")));
 //BA.debugLineNum = 122;BA.debugLine="If cu.RowCount > 0 Then";
if (_cu.getRowCount()>0) { 
 //BA.debugLineNum = 124;BA.debugLine="For i = 0 To cu.RowCount - 1";
{
final int step100 = 1;
final int limit100 = (int) (_cu.getRowCount()-1);
for (_i = (int) (0); (step100 > 0 && _i <= limit100) || (step100 < 0 && _i >= limit100); _i = ((int)(0 + _i + step100))) {
 //BA.debugLineNum = 126;BA.debugLine="cu.Position = i";
_cu.setPosition(_i);
 //BA.debugLineNum = 128;BA.debugLine="Dim pk As Package";
_pk = new collection.app.lock.mydb._package();
 //BA.debugLineNum = 129;BA.debugLine="pk.Initialize";
_pk.Initialize();
 //BA.debugLineNum = 131;BA.debugLine="pk.sID       = cu.GetInt(\"sID\")";
_pk.sID = BA.NumberToString(_cu.GetInt("sID"));
 //BA.debugLineNum = 132;BA.debugLine="pk.sPassword = cu.GetString(\"sPassword\")";
_pk.sPassword = _cu.GetString("sPassword");
 //BA.debugLineNum = 133;BA.debugLine="pk.sTitle    = cu.GetString(\"sPackageName\")";
_pk.sTitle = _cu.GetString("sPackageName");
 //BA.debugLineNum = 135;BA.debugLine="l1.Add(pk)";
_l1.Add((Object)(_pk));
 }
};
 //BA.debugLineNum = 139;BA.debugLine="Return l1";
if (true) return _l1;
 }else {
 //BA.debugLineNum = 143;BA.debugLine="Return Null";
if (true) return (anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(__c.Null));
 };
 //BA.debugLineNum = 147;BA.debugLine="End Sub";
return null;
}
public String  _getpasswordapp(String _name) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _c1 = null;
 //BA.debugLineNum = 93;BA.debugLine="Sub getPasswordApp(Name As String) As String";
 //BA.debugLineNum = 94;BA.debugLine="Dim c1 As Cursor";
_c1 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 95;BA.debugLine="c1 = sql1.ExecQuery(\"SELECT * FROM tbl_package WH";
_c1.setObject((android.database.Cursor)(_sql1.ExecQuery("SELECT * FROM tbl_package WHERE sPackageName = '"+_name+"'")));
 //BA.debugLineNum = 97;BA.debugLine="Try";
try { //BA.debugLineNum = 98;BA.debugLine="If c1.RowCount > 0 Then";
if (_c1.getRowCount()>0) { 
 //BA.debugLineNum = 99;BA.debugLine="c1.Position = 0";
_c1.setPosition((int) (0));
 //BA.debugLineNum = 100;BA.debugLine="Return c1.GetString(\"sPassword\")";
if (true) return _c1.GetString("sPassword");
 }else {
 //BA.debugLineNum = 102;BA.debugLine="Return \"\"";
if (true) return "";
 };
 } 
       catch (Exception e88) {
			ba.setLastException(e88); //BA.debugLineNum = 105;BA.debugLine="Return \"\"";
if (true) return "";
 };
 //BA.debugLineNum = 108;BA.debugLine="End Sub";
return "";
}
public String  _getpk(String _spass) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _c1 = null;
 //BA.debugLineNum = 66;BA.debugLine="Public Sub getPK(sPass As String) As String";
 //BA.debugLineNum = 67;BA.debugLine="Dim c1 As Cursor";
_c1 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 68;BA.debugLine="c1 = sql1.ExecQuery(\"Select sPackageName FROM tbl_";
_c1.setObject((android.database.Cursor)(_sql1.ExecQuery("Select sPackageName FROM tbl_package where sPassword = '"+_spass+"'")));
 //BA.debugLineNum = 69;BA.debugLine="If c1.RowCount = 0 Then";
if (_c1.getRowCount()==0) { 
 //BA.debugLineNum = 70;BA.debugLine="Return \"\"";
if (true) return "";
 }else {
 //BA.debugLineNum = 72;BA.debugLine="c1.Position = 0";
_c1.setPosition((int) (0));
 //BA.debugLineNum = 73;BA.debugLine="Return c1.GetString(\"sPackageName\")";
if (true) return _c1.GetString("sPackageName");
 };
 //BA.debugLineNum = 75;BA.debugLine="End Sub";
return "";
}
public int  _getrecordcount(String _ssql) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _c1 = null;
String _s1 = "";
 //BA.debugLineNum = 25;BA.debugLine="Public Sub getRecordCount(sSql As String) As Int";
 //BA.debugLineNum = 26;BA.debugLine="Dim c1 As Cursor";
_c1 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Dim s1 As String";
_s1 = "";
 //BA.debugLineNum = 28;BA.debugLine="Try";
try { //BA.debugLineNum = 29;BA.debugLine="c1 = sql1.ExecQuery(sSql)";
_c1.setObject((android.database.Cursor)(_sql1.ExecQuery(_ssql)));
 //BA.debugLineNum = 30;BA.debugLine="s1 = c1.RowCount";
_s1 = BA.NumberToString(_c1.getRowCount());
 //BA.debugLineNum = 31;BA.debugLine="c1.Close";
_c1.Close();
 //BA.debugLineNum = 32;BA.debugLine="Return s1";
if (true) return (int)(Double.parseDouble(_s1));
 } 
       catch (Exception e29) {
			ba.setLastException(e29); //BA.debugLineNum = 34;BA.debugLine="Try";
try { //BA.debugLineNum = 35;BA.debugLine="File.Copy(File.DirAssets,\"pass.db\",File.DirInter";
__c.File.Copy(__c.File.getDirAssets(),"pass.db",__c.File.getDirInternal(),"pass.db");
 } 
       catch (Exception e32) {
			ba.setLastException(e32); };
 };
 //BA.debugLineNum = 39;BA.debugLine="Return 0";
if (true) return (int) (0);
 //BA.debugLineNum = 40;BA.debugLine="End Sub";
return 0;
}
public anywheresoftware.b4a.objects.collections.List  _getrecords() throws Exception{
anywheresoftware.b4a.objects.collections.List _l1 = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _c1 = null;
int _i = 0;
 //BA.debugLineNum = 77;BA.debugLine="Public Sub GetRecords() As List";
 //BA.debugLineNum = 78;BA.debugLine="Dim l1 As List";
_l1 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 79;BA.debugLine="l1.Initialize";
_l1.Initialize();
 //BA.debugLineNum = 80;BA.debugLine="Dim c1 As Cursor";
_c1 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 82;BA.debugLine="c1 = sql1.ExecQuery(\"SELECT * FROM tbl_package\")";
_c1.setObject((android.database.Cursor)(_sql1.ExecQuery("SELECT * FROM tbl_package")));
 //BA.debugLineNum = 84;BA.debugLine="For i = 0 To c1.RowCount - 1";
{
final int step71 = 1;
final int limit71 = (int) (_c1.getRowCount()-1);
for (_i = (int) (0); (step71 > 0 && _i <= limit71) || (step71 < 0 && _i >= limit71); _i = ((int)(0 + _i + step71))) {
 //BA.debugLineNum = 85;BA.debugLine="c1.Position = i";
_c1.setPosition(_i);
 //BA.debugLineNum = 86;BA.debugLine="l1.Add(c1.GetString(\"sPackageName\"))";
_l1.Add((Object)(_c1.GetString("sPackageName")));
 }
};
 //BA.debugLineNum = 89;BA.debugLine="Return l1";
if (true) return _l1;
 //BA.debugLineNum = 91;BA.debugLine="End Sub";
return null;
}
public String  _initialize(anywheresoftware.b4a.BA _ba) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 7;BA.debugLine="Public Sub Initialize";
 //BA.debugLineNum = 8;BA.debugLine="Try";
try { //BA.debugLineNum = 9;BA.debugLine="If File.Exists(File.DirInternal,\"pass.db\") = False";
if (__c.File.Exists(__c.File.getDirInternal(),"pass.db")==__c.False) { 
 //BA.debugLineNum = 10;BA.debugLine="File.Copy(File.DirAssets,\"pass.db\",File.DirInter";
__c.File.Copy(__c.File.getDirAssets(),"pass.db",__c.File.getDirInternal(),"pass.db");
 };
 } 
       catch (Exception e10) {
			ba.setLastException(e10); };
 //BA.debugLineNum = 14;BA.debugLine="sql1.Initialize(File.DirInternal,\"pass.db\",False";
_sql1.Initialize(__c.File.getDirInternal(),"pass.db",__c.False);
 //BA.debugLineNum = 15;BA.debugLine="End Sub";
return "";
}
public String  _newpass(String _spk,String _spass) throws Exception{
 //BA.debugLineNum = 42;BA.debugLine="Public Sub newPass(sPK As String,sPass As String)";
 //BA.debugLineNum = 43;BA.debugLine="Try";
try { //BA.debugLineNum = 44;BA.debugLine="sql1.ExecNonQuery(\"UPDATE tbl_package SET sPassw";
_sql1.ExecNonQuery("UPDATE tbl_package SET sPassword = '"+_spass+"' WHERE sPackageName = '"+_spk+"'");
 } 
       catch (Exception e40) {
			ba.setLastException(e40); };
 //BA.debugLineNum = 48;BA.debugLine="End Sub";
return "";
}
public int  _passexist(String _pass) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _c1 = null;
 //BA.debugLineNum = 60;BA.debugLine="Sub PassExist(pass As String) As Int";
 //BA.debugLineNum = 61;BA.debugLine="Dim c1 As Cursor";
_c1 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 62;BA.debugLine="c1 = sql1.ExecQuery(\"SELECT * FROM tbl_package whe";
_c1.setObject((android.database.Cursor)(_sql1.ExecQuery("SELECT * FROM tbl_package where sPassword = '"+_pass+"'")));
 //BA.debugLineNum = 63;BA.debugLine="Return c1.RowCount";
if (true) return _c1.getRowCount();
 //BA.debugLineNum = 64;BA.debugLine="End Sub";
return 0;
}
public String  _query(String _ssql) throws Exception{
 //BA.debugLineNum = 17;BA.debugLine="Public Sub Query(sSql As String)";
 //BA.debugLineNum = 18;BA.debugLine="Try";
try { //BA.debugLineNum = 19;BA.debugLine="sql1.ExecNonQuery(sSql)";
_sql1.ExecNonQuery(_ssql);
 } 
       catch (Exception e17) {
			ba.setLastException(e17); //BA.debugLineNum = 21;BA.debugLine="File.Copy(File.DirAssets,\"pass.db\",File.DirIntern";
__c.File.Copy(__c.File.getDirAssets(),"pass.db",__c.File.getDirInternal(),"pass.db");
 };
 //BA.debugLineNum = 23;BA.debugLine="End Sub";
return "";
}
public int  _recordcount(String _name) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _c1 = null;
 //BA.debugLineNum = 50;BA.debugLine="Sub recordCount(name As String) As Int";
 //BA.debugLineNum = 51;BA.debugLine="Dim c1 As Cursor";
_c1 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 52;BA.debugLine="If name = \"\" Then";
if ((_name).equals("")) { 
 //BA.debugLineNum = 53;BA.debugLine="c1 = sql1.ExecQuery(\"SELECT * FROM tbl_package\")";
_c1.setObject((android.database.Cursor)(_sql1.ExecQuery("SELECT * FROM tbl_package")));
 }else {
 //BA.debugLineNum = 55;BA.debugLine="c1 = sql1.ExecQuery(\"SELECT * FROM tbl_package wh";
_c1.setObject((android.database.Cursor)(_sql1.ExecQuery("SELECT * FROM tbl_package where sPackageName = '"+_name+"'")));
 };
 //BA.debugLineNum = 57;BA.debugLine="Return c1.RowCount";
if (true) return _c1.getRowCount();
 //BA.debugLineNum = 58;BA.debugLine="End Sub";
return 0;
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
