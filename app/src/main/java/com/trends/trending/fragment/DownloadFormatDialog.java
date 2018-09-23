package com.trends.trending.fragment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.trends.trending.R;
import com.trends.trending.model.youtube.VideoLink;
import com.trends.trending.service.DownloadService;
import com.trends.trending.utils.ExtraHelper;

import at.huber.youtubeExtractor.YtFile;
import butterknife.BindView;

import static com.trends.trending.utils.ExtraHelper.PREFS_NAME;
import static com.trends.trending.utils.ExtraHelper.VIDEO_TYPE;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_VIDEO_FILENAME;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_VIDEO_TITLE;
import static com.trends.trending.utils.Keys.VideoInfo.KEY_VIDEO_URL;

/**
 * Created by USER on 4/13/2018.
 */

public class DownloadFormatDialog extends DialogFragment {

    LinearLayout mainLayout;
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    public static final String MESSAGE_PROGRESS = "message_progress";
    private static final int PERMISSION_REQUEST_CODE = 1;
    String vUrl;
    String videoFileName;
    String vTitle;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.download_format_dialog, container, false);
        mainLayout = rootView.findViewById(R.id.main_layout);

        VideoLink videoLink;
        settings = getActivity().getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(VIDEO_TYPE)) {
            //Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            String jsonVideoLink = settings.getString(VIDEO_TYPE, null);
            Gson gson = new Gson();
            videoLink = gson.fromJson(jsonVideoLink, VideoLink.class);
            mainLayout.removeView(mProgressBar);
            for (YtFile ytFile:videoLink.getYtFiles()){
                if (ytFile.getFormat().getHeight() == -1 || ytFile.getFormat().getHeight() >= 360) {
                    addButtonToMainLayout(videoLink.getVideoMeta().getTitle(), ytFile);
                }
            }
            //return;
        }
        return rootView;
    }

    private void addButtonToMainLayout(final String videoTitle, final YtFile ytfile) {
        // Display some buttons and let the user choose the format
        String btnText = (ytfile.getFormat().getHeight() == -1) ? "Audio " +
                ytfile.getFormat().getAudioBitrate() + " kbit/s" :
                ytfile.getFormat().getHeight() + "p";
        btnText += (ytfile.getFormat().isDashContainer()) ? " dash" : "";
        Button btn = new Button(getActivity());
        btn.setText(btnText);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               // String filename;
                if (videoTitle.length() > 55) {
                    videoFileName = videoTitle.substring(0, 55) + "." + ytfile.getFormat().getExt();
                } else {
                    videoFileName = videoTitle + "." + ytfile.getFormat().getExt();
                }
                vTitle = videoTitle;
                videoFileName = videoFileName.replaceAll("\\\\|>|<|\"|\\||\\*|\\?|%|:|#|/", "");
                vUrl = ytfile.getUrl();
                if(ExtraHelper.checkPermission(getActivity())){
                    startDownload(ytfile.getUrl(), videoFileName, vTitle);
//                    downloadFromUrl(ytfile.getUrl(), videoTitle, filename);
                    editor = settings.edit();
                    editor.remove(VIDEO_TYPE);
                    editor.commit();
                    Toast.makeText(getContext(), "Downloading...", Toast.LENGTH_SHORT).show();

                } else {
                    requestPermission();
                }
                dismiss();
                //finish();
            }
        });
        mainLayout.addView(btn);
    }

    private void startDownload(String url, String fileName, String title){

        Intent intent = new Intent(getActivity(),DownloadService.class);
        intent.putExtra(KEY_VIDEO_URL, url);
        intent.putExtra(KEY_VIDEO_FILENAME, fileName);
        intent.putExtra(KEY_VIDEO_TITLE, title);
        getActivity().startService(intent);

    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
    }


//    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//            if(intent.getAction().equals(MESSAGE_PROGRESS)){
//
//                Download download = intent.getParcelableExtra("download");
////                mProgressBar.setProgress(download.getProgress());
////                if(download.getProgress() == 100){
////
////                    mProgressText.setText("File Download Complete");
////
////                } else {
////
////                    mProgressText.setText(String.format("Downloaded (%d/%d) MB",download.getCurrentFileSize(),download.getTotalFileSize()));
////
////                }
//            }
//        }
//    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startDownload(vUrl, videoFileName, vTitle);
                } else {
                    Toast.makeText(getActivity(), "Permission Denied, Please allow to proceed !", Toast.LENGTH_SHORT).show();
                    //Snackbar.make(findViewById(R.id.coordinatorLayout),"Permission Denied, Please allow to proceed !", Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }

}
