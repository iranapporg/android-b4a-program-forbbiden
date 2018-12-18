Type=Activity
Version=4.3
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: False
#End Region

Sub Process_Globals
	Private tim As Timer
End Sub

Sub Globals
	Private imgbg As ImageView
	Private lbl1 As Label
	Dim pre As PreferenceManager
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("frmsplash")

	If pre.GetBoolean("service") = True Then
		StartService(force_close)
	End If
	
	tim.Initialize("timer",1300)
	tim.Enabled = True
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)
	 Activity.Finish
End Sub

Sub timer_Tick
	tim.Enabled = False
	
	If pre.GetBoolean("service") = True Then
		StartService(force_close)
	End If
	
	Activity.Finish
	ProgressDialogShow2("منتظر بمانید",False)
	StartActivity(actList)
	
End Sub

Sub Activity_KeyPress (KeyCode As Int) As Boolean 'Return True to consume the event
	If KeyCode = KeyCodes.KEYCODE_BACK Then
		Return True
	End If
End Sub
