package collection.app.lock.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_frmlist{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("panel2").vw.setLeft((int)((50d / 100 * width) - (views.get("panel2").vw.getWidth() / 2)));
views.get("panel2").vw.setTop((int)((50d / 100 * height) - (views.get("panel2").vw.getHeight() / 2)));
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0d);
//BA.debugLineNum = 4;BA.debugLine="AutoScaleAll"[frmlist/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);

}
}