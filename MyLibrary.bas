Type=StaticCode
Version=4.3
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
'Code module
'Subs in this code module will be accessible from all modules.
Sub Process_Globals

End Sub

Sub AnimationView(View1 As View,blnPersist As Boolean,duration As Int)
	Dim ani As AnimationPlus
	ani.InitializeAlpha("ani",0,1.0)
	ani.duration = duration
	ani.PersistAfter = blnPersist
	ani.Start(View1)
End Sub

Sub FilterPackage(Package As String) As Boolean
	Dim filter(3) As String
	filter(0) = "com.android.browser"
	filter(1) = "com.android.gallery3d"
	filter(2) = "com.android.settings"
	
	For i = 0 To filter.Length - 1
		If filter(i) = Package.ToLowerCase Then
			Return True
		End If
	Next
	
	Return False
	
End Sub

Sub ShowHomeScreen
	Dim i As Intent
	i.Initialize(i.ACTION_MAIN, "")
	i.AddCategory("android.intent.category.HOME")
	i.Flags = 0x10000000
	StartActivity(i)
End Sub

Sub LoadPackageList

	Dim listpk        As List
	Dim except	   As String = "'"
	Dim pk1           As PackageManager
	Dim myPackageName As String
	Dim r             As Reflector
	Dim pt            As PackageType
	Dim db            As myDB
	
	listpk.Initialize
	db.Initialize
	
	listpk = pk1.GetInstalledPackages
	
	myPackageName = r.GetStaticField("anywheresoftware.b4a.BA", "packageName")
	
	For i = 0 To listpk.Size - 1
		
	 If listpk.get(i) = myPackageName Then Continue
	 
	 If pt.IsSystemApp(listpk.get(i)) = False OR FilterPackage(listpk.get(i)) Then
		 db.AddPackage2Table(listpk.get(i))
		 except = except & listpk.Get(i) & "','"
	 End If	
		
	 DoEvents
		
	Next
	
	except = except.SubString2(0,except.Length -2)
	
	db.ClearTable(except)
	
	Try
		File.WriteString(File.DirInternal,"countPK",listpk.Size)
		Log(listpk.Size)
	Catch
	
	End Try
	
End Sub

Sub getPackageListCount As Int
	Dim listpk As List
	Dim pk1 As PackageManager
	
	listpk.Initialize
	listpk = pk1.GetInstalledPackages
	
	Return listpk.Size
End Sub