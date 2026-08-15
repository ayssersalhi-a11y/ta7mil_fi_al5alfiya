package com.example.godotuploader;

import androidx.annotation.NonNull;
import org.godotengine.godot.Godot;
import org.godotengine.godot.plugin.GodotPlugin;
import org.godotengine.godot.plugin.UsedByGodot;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.Collections;
import java.util.Set;

public class GodotUploader extends GodotPlugin {

    public GodotUploader(Godot godot) {
        super(godot);
    }

    @NonNull
    @Override
    public String getPluginName() {
        return "GodotUploader";
    }

    @UsedByGodot
    public void startBackgroundUpload(String filePath, String serverUrl) {
        Data inputData = new Data.Builder()
                .putString("file_path", filePath)
                .putString("server_url", serverUrl)
                .build();

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        OneTimeWorkRequest uploadRequest = new OneTimeWorkRequest.Builder(UploadWorker.class)
                .setConstraints(constraints)
                .setInputData(inputData)
                .build();

        WorkManager.getInstance(getActivity()).enqueue(uploadRequest);
    }
}
