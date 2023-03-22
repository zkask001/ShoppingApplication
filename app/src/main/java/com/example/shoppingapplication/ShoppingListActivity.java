//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Toast;
//
//import com.example.shoppingapplication.ShoppingList;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.ChildEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//public class ShoppingListActivity extends AppCompatActivity implements ShoppingListAdapter.OnListItemClickListener {
//
//    private RecyclerView recyclerView;
//    private ShoppingListAdapter shoppingListAdapter;
//    private ArrayList<ShoppingList> shoppingLists = new ArrayList<>();
//    private DatabaseReference databaseReference;
//    private FirebaseUser user;
//    private FloatingActionButton fab;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_shopping_list);
//
//        recyclerView = findViewById(R.id.recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        shoppingListAdapter = new ShoppingListAdapter(shoppingLists, this);
//        recyclerView.setAdapter(shoppingListAdapter);
//
//        databaseReference = FirebaseDatabase.getInstance().getReference();
//        user = FirebaseAuth.getInstance().getCurrentUser();
//
//        fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ShoppingListActivity.this, AddListActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        if (user != null) {
//            databaseReference.child("shopping_lists").child(user.getUid()).addChildEventListener(new ChildEventListener() {
//                @Override
//                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
//                    ShoppingList shoppingList = dataSnapshot.getValue(ShoppingList.class);
//                    shoppingList.setListId(dataSnapshot.getKey());
//                    shoppingLists.add(shoppingList);
//                    shoppingListAdapter.notifyDataSetChanged();
//                }
//
//                @Override
//                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
//                    String listId = dataSnapshot.getKey();
//                    ShoppingList updatedList = dataSnapshot.getValue(ShoppingList.class);
//                    for (ShoppingList shoppingList : shoppingLists) {
//                        if (shoppingList.getListId().equals(listId)) {
//                            shoppingList.setListName(updatedList.getListName());
//                            shoppingListAdapter.notifyDataSetChanged();
//                            break;
//                        }
//                    }
//                }
//
//                @Override
//                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//                    String listId = dataSnapshot.getKey();
//                    for (ShoppingList shoppingList : shoppingLists) {
//                        if (shoppingList.getListId().equals(listId)) {
//                            shoppingLists.remove(shoppingList);
//                            shoppingListAdapter.notifyDataSetChanged();
//                            break;
//                        }
//                    }
//                }
//
//                @Override
//                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {}
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    Toast.makeText(ShoppingListActivity.this, "Failed to load shopping lists.", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//    }
//
//    @Override
//    public void onListItemClick(int clickedItemIndex) {
//        Intent intent = new Intent(ShoppingListActivity.this, AddItemActivity.class);
//        intent.putExtra("listId", shoppingLists.get(clickedItemIndex).getListId());
//        startActivity(intent);
//    }
//}