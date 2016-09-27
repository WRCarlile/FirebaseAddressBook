package com.example.trancer.addressbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseAddressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    View mView;
    Context mContext;

    public FirebaseAddressViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindAddress(Address address) {

        TextView firstNameTV = (TextView) mView.findViewById(R.id.firstNameTV);
        TextView lastNameTV = (TextView) mView.findViewById(R.id.lastNameTV);
        TextView addressTV = (TextView) mView.findViewById(R.id.addressTV);
        TextView zipTV = (TextView) mView.findViewById(R.id.zipTV);



        firstNameTV.setText(address.getFirstName());
        lastNameTV.setText(address.getLastName());
        addressTV.setText(address.getAddress());
        zipTV.setText(address.getZip());
    }

    @Override
    public void onClick(View view) {
//        final ArrayList<Address> addresss = new ArrayList<>();
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_ADDRESS);
//        ref.addListenerForSingleValueEvent(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    addresss.add(snapshot.getValue(Address.class));
//                }
//
//                int itemPosition = getLayoutPosition();
//
//                Intent intent = new Intent(mContext, RestaurantDetailActivity.class);
//                intent.putExtra("position", itemPosition + "");
//                intent.putExtra("addresss", Parcels.wrap(addresss));
//
//                mContext.startActivity(intent);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
    }
}