package com.example.hansung.band_cctv.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.hansung.band_cctv.R;
import com.example.hansung.band_cctv.Retrofit.RetroCallback;
import com.example.hansung.band_cctv.Retrofit2.RetroClient2;
import com.example.hansung.band_cctv.util.RtspViewPlayer;

import java.util.HashMap;

public class VideoFragment extends Fragment {
    private static VideoFragment instance;
    private RtspViewPlayer playView_first;
    private RtspViewPlayer playView_second;
    private RelativeLayout surfaceView_first;
    private RelativeLayout surfaceView_second;

    public String right;
    public String left;
    public String center;
    public String right_center;
    public String left_center;
    public String stop;

    HashMap<String, Object> parameter_right;
    HashMap<String, Object> parameter_left;
    HashMap<String, Object> parameter_center;
    HashMap<String, Object> parameter_right_center;
    HashMap<String, Object> parameter_left_center;
    HashMap<String, Object> parameter_stop;


    HashMap<String, Object> parameter_right2;
    HashMap<String, Object> parameter_left2;
    HashMap<String, Object> parameter_center2;
    HashMap<String, Object> parameter_right_center2;
    HashMap<String, Object> parameter_left_center2;
    HashMap<String, Object> parameter_stop2;

    Button left_btn1;
    Button right_btn1;
    Button right_center_btn1;
    Button left_center_btn1;
    Button center_btn1;

    Button left_btn2;
    Button right_btn2;
    Button right_center_btn2;
    Button left_center_btn2;
    Button center_btn2;
    Button exit_btn;
    Button show_video;

    RetroClient2 retroClient2;
    HashMap<String ,Object> exitmap;

    public static VideoFragment getInstance() {
        if (instance == null)
            instance = new VideoFragment();
        return instance;
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_video, container, false);
        exitmap = new HashMap<>();
        exitmap.put("exit","exit");
        exit_btn = view.findViewById(R.id.exit_btn);
        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retroClient2.Exit_PI(exitmap, new RetroCallback() {
                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onSuccess(int code, Object receivedData) {

                    }

                    @Override
                    public void onFailure(int code) {

                    }
                });
            }
        });

        show_video = (Button)view.findViewById(R.id.show_video);
        show_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> alarmmap = new HashMap<>();
                alarmmap.put("alarm","alarm");
                retroClient2.Send_Alarm(alarmmap, new RetroCallback() {
                    @Override
                    public void onError(Throwable t) {
                    }
                    @Override
                    public void onSuccess(int code, Object receivedData) {
                        Log.e("send alarm","messagingservice");
                    }

                    @Override
                    public void onFailure(int code) {
                    }
                });
            }
        });

        left_btn1 = view.findViewById(R.id.left_btn1);
        right_btn1 = view.findViewById(R.id.right_btn1);
        right_center_btn1 = view.findViewById(R.id.right_center_btn1);
        left_center_btn1 = view.findViewById(R.id.left_center_btn1);
        center_btn1 = view.findViewById(R.id.center_btn1);

        left_btn2 = view.findViewById(R.id.left_btn2);
        right_btn2 = view.findViewById(R.id.right_btn2);
        right_center_btn2 = view.findViewById(R.id.right_center_btn2);
        left_center_btn2 = view.findViewById(R.id.left_center_btn2);
        center_btn2 = view.findViewById(R.id.center_btn2);

        retroClient2 = RetroClient2.getInstance().createBaseApi2();

        right = "right";
        left = "left";
        right_center = "right_center";
        left_center = "left_center";
        center = "center";
        stop = "stop";

        parameter_left = new HashMap<>();
        parameter_right = new HashMap<>();
        parameter_center = new HashMap<>();
        parameter_stop = new HashMap<>();
        parameter_left_center = new HashMap<>();
        parameter_right_center = new HashMap<>();


        parameter_left2 = new HashMap<>();
        parameter_right2 = new HashMap<>();
        parameter_center2 = new HashMap<>();
        parameter_stop2 = new HashMap<>();
        parameter_left_center2 = new HashMap<>();
        parameter_right_center2 = new HashMap<>();

        parameter_right.put("rsl",right);
        parameter_left.put("rsl",left);
        parameter_center.put("rsl",center);
        parameter_right_center.put("rsl",right_center);
        parameter_left_center.put("rsl",left_center);

        parameter_right2.put("rsl",right);
        parameter_left2.put("rsl",left);
        parameter_center2.put("rsl",center);
        parameter_right_center2.put("rsl",right_center);
        parameter_left_center2.put("rsl",left_center);


        left_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retroClient2.Motor_Controller(parameter_left, new RetroCallback() {
                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onSuccess(int code, Object receivedData) {

                    }

                    @Override
                    public void onFailure(int code) {

                    }
                });
            }
        });

        left_center_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retroClient2.Motor_Controller(parameter_left_center, new RetroCallback() {
                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onSuccess(int code, Object receivedData) {

                    }

                    @Override
                    public void onFailure(int code) {

                    }
                });
            }
        });

        center_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retroClient2.Motor_Controller(parameter_center, new RetroCallback() {
                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onSuccess(int code, Object receivedData) {

                    }

                    @Override
                    public void onFailure(int code) {

                    }
                });
            }
        });

        right_center_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retroClient2.Motor_Controller(parameter_right_center, new RetroCallback() {
                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onSuccess(int code, Object receivedData) {

                    }

                    @Override
                    public void onFailure(int code) {

                    }
                });
            }
        });

        right_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retroClient2.Motor_Controller(parameter_right, new RetroCallback() {
                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onSuccess(int code, Object receivedData) {

                    }

                    @Override
                    public void onFailure(int code) {

                    }
                });
            }
        });

        left_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retroClient2.Motor_Controller2(parameter_left2, new RetroCallback() {
                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onSuccess(int code, Object receivedData) {

                    }

                    @Override
                    public void onFailure(int code) {

                    }
                });
            }
        });

        left_center_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retroClient2.Motor_Controller2(parameter_left_center2, new RetroCallback() {
                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onSuccess(int code, Object receivedData) {

                    }

                    @Override
                    public void onFailure(int code) {

                    }
                });
            }
        });

        center_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retroClient2.Motor_Controller2(parameter_center2, new RetroCallback() {
                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onSuccess(int code, Object receivedData) {

                    }

                    @Override
                    public void onFailure(int code) {

                    }
                });
            }
        });

        right_center_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retroClient2.Motor_Controller2(parameter_right_center2, new RetroCallback() {
                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onSuccess(int code, Object receivedData) {

                    }

                    @Override
                    public void onFailure(int code) {

                    }
                });
            }
        });

        right_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retroClient2.Motor_Controller2(parameter_right2, new RetroCallback() {
                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onSuccess(int code, Object receivedData) {

                    }

                    @Override
                    public void onFailure(int code) {

                    }
                });
            }
        });

        playView_first = new RtspViewPlayer(getContext(),"rtsp://192.168.0.2:8091/rtsp");
        surfaceView_first = view.findViewById(R.id.surface_video1);
        surfaceView_first.addView(playView_first);

        playView_second = new RtspViewPlayer(getContext(),"rtsp://192.168.0.2:8091/rtsp1");
        surfaceView_second = view.findViewById(R.id.surface_video2);
        surfaceView_second.addView(playView_second);

        return view;
    }


    @Override
    public void onResume() {
        Log.e("Video Fragment", "Video onResume()");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.e("Video Fragment", "Video onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.e("Video Fragment", "Video onStop()");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.e("Video Fragment", "Video onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        Log.e("Video Fragment", "Video onDestroyView()");
        super.onDestroyView();
    }

    @Override
    public void onStart() {
        Log.e("Video Fragment","Video onStart()");
        super.onStart();

    }
}
