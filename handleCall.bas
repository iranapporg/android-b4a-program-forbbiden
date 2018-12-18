Type=Service
Version=4
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Service Attributes 
	#StartAtBoot: True
#End Region

Sub Process_Globals
	Dim broadcast As BroadCastReceiver
	Dim psl As PhoneStateListener
	Dim p1 As String
	Dim db1 As myDB
	Dim pre As PreferenceManager
End Sub
Sub Service_Create
	broadcast.Initialize("BroadcastReceiver")
	broadcast.addAction("android.intent.action.NEW_OUTGOING_CALL")
	broadcast.SetPriority(2147483647)
	broadcast.registerReceiver("")
	psl.Initialize("PSL", False)
	psl.startListeningForEvent(psl.LISTEN_CALL_STATE)
	db1.Initialize
End Sub

Sub PSL_onCallStateChanged (State As Int, incomingNumber As String)
Dim pk,password As String

	password = pre.GetString("password")

	If password = p1 Then
		StartActivity(actSplash)
		killThread
		Return
	End If

	pk = db1.getPK(p1)
	If pk <> "" Then
	 Dim pa As PackageManager
	 Dim in1 As Intent
	 in1 = pa.GetApplicationIntent(pk)
	 force_close.opener = pk
	 StartActivity(in1)
	 killThread
	 DoEvents
	End If
End Sub

Sub killThread
	Dim t1 As Thread
	t1.Initialise("th")
	t1.Start(Me,"killMes",Null)
End Sub

Sub killMes
KillCall
End Sub

Sub BroadcastReceiver_OnReceive (Action As String,i As Object)
Try
    Dim i2 As Intent
    i2 = i  
    Dim X As String : X = i2.ExtrasToString
    Dim Z As String : Z = X.IndexOf("NUMBER=")
    Dim y As String
    y = X.SubString2(Z+7,Z+24)
    y= y.replace(" ","")
    y= y.replace(",","")
    y= y.replace("a","")
    y= y.replace("n","")
    y= y.replace("d","")
    y= y.replace("r","")
    y= y.replace("o","")
    y= y.replace("i","")
    y= y.replace("d","")
    y= y.replace(".","")
    y = y.replace("+98","")
    y = replaceWord(y)
    p1 = y
Catch
 Log(LastException.Message)
End Try
End Sub

Sub replaceWord(number As String) As String
Dim s1,n1 As String
n1 = number

	For i = 65 To 90
	 s1 = Chr(i)
	 s1 = s1.ToLowerCase
	 n1 = n1.Replace(s1,"")
	Next

Return n1
End Sub

Sub Service_Start (StartingIntent As Intent)

End Sub

Sub Service_Destroy
	broadcast.unregisterReceiver
End Sub

Sub KillCall
   Dim r As Reflector
   r.Target = r.GetContext
   Dim TelephonyManager, TelephonyInterface As Object
   TelephonyManager = r.RunMethod2("getSystemService", "phone", "java.lang.String")
   r.Target = TelephonyManager
   TelephonyInterface = r.RunMethod("getITelephony")
   r.Target = TelephonyInterface
   r.RunMethod("endCall")
End Sub