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
	Dim internal As Boolean
End Sub

Sub Globals
	Private SV1 As ScrollView
	Private blnFinishAnimation As Boolean
	Private lbltitle As Label
	Private imgicon As ImageView
	Private imgmore As ImageView
	Dim ani1,ani2 As AnimationPlus
	
	Dim currentClick As Panel
	Dim currentOffset As Int
	
	Private pnlitem As Panel
	Dim mAnimation As Map
	Private lblpass As Label
	Private lblrun As Label
	Private pnloverlay As Panel
	Private txtval As EditText
	
	Dim db1 As myDB
	
	Dim sm As SlidingMenu
	Dim pk1 As PackageManager
	Private imglock,currentLockImage As ImageView
	Dim currentPass As String
	Private imgicon_1 As ImageView
	Private btnback As Button
	
	Dim pre As PreferenceManager
	
	Dim top As Int : 5dip
	
	Dim APK As List
	
	Dim offsetList As Int
End Sub

Sub Activity_Create(FirstTime As Boolean)
Activity.LoadLayout("frmlist")


	mAnimation.Initialize
	APK.Initialize
	db1.Initialize
	
	txtval.Color = Colors.Transparent
	
	createMenu

	APK = db1.GetPackageList
	
	If APK.IsInitialized = False Then
		ToastMessageShow("خطا : در دستگاه گوشی شما برنامه ای پیدا نشد",False)
		Return
	End If
	
	Try
		For i = 0 To 15
			addItem(i)
		Next
	Catch
	End Try
	
	ProgressDialogHide
	
	offsetList = 16
	
End Sub

Sub addItem(i As Int)
		 Dim package1 As Package
		 Dim p1 As Panel
		 
		 package1.Initialize
		 p1.Initialize("")
		 
		 package1 = APK.Get(i)
		 
		 Try
		 	pk1.GetApplicationLabel(package1.sTitle)
		 Catch
		 
		 End Try
		 
		 SV1.Panel.AddView(p1,0,top,100%x,95dip)
		 p1.LoadLayout("frmtemplate")
		 lbltitle.Text = pk1.GetApplicationLabel(package1.sTitle)
		 imgicon.Background = pk1.GetApplicationIcon(package1.sTitle)
		 imgmore.Tag = Array(i,pnlitem,imglock)
		 top = top + 100dip
		 mAnimation.Put(i,False)
		 
		 lblpass.Tag = i
		 lblrun.Tag = i
		 
		 If db1.getPasswordApp(package1.sTitle).Length > 0 Then
		 	imglock.Visible = True
		 End If
		 
		 
		 Dim rip As RippleView
		 rip.Initialize(lblpass,Colors.White,300,True)
		 rip.Initialize(lblrun,Colors.White,300,True)
		 
		 SV1.Panel.Height = top
End Sub

Sub sv1_ScrollChanged(Position As Int)
 
 If offsetList = APK.Size Then
 	Return
 End If
 
 If Position + SV1.Height >= SV1.Panel.Height Then
  
  ProgressDialogShow("بارگذاری...")
  For i = offsetList To offsetList + 16
   If i = APK.Size Then
    offsetList = APK.Size
    SV1.Panel.Height = SV1.Panel.Height + 55dip
    ProgressDialogHide
    Return
   End If
   
   Try
   	addItem(i)
   Catch
     offsetList = APK.Size
     SV1.Panel.Height = SV1.Panel.Height + 55dip
	ProgressDialogHide
     Return
   End Try
   
  Next
  ProgressDialogHide
  offsetList = offsetList + 17
  
 End If
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)
	If UserClosed = False Then
	 If internal = False Then
	 	Activity.Finish
	Else
		internal = False
	End If
	End If
End Sub

Sub imgmore_Click
	Dim img As ImageView
	img = Sender
	
	Dim ob(2) As Object
	ob = img.Tag
	
	Dim offset As Int
	offset = ob(0)
	
	currentOffset = offset
	currentClick = ob(1)
	currentLockImage = ob(2)
	
	animationView(offset,currentClick)
End Sub

#Region Animation
Sub animationView(offset As Int,p1 As Panel)
	Try
	   If mAnimation.Get(offset) = False Then Then
		 ani1.InitializeTranslate("a1",0,0,-150dip,0)
		 ani1.Duration = 400
		 ani1.SetInterpolator(ani1.INTERPOLATOR_BOUNCE)
		 ani1.start(p1)
		
	   Else
		 ani2.InitializeTranslate("a2",0,0,150dip,0)
		 ani2.Duration = 400
		 ani2.SetInterpolator(ani2.INTERPOLATOR_BOUNCE)
		 ani2.start(p1)
		
	   End If
	  Catch
	  End Try
End Sub

Sub a1_AnimationEnd
	If blnFinishAnimation = False Then
	 currentClick.Left = currentClick.Left-150dip
	 blnFinishAnimation = True
	 mAnimation.Put(currentOffset,True)
	End If
End Sub


Sub a2_AnimationEnd
	If blnFinishAnimation = True Then
		currentClick.Left = currentClick.Left+150dip
		blnFinishAnimation = False
		mAnimation.Put(currentOffset,False)
	End If
End Sub

#End Region

Sub lblrun_Click
Dim b1 As Label
b1 = Sender

	Try
	 force_close.opener = GetPackageInfo(b1.Tag).sTitle
	 force_close.running = True
	 StartActivity(pk1.GetApplicationIntent(GetPackageInfo(b1.Tag).sTitle))
	 animationView(currentOffset,currentClick)
	Catch
	 ToastMessageShow("اخطار : اين برنامه امکان اجرا ندارد",False)
	End Try
	
End Sub

Sub lblpass_Click
	Dim lb As Label
	lb = Sender
	
	Dim pass As String
	pass = db1.getPasswordApp( GetPackageInfo(lb.Tag).sTitle)
	pnloverlay.SetVisibleAnimated(800,True)
	imgicon_1.Background = pk1.GetApplicationIcon( GetPackageInfo(lb.Tag).sTitle)
	pnloverlay.Tag =  GetPackageInfo(lb.Tag).sTitle
	
	If pass <> Null Then
		txtval.Text = pass
		currentPass = pass
	End If
	
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
		db1.newPass(pnloverlay.Tag,txtval.Text)
	Else
		If pre.GetString("password") = txtval.Text Then
			ToastMessageShow("خطا : این رمز برای ورود به برنامه اصلی تعین شده است",False)
			Return
		End If
		
		If db1.PassExist(txtval.Text) > 0 Then
			ToastMessageShow("رمزی که وارد کرده اید، قبلا استفاده شده است. لطفا رمز دیگری را انتخاب کنید.",False)
			Return
		End If
	
		db1.newPass(pnloverlay.Tag,txtval.Text)
	End If
	
	If txtval.Text.Length = 0 Then
	 ToastMessageShow("رمز شما با موفقیت حذف شد",False)
	 currentLockImage.SetVisibleAnimated(800,False)
	Else
	 ToastMessageShow("رمز جدید با موفقیت ثبت شد",False)
	 currentLockImage.SetVisibleAnimated(800,True)
	End If

	animationView(currentOffset,currentClick)
	pnloverlay.SetVisibleAnimated(800,False)
	txtval.Text = ""
	
End Sub

#End Region

#Region Menu
Sub createMenu
   sm.Initialize("Menu")
   Dim offset As Int = 120dip
   sm.BehindOffset = offset
   sm.Mode = sm.RIGHT
   Dim lv1 As ListView
   
   lv1.Initialize("PanelMenu")
   sm.Menu.Color = Colors.rgb(231,76,60)
   lv1.TwoLinesAndBitmap.Label.Gravity = Gravity.RIGHT
   lv1.TwoLinesAndBitmap.Label.Width = 52%x
   lv1.TwoLinesAndBitmap.ImageView.Width = 8%x
   lv1.TwoLinesAndBitmap.ImageView.Height = lv1.TwoLinesAndBitmap.ImageView.Width
   lv1.TwoLinesAndBitmap.ImageView.Left = 53%x
   lv1.TwoLinesAndBitmap.ImageView.top = 16dip
   lv1.TwoLinesAndBitmap.Label.Left = 0
   lv1.TwoLinesAndBitmap.Label.top = 15dip
   lv1.TwoLinesAndBitmap.Label.Typeface = Typeface.DEFAULT_BOLD
   lv1.TwoLinesAndBitmap.Label.Typeface = Typeface.LoadFromAssets("byekan.ttf")
   
   lv1.AddTwoLinesAndBitmap2("قفل مخفی","",LoadBitmap(File.DirAssets,"icon.png"),"about")
   'lv1.AddTwoLinesAndBitmap2("ارتباط با ما","",LoadBitmap(File.DirAssets,"contact1.png"),"contact")
   lv1.AddTwoLinesAndBitmap2("درباره ما","",LoadBitmap(File.DirAssets,"about.png"),"about")
   lv1.AddTwoLinesAndBitmap2("تنظیمات","",LoadBitmap(File.DirAssets,"setting.png"),"setting")
   lv1.AddTwoLinesAndBitmap2("خروج از برنامه","",LoadBitmap(File.DirAssets,"exit.png"),"exit")
   
   sm.Menu.AddView(lv1, 0, 0, 100%x - offset, 100%y) 
End Sub

Sub PanelMenu_ItemClick (Position As Int, Value As Object)
Select Value
 Case "contact"
  internal = True
  StartActivity(actContact)
 Case "about"
  Dim c1 As CustomDialog
  Dim p1 As Panel
  p1.Initialize("")
  c1.AddView(p1,0,0,85%x,300)
  p1.LoadLayout("frmabout")
  c1.Show("درباره ما","اکی","","",Null)
 Case "setting"
  showSetting
 Case "exit"
  If Msgbox2("آیا مایل به خروج هستید؟","خروج","بله","خیر","",Null) = DialogResponse.POSITIVE Then
   Activity.Finish
  End If
End Select
sm.HideMenus
End Sub

#End Region

Sub Activity_KeyPress (KeyCode As Int) As Boolean 'Return True to consume the event
	If KeyCode = KeyCodes.KEYCODE_BACK Then
		Return True
	Else If KeyCode = KeyCodes.KEYCODE_MENU Then
		If sm.Visible = False Then
			sm.ShowMenu
		Else
			sm.HideMenus
		End If
	End If
End Sub

Sub imgmenu_Click
	sm.ShowMenu
End Sub

Sub btnback_Click
	If currentPass <> txtval.Text Then
		If Msgbox2("رمز شما تغییر پیدا کرده است. آیا قصد ذخیره تغییرات جدید را دارید؟","توجه","آری","خیر","",Null) = DialogResponse.POSITIVE Then
			btnlogin_Click
			Return
		End If
	End If
	
	animationView(currentOffset,currentClick)
	pnloverlay.SetVisibleAnimated(800,False)
	txtval.Text = ""
End Sub

Sub showSetting
	internal = True
	StartActivity(actSetting)
End Sub

Sub GetPackageInfo(i As Int) As Package
	
	Dim p1 As Package
	p1.Initialize
	
	p1 = APK.Get(i)
	
	Return p1
	
End Sub