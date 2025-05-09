package com.example.compras;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List <Product> products = new ArrayList<>();

    private DBHandler dbHandler;
    private ProductAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHandler = new DBHandler(this);

        adapter = new ProductAdapter(products);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        loadProducts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.optionAll:
                updateRV();
                Toast.makeText(this, "Todos", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.optionInCart:
                List <Product> inCartProducts = new ArrayList<>();
                for (Product product : products) {
                    if (product.isInCart()) {
                        inCartProducts.add(product);
                    }
                }
                updateRV(inCartProducts);
                Toast.makeText(this, "Produtos no carrinho", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.optionMiss:
                loadProducts();

                Toast.makeText(this, "Falta", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.optionSort:

            case R.id.optionRefresh: // tá certo
                dbHandler.clearDatabase();
                loadProducts();

                Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadProducts() {
        new Thread(() -> {
            HTTPHandler httpHandler = new HTTPHandler();
            String url = "https://hostingalunos.upt.pt/~dam/produtos.html";
            String result = httpHandler.readInfo(url);

            if (result != null) {
                try {
                    String[] rows = result.split("\n");

                    dbHandler.clearDatabase();
                    int i = 0;
                    for (String row : rows) {
                        String name = row.trim();
                        String cleanData = name.replaceAll("<br\\s*/?>", "\n");
                        if (!name.isEmpty()) {
                            Product product = new Product(i, cleanData, 1, false);
                            dbHandler.insertProduct(product);
                            i++;
                        }
                    }
                    runOnUiThread(() -> {
                        updateRV();
                        Toast.makeText(this, "Produtos atualizados com sucesso!", Toast.LENGTH_SHORT).show();
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Erro ao processar produtos.", Toast.LENGTH_SHORT).show();
                    });
                }
            } else {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Erro ao encontrar produtos.", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }

    private void updateRV() {
        products.clear();
        products.addAll(dbHandler.getAllProducts());
        adapter.notifyDataSetChanged();
    }

    private List updateRV(List list) {
        return list;
    }
}