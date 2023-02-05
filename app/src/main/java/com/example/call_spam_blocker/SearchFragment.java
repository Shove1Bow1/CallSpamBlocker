package com.example.call_spam_blocker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    private EditText editText;
    private ImageView searchBtn;
    private RecyclerView searchedHistoryRC;
    private ArrayList<SearchedNumber> searchedNumbers;
    private LinearLayoutManager linearLayout;
    private SearchedNumbersAdapter searchedNumbersAdapter;
    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.searchBar);
        searchBtn = view.findViewById(R.id.searchBtn);
        searchedHistoryRC = view.findViewById(R.id.searchHistoryRC);
        searchedNumbers = new ArrayList<>();
        getSearchHistory();
        linearLayout = new LinearLayoutManager(getContext());
        linearLayout.setOrientation(RecyclerView.VERTICAL);
        searchedNumbersAdapter=new SearchedNumbersAdapter(searchedNumbers,getContext());
        searchedHistoryRC.setLayoutManager(linearLayout);
        searchedHistoryRC.setAdapter(searchedNumbersAdapter);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText() != null && editText.getText().length() > 0) {
                    Log.d("this is running", "onClick: "+editText.getText());
                    addToSearchHistory(editText.getText().toString());
                } else {
                    new AlertDialog.Builder(getContext())
                            .setTitle("You need enter phone number")
                            .setNeutralButton("Close", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).create().show();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                return handled;
            }
        });
    }

    public void addToSearchHistory(String newNumber) {
        SharedPreferences editorSearchedList = getActivity()
                .getSharedPreferences("HistorySearched", Context.MODE_PRIVATE);
        if (editorSearchedList.contains("SearchedList") && editorSearchedList.getString("SearchedList", null).length() > 0) {
            String editStringList = editorSearchedList
                    .getString("SearchedList", null);
            editStringList = newNumber+","+editStringList ;
            editorSearchedList.edit().putString("SearchedList", editStringList).apply();
        } else {
            editorSearchedList.edit().putString("SearchedList", newNumber).commit();
        }
    }

    public void getSearchHistory() {
        String stringSearchedList;
        SharedPreferences getSearchedList = getActivity()
                .getSharedPreferences("HistorySearched",Context.MODE_PRIVATE);
        stringSearchedList = getSearchedList
                .getString("SearchedList", null);
        if (stringSearchedList != null && stringSearchedList.length() > 0) {
            String[] searchedList = stringSearchedList.split(",");
            for (String a : searchedList) {
                SearchedNumber searchedNumber = new SearchedNumber(a);
                searchedNumbers.add(searchedNumber);
            }
        }
    }
}

class SearchedNumber {
    private String numberResult;

    public SearchedNumber() {

    }

    public SearchedNumber(String numberResult) {
        this.numberResult = numberResult;
    }

    public void setNumberResult(String numberResult) {
        this.numberResult = numberResult;
    }

    public String getNumberResult() {
        return numberResult;
    }
}

class SearchedNumbersAdapter extends RecyclerView.Adapter<SearchedNumbersAdapter.SearchedHolder> {
    private ArrayList<SearchedNumber> searchedNumbers;
    private Context context;

    public SearchedNumbersAdapter(ArrayList<SearchedNumber> searchedNumbers, Context context) {
        this.searchedNumbers = searchedNumbers;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searched_history_model, parent, false);
        return new SearchedHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchedHolder holder, int position) {
        SearchedNumber searchedNumber = searchedNumbers.get(position);
        holder.searchedPhoneNumber.setText(searchedNumber.getNumberResult());
    }

    @Override
    public int getItemCount() {
        if (searchedNumbers != null && searchedNumbers.size() > 0)
            return searchedNumbers.size();
        return 0;
    }


    public class SearchedHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout historyModel;
        private TextView searchedPhoneNumber;

        public SearchedHolder(@NonNull View itemView) {
            super(itemView);
            historyModel = itemView.findViewById(R.id.historyModel);
            searchedPhoneNumber = itemView.findViewById(R.id.searchedPhoneNumber);

        }
    }

}