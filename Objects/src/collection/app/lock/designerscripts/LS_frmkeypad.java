package collection.app.lock.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_frmkeypad{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 1;BA.debugLine="Panel2.HorizontalCenter = 50%x"[frmkeypad/General script]
views.get("panel2").vw.setLeft((int)((50d / 100 * width) - (views.get("panel2").vw.getWidth() / 2)));
//BA.debugLineNum = 2;BA.debugLine="Panel2.VerticalCenter = 50%y"[frmkeypad/General script]
views.get("panel2").vw.setTop((int)((50d / 100 * height) - (views.get("panel2").vw.getHeight() / 2)));
//BA.debugLineNum = 3;BA.debugLine="AutoScaleRate(0)"[frmkeypad/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0d);
//BA.debugLineNum = 4;BA.debugLine="AutoScaleAll"[frmkeypad/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);

}
}