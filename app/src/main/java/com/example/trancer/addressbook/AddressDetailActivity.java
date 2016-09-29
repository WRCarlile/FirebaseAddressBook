package com.example.trancer.addressbook;

import android.content.Intent;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddressDetailActivity extends AppCompatActivity {

    private DatabaseReference mAddressReference;
    private ValueEventListener mAddressReferenceListener;

    @Bind(R.id.fullNameTV) TextView mFullName;
    @Bind(R.id.addressTV) TextView mAddressTV;
    @Bind(R.id.zipTV) TextView mStateAndZipTV;
    @Bind(R.id.deleteBtn) Button mDelete;

    private ArrayList<Address> mAddress = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_address_detail);
        ButterKnife.bind(this);

        final int startingPosition = getIntent().getIntExtra("position", 0);
        mAddress = Parcels.unwrap(getIntent().getParcelableExtra("address"));

        final Address detail = mAddress.get(startingPosition);

        Log.d("starting Position", startingPosition + "");
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



        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAddressReference = FirebaseDatabase.getInstance().getReference("address");

//                mAddressReference.child(queryRef).removeValue();
//                Log.d("queryRef", queryRef +"");
                mAddressReferenceListener = mAddressReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
//                     Address node = dataSnapshot.child("address").getValue(Address.class);

                        for (DataSnapshot addressSnapshot : dataSnapshot.getChildren()) {
                            Address node = addressSnapshot.getValue(Address.class);

                            if(node.getFirstName().equals(detail.getFirstName())&&node.getLastName().equals(detail.getLastName())&&node.getAddress().equals(detail.getAddress())&&node.getCity().equals(detail.getCity())&&node.getState().equals(detail.getState())&&node.getZip().equals(detail.getZip())) {
                                String key = addressSnapshot.getKey();
                                mAddressReference.getRef().child(key).removeValue();
                                Log.d("data node", mAddressReference.child(key).toString());
//                                node.child(key).removeValue();
                            }
//                            Map<String, Boolean> firstName = node.getFirstName();
//                            for (String key: tags.keySet()) {
//                                if(key.equals(mLevelOneTag.toLowerCase())){

//                        final Query queryRef = dataSnapshot.orderByChild("address").equalTo(detail.getFirstName());
//                        dataSnapshot.child(queryRef).remove();

//                        node.getRef().setValue(null);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                Intent intent = new Intent(AddressDetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
