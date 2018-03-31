package pl.almestinio.countdowndays.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pl.almestinio.countdowndays.R;
import pl.almestinio.countdowndays.ui.menuView.MenuContracts;

/**
 * Created by mesti193 on 3/31/2018.
 */

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

    private MenuContracts.Presenter presenter;
    private List<String> stringList;
    private Context context;
    private View view;

    public TestAdapter(List<String> stringList, Context context, MenuContracts.Presenter presenter){
        this.stringList = stringList;
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    public TestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_countdown, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view){
            super(view);
        }

    }

}
