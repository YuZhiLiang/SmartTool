package com.zhiliang.smarttool.battery;

import android.os.Bundle;

import com.zhiliang.smarttool.R;

import androidx.preference.PreferenceFragmentCompat;

public class BatteryRemindMianFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.pre_battery_remind, rootKey);
        }

        /*@Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), BatteryRemindSettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }*/
    }