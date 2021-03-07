package cn.dreamn.qianji_auto.ui.views;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class IconView extends AppCompatTextView {

    public IconView(Context context) {
        super(context);
        init(context);
    }
    public IconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public IconView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context) {
//        设置字体图标
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"icon/iconfont.ttf"));
    }
    public void setFont(String fontName){
        this.setText(Html.fromHtml(fontName,Html.FROM_HTML_MODE_LEGACY));
    }



}