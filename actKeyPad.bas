Type=Activity
Version=4
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: False
#End Region

Sub Process_Globals
	Dim PK As String
End Sub

Sub Globals
	Private db1 As myDB
	Private txtval As EditText
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("frmlock")
 	'Activity.Color = Colors.Transparent
 	db1.Initialize
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)
	Activity.Finish
End Sub

Sub Activity_KeyPress (KeyCode As Int) As Boolean 'Return True to consume the event
	Return True
End Sub

#Region KeyPad
Sub numbers_Click
	Dim img1 As ImageView
	img1 = Sender
	txtval.Text = txtval.Text & img1.Tag
	MyLibrary.animationView(img1,False,700)
End Sub

Sub btndelete_Click
	Try
	 txtval.Text = txtval.Text.SubString2(0,txtval.Text.Length - 1)
	Catch
	End Try	
End Sub

Sub btnlogin_Click
	
	If txtval.Text.Length = 0 Then
		ToastMessageShow("خطا : رمز عبور به برنامه را وارد کنید",False)
		Return
	End If
	
	If db1.getPasswordApp(PK) = txtval.Text Then
		Activity.Finish
	Else
		ToastMessageShow("خطا : رمز عبور اشتباه است.دوباره تلاش کنید",False)
	End If
	
End Sub

#End Region
Sub btnback_Click
	Dim i As Intent
	i.Initialize(i.ACTION_MAIN, "")
	i.AddCategory("android.intent.category.HOME")
	i.Flags = 0x10000000
	StartActivity(i)
End Sub