package collection.app.lock.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_frmsplash{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("imgbg").vw.setLeft((int)((52d / 100 * width) - (views.get("imgbg").vw.getWidth() / 2)));
views.get("imgbg").vw.setTop((int)((45d / 100 * height) - (views.get("imgbg").vw.getHeight() / 2)));
//BA.debugLineNum = 4;BA.debugLine="lbl1.Top = imgbg.Top + imgbg.Height + 10dip"[frmsplash/General script]
views.get("lbl1").vw.setTop((int)((views.get("imgbg").vw.getTop())+(views.get("imgbg").vw.getHeight())+(10d * scale)));

}
}