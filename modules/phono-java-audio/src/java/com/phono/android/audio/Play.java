/*
 * Copyright 2011 Voxeo Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.phono.android.audio;


import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import com.phonegap.api.PhonegapActivity;
import com.phono.rtp.Endpoint;
import com.phono.srtplight.Log;


/**
 *
 * @author tim
 */
public class Play extends Endpoint implements OnPreparedListener {

    MediaPlayer _mp;
    String _uri;
    boolean _prepared = false;
    final static String FILE = "file://";

    public Play(String uri, PhonegapActivity ctx) throws Exception {
        super(uri);
        _uri = uri;
        _mp = new MediaPlayer();
        if (uri.startsWith(FILE)) {
            _mp.setDataSource(uri);
            _mp.prepare();
            _prepared = true;
        } else {
            _mp.setDataSource(uri);
            _mp.setOnPreparedListener(this);
            _mp.prepareAsync();
        }


    }

    public void stop() {
        if (_mp != null) {
            _mp.stop();
        }
    }

    public void start() {
        if (_mp != null) {
            if (_prepared) {
                _mp.start();
            } else {
                Log.debug("not ready to play " + _uri);
            }
        }
    }

    public void volume(float val) {
        float fval = val / 100.0f;
        if (_mp != null) {
            _mp.setVolume(fval, fval);
        }
    }

    public void onPrepared(MediaPlayer mp) {
        Log.debug("prepared " + _uri);
        _prepared = true;
    }
}
