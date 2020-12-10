package yaobeidanci.view.dashboard.overview;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import yaobeidanci.view.R;


public class BarChartFragment extends Fragment {
    private View view;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.layout_dashboard_word_list_mastered,container,false);

        return view;
    }
}
