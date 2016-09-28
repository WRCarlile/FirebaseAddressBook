package com.example.trancer.addressbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseAddressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    View mView;
    Context mContext;

    public FirebaseAddressViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindAddress(Address address) {

        TextView fullNameTV = (TextView) mView.findViewById(R.id.fullNameTV);
        TextView addressTV = (TextView) mView.findViewById(R.id.addressTV);
        TextView stateAndZipTV = (TextView) mView.findViewById(R.id.zipTV);

        String firstName = address.getFirstName();
        String lastName = address.getLastName();
        String city = address.getCity();
        String state = address.getState();
        String zip = address.getZip();

        String fullName = lastName + ", " + firstName;
        String stateAndZip = city + ", " + state + " " + zip;

        fullNameTV.setText(fullName);
        addressTV.setText(address.getAddress());
        stateAndZipTV.setText(stateAndZip);
    }

    @Override
    public void onClick(View view) {

        final ArrayList<Address> address = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_ADDRESS);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    address.add(snapshot.getValue(Address.class));
                }

                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, AddressDetailActivity.class);
                intent.putExtra("position", itemPosition);
                intent.putExtra("address", Parcels.wrap(address));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}