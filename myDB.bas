Type=Class
Version=4.3
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
'Class module
Sub Class_Globals
 Dim sql1 As SQL
 Type Package(sTitle As String,sPassword As String,sID As String)
End Sub

Public Sub Initialize
Try
If File.Exists(File.DirInternal,"pass.db") = False Then
  File.Copy(File.DirAssets,"pass.db",File.DirInternal,"pass.db")
End If
Catch
End Try
  sql1.Initialize(File.DirInternal,"pass.db",False)
End Sub

Public Sub Query(sSql As String)
Try
 sql1.ExecNonQuery(sSql)
Catch
 File.Copy(File.DirAssets,"pass.db",File.DirInternal,"pass.db")
End Try
End Sub

Public Sub getRecordCount(sSql As String) As Int
Dim c1 As Cursor
Dim s1 As String
Try
 c1 = sql1.ExecQuery(sSql)
 s1 = c1.RowCount
 c1.Close
 Return s1
Catch
	Try
	 File.Copy(File.DirAssets,"pass.db",File.DirInternal,"pass.db")
	Catch
	End Try
End Try
Return 0
End Sub

Public Sub newPass(sPK As String,sPass As String)
Try
  sql1.ExecNonQuery("UPDATE tbl_package SET sPassword = '" & sPass & "' WHERE sPackageName = '" & sPK & "'")
Catch

End Try
End Sub

Sub recordCount(name As String) As Int
Dim c1 As Cursor
If name = "" Then
 c1 = sql1.ExecQuery("SELECT * FROM tbl_package")
Else
 c1 = sql1.ExecQuery("SELECT * FROM tbl_package where sPackageName = '" & name & "'")
End If
Return c1.RowCount
End Sub

Sub PassExist(pass As String) As Int
Dim c1 As Cursor
c1 = sql1.ExecQuery("SELECT * FROM tbl_package where sPassword = '" & pass & "'")
Return c1.RowCount
End Sub

Public Sub getPK(sPass As String) As String
Dim c1 As Cursor
c1 = sql1.ExecQuery("Select sPackageName FROM tbl_package where sPassword = '" & sPass & "'")
If c1.RowCount = 0 Then
 Return ""
Else
 c1.Position = 0
 Return c1.GetString("sPackageName")
End If
End Sub

Public Sub GetRecords() As List
Dim l1 As List
l1.Initialize
Dim c1 As Cursor

c1 = sql1.ExecQuery("SELECT * FROM tbl_package")

	For i = 0 To c1.RowCount - 1
	 c1.Position = i
	 l1.Add(c1.GetString("sPackageName"))
	Next

Return l1

End Sub

Sub getPasswordApp(Name As String) As String
	Dim c1 As Cursor
	c1 = sql1.ExecQuery("SELECT * FROM tbl_package WHERE sPackageName = '" & Name & "'")
	
	Try
		If c1.RowCount > 0 Then
			c1.Position = 0
			Return c1.GetString("sPassword")
		Else
			Return ""
		End If
	Catch
		Return ""
	End Try
	
End Sub

Sub ClearTable(Except As String)
	sql1.ExecNonQuery("DELETE FROM tbl_package WHERE sPackageName NOT IN (" & Except & ")")
End Sub

Sub GetPackageList As List
	
	Dim cu As Cursor
	Dim l1 As List
	
	l1.Initialize
	cu = sql1.ExecQuery("SELECT * FROM tbl_package ORDER BY sPassword DESC")
	
	If cu.RowCount > 0 Then
	
		For i = 0 To cu.RowCount - 1
		
			cu.Position = i
			
			Dim pk As Package
			pk.Initialize
			
			pk.sID       = cu.GetInt("sID")
			pk.sPassword = cu.GetString("sPassword")
			pk.sTitle    = cu.GetString("sPackageName")
			
			l1.Add(pk)
		
		Next
		
		Return l1
	
	Else
	
		Return Null
	
	End If
	
End Sub

Sub AddPackage2Table(PackageName As String)
	Try
		Dim cu As Cursor
		cu = sql1.ExecQuery("SELECT * FROM tbl_package WHERE sPackageName = '" & PackageName & "'")
		
		If cu.RowCount = 0 Then
			sql1.ExecNonQuery("INSERT INTO tbl_package(sPackageName,sPassword) VALUES('" & PackageName & "','')")
		End If
		
	Catch
	
	End Try
End Sub

Sub ExistAndLockApp(Package1 As String) As Boolean
	
	Dim cu As Cursor
	cu = sql1.ExecQuery("SELECT * FROM tbl_package WHERE sPackageName ='" & Package1 & "'")
	
	If cu.RowCount = 0 Then
		Return False
	Else
		cu.Position = 0
		If cu.GetString("sPassword").Length = 0 Then
			Return False
		Else
			Return True
		End If
	End If
	
End Sub