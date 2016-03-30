package com.pgt.JSTL;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by zhangxiaodong on 16-2-26.
 */
public class JSTLDateFormat extends TagSupport{

    private static final long serialVersionUID = -2312310581852395045L;

    private String value;

    private String style;

    @Override
    public int doStartTag() throws JspException {
        String date = "" + value;
        long time = Long.valueOf(date);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        SimpleDateFormat dateformat = new SimpleDateFormat(style);
        String s = dateformat.format(c.getTime());
        try {
            pageContext.getOut().write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.doStartTag();
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}
