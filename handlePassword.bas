Type=Service
Version=4.3
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Service Attributes 
	#StartAtBoot: True
#End Region

Sub Process_Globals
	Dim broadcast As BroadCastReceiver
	Dim db1 As myDB
	Dim pre As PreferenceManager
	Dim pec As PhoneStateListener
	Dim pe As PhoneEvents
	Dim blnKill As Boolean
End Sub

Sub Service_Create
	
	pe.Initialize("phoneevent")
	
	pec.Initialize("PSL",False)
	
	db1.Initialize
	
	broadcast.Initialize("BroadcastReceiver")
	broadcast.addAction("android.intent.action.NEW_OUTGOING_CALL")
	broadcast.SetPriority(2147483647)
	broadcast.registerReceiver("")
	pec.startListeningForEvent(pec.LISTEN_CALL_STATE)
	
End Sub

Sub PSL_onCallStateChanged (State As Int, incomingNumber As String)
	If blnKill = True Then
		blnKill = False
		killThread
	End If
End Sub

Sub phoneevent_ScreenOn (Intent As Intent)
	If pre.GetBoolean("service") = True Then
		StartService(force_close)
	End If
End Sub

Sub phoneevent_ScreenOff (Intent As Intent)
	StopService(force_close)
	CancelScheduledService(force_close)
	MyLibrary.ShowHomeScreen
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
    Done(y)
Catch
	Log(LastException.Message)
End Try

End Sub

Sub Done(number As String)
	Dim pk,password As String

	password = pre.GetString("password")
	
	If password = number Then
		blnKill = True
		DeletePasswordLog(number)
		MyLibrary.ShowHomeScreen
		DoEvents
		StartActivity(actSplash)
		killThread
	Else If number = password & "0" Then
		StopService(force_close)
		CancelScheduledService(force_close)
		pre.SetBoolean("service",False)
		ToastMessageShow("برنامه غیر فعال شد",False)
		MyLibrary.ShowHomeScreen
		DoEvents
		killThread
		blnKill = True
		DeletePasswordLog(number & "0")
	Else If number = password & "1" Then
		StartService(force_close)
		pre.SetBoolean("service",True)
		ToastMessageShow("برنامه فعال شد",False)
		MyLibrary.ShowHomeScreen
		DoEvents
		killThread
		blnKill = True
		DeletePasswordLog(number & "1")
	Else
		pk = db1.getPK(number)
	
		If pk <> "" Then
		 Dim pa As PackageManager
		 Dim in1 As Intent
			 in1 = pa.GetApplicationIntent(pk)
			 DeletePasswordLog(number)
			 force_close.opener = pk
			 blnKill = True
			 MyLibrary.ShowHomeScreen
			 DoEvents
			 killThread
			 force_close.running = True
			 StartActivity(in1)
		End If
	End If
	
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

Sub killThread
	Dim t1 As Thread
	t1.Initialise("th")
	t1.Start(Me,"killMes",Null)
End Sub

Sub killMes
	KillCall
End Sub

Sub DeletePasswordLog(Number As String)

End Sub