/*
 * Copyright (C) 2016 The Android Open Source Project
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
 * limitations under the License
 */
package com.android.settings.network;

import android.content.Context;
import android.os.Build;
import android.os.SystemProperties;
import android.provider.Settings;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.Preference;
import android.text.TextUtils;

import com.android.settings.core.PreferenceController;
import com.android.settings.core.instrumentation.MetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactory;

import static android.provider.Settings.System.SHOW_LTE_FOURGEE;
import static com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_LTE_FOURGEE;

public class ShowLteFourGeePreferenceController extends PreferenceController implements
        Preference.OnPreferenceChangeListener {

    private static final String SHOW_LTE_FOURGEE = "show_lte_fourgee";

    private final MetricsFeatureProvider mMetricsFeatureProvider;

    public ShowLteFourGeePreferenceController(Context context) {
        super(context);
        mMetricsFeatureProvider = FeatureFactory.getFactory(context).getMetricsFeatureProvider();
    }

    @Override
    public String getPreferenceKey() {
        return SHOW_LTE_FOURGEE;
    }

    @Override
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (SHOW_LTE_FOURGEE.equals(preference.getKey())) {
            mMetricsFeatureProvider.action(mContext, ACTION_LTE_FOURGEE);
        }
        return false;
    }

    @Override
    public void updateState(Preference preference) {
        int value = Settings.System.getInt(mContext.getContentResolver(), Settings.System.SHOW_LTE_FOURGEE, 0);
        ((SwitchPreference) preference).setChecked(value != 0);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        boolean value = (Boolean) newValue;
        Settings.System.putInt(mContext.getContentResolver(), Settings.System.SHOW_LTE_FOURGEE, value ? 1 : 0);
        return true;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }
}
