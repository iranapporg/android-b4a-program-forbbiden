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
	Private pre As PreferenceManager
	Private txtpass As EditText
	Private rbmessage As RadioButton
	Private rbhome As RadioButton
	Private imgservice As ImageView
	Private db1 As myDB
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("frmsetting")
	txtpass.Color = Colors.Transparent
	txtpass.Text = pre.GetString("password")
	
	db1.Initialize
	
	If pre.GetBoolean("lock_alert") = True Then
		rbmessage.Checked = True
	End If
	
	If IsPaused(force_close) = True Then
		imgservice.SetBackgroundImage(LoadBitmap(File.DirAssets,"toggle-off.png"))
	Else
		imgservice.SetBackgroundImage(LoadBitmap(File.DirAssets,"toggle-on.png"))
	End If
	
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)
	Activity.Finish
End Sub

Sub tnsavepass_Click
	If txtpass.Text.Length < 2 Then
		ToastMessageShow("خطا : رمز عبور حداقل باید 2 کاراکتر باشد",False)
		Return
	End If
	
	If db1.PassExist(txtpass.Text) > 0 Then
		Msgbox("این رمز برای برنامه قفل شده انتخاب شده است" & CRLF & "لطفا رمز دیگری انتخاب کنید","خطا")
		Return
	End If
	
	pre.SetString("password",txtpass.Text)
	ToastMessageShow("رمز عبور با موفقیت ثبت شد",False)
End Sub

Sub lblhome_Click

End Sub

Sub lblmessage_Click

End Sub

Sub rbhome_CheckedChange(Checked As Boolean)
	pre.SetBoolean("lock_alert",False)
End Sub

Sub rbmessage_CheckedChange(Checked As Boolean)
	pre.SetBoolean("lock_alert",True)
End Sub

Sub lblservice_Click
	
End Sub

Sub imgservice_Click
	If IsPaused(force_close) = True Then
		imgservice.SetBackgroundImage(LoadBitmap(File.DirAssets,"toggle-on.png"))
		pre.SetBoolean("service",True)
		StartService(force_close)
		ToastMessageShow("برنامه فعال شد",False)
	Else
		imgservice.SetBackgroundImage(LoadBitmap(File.DirAssets,"toggle-off.png"))
		pre.SetBoolean("service",False)
		StopService(force_close)
		CancelScheduledService(force_close)
		ToastMessageShow("برنامه غیر فعال شد",False)
	End If
End Sub