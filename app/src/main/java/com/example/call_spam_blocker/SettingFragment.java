package com.example.call_spam_blocker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment   {

    private LinearLayout languageOpener;
    private TextView clearHistory;
    public SettingFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
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

        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        languageOpener=view.findViewById(R.id.languageOpener);
        clearHistory=view.findViewById(R.id.clearHistoryButton);
        languageOpener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LanguageDialog languageDialog = new LanguageDialog(view.getContext());
                Dialog dialog;
                dialog=languageDialog.onCreateDialog(savedInstanceState);
                dialog.show();
            }
        });
        clearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClearSearchedList();
            }
        });
    }
    class LanguageDialog extends DialogFragment {
        private Context context;
        public LanguageDialog(Context context){
            this.context=context;
        }
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.language)
                    .setItems(R.array.en_language_list, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            return builder.create();
        }
    }
    public void ClearSearchedList(){SharedPreferences sharedPreferences=getActivity().getSharedPreferences("HistorySearched",Context.MODE_PRIVATE);
        if(sharedPreferences.contains("SearchedList")){
            sharedPreferences.edit().remove("SearchedList").commit();
            Toast.makeText(getContext(),"History cache has been cleared",Toast.LENGTH_SHORT).show();
        }

    }
}