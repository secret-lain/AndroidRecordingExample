package com.echo.common.record;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by admin on 2017-02-17.
 */

@SuppressWarnings("unchecked")
public class RecordController {
    Context mContext;
    Timer timer;
    private final int SAMPLEING_RATE = 44100;
    private final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
    private final int AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;
    private final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    private final int BUFFER_SIZE_BYTES = AudioRecord.getMinBufferSize(SAMPLEING_RATE, CHANNEL_CONFIG, AUDIO_FORMAT);

    private AudioRecord recorder;
    private AudioTrack player;
    private ArrayList<Short> recordedBuffer;

    private boolean isRecording;
    private boolean isPlaying;

    public RecordController(Context parentContext){
        mContext = parentContext;
        recorder = new AudioRecord(AUDIO_SOURCE, SAMPLEING_RATE, CHANNEL_CONFIG, AUDIO_FORMAT, BUFFER_SIZE_BYTES);
        isRecording = false;
        isPlaying = true;
    }


    public void recordStart(final recordCallback callback) {
        final int LIMITSECOND = 10; // 10 seconds period repeat
        final int[] afterStartedSecond = {0};
        timer = new Timer();

        new AsyncTask(){
            @Override
            protected Object doInBackground(Object[] objects) {
                recordedBuffer = new ArrayList<>();
                recorder.startRecording();
                int mRecorderReaded = 0;
                isRecording = true;

                while(isRecording){
                    short[] currentBuffer = new short[BUFFER_SIZE_BYTES / 2]; // short = 2byte
                    int currentRecorderReaded = recorder.read(currentBuffer, 0, BUFFER_SIZE_BYTES / 2);
                    for(int i = 0 ; i < currentRecorderReaded; i++){
                        recordedBuffer.add(currentBuffer[i]);
                    }
                    mRecorderReaded += currentRecorderReaded;
                }
                recorder.stop();
                recordStop();
                return null;
            }
            @Override
            protected void onPreExecute() {
                callback.recordingStarted();
                Toast.makeText(mContext,
                        "녹음을 시작합니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onPostExecute(Object o) {
                //TODO isRecording 상태에 따라서 차이를 두자.
                callback.recordingEnded();
                recordStop();
                Toast.makeText(mContext,
                        "녹음이 종료.", Toast.LENGTH_SHORT).show();
            }
        }.execute();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(afterStartedSecond[0] > LIMITSECOND){
                    callback.recordingEnded();
                }
                else{
                    callback.onProgress(afterStartedSecond[0]);
                    afterStartedSecond[0]++;
                }
            }
        }, 0, 1000);
    }

    public void recordStop(){
        if(isRecording){
            timer.cancel();
            timer.purge();
            timer = null;
        }
        isRecording = false;
    }

    public void playAudio(){
        if(recordedBuffer == null) return;
        destroyPlayer();

        new AsyncTask(){
            @Override
            protected Object doInBackground(Object[] objects) {
                player = new AudioTrack(AudioManager.STREAM_MUSIC,SAMPLEING_RATE,AudioFormat.CHANNEL_OUT_MONO,AUDIO_FORMAT,BUFFER_SIZE_BYTES,AudioTrack.MODE_STREAM);
                player.play();
                int maxBufferShort = recordedBuffer.size();
                short[] arrayBuffer = new short[maxBufferShort];
                for(int i = 0 ; i < maxBufferShort ; i++){
                    arrayBuffer[i] = recordedBuffer.get(i);
                }
                player.write(arrayBuffer, 0, arrayBuffer.length);
                player.stop();
                return null;
            }
        }.execute();
    }

    public void destroyPlayer(){
        if(player != null){
            player.stop();
            player.release();
            player = null;
        }
    }

    public interface recordCallback{
        void recordingStarted();
        void recordingEnded();
        void onProgress(int second);
    }
}
