package com.sedulous.fsdsdanapur;

import static com.sedulous.fsdsdanapur.O.alertLayout;
import static com.sedulous.fsdsdanapur.O.dialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.sedulous.fsdsdanapur.Fragments.AddCoachFragment;
import com.sedulous.fsdsdanapur.Fragments.AddTrainFragment;
import com.sedulous.fsdsdanapur.Fragments.ChangePasswordFragment;
import com.sedulous.fsdsdanapur.Fragments.DeviceStatusFragment;
import com.sedulous.fsdsdanapur.Fragments.HomeFragment;
import com.sedulous.fsdsdanapur.Fragments.LoginHistoryFragment;
import com.sedulous.fsdsdanapur.Fragments.NextTrainScreenFragment;
import com.sedulous.fsdsdanapur.Fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private ImageView menuOpen ;
//    private LinearLayout add_data;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ImageView logo_menu;
    private FrameLayout frameLayout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        frameLayout = findViewById(R.id.content_frame);
        logo_menu = findViewById(R.id.logo_menu);

//        add_data = findViewById(R.id.linear);
//        add_data.setVisibility(View.GONE);


        logo_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.open();
            }
        });

        NextTrainScreenFragment nextTrainScreenFragment = new NextTrainScreenFragment();
        O.loadFragments(nextTrainScreenFragment, getSupportFragmentManager(), drawerLayout, R.id.content_frame);
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }

//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.content_frame, new HomeFragment())
//                    .commit();
//        }

//        add_data.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
//
//                if (currentFragment != null) {
//                    String fragmentName = currentFragment.getClass().getSimpleName();  // Get the fragment name
//                    if (fragmentName.equals("AddCoachFragment")){
//                        show_aleart_dialog(MainActivity.this, R.layout.add_train_dialog, "Add Coach", R.id.textTitle, R.id.et_coach_dialog,"Enter Coach Number","Add Coach", R.id.bt_submit);
//                    }else if (fragmentName.equals("AddTrainFragment")){
//                        show_aleart_dialog(MainActivity.this, R.layout.add_train_dialog, "Add Train", R.id.textTitle, R.id.et_coach_dialog,"Enter Train Number","Add Train", R.id.bt_submit);
//                    }
//                }
//
//            }
//            public void show_aleart_dialog(Activity activity, int view, String message, int textViewid, int editTextViewIdHint,String hint, String buttonText, int buttonId){
//                LayoutInflater inflater = activity.getLayoutInflater();
//                View layoutview = inflater.inflate(view, null); // Inflate the layout view
//
//                TextView textView = layoutview.findViewById(textViewid);
//                EditText editText = layoutview.findViewById(editTextViewIdHint);
//                AppCompatButton button = layoutview.findViewById(buttonId);
//
//                // Update the TextView with the passed message
//                textView.setText(message);
//                editText.setHint(hint);
//                button.setText(buttonText);
//
//                // Configure and show the alert dialog
//                alertLayout = new AlertDialog.Builder(activity);
//                alertLayout.setView(layoutview);
//
//                dialog = alertLayout.create();
//                dialog.show();
//            }
//
////            public void show_aleart_dialog(Activity activity, int view, String message, int textViewid, int editTextViewIdHint, String buttonText, int buttonId){
////                LayoutInflater inflater = activity.getLayoutInflater();
////                View layoutview = inflater.inflate(view, null);
////
////                TextView textView = (TextView) layoutview.findViewById(textViewid);
////                EditText editText = (EditText) layoutview.findViewById(editTextViewIdHint) ;
////                AppCompatButton button = (AppCompatButton) layoutview.findViewById(buttonId);
////
////
////                textView.setText("message");
////                alertLayout = new AlertDialog.Builder(activity);
////                alertLayout.setView(view);
////
////                dialog = alertLayout.create();
//////                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
////                dialog.show();
////            }
//        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_hm:
//                        add_data.setVisibility(View.GONE);
                        O.loadFragments(nextTrainScreenFragment, getSupportFragmentManager(), drawerLayout, R.id.content_frame);
                        if (drawerLayout != null) {
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }
                        break;

                    case R.id.nav_profile:
//                        add_data.setVisibility(View.GONE);
                        ProfileFragment profileFragment = new ProfileFragment();
                        O.loadFragments(profileFragment, getSupportFragmentManager(), R.id.content_frame);
                        if (drawerLayout != null) {
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }
                        break;
//                    case R.id.nav_add_train:
////                        add_data.setVisibility(View.VISIBLE);
//                        AddTrainFragment addTrainFragment = new AddTrainFragment();
//                        O.loadFragments(addTrainFragment, getSupportFragmentManager(), R.id.content_frame);
//                        if (drawerLayout != null) {
//                            drawerLayout.closeDrawer(GravityCompat.START);
//                        }
//                        break;
//
//                    case R.id.nav_add_coach:
////                        add_data.setVisibility(View.VISIBLE);
//                        AddCoachFragment addCoachFragment = new AddCoachFragment();
//                        O.loadFragments(addCoachFragment, getSupportFragmentManager(), R.id.content_frame);
//                        if (drawerLayout != null) {
//                            drawerLayout.closeDrawer(GravityCompat.START);
//                        }
//                        break;

                    case R.id.nav_changePassword:
//                        add_data.setVisibility(View.GONE);
                        ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
                        O.loadFragments(changePasswordFragment, getSupportFragmentManager(), R.id.content_frame);
                        if (drawerLayout != null) {
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }
                        break;

                    case R.id.nav_history:
//                        add_data.setVisibility(View.GONE);
                        LoginHistoryFragment historyFragment = new LoginHistoryFragment();
                        O.loadFragments(historyFragment, getSupportFragmentManager(), R.id.content_frame);
                        if (drawerLayout != null) {
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }
                        break;

                    case R.id.nav_device_status:
//                        add_data.setVisibility(View.GONE);
                        DeviceStatusFragment deviceStatusFragment = new DeviceStatusFragment();
                        O.loadFragments(deviceStatusFragment, getSupportFragmentManager(), R.id.content_frame);
                        if (drawerLayout != null) {
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }
                        break;

                    case R.id.nav_logOut:
                        if (drawerLayout != null) {
                            drawerLayout.closeDrawer(GravityCompat.START);
                            logout_out_dialog();
                        }
                        break;
                    default:
                        HomeFragment homeFragment1 = new HomeFragment();
//                        add_data.setVisibility(View.GONE);
                        O.loadFragments(homeFragment1, getSupportFragmentManager(), R.id.content_frame);
                        if (drawerLayout != null) {
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }
                        break;
                }
                return true;
            }
        });
    }

    private void logout_out_dialog() {
        final Dialog dialog = new Dialog(MainActivity.this, R.style.Dialog);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        final TextView tvTextview = dialog.findViewById(R.id.tv);
        tvTextview.setText("Are you sure to Logout?");
        TextView t_negative = dialog.findViewById(R.id.v_negative);
        TextView t_positive = dialog.findViewById(R.id.v_positive);

        t_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                     startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                     finish();
                } catch (Exception e) {
                    throw new RuntimeException(e);

                }
            }

        });

        t_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                drawerLayout.open();
            }
        });
        dialog.show();


    }
}