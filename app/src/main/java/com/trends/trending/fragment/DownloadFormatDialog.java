package com.trends.trending.fragment;


import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.trends.trending.R;
import com.trends.trending.model.youtube.VideoLink;

import at.huber.youtubeExtractor.YtFile;

import static com.trends.trending.utils.ExtraHelper.PREFS_NAME;
import static com.trends.trending.utils.ExtraHelper.VIDEO_TYPE;

/**
 * Created by USER on 4/13/2018.
 */

public class DownloadFormatDialog extends DialogFragment {

    LinearLayout mainLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.download_format_dialog, container, false);
        mainLayout = rootView.findViewById(R.id.main_layout);
        SharedPreferences settings;
        //ArrayList<YtFile> ytFileArrayList;
        VideoLink videoLink;
        settings = getActivity().getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(VIDEO_TYPE)) {
            //Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            String jsonVideoLink = settings.getString(VIDEO_TYPE, null);
            Gson gson = new Gson();
            videoLink = gson.fromJson(jsonVideoLink, VideoLink.class);
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
                String filename;
                if (videoTitle.length() > 55) {
                    filename = videoTitle.substring(0, 55) + "." + ytfile.getFormat().getExt();
                } else {
                    filename = videoTitle + "." + ytfile.getFormat().getExt();
                }
                filename = filename.replaceAll("\\\\|>|<|\"|\\||\\*|\\?|%|:|#|/", "");
                downloadFromUrl(ytfile.getUrl(), videoTitle, filename);
                //finish();
            }
        });
        mainLayout.addView(btn);
    }

    private void downloadFromUrl(String youtubeDlUrl, String downloadTitle, String fileName) {
        Uri uri = Uri.parse(youtubeDlUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(downloadTitle);

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

        DownloadManager manager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

}
