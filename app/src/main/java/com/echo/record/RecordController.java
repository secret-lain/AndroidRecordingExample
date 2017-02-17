package com.echo.record;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by admin on 2017-02-17.
 */

public class RecordController {
    Context mContext;
    final int SAMPLEING_RATE = 44100;
    final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
    final int AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;
    final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    final int BUFFER_SIZE_BYTES = AudioRecord.getMinBufferSize(SAMPLEING_RATE, CHANNEL_CONFIG, AUDIO_FORMAT);

    AudioRecord recorder;
    MediaPlayer player;
    ArrayList<Short> recordedBuffer;

    boolean isRecording;

    public RecordController(Context parentContext){
        mContext = parentContext;
        recorder = new AudioRecord(AUDIO_SOURCE, SAMPLEING_RATE, CHANNEL_CONFIG, AUDIO_FORMAT, BUFFER_SIZE_BYTES);
        recordedBuffer = new ArrayList<>();
        isRecording = false;
    }

    public void recordStart() {
        Toast.makeText(mContext,
                "녹음을 시작합니다.", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(mContext,
                "녹음이 종료.", Toast.LENGTH_SHORT).show();
    }

    public void recordStop(){
        isRecording = false;
        Toast.makeText(mContext,
                "녹음이 중지되었습니다.", Toast.LENGTH_LONG).show();
    }

    public void playAudio(){
        killMediaPlayer();

        AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,SAMPLEING_RATE,AudioFormat.CHANNEL_OUT_MONO,AUDIO_FORMAT,BUFFER_SIZE_BYTES,AudioTrack.MODE_STREAM);
        audioTrack.play();
        int maxBufferShort = recordedBuffer.size();
        short[] arrayBuffer = new short[maxBufferShort];
        for(int i = 0 ; i < maxBufferShort ; i++){
            arrayBuffer[i] = recordedBuffer.get(i);
        }
        audioTrack.write(arrayBuffer, 0, arrayBuffer.length);

        audioTrack.stop();
        audioTrack.release();
    }

    private void killMediaPlayer() {
        if(player != null){
            try {
                player.release();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}
