package com.example.ngudirasa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.ngudirasa.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());


        setFragment(new HomeFragment());

        binding.navBar.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.botNavHome){
                setFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.botNavRP) {
                setFragment(new RpFragment());
            }

            return true;
        });

    }

    protected void setFragment(Fragment frg){
        FragmentManager frgMan = getSupportFragmentManager();
        FragmentTransaction frgTrs = frgMan.beginTransaction();
        frgTrs.replace(R.id.container, frg);
        frgTrs.commit();
    }



}
