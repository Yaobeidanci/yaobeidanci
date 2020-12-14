package yaobeidanci.view.dashboard.calender;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;

public class MyCalenderView extends MonthView {
    private Paint mTextPaint = new Paint();
    private Paint mSchemeBasicPaint = new Paint();
    private float mRadio;
    private int mPadding;
    private float mSchemeBaseLine;

    public MyCalenderView(Context context) {
        super(context);

        mTextPaint.setTextSize(dipToPx(context, 6));
        mTextPaint.setColor(0xff111111);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setFakeBoldText(true);

        mSchemeBasicPaint.setAntiAlias(true);
        mSchemeBasicPaint.setStyle(Paint.Style.FILL);
        mSchemeBasicPaint.setTextAlign(Paint.Align.CENTER);
        mSchemeBasicPaint.setColor(0xffed5353);
        mSchemeBasicPaint.setFakeBoldText(true);
        mRadio = dipToPx(getContext(), 14);
        mPadding = dipToPx(getContext(), 4);
        Paint.FontMetrics metrics = mSchemeBasicPaint.getFontMetrics();
        mSchemeBaseLine = mRadio - metrics.descent + (metrics.bottom - metrics.top) / 2 + dipToPx(getContext(), 1);
    }

    /**
     * 绘制选中的日子
     *
     * @param canvas    canvas
     * @param calendar  日历日历calendar
     * @param x         日历Card x起点坐标
     * @param y         日历Card y起点坐标
     * @param hasScheme hasScheme 非标记的日期
     * @return 返回true 则绘制onDrawScheme，因为这里背景色不是是互斥的，所以返回true
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        //这里绘制选中的日子样式，看需求需不需要继续调用onDrawScheme
//        mSelectedPaint.setStyle(Paint.Style.FILL);
//        mSelectedPaint.setColor(0x80cfcfcf);
//        canvas.drawRoundRect(x + mPadding +10, y + mPadding, x + mItemWidth - mPadding-10, y + mItemHeight - mPadding, 30,30,mSelectedPaint);
        return true;
    }

    /**
     * 绘制标记的事件日子
     *
     * @param canvas   canvas
     * @param calendar 日历calendar
     * @param x        日历Card x起点坐标
     * @param y        日历Card y起点坐标
     */
    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {
        //这里绘制标记的日期样式，想怎么操作就怎么操作
    }

    /**
     * 绘制文本
     *
     * @param canvas     canvas
     * @param calendar   日历calendar
     * @param x          日历Card x起点坐标
     * @param y          日历Card y起点坐标
     * @param hasScheme  是否是标记的日期
     * @param isSelected 是否选中
     */
    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        //这里绘制文本，不要再问我怎么隐藏农历了，不要再问我怎么把某个日期换成特殊字符串了，要怎么显示你就在这里怎么画，你不画就不显示，是看你想怎么显示日历的，而不是看框架
        int cx = x + mItemWidth / 2;
        int top = y - mItemHeight / 6;
//        Log.d("TAG", "onDrawText: " + calendar.toString());
        String date = calendar.isCurrentDay() ? "今" : String.valueOf(calendar.getDay());
        if (hasScheme && calendar.isCurrentMonth()) {
//            //绘制农历
//            canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10, mCurMonthLunarTextPaint);
//            mTextPaint.setColor(Color.WHITE);
//            mSchemeBasicPaint.setColor(calendar.getSchemeColor());

            //绘制圆圈
            mSelectedPaint.setColor(Color.rgb(255, 120, 100));
            canvas.drawCircle(x + mItemWidth - mRadio, y + mItemHeight - mRadio, mRadio, mSelectedPaint);
            //绘制日期
            mSchemeTextPaint.setColor(Color.WHITE);
            canvas.drawText(date, cx, mTextBaseLine + top, mSchemeTextPaint);
        } else {

            canvas.drawText(date, cx, mTextBaseLine + top,
                    calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);

            canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10, mCurMonthLunarTextPaint);

        }
    }

    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    private static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
