/*
 * Copyright (C) 2017 TripNDroid Mobile Engineering
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

package com.android.settings.td;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.support.v7.preference.PreferenceScreen;
import android.support.v14.preference.SwitchPreference;
import android.provider.Settings;
import android.text.TextUtils;

import com.android.internal.logging.nano.MetricsProto;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class MiscSettings extends SettingsPreferenceFragment implements OnPreferenceChangeListener {

    private static final String TAG = "MiscSettings";

    private static final String VOLUME_ROCKER_WAKE = "volume_rocker_wake";
    private static final String PREF_MEDIA_SCANNER_ON_BOOT = "media_scanner_on_boot";
    private static final String HIDE_LOCKSCREEN_BOTTOM_SHORTCUTS = "hide_lockscreen_bottom_shortcuts";
    private static final String NAVIGATION_BAR_VISIBLE = "navigation_bar_visible";

    private SwitchPreference mVolumeRockerWake;
    private ListPreference mMsob;
    private SwitchPreference mHideLockscreenBottomShortcuts;
    private SwitchPreference mNavbarVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Activity activity = getActivity();
        final ContentResolver resolver = activity.getContentResolver();

        addPreferencesFromResource(R.xml.misc_settings);

        mVolumeRockerWake = (SwitchPreference) findPreference(VOLUME_ROCKER_WAKE);
        mVolumeRockerWake.setOnPreferenceChangeListener(this);
        int volumeRockerWake = Settings.System.getInt(getContentResolver(),
                VOLUME_ROCKER_WAKE, 0);
        mVolumeRockerWake.setChecked(volumeRockerWake != 0);

        mMsob = (ListPreference) findPreference(PREF_MEDIA_SCANNER_ON_BOOT);
        mMsob.setValue(String.valueOf(Settings.System.getInt(resolver,
                Settings.System.MEDIA_SCANNER_ON_BOOT, 0)));
        mMsob.setSummary(mMsob.getEntry());
        mMsob.setOnPreferenceChangeListener(this);

        mHideLockscreenBottomShortcuts = (SwitchPreference) findPreference(HIDE_LOCKSCREEN_BOTTOM_SHORTCUTS);
        mHideLockscreenBottomShortcuts.setOnPreferenceChangeListener(this);
        int HideLockscreenBottomShortcuts = Settings.Secure.getInt(getContentResolver(),
                HIDE_LOCKSCREEN_BOTTOM_SHORTCUTS, 0);
        mHideLockscreenBottomShortcuts.setChecked(HideLockscreenBottomShortcuts != 0);

        mNavbarVisible = (SwitchPreference) findPreference(NAVIGATION_BAR_VISIBLE);
        mNavbarVisible.setOnPreferenceChangeListener(this);
        int navbarVisible = Settings.System.getInt(getContentResolver(),
                NAVIGATION_BAR_VISIBLE, 0);
        mNavbarVisible.setChecked(navbarVisible != 0);

    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.TD_SETTINGS;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {

        if (preference == mVolumeRockerWake) {
            boolean value = (Boolean) newValue;
            Settings.System.putInt(getContentResolver(), VOLUME_ROCKER_WAKE,
                    value ? 1 : 0);
            return true;
        } else if (preference == mMsob) {
            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.MEDIA_SCANNER_ON_BOOT,
                    Integer.valueOf(String.valueOf(newValue)));
            mMsob.setValue(String.valueOf(newValue));
            mMsob.setSummary(mMsob.getEntry());
            return true;
        } else if (preference == mHideLockscreenBottomShortcuts) {
            boolean value = (Boolean) newValue;
            Settings.Secure.putInt(getContentResolver(), HIDE_LOCKSCREEN_BOTTOM_SHORTCUTS,
                    value ? 1 : 0);
            return true;
        } else if (preference == mNavbarVisible) {
            boolean value = (Boolean) newValue;
            Settings.System.putInt(getContentResolver(), NAVIGATION_BAR_VISIBLE,
                    value ? 1 : 0);
            return true;
        }
        return false;
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        return super.onPreferenceTreeClick(preference);
    }
}
