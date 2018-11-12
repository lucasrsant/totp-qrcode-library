/*
 * Copyright (C) 2008 ZXing authors
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

package br.edu.fei.lite_zxing.result;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;

import br.edu.fei.lite_zxing.Intents;
import br.edu.fei.lite_zxing.R;

/**
 * A base class for the Android-specific barcode handlers. These allow the app to polymorphically
 * suggest the appropriate actions for each data type.
 * <p>
 * This class also contains a bunch of utility methods to take common actions like opening a URL.
 * They could easily be moved into a helper object, but it can't be static because the Activity
 * instance is needed to launch an intent.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 * @author Sean Owen
 */
public abstract class ResultHandler {

    private static final String TAG = ResultHandler.class.getSimpleName();
    private static final int NO_TYPE = -1;

    public static final int MAX_BUTTON_COUNT = 4;

    private final ParsedResult result;
    private final Activity activity;
    private final Result rawResult;
    private final String customProductSearch;



    ResultHandler(Activity activity, ParsedResult result, Result rawResult) {
        this.result = result;
        this.activity = activity;
        this.rawResult = rawResult;
        this.customProductSearch = parseCustomSearchURL();
    }

    public final ParsedResult getResult() {
        return result;
    }

    final boolean hasCustomProductSearch() {
        return customProductSearch != null;
    }

    final Activity getActivity() {
        return activity;
    }

    /**
     * Indicates how many buttons the derived class wants shown.
     *
     * @return The integer button count.
     */
    public abstract int getButtonCount();

    /**
     * The text of the nth action button.
     *
     * @param index From 0 to getButtonCount() - 1
     * @return The button text as a resource ID
     */
    public abstract int getButtonText(int index);

    /**
     * Execute the action which corresponds to the nth button.
     *
     * @param index The button that was clicked.
     */
    //public abstract void handleButtonPress(int index);

    /**
     * Create a possibly styled string for the contents of the current barcode.
     *
     * @return The text to be displayed.
     */
    public CharSequence getDisplayContents() {
        String contents = result.getDisplayResult();
        return contents.replace("\r", "");
    }

    /**
     * A string describing the kind of barcode that was found, e.g. "Found contact info".
     *
     * @return The resource ID of the string.
     */
    public abstract int getDisplayTitle();

    /**
     * A convenience method to get the parsed type. Should not be overridden.
     *
     * @return The parsed type, e.g. URI or ISBN
     */
    public final ParsedResultType getType() {
        return result.getType();
    }

    /**
     * Like {@link #launchIntent(Intent)} but will tell you if it is not handle-able
     * via {@link ActivityNotFoundException}.
     *
     * @throws ActivityNotFoundException if Intent can't be handled
     */
    final void rawLaunchIntent(Intent intent) {
        if (intent != null) {
            intent.addFlags(Intents.FLAG_NEW_DOC);
            activity.startActivity(intent);
        }
    }

    /**
     * Like {@link #rawLaunchIntent(Intent)} but will show a user dialog if nothing is available to handle.
     */
    final void launchIntent(Intent intent) {
        try {
            rawLaunchIntent(intent);
        } catch (ActivityNotFoundException ignored) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(R.string.app_name);
            builder.setMessage(R.string.msg_intent_failed);
            builder.setPositiveButton(R.string.button_ok, null);
            builder.show();
        }
    }

    private String parseCustomSearchURL() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        //String customProductSearch = prefs.getString(PreferencesActivity.KEY_CUSTOM_PRODUCT_SEARCH, null);
        if (customProductSearch != null && customProductSearch.trim().isEmpty()) {
            return null;
        }
        return customProductSearch;
    }
}
