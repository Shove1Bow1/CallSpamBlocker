package com.example.call_spam_blocker;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.stream.IntStream;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static final int READ_CONTACT_PERMISSION = 1;
    private static final int ANSWER_PHONE_CALL_PERMISSION = 2;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.Search);
        onRequestPermission();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Search:
                SearchingFragment searchingFragment = new SearchingFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder, searchingFragment).commit();
                return true;
            case R.id.Contacts:
                ContactsFragment contactsFragment = new ContactsFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder, contactsFragment).commit();
                return true;
            case R.id.BlockedList:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder, new BlockedListFragment()).commit();
                return true;
            case R.id.Setting:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder, new SettingFragment()).commit();
                return true;
        }
        return false;
    }

    public void onRequestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            providePermission();
        } else {
            Toast.makeText(this, "App doesn't support this Android Version", Toast.LENGTH_SHORT).show();
        }
    }

    public void providePermission() {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS)== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_CONTACTS,
            }, READ_CONTACT_PERMISSION);
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ANSWER_PHONE_CALLS)==PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.MODIFY_PHONE_STATE,
                    Manifest.permission.ANSWER_PHONE_CALLS,
            }, ANSWER_PHONE_CALL_PERMISSION);
        }

    }

    public void createCustomDialog(String contentDialog, @NonNull String[] providePermissions) {
        new AlertDialog.Builder(this)
                .setTitle("Permission needed")
                .setMessage(contentDialog)
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(MainActivity.this
                                , providePermissions
                                , 1);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case READ_CONTACT_PERMISSION:
                if(ContextCompat.checkSelfPermission(this,Manifest.permission.ANSWER_PHONE_CALLS)==PackageManager.PERMISSION_DENIED){
                    ActivityCompat.requestPermissions(this
                            , new String[]{Manifest.permission.ANSWER_PHONE_CALLS
                                    , Manifest.permission.MODIFY_PHONE_STATE}
                            , ANSWER_PHONE_CALL_PERMISSION);
                }
                break;
        }

    }
}