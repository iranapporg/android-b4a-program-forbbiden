Type=Class
Version=3.82
B4A=true
@EndOfDesignText@
'Class module
Sub Class_Globals
Dim sql1 As SQL
End Sub

'Initializes the object. You can add parameters to this method if needed.
Public Sub Initialize
If File.Exists(File.DirInternal,"pass") = False Then
 File.Copy(File.DirAssets,"pass.db",File.DirInternal,"pass.db")
 sql1.Initialize(File.DirInternal,"pass",True)
Else
 sql1.Initialize(File.DirInternal,"pass",False)
End If
End Sub

Sub newPass(PK As String,pass As String)
If existPass(PK) = 0 Then
 sql1.ExecQuery("INSERT INTO tbl_pass(PK,Pass) VALUES('1','1')")
Else
 sql1.ExecQuery("UPDATE TABLE tbl_pass SET Pass = '" & pass & "' WHERE PK = '" & PK & "'")
End If
End Sub

Sub existPass(PK As String) As Int
Dim cu As Cursor
cu = sql1.ExecQuery("SELECT * FROM tbl_pass")
Log(cu.RowCount)
Return cu.RowCount
End Sub