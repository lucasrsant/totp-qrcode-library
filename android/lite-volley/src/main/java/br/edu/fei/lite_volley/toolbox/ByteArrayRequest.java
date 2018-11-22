/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.edu.fei.lite_volley.toolbox;

import android.support.annotation.GuardedBy;
import android.support.annotation.Nullable;

import br.edu.fei.lite_volley.AuthFailureError;
import br.edu.fei.lite_volley.NetworkResponse;
import br.edu.fei.lite_volley.Request;
import br.edu.fei.lite_volley.Response;
import br.edu.fei.lite_volley.Response.ErrorListener;
import br.edu.fei.lite_volley.Response.Listener;

/** A canned request for retrieving the response body at a given URL as a String. */
public class ByteArrayRequest extends Request<byte[]> {

    /** Lock to guard mListener as it is cleared on cancel() and read on delivery. */
    private final Object mLock = new Object();

    @Nullable
    @GuardedBy("mLock")
    private Listener<byte[]> mListener;

    private final byte[] mBody;

    /**
     * Creates a new request with the given method.
     *
     * @param method the request {@link Method} to use
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public ByteArrayRequest(
            int method,
            String url,
            byte[] body,
            Listener<byte[]> listener,
            @Nullable ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
        mBody = body;
    }

    /**
     * Creates a new GET request.
     *
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public ByteArrayRequest(String url, Listener<byte[]> listener, @Nullable ErrorListener errorListener) {
        this(Method.GET, url, null, listener, errorListener);
    }

    @Override
    public void cancel() {
        super.cancel();
        synchronized (mLock) {
            mListener = null;
        }
    }

    @Override
    protected void deliverResponse(byte[] response) {
        Listener<byte[]> listener;
        synchronized (mLock) {
            listener = mListener;
        }
        if (listener != null) {
            listener.onResponse(response);
        }
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {
        return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return mBody;
    }
}
