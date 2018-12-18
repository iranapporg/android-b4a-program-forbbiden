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

End Sub

Sub Globals

End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("frmlock")
 	Activity.Color = Colors.Transparent
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)
	Activity.Finish
End Sub

Sub Activity_KeyPress (KeyCode As Int) As Boolean 'Return True to consume the event
	Return True
End Sub

Sub btnback_Click
	Dim i As Intent
	i.Initialize(i.ACTION_MAIN, "")
	i.AddCategory("android.intent.category.HOME")
	i.Flags = 0x10000000
	StartActivity(i)
End Sub