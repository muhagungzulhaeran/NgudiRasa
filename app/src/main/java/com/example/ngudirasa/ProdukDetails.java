package com.example.ngudirasa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ngudirasa.AsynTask.PostToRiwayatTask;

public class ProdukDetails extends AppCompatActivity {
    private int currentQty = 1; // Initial quantity
    TextView namaProdukTV, hargaProdukTV, deskripsiTV, qtyTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);

        Intent intent = getIntent();
        String idProduk = intent.getStringExtra("EXTRA_ID_PRODUK");
        String namaProduk = intent.getStringExtra("EXTRA_NAMA_PRODUK");
        String premium = intent.getStringExtra("EXTRA_HARGA_PREMIUM");
        String ekonomis = intent.getStringExtra("EXTRA_HARGA_EKONOMIS");
        String deskripsi = intent.getStringExtra("EXTRA_DESKRIPSI");




        namaProdukTV = findViewById(R.id.namaProdukDetail);
        hargaProdukTV = findViewById(R.id.harga);
        deskripsiTV = findViewById(R.id.deskripsi);
        qtyTV = findViewById(R.id.qty);

        ImageView decrementImageView, incrementImageView, imageViewProduk;
        imageViewProduk = findViewById(R.id.imageView);
        int resourceId = getResourceId(idProduk, ProdukDetails.this);
        imageViewProduk.setImageResource(resourceId);

        decrementImageView = findViewById(R.id.decrement);
        incrementImageView = findViewById(R.id.increment);

        qtyTV.setText(String.valueOf(currentQty));

        decrementImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementQty();
            }
        });

        incrementImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementQty();
            }
        });

        namaProdukTV.setText(namaProduk);
        String combinedText;

        if (premium.equals(ekonomis)) {
            combinedText = "Rp. " + ekonomis;
        } else {
            combinedText = "Rp. " + ekonomis + " - " + premium;
        }
        hargaProdukTV.setText(combinedText);
        deskripsiTV.setText(deskripsi);


        ImageButton backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        Button tambahkanKeRiwayatButton = findViewById(R.id.button);
        tambahkanKeRiwayatButton.setOnClickListener(new View.OnClickListener() {
            private boolean isClicked = false;

            @Override
            public void onClick(View v) {
                if (!isClicked) {
                    isClicked = true;
                    tambahkanKeRiwayat(idProduk);
                    // Disable the button for a short duration
                    tambahkanKeRiwayatButton.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            isClicked = false;
                        }
                    }, 1000); // 1000 milliseconds (adjust as needed)
                }
            }
        });

    }

    private void decrementQty() {
        if (currentQty > 1) {
            currentQty--;
            updateQtyTextView();
        }
    }

    private void incrementQty() {
        currentQty++;
        updateQtyTextView();
    }

    private void updateQtyTextView() {
        qtyTV.setText(String.valueOf(currentQty));
    }

    private int getResourceId(String idProduk, Context context) {
        // Assuming "drawable" is the resource type
        String resourceName = "p" + idProduk;
        return getResources().getIdentifier(resourceName, "drawable", context.getPackageName());
    }

    private void tambahkanKeRiwayat(String idProduk) {

        String quantity = qtyTV.getText().toString();
        String hargabaru  = hargaProdukTV.getText().toString();
        // Check if idProduk and quantity are available
        if (idProduk != null && !idProduk.isEmpty() && quantity != null && !quantity.isEmpty()) {

            PostToRiwayatTask postTask = new PostToRiwayatTask(ProdukDetails.this, idProduk, hargabaru, quantity);
            postTask.execute();
            finish();
        } else {
            // Handle the case where idProduk or quantity is not available
            Toast.makeText(this, "Error: Invalid data", Toast.LENGTH_SHORT).show();
        }
    }

}
