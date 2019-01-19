package co.roguestudios.spyfalloffline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.gms.ads.MobileAds;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;

public class MenuActivity extends AppCompatActivity {

    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        prefManager = new PrefManager(this);
        MobileAds.initialize(this, "ca-app-pub-4605466962808569~6361837645");
    }

    public void clickCreate(View view) {
        startActivity(new Intent(this, LobbyActivity.class));
    }

    public void clickSettings(View view) {

        // Create a dialog and the view for it
        LayoutInflater inflater = LayoutInflater.from(this);
        View settingsDialogView = inflater.inflate(R.layout.settings_dialog, null);
        final AlertDialog settingsDialog = new AlertDialog.Builder(this).create();
        settingsDialog.setView(settingsDialogView);
        settingsDialog.show();

        // Get components from view
        NumberPicker numberPicker = settingsDialogView.findViewById(R.id.numberPicker);
        Switch addRecentSwitch = settingsDialogView.findViewById(R.id.addRecentSwitch);
        Switch timerAlertSwitch = settingsDialogView.findViewById(R.id.timerAlertSwitch);
        Button closeButton = settingsDialogView.findViewById(R.id.closeSettingsButton);

        // Set values of components to show current value
        numberPicker.setValue(prefManager.getGameLength());
        addRecentSwitch.setChecked(prefManager.getAddRecentPlayers());
        timerAlertSwitch.setChecked(prefManager.getTimerAlertNoise());

        // Create listeners
        numberPicker.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                prefManager.setGameLength(value);
            }
        });

        addRecentSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prefManager.setAddRecentPlayers(isChecked);
            }
        });

        timerAlertSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prefManager.setTimerAlertNoise(isChecked);
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsDialog.dismiss();
            }
        });

    }

}
