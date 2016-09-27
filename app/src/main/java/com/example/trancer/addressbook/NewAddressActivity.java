package com.example.trancer.addressbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewAddressActivity extends AppCompatActivity {
    private DatabaseReference mCreateNewAddressReference;
    @Bind(R.id.etFirstName) EditText mFirstName;
    @Bind(R.id.eTLastName) EditText mLastName;
    @Bind(R.id.eTAddress) EditText mAddress;
    @Bind(R.id.eTZip) EditText mZip;
    @Bind(R.id.saveFB) Button mSaveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_address);
        ButterKnife.bind(this);

        mCreateNewAddressReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_ADDRESS);

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = mFirstName.getText().toString();
                String lastName = mLastName.getText().toString();
                String address = mAddress.getText().toString();
                String zip = mZip.getText().toString();
                Address addressObject = new Address(firstName,lastName,address,zip);
                saveToFirebase(addressObject);
                Intent intent = new Intent(NewAddressActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void saveToFirebase(Address address) {
        mCreateNewAddressReference.push().setValue(address);
    }

}
