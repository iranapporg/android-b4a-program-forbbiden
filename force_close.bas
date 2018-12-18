Type=Service
Version=4.3
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Service Attributes 
	#StartAtBoot: False
#End Region

Sub Process_Globals
	Dim os As OperatingSystem
	Dim db As myDB
	Dim pre As PreferenceManager
	Dim opener As String
	Dim running As Boolean
End Sub

Sub Service_Create
	os.Initialize("os1")
	db.Initialize
End Sub

Sub Service_Start (StartingIntent As Intent)
Dim c2 As List
Dim pk As String

	c2.Initialize
	c2 = os.getRunningTasks(2)
	pk = getpk(c2.Get(0)).ToLowerCase

	Log(pk & " and " & opener)
	
	If pk <> opener Then
		running = False 
	End If
	
	If running = True Then
		
		StartServiceAt("",DateTime.Now + 500,False)
		Return
		
	End If
	
	If pk.IndexOf("phone") <> -1 Then
		
		If db.ExistAndLockApp(opener) = True Then
			StartServiceAt("",DateTime.Now + 500,False)
			Return
		End If
	End If
	
	If db.ExistAndLockApp(pk) = True Then
	  If pre.GetBoolean("lock_alert") = False Then
	  	MyLibrary.ShowHomeScreen
	  Else
	  	StartActivity(actLock)
	  End If
	End If

	StartServiceAt("",DateTime.Now + 500,False)

End Sub

Sub Service_Destroy

End Sub

Sub getpk(s As Object) As String
	Dim r As Reflector
	r.Target = s
	Dim l As String
	l = r.GetField("topActivity")
	Return l.SubString2(14,l.IndexOf("/"))
End Sub