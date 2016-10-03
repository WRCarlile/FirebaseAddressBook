package com.example.trancer.addressbook.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trancer.addressbook.Constants;
import com.example.trancer.addressbook.R;
import com.example.trancer.addressbook.adapters.DateValidator;
import com.example.trancer.addressbook.models.Address;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UpdateAddressActivity extends AppCompatActivity implements View.OnClickListener{
    private DatabaseReference mUpdateAddressReference;
    private ValueEventListener mAddressReferenceListener;
    private int mYear, mMonth, mDay;
    @Bind(R.id.etFirstName) EditText mFirstName;
    @Bind(R.id.eTLastName) EditText mLastName;
    @Bind(R.id.eTAddress) EditText mAddress;
    @Bind(R.id.eTCity) EditText mCity;
    @Bind(R.id.eTState) EditText mState;
    @Bind(R.id.eTZip) EditText mZip;
    @Bind(R.id.eTBirthDate) EditText mBirthDate;
    @Bind(R.id.birthBtn) Button mBirthDateBtn;
    @Bind(R.id.saveFB) Button mSaveBtn;

    private ArrayList<Address> mUpdateAddress = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_address);
        ButterKnife.bind(this);

        mUpdateAddress = Parcels.unwrap(getIntent().getParcelableExtra("address"));

        final int startingPosition = getIntent().getIntExtra("position", 0);
        final Address detail = mUpdateAddress.get(startingPosition);

        mUpdateAddressReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_ADDRESS);

        mFirstName.setText(detail.getFirstName());
        mLastName.setText(detail.getLastName());
        mAddress.setText(detail.getAddress());
        mCity.setText(detail.getCity());
        mState.setText(detail.getState());
        mZip.setText(detail.getZip());
        mBirthDate.setText(detail.getBirthDate());




        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mFirstName.getText().toString().trim().length() <= 0) {
                    Toast.makeText(UpdateAddressActivity.this, "Enter First Name", Toast.LENGTH_SHORT).show();
                    mFirstName.setError("Enter First Name");
                    return;
                }
                if (mLastName.getText().toString().trim().length() <= 0) {
                    Toast.makeText(UpdateAddressActivity.this, "Enter Last Name", Toast.LENGTH_SHORT).show();
                    mLastName.setError("Enter Last Name");
                    return;
                }
                if (mAddress.getText().toString().trim().length() <= 0) {
                    Toast.makeText(UpdateAddressActivity.this, "Enter Address", Toast.LENGTH_SHORT).show();
                    mAddress.setError("Enter Address");
                    return;
                }
                if (mCity.getText().toString().trim().length() <= 0) {
                    Toast.makeText(UpdateAddressActivity.this, "Enter City", Toast.LENGTH_SHORT).show();
                    mCity.setError("Enter City");
                    return;
                }
                if (mState.getText().toString().trim().length() <= 0) {
                    Toast.makeText(UpdateAddressActivity.this, "Enter State", Toast.LENGTH_SHORT).show();
                    mState.setError("Enter State");
                    return;
                }
                if (mZip.getText().toString().trim().length() <= 0 || mZip.getText().toString().trim().length() != 5) {
                    Toast.makeText(UpdateAddressActivity.this, "Enter Valid Zip", Toast.LENGTH_SHORT).show();
                    mZip.setError("Enter Valid Zip");
                    return;
                }

                if (mBirthDate.getText().toString().trim().length() <= 0) {
                    Toast.makeText(UpdateAddressActivity.this, "Enter Date of Birth", Toast.LENGTH_SHORT).show();
                    mBirthDate.setError("Enter Date of Birth");
                    return;
                }
                DateValidator dateValidator = new DateValidator();
                String birthDate = mBirthDate.getText().toString();
                boolean valid = dateValidator.validate(birthDate);

                if (valid == false) {
                    Toast.makeText(UpdateAddressActivity.this, "Enter Date mm/dd/yyyy", Toast.LENGTH_SHORT).show();
                    mBirthDate.setError("Enter Date mm/dd/yyyy");
                    return;
                }

                String firstName = mFirstName.getText().toString();
                String lastName = mLastName.getText().toString();
                String address = mAddress.getText().toString();
                String city = mCity.getText().toString();
                String state = mState.getText().toString();
                String zip = mZip.getText().toString();
                final String pushId = detail.getPushId();
                Log.d("address push node", pushId + "");

                Address addressObject = new Address(firstName, lastName, address, city, state, zip, birthDate);
                updateFirebase(addressObject, pushId);
                Intent intent = new Intent(UpdateAddressActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
        mBirthDateBtn.setOnClickListener(this);
    }
    @Override
    public void onClick (View v) {

        if (v == mBirthDateBtn) {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            mBirthDate.setText((monthOfYear + 1)+ "/" + dayOfMonth  + "/" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();

        }
    }

    public void updateFirebase(final Address address, String pushId) {

        DatabaseReference pushRef = mUpdateAddressReference.child(pushId);
        String pushRefId = pushRef.getKey();
        address.setPushId(pushRefId);
        pushRef.setValue(address);

        Intent intent = new Intent(UpdateAddressActivity.this, MainActivity.class);
        startActivity(intent);

    }
}
