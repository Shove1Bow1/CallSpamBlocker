package com.example.call_spam_blocker;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

class Contact {
    private String nameContact;
    private String phoneNumber;

    public String getNameContact() {
        return nameContact;
    }

    public String getPhoneNumber() {

        return phoneNumber;
    }

    public Contact(){

    }
    public Contact(String nameContact, String phoneNumber){
        this.nameContact=nameContact;
        this.phoneNumber=phoneNumber;
    }

}
public class ContactsFragment extends Fragment {
    private @NonNull ArrayList<Contact> contacts=new ArrayList<>();
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
        this.contacts=getContactList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    public ArrayList<Contact> getContactList(){
        ArrayList<Contact> contactList=new ArrayList<>();
        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS)==PackageManager.PERMISSION_GRANTED){
            for(int i=0;i<Integer.parseInt(ContactsContract.);i++){
                Log.d("check contact", "getContactList: "+i);
            }
        }
        return contactList;
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

    }

    @Override
    public int getItemCount() {
        if(contacts.size()>0){
            return contacts.size();
        }
        return 0;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView contactNameText;
        private TextView phoneNumberText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contactNameText=itemView.findViewById(R.id.contactNameText);
            phoneNumberText=itemView.findViewById(R.id.phoneNumberText);
        }
    }
}
