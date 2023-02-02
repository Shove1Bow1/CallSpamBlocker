package com.example.call_spam_blocker;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

class Contact {
    private String nameContact;
    private ArrayList<String> phoneNumber;

    public String getNameContact() {
        return nameContact;
    }

    public ArrayList<String> getPhoneNumber() {

        return phoneNumber;
    }

    public Contact(){

    }
    public Contact(String nameContact, ArrayList<String> phoneNumber){
        this.nameContact=nameContact;
        this.phoneNumber=phoneNumber;
    }

    public void setNameContact(String nameContact) {
        this.nameContact = nameContact;
    }

    public void setPhoneNumber(ArrayList<String> phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
public class ContactsFragment extends Fragment {
    private ArrayList<Contact> contacts=new ArrayList<>();
    private String[] dataTest;
    private Cursor cursor;
    private RecyclerView contactRC;
    private ContactAdapter contactAdapter;
    private LinearLayoutManager linearLayout;
    public ContactsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ContactsFragment newInstance(String param1, String param2) {
        ContactsFragment fragment = new ContactsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    public ArrayList<Contact> getContactList(){
        ArrayList<Contact> contactList=new ArrayList<>();
        if(ActivityCompat.checkSelfPermission(getContext()
                , Manifest.permission.READ_CONTACTS)==PackageManager.PERMISSION_GRANTED){

            ContentResolver cr = getContext().getContentResolver();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, null);
            int i=0;
            if (cur != null )
            {
                while (cur != null && cur.moveToNext()) {
                    String id = cur.getString(
                            cur.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                    String name = cur.getString(cur.getColumnIndexOrThrow(
                            ContactsContract.Contacts.DISPLAY_NAME));
                    Contact contact=new Contact();
                    contact.setNameContact(name);
                    if (cur.getInt(cur.getColumnIndexOrThrow( ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                        Cursor pCur = cr.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        ArrayList<String> phoneNo=new ArrayList<>();
                        while (pCur.moveToNext()) {
                             phoneNo.add(pCur.getString(pCur.getColumnIndexOrThrow(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER)));
                            contact.setPhoneNumber(phoneNo);

                        }
                        contact.setPhoneNumber(phoneNo);
                        contactList.add(contact);
                        pCur.close();
                    }
                }
            }
            if (cur != null) {
                cur.close();
            }
        }
        return contactList;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contacts=getContactList();
        contactRC=view.findViewById(R.id.contactList);
        contactAdapter=new ContactAdapter(contacts);
        linearLayout=new LinearLayoutManager(getContext());
        linearLayout.setOrientation(RecyclerView.VERTICAL);
        contactRC.setLayoutManager(linearLayout);
        contactRC.setAdapter(contactAdapter);
    }
}

class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder>{
    private ArrayList<Contact> contacts;
    public ContactAdapter(ArrayList<Contact> contacts){
        this.contacts=contacts;
    }
    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_contact_model, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ViewHolder holder, int position) {
        Contact contact=contacts.get(position);
        holder.contactNameText.setText(contact.getNameContact());
//        holder.contactModel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }
    @Override
    public int getItemCount() {
            return contacts.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView contactNameText;
        private ConstraintLayout contactModel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contactNameText=itemView.findViewById(R.id.contactNameText);
        }
    }
}
