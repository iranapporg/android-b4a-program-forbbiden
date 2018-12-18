Type=Service
Version=4.3
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Service Attributes 
	#StartAtBoot: False
#End Region

Sub Process_Globals
	Dim sms1 As SmsInterceptor
	Dim pre As PreferenceManager
	Dim db1 As myDB
End Sub

Sub Service_Create
	sms1.Initialize2("sms1",999)
	db1.Initialize
End Sub

Sub Service_Start (StartingIntent As Intent)

End Sub

Sub Service_Destroy
	sms1.StopListening
End Sub

Sub sms1_MessageReceived (From As String, Body As String) As Boolean
	Return Done(Body)
End Sub

Sub Done(number As String) As Boolean
	Dim pk,password As String

	password = pre.GetString("password")

	If password = number Then
		StartActivity(actSplash)
		Return True
	Else
		pk = db1.getPK(number)
	
		If pk <> "" Then
		 Dim pa As PackageManager
		 Dim in1 As Intent
		 in1 = pa.GetApplicationIntent(pk)
		 StartActivity(in1)
		 Return True
		End If
	End If
	
	Return False
	
End Sub