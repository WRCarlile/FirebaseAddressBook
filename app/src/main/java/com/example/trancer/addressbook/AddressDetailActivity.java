package com.example.trancer.addressbook;

import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddressDetailActivity extends AppCompatActivity {
    private DatabaseReference mAddressReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;
    @Bind(R.id.fullNameTV) TextView mFullName;
    @Bind(R.id.addressTV) TextView mAddressTV;
    @Bind(R.id.zipTV) TextView mStateAndZipTV;
    private String mPostition;
    ArrayList<Address> mAddress = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_address_detail);
        ButterKnife.bind(this);

        int startingPosition = getIntent().getIntExtra("position", 0);
        mAddress = Parcels.unwrap(getIntent().getParcelableExtra("address"));

        Address detail = mAddress.get(startingPosition);

        Log.d("starting Position", startingPosition +"");
        Log.d("parcel", detail + "");

        String firstName = detail.getFirstName();
        String lastName = detail.getLastName();
        String city = detail.getCity();
        String state = detail.getState();
        String zip = detail.getZip();

        String fullName = lastName + ", " + firstName;
        String stateAndZip = city + ", " + state + " " + zip;

        mFullName.setText(fullName);
        mAddressTV.setText(detail.getAddress());
        mStateAndZipTV.setText(stateAndZip);



        mAddressReference = FirebaseDatabase.getInstance().getReference("address");
//        mAddressReferenceListener = mAddressReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot addressSnapshot : dataSnapshot.getChildren()) {
//                    Address address = addressSnapshot.getValue(Address.class);
//                    String noteName = note.getName();
//                    String body = note.getBody();
//                    Map<String, Boolean> tags = note.getTags();
//                    NoteModel newNote = new NoteModel(noteName, body, tags);
//                    mLevelOneNotes.add(newNote);
//                    Log.d("name------", newNote + "");
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        })


//        setUpFirebaseAdapter();
    }
//
//    private void setUpFirebaseAdapter() {
//        mFirebaseAdapter = new FirebaseRecyclerAdapter<Address, FirebaseAddressViewHolder>
//                (Address.class, R.layout.activity_address_detail, FirebaseAddressViewHolder.class,
//                        mAddressReference) {
//
//            @Override
//            protected void populateViewHolder(FirebaseAddressViewHolder viewHolder,
//                                              Address model, int position) {
//                viewHolder.bindAddress(model);
//
//            }
//        };
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mFirebaseAdapter.cleanup();
//
//    }
}
