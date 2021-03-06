package br.com.getapp.structure.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.getapp.R;
import br.com.getapp.structure.alarmbuild.AlarmBuilder;
import br.com.getapp.structure.controller.DataBase;
import br.com.getapp.structure.model.Alarm;
import br.com.getapp.structure.view.AlarmHolder;
import br.com.getapp.structure.view.fragment.ListFragment;
import br.com.getapp.structure.view.fragment.RemoveFragment;

/**
 * Created by User on 12/05/2016.
 */
public class RemoveAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<Alarm> alarms;

    public RemoveAdapter(Context context) {
        this.context = context;
        DataBase db = new DataBase(context);
        alarms = db.getAllAlarms();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.removeralarm_adapter_layout, null);
        AlarmHolder holder = new AlarmHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int pos = position;
        final DataBase db = new DataBase(context);

        final Alarm clock = alarms.get(position);

        String hora;
        String minuto;
        hora = clock.getHora();
        minuto = clock.getMinuto();
        if(Integer.valueOf(hora) >= 0 && Integer.valueOf(hora) <= 9) {
            hora = "0"+hora;
        }
        if(Integer.valueOf(minuto) >= 0 && Integer.valueOf(minuto) <= 9) {
            minuto = "0"+minuto;
        }

        ImageButton imgBtn = (ImageButton) holder.itemView.findViewById(R.id.removeImageButton);
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.delete(clock);
                AlarmBuilder alarm = new AlarmBuilder(context);
                alarm.alarmCancel(clock);
                ListFragment.refresh();
                RemoveFragment.refresh();
            }
        });

        TextView tempo = (TextView) holder.itemView.findViewById(R.id.temp);
        tempo.setText(hora + ":" + minuto);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

}