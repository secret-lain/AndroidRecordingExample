package com.echo.record;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by admin on 2017-02-17.
 */

public class RecordController {
    Context mContext;
    private final int SAMPLEING_RATE = 44100;
    private final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
    private final int AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;
    private final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    private final int BUFFER_SIZE_BYTES = AudioRecord.getMinBufferSize(SAMPLEING_RATE, CHANNEL_CONFIG, AUDIO_FORMAT);

    private AudioRecord recorder;
    private AudioTrack player;
    private ArrayList<Short> recordedBuffer;

    public boolean isRecording;
    public boolean isPlaying;

    public RecordController(Context parentContext){
        mContext = parentContext;
        recorder = new AudioRecord(AUDIO_SOURCE, SAMPLEING_RATE, CHANNEL_CONFIG, AUDIO_FORMAT, BUFFER_SIZE_BYTES);
        isRecording = false;
        isPlaying = true;
    }

    @SuppressWarnings("unchecked")
    public void recordStart(final recordCallback callback) {
        new AsyncTask(){
            @Override
            protected Object doInBackground(Object[] objects) {
                recordedBuffer = new ArrayList<>();
                recorder.startRecording();
                int mRecorderReaded = 0;
                isRecording = true;

                while(mRecorderReaded < SAMPLEING_RATE /2 * 10 && isRecording){
                    short[] currentBuffer = new short[BUFFER_SIZE_BYTES / 2]; // short = 2byte
                    int currentRecorderReaded = recorder.read(currentBuffer, 0, BUFFER_SIZE_BYTES / 2);
                    for(int i = 0 ; i < currentRecorderReaded; i++){
                        recordedBuffer.add(currentBuffer[i]);
                    }
                    mRecorderReaded += currentRecorderReaded;
                }
                recorder.stop();
                isRecording = false;

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
                Toast.makeText(mContext,
                        "녹음이 종료.", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

    public void recordStop(){
        isRecording = false;
        Toast.makeText(mContext,
                "녹음이 중지되었습니다.", Toast.LENGTH_LONG).show();
    }

    public void playAudio(){
        if(recordedBuffer == null) return;
        destroyPlayer();

        player = new AudioTrack(AudioManager.STREAM_MUSIC,SAMPLEING_RATE,AudioFormat.CHANNEL_OUT_MONO,AUDIO_FORMAT,BUFFER_SIZE_BYTES,AudioTrack.MODE_STREAM);
        player.play();
        int maxBufferShort = recordedBuffer.size();
        short[] arrayBuffer = new short[maxBufferShort];
        for(int i = 0 ; i < maxBufferShort ; i++){
            arrayBuffer[i] = recordedBuffer.get(i);
        }
        player.write(arrayBuffer, 0, arrayBuffer.length);
        player.stop();
    }

    public void destroyPlayer(){
        if(player != null){
            player.release();
            player = null;
        }
    }

    public interface recordCallback{
        void recordingStarted();
        void recordingEnded();
    }
}
