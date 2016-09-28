package com.example.trancer.addressbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewAddressActivity extends AppCompatActivity {
    private DatabaseReference mCreateNewAddressReference;
    @Bind(R.id.etFirstName) EditText mFirstName;
    @Bind(R.id.eTLastName) EditText mLastName;
    @Bind(R.id.eTAddress) EditText mAddress;
    @Bind(R.id.eTCity) EditText mCity;
    @Bind(R.id.eTState) EditText mState;
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
                if (mFirstName.getText().toString().trim().length() <= 0) {
                    Toast.makeText(NewAddressActivity.this, "Enter First Name", Toast.LENGTH_SHORT).show();
                    mFirstName.setError("Enter First Name");
                    return;
                }
                if (mLastName.getText().toString().trim().length() <= 0) {
                    Toast.makeText(NewAddressActivity.this, "Enter Last Name", Toast.LENGTH_SHORT).show();
                    mLastName.setError("Enter Last Name");
                    return;
                }
                if (mAddress.getText().toString().trim().length() <= 0) {
                    Toast.makeText(NewAddressActivity.this, "Enter Address", Toast.LENGTH_SHORT).show();
                    mAddress.setError("Enter Address");
                    return;
                }
                if (mCity.getText().toString().trim().length() <= 0) {
                    Toast.makeText(NewAddressActivity.this, "Enter City", Toast.LENGTH_SHORT).show();
                    mCity.setError("Enter City");
                    return;
                }
                if (mState.getText().toString().trim().length() <= 0) {
                    Toast.makeText(NewAddressActivity.this, "Enter State", Toast.LENGTH_SHORT).show();
                    mState.setError("Enter State");
                    return;
                }
                if (mZip.getText().toString().trim().length() <= 0) {
                    Toast.makeText(NewAddressActivity.this, "Enter Last Name", Toast.LENGTH_SHORT).show();
                    mZip.setError("Enter Zip");
                    return;
                }

                    String firstName = mFirstName.getText().toString();
                    String lastName = mLastName.getText().toString();
                    String address = mAddress.getText().toString();
                    String city = mCity.getText().toString();
                    String state = mState.getText().toString();
                    String zip = mZip.getText().toString();
                    Address addressObject = new Address(firstName,lastName,address,city, state,zip);
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
