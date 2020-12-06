package com.example.gmail2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<EmailItem> items;
    EmailItemAdapter adapter;
    boolean favorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = EmailItem.samples();
        adapter = new EmailItemAdapter(this, items);
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        listView.setLongClickable(true);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 101, 0, "Xóa");
        menu.add(0, 102, 0, "Trả lời");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        int id = item.getItemId();
        if (id == 101) {
            items.remove(info.position);
            adapter.notifyDataSetChanged();
        } else if (id == 102) {
            EmailItem selected = items.get(info.position);

            String emailSubject = selected.getSubject();
            String emailText = "Reply Email";
            String[] emailReceiverList = {selected.getEmail()};
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("vnd.android.cursor.dir/email");
            intent.putExtra(Intent.EXTRA_EMAIL, emailReceiverList);
            intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
            intent.putExtra(Intent.EXTRA_TEXT, emailText);
            startActivity(Intent.createChooser(intent,
                    "To complete action choose:"));
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }else{
            if (favorite){
                favorite = false;
                item.setIcon(R.drawable.ic_baseline_star_24);
                adapter.getFilter().filter("NoFavorite");
            }
            else{
                favorite = true;
                item.setIcon(R.drawable.ic_baseline_star_border_24);
                adapter.getFilter().filter("Favorite");
            }
        }

        return super.onOptionsItemSelected(item);
    }
}