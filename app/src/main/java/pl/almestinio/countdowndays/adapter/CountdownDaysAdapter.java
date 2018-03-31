package pl.almestinio.countdowndays.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.almestinio.countdowndays.R;
import pl.almestinio.countdowndays.model.CountdownDay;
import pl.almestinio.countdowndays.ui.menuView.MenuContracts;
import pl.almestinio.countdowndays.util.DateUtil;

/**
 * Created by mesti193 on 3/31/2018.
 */

public class CountdownDaysAdapter extends RecyclerView.Adapter<CountdownDaysAdapter.ViewHolder> {

    private MenuContracts.Presenter presenter;
    private List<CountdownDay> countdownDayList;
    private Context context;
    private View view;

    public CountdownDaysAdapter(List<CountdownDay> countdownDayList, Context context, MenuContracts.Presenter presenter){
        this.countdownDayList = countdownDayList;
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    public CountdownDaysAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_countdown, parent, false);
        return new CountdownDaysAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CountdownDay countdownDay = countdownDayList.get(position);

        setCountdownDay(holder, countdownDay);

        holder.textViewTitle.setText(countdownDay.getTitle());
        holder.textViewDate.setText(countdownDay.getDate().getDayOfMonth()+"."+countdownDay.getDate().getMonthOfYear()+"."+countdownDay.getDate().getYear());

        switch (countdownDay.getColor()){
            case "red":
                holder.textViewCountdownDays.setBackgroundResource(R.drawable.circle_red);
                break;
            default:
                holder.textViewCountdownDays.setBackgroundResource(R.drawable.circle);
                break;
        }


        holder.constraintLayout.setOnClickListener(v -> presenter.onHolderClicked(countdownDay.getTitle()));
        holder.buttonMoreOptions.setOnClickListener(v -> presenter.onMoreOptionsButtonClicked(holder.buttonMoreOptions, position));
    }

    public void setCountdownDay(ViewHolder holder, CountdownDay countdownDay){

        DateTime dateTime = new DateTime();
        int days = DateUtil.getDifferenceBetweenTwoDates(dateTime, countdownDay.getDate());
        holder.textViewCountdownDays.setText(String.valueOf(days));
    }

    @Override
    public int getItemCount() {
        return countdownDayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layoutHolder)
        ConstraintLayout constraintLayout;
        @BindView(R.id.textViewTitle)
        TextView textViewTitle;
        @BindView(R.id.textViewDate)
        TextView textViewDate;
        @BindView(R.id.textViewCountdownDays)
        TextView textViewCountdownDays;
        @BindView(R.id.buttonMoreOptions)
        Button buttonMoreOptions;

        public ViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }

    }

}
