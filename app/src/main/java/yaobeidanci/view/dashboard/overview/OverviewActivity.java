package yaobeidanci.view.dashboard.overview;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import yaobeidanci.MyUtil;
import yaobeidanci.view.MainActivity;
import yaobeidanci.view.R;
import yaobeidanci.view.book.StudyPlan;

/**
 * 255行，272行
 */
public class OverviewActivity extends AppCompatActivity {
    private Button button_return;
    private RadioGroup radioGroup_amount = null;
    private RadioGroup radioGroup_time = null;
    private BarChart[] barChart = new BarChart[2];
    private LineChart[] lineChart = new LineChart[2];
    private TextView textView_review_amount = null;
    private TextView textView_learn_amount = null;
    private TextView textView_review_amount_hint = null;
    private TextView textView_learn_amount_hint = null;
    private TextView textView_learn_time = null;
    private TextView textView_total_time = null;
    private TextView textView_learn_time_hint = null;
    private final List<List<Integer>> word_amount = new ArrayList<>();
    private final ArrayList<Entry> time = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dashboard_overview);

        FindViews();
        SetListeners();
        AcquireData();
    }

    private void FindViews() {
        button_return = findViewById(R.id.button_return);
        radioGroup_amount = findViewById(R.id.radioGroup_amount);
        radioGroup_time = findViewById(R.id.radioGroup_time);
        barChart[0] = findViewById(R.id.barChart_week);
        barChart[1] = findViewById(R.id.barChart_month);
        barChart[1].setVisibility(View.INVISIBLE);
        textView_learn_amount = findViewById(R.id.textView_learn_amount);
        textView_review_amount = findViewById(R.id.textView_review_amount);
        textView_learn_amount_hint = findViewById(R.id.textView_learn_amount_hint);
        textView_review_amount_hint = findViewById(R.id.textView_review_amount_hint);
        lineChart[0] = findViewById(R.id.lineChart_week);
        lineChart[1] = findViewById(R.id.lineChart_month);
        lineChart[1].setVisibility(View.INVISIBLE);
        textView_learn_time = findViewById(R.id.textView_learn_time);
        textView_total_time = findViewById(R.id.textView_total_time);
        textView_learn_time_hint = findViewById(R.id.textView_learn_time_hint);
    }

    private void SetListeners() {
        button_return.setOnClickListener(onClickListener);
        radioGroup_amount.setOnCheckedChangeListener(onCheckedChangeListener);
        radioGroup_time.setOnCheckedChangeListener(onCheckedChangeListener);
        barChart[0].setOnChartValueSelectedListener(onBarChartValueSelectedListener);
        lineChart[0].setOnChartValueSelectedListener(onLineChartValueSelectedListener);
    }

    private void InitBarCharts() {
        //堆叠条形图
        for (int i = 0; i < 2; i++) {
            barChart[i].setDescription("");                  //不描述
            barChart[i].setDoubleTapToZoomEnabled(false);    //取消双击放大
            barChart[i].setPinchZoom(false);
            barChart[i].setDrawGridBackground(false);
            barChart[i].setDrawBarShadow(false);
            barChart[i].setDrawValueAboveBar(false);
            barChart[i].setHighlightFullBarEnabled(false);
            barChart[i].getAxisLeft().setAxisMinValue(0);
            barChart[i].getAxisRight().setEnabled(false);
            barChart[i].getAxisLeft().setEnabled(false);     //去掉左右侧y轴
        }
//        barChart_amount[0].setOnChartValueSelectedListener();

        barChart[0].setMaxVisibleValueCount(7);
        barChart[0].getAxisLeft().setAxisMaxValue(25);   //Y轴最大值
        XAxis xLabels = barChart[0].getXAxis();
        xLabels.setValueFormatter(new AxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                final SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
                final ArrayList<Date> datesFinal = GetLastWeekDate();
                int val = (int) value;
                if (val == 6) {
                    return "今日";
                }
                return formatter.format(datesFinal.get(val));
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });
        xLabels.setDrawAxisLine(false);
        xLabels.setPosition(XAxis.XAxisPosition.BOTTOM);

        //标签
        Legend l = barChart[0].getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setFormToTextSpace(4f);   //图标与文字间距
        l.setXEntrySpace(6f);       //标签之间的间距
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_INSIDE);
        SetBarChartData();
    }

    private void SetBarChartData() {
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        //获取数据
        for (int i = 0; i < 7; i++) {
            int val1 = word_amount.get(0).get(i);
            int val2 = word_amount.get(1).get(i);
            yVals1.add(new BarEntry(i, new float[]{val1, val2}));
        }

        BarDataSet set1;

        if (barChart[0].getData() != null &&
                barChart[0].getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) barChart[0].getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            barChart[0].getData().notifyDataChanged();
            barChart[0].notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "");
            set1.setColors(getColors());
            set1.setStackLabels(new String[]{"学习", "复习"});

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setBarWidth(0.1f);
//            data.setValueFormatter(new DefaultValueFormatter());
            data.setValueTextColor(Color.WHITE);

            barChart[0].setData(data);
        }
        barChart[0].setFitBars(true);
        barChart[0].invalidate();
    }

    private int[] getColors() {
        int[] colors = new int[2];
        colors[0] = Color.rgb(255, 102, 0);
        colors[1] = Color.rgb(200, 70, 200);
        return colors;
    }

    private void InitLineCharts() {
        for (int i = 0; i < 2; i++) {
            //后台绘制
            lineChart[0].setDrawGridBackground(false);
            lineChart[0].setDescription("");
            //设置支持触控手势
            lineChart[0].setTouchEnabled(true);
            //设置缩放
            lineChart[0].setDragEnabled(true);
            //设置推动
            lineChart[0].setScaleEnabled(true);
            //如果禁用,扩展可以在x轴和y轴分别完成
            lineChart[0].setPinchZoom(false);
            lineChart[0].getAxisLeft().setEnabled(false);
            lineChart[0].getAxisRight().setEnabled(false);
        }

//        //x轴
//        LimitLine llXAxis = new LimitLine(10f, "标记");
//        //设置线宽
//        llXAxis.setLineWidth(0f);
//        //
//        llXAxis.enableDashedLine(10f, 10f, 0f);
//        //设置
//        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//        llXAxis.setTextSize(10f);

        XAxis xAxis = lineChart[0].getXAxis();
        xAxis.setValueFormatter(new AxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                final SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
                final ArrayList<Date> datesFinal = GetLastWeekDate();
                int val = (int) value;
                if (val == 6) {
                    return "今日";
                }
                return formatter.format(datesFinal.get(val));
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisLineColor(Color.WHITE);

        //设置数据
        SetLineChartData(time);

        //默认动画
        lineChart[0].animateX(2000);
        //刷新
        //mChart.invalidate();

    }

    public void AcquireData() {
        final JSONObject object1 = new JSONObject();
        try {
            object1.put("uid", MyUtil.getUid());
            MyUtil.httpGet(MyUtil.BASE_URL + "/resource/learnData", object1, new MyUtil.MyCallback() {
                @Override
                public void onSuccess(MyUtil.Res result) {
                    try {
                        JSONObject res = new JSONObject((String) result.data);
                        JSONArray jsonArray = res.getJSONArray("data");

                        List<Integer> value1 = new ArrayList<>();
                        List<Integer> value2 = new ArrayList<>();
                        for (int j = 0; j < 7; j++) {
                            value1.add(((JSONObject) jsonArray.get(j)).getInt("word_learn"));
                            value2.add(((JSONObject) jsonArray.get(j)).getInt("word_review"));
                        }
                        word_amount.add(value1);
                        word_amount.add(value2);

                        List<Integer> minutes = new ArrayList<>();      //请求

                        for (int i = 0; i < jsonArray.length(); i++) {
                            minutes.add(((JSONObject) jsonArray.get(i)).getInt("learn_time"));
                        }

                        for (int i = 0; i < 7; i++) {
                            time.add(new Entry(i, minutes.get(i)));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    InitBarCharts();
                    InitLineCharts();
                }

                @Override
                public void onError(MyUtil.Res result) {
                    Toast.makeText(MainActivity.getContext(), result.msg, Toast.LENGTH_SHORT).show();
                }
            }, true);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //传递数据集
    private void SetLineChartData(ArrayList<Entry> values) {
        LineDataSet set1;
        if (lineChart[0].getData() != null && lineChart[0].getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) lineChart[0].getData().getDataSetByIndex(0);
            set1.setValues(values);
            lineChart[0].getData().notifyDataChanged();
            lineChart[0].notifyDataSetChanged();
        } else {
            // 创建一个数据集,并给它一个类型
            set1 = new LineDataSet(values, "学习时间");

            // 在这里设置线
            set1.setColor(Color.GRAY);
            set1.setCircleColor(Color.GRAY);
            set1.setLineWidth(1f);
            set1.setCircleRadius(4f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(false);

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            //添加数据集
            dataSets.add(set1);

            //创建一个数据集的数据对象
            LineData data = new LineData(dataSets);

            //设置数据
            lineChart[0].setData(data);
        }
    }

    private ArrayList<Date> GetLastWeekDate() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -6);
        ArrayList<Date> dates = new ArrayList<Date>(6);
        for (int i = 0; i < 6; i++) {
            dates.add(c.getTime());
            c.add(Calendar.DATE, 1);
        }
        return dates;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            switch (view.getId()) {
                case R.id.button_return:
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        private static final String TAG = "RadioGroup";

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            if (i == R.id.radioButton_last_week_1) {
                barChart[0].setVisibility(View.VISIBLE);
                barChart[1].setVisibility(View.INVISIBLE);
                textView_learn_amount_hint.setText("当日学习");
                textView_review_amount_hint.setText("当日复习");
            } else if (i == R.id.radioButton_month_1) {
                barChart[1].setVisibility(View.VISIBLE);
                barChart[0].setVisibility(View.INVISIBLE);
                textView_learn_amount_hint.setText("当月学习");
                textView_review_amount_hint.setText("当月复习");
            } else if (i == R.id.radioButton_last_week_2) {
                lineChart[0].setVisibility(View.VISIBLE);
                lineChart[1].setVisibility(View.INVISIBLE);
                textView_learn_time_hint.setText("当日学习");
//                textView_total_time.setText(lineChart[0].getLineData().getDataSets().get(0).);
            } else if (i == R.id.radioButton_month_2) {
                lineChart[1].setVisibility(View.VISIBLE);
                lineChart[0].setVisibility(View.INVISIBLE);
                textView_learn_time_hint.setText("当月学习");
            }
        }
    };

    private OnChartValueSelectedListener onBarChartValueSelectedListener = new OnChartValueSelectedListener() {
        @Override
        public void onValueSelected(Entry e, Highlight h) {

            textView_learn_amount.setText(String.valueOf((int) ((BarEntry) e).getYVals()[0]));
            textView_review_amount.setText(String.valueOf((int) ((BarEntry) e).getYVals()[1]));
//            Log.d("POSITION", "onValueSelected: " + ((BarEntry)e).;
        }

        @Override
        public void onNothingSelected() {
//            BarEntry e = barChart[0].getBarData().getDataSetByIndex(0).getEntryIndex()
//            textView_learn_amount.setText(String.valueOf((int)((BarEntry)e).getYVals()[0]));
//            textView_review_amount.setText(String.valueOf((int)((BarEntry)e).getYVals()[1]));
        }
    };

    private OnChartValueSelectedListener onLineChartValueSelectedListener = new OnChartValueSelectedListener() {
        @Override
        public void onValueSelected(Entry e, Highlight h) {
            textView_learn_time.setText(String.valueOf((int) e.getY()));
//            Log.d("POSITION", "onValueSelected: " + ((BarEntry)e).;
        }

        @Override
        public void onNothingSelected() {
//            BarEntry e = barChart[0].getBarData().getDataSetByIndex(0).getEntryIndex()
//            textView_learn_amount.setText(String.valueOf((int)((BarEntry)e).getYVals()[0]));
//            textView_review_amount.setText(String.valueOf((int)((BarEntry)e).getYVals()[1]));
        }
    };

    public void toStudyPlan(View view) {
        Intent intent = new Intent(OverviewActivity.this, StudyPlan.class);
        startActivity(intent);
    }
}