package com.example.trancer.addressbook.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.trancer.addressbook.R;
import com.example.trancer.addressbook.models.Address;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.parceler.Parcels;
import java.util.ArrayList;
import butterknife.Bind;
import butterknife.ButterKnife;

public class AddressDetailActivity extends AppCompatActivity {

    private DatabaseReference mAddressReference;
    private ValueEventListener mAddressReferenceListener;

    @Bind(R.id.fullNameTV) TextView mFullName;
    @Bind(R.id.addressTV) TextView mAddressTV;
    @Bind(R.id.zipTV) TextView mStateAndZipTV;
    @Bind(R.id.birthDateTV) TextView mBirthDate;
    @Bind(R.id.deleteBtn) Button mDelete;
    @Bind(R.id.updateBtn) Button mUpdate;

    private ArrayList<Address> mAddress = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_detail);
        ButterKnife.bind(this);

        mAddress = Parcels.unwrap(getIntent().getParcelableExtra("address"));
        final int startingPosition = getIntent().getIntExtra("position", 0);
        final Address detail = mAddress.get(startingPosition);

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
        mBirthDate.setText(detail.getBirthDate());

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAddressReference = FirebaseDatabase.getInstance().getReference("address");
                Log.d("clicked", "its clicked");
                mAddressReferenceListener = mAddressReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot addressSnapshot : dataSnapshot.getChildren()) {
                            Address node = addressSnapshot.getValue(Address.class);

                            if(node.getPushId().equals(detail.getPushId())){

                            String key = addressSnapshot.getKey();
                            mAddressReference.getRef().child(key).removeValue();
                            Log.d("data node", mAddressReference.child(key).toString());
                            }

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

        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressDetailActivity.this, UpdateAddressActivity.class);
                intent.putExtra("position", startingPosition);
                intent.putExtra("address", Parcels.wrap(mAddress));
                startActivity(intent);
            }
        });
    }
}


