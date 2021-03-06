package com.example.hansung.band_cctv.activity;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.hansung.band_cctv.R;
import com.example.hansung.band_cctv.Retrofit.Model.Response_MaxIndex;
import com.example.hansung.band_cctv.Retrofit.Model.Response_Sensor;
import com.example.hansung.band_cctv.Retrofit.Model.Response_lastPulse;
import com.example.hansung.band_cctv.Retrofit.RetroCallback;
import com.example.hansung.band_cctv.Retrofit.RetroClient;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class PulseFragment extends Fragment {
    private static PulseFragment instance;

    public static final long ref = System.currentTimeMillis();
    SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
    long mNow;
    public static Date mDate;
    public Response_MaxIndex data;
    public static int maxIndex;
    public static int startIndex;
    public boolean state = true;
    String sex_f;
    public int age;
    public int sex;
    SharedPreferences sharedPreferencesForPulse;
    SharedPreferences.Editor editorForPulse;
    int MALE = 0, FEMALE = 1;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    RetroClient retroClient;
    public int xindexstart = 0;
    Double xindex = 1.0 ;
    LineChart lineChart;
    XAxis xAxis;
    List<Entry> entries;
    int dataString;
    int statedata;
    public int count = 0;
    public int count2 = 0;

    TextView dataview;
    TextView state_textview;
    int result;
    int last_pulse;
    TextView today_tv;
    TextView banduserinfo_textview;
    TextView banduserinfo_txt;

    ArrayList<LineDataSet> dataSets;
    ArrayList<Response_lastPulse> lastPulseArrayList;


    public static PulseFragment getInstance() {
        if (instance == null)
            instance = new PulseFragment();
        return instance;
    }

    @Override
    public void onStart() {
        super.onStart();
        DataThread thread = new DataThread();
        thread.setDaemon(true);
        thread.start();
        Log.e("mapview2","mapView");
        Log.e("zxcvb","Pulse onStart()");
    }

    @Override
    public void onResume() {
        Log.e("zxcvb", "Pulse onResume()");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.e("zxcvb", "Pulse onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.e("zxcvb", "Pulse onStop()");
        state = false;
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.e("zxcvb", "Pulse onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        Log.e("zxcvb", "Pulse onDestroyView()");
        //state = false;
        super.onDestroyView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pulse, container, false);

        retroClient = RetroClient.getInstance().createBaseApi();

        dataview = view.findViewById(R.id.dataview);
        lineChart = view.findViewById(R.id.linechart);
        state_textview = view.findViewById(R.id.state_textview);
        //banduserinfo_textview = (TextView)view.findViewById(R.id.banduserinfo_txt);
        sharedPreferencesForPulse=  getContext().getSharedPreferences("pulse", MODE_PRIVATE);
        editorForPulse = sharedPreferencesForPulse.edit();
        sharedPreferences = getContext().getSharedPreferences("info", MODE_PRIVATE);

        age =  sharedPreferencesForPulse.getInt("age",1990);
        sex = sharedPreferencesForPulse.getInt("sex",0);

        Log.e("dfdff",""+age+""+sex);
        if(sex == 1){
            sex_f = "여자";
        }else{
            sex_f = "남자";
        }




        ImageView rabbit = view.findViewById(R.id.heart_img);
        GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(rabbit);
        Glide.with(this).load(R.drawable.heart).into(gifImage);

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
        String getTime = sdf.format(date);
        today_tv = view.findViewById(R.id.today_tv);
        today_tv.setText(getTime);

        retroClient.GetMaxIndex(new RetroCallback() {
            @Override
            public void onError(Throwable t) {
            }
            @Override
            public void onSuccess(int code, Object receivedData) {
                data = (Response_MaxIndex)receivedData;
                maxIndex = data.getMax();
                startIndex = maxIndex-3;
            }
            @Override
            public void onFailure(int code) {
            }
        });

        entries = new ArrayList<>();
        //entries.add(new Entry(0,0));

        LineDataSet lineDataSet = new LineDataSet(entries, "심박수(heart rate)");
        lineDataSet.setLineWidth(1);
        lineDataSet.setCircleRadius(4);
        lineDataSet.setCircleColor(Color.parseColor("#FFEC6253"));
        lineDataSet.setCircleColorHole(Color.WHITE);
        lineDataSet.setColor(Color.parseColor("#FFEF450C"));
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawHorizontalHighlightIndicator(true);
        lineDataSet.setDrawHighlightIndicators(true);
        lineDataSet.setDrawValues(false);
        lineDataSet.setDrawFilled(false);

        dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);

        IAxisValueFormatter myformat = new HourAxisValueFormatter();

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);

        xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setAxisMaximum(70);
        xAxis.setDrawAxisLine(true);
        xAxis.setAxisMinimum(xindexstart);
        xAxis.setValueFormatter(myformat);
        xAxis.enableGridDashedLine(15, 80, 15);

        LimitLine min = new LimitLine(50, " 최소심박수");
        LimitLine max = new LimitLine(90, "최대심박수");
        min.setLineColor(Color.GRAY);
        min.setTextColor(Color.BLACK);
        max.setLineColor(Color.GRAY);
        max.setTextColor(Color.BLACK);
        YAxis yLAxis = lineChart.getAxisLeft();
        yLAxis.setAxisMaximum(100);
        yLAxis.setAxisMinimum(30);
        yLAxis.setTextColor(Color.BLACK);
        yLAxis.addLimitLine(min);
        yLAxis.addLimitLine(max);

        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);

        Description description = new Description();
        description.setText("현재시간(분:초)");

        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setAutoScaleMinMaxEnabled(true);
        lineChart.notifyDataSetChanged();
        lineChart.setDescription(description);
        lineChart.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        lineChart.animateY(4000, Easing.EasingOption.EaseInElastic);
        lineChart.zoom((float) 1.4,1,0,getData2());


        return view;
    }

    public int getData() {
        retroClient.GetSensor(startIndex, new RetroCallback() {
            Response_Sensor data;
            @Override
            public void onError(Throwable t) {}
            @Override
            public void onSuccess(int code, Object receivedData) {
                data = (Response_Sensor) receivedData;
                result = data.getSensor_data();
            }
            @Override
            public void onFailure(int code) {}
        });
        return result;
    }

    public int getData2(){
        Log.e("getdata2","@@@@@@@@@@");
        retroClient.LastPulse(new RetroCallback() {
            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                lastPulseArrayList = (ArrayList<Response_lastPulse>)receivedData;
                last_pulse = lastPulseArrayList.get(0).getSensor_data();
            }

            @Override
            public void onFailure(int code) {

            }
        });
        dataString = last_pulse;
        statedata = last_pulse;
        return last_pulse;
    }

    public void chartUpdate(int x){
        Log.e("getdata22","@@@@@@@@@@");
        entries.add(new Entry(xindexstart,getData2()));
        lineChart.notifyDataSetChanged();
        lineChart.invalidate();
        xAxis.setAxisMaximum((float) (xindexstart+xindex));
        xAxis.setAxisMinimum(xindexstart-3);
        dataview.setText(String.valueOf(dataString));

        if(dataString>50 && dataString <90)state_textview.setText("정상");
        else state_textview.setText("위험한 상태");
    }


     Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0 ){
                chartUpdate(startIndex);
                xindex = xindex + 0.3;
                startIndex+=2;
                xindexstart++;
            }
        }
    };
    public class DataThread extends Thread {
        public DataThread() {
            state = true;
            Log.e("DataThread", "success");
        }

        @Override
        public void run() {
            while (state == true) {
                Log.e("DataThread run()", "run()");
                handler.sendEmptyMessage(0);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getTime() {
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return dateFormat.format(mDate);
    }

    public class HourAxisValueFormatter implements IAxisValueFormatter {

        private DateFormat mDataFormat;
        public Date mDate;

        public HourAxisValueFormatter(){
            this.mDataFormat = new SimpleDateFormat("mm:ss", Locale.KOREAN);
            this.mDate = new Date();
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            long originalTimestamp = ref + (long)value * 3000;
            mDate.setTime(originalTimestamp);
            return mDataFormat.format(mDate);
        }
    }

    public void stopThread(){
        state = false;
    }
}
