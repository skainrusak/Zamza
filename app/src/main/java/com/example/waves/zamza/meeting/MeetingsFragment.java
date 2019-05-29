package com.example.waves.zamza.meeting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;
import com.example.waves.zamza.ColdCallingLab;
import com.example.waves.zamza.MapsActivity;
import com.example.waves.zamza.R;

import java.util.List;

public class MeetingsFragment extends Fragment {
    private RecyclerView mRecyclerViewMeeting;
    private MeetingAdapter mMeetingAdapter;
    private FloatingActionButton addNewMeetingFAB;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu , MenuInflater menuInflater){
        super.onCreateOptionsMenu(menu , menuInflater);
        menuInflater.inflate(R.menu.menu_main_tool_bar , menu);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        super.onAttach(context);

    }
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meetings, container, false);
        mRecyclerViewMeeting = (RecyclerView)view.findViewById(R.id.meeting_rv);
        mRecyclerViewMeeting.setLayoutManager(new LinearLayoutManager(getActivity()));
        addNewMeetingFAB = (FloatingActionButton)view.findViewById(R.id.add_meeting);


        addNewMeetingFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Meeting meeting = new Meeting();
                ColdCallingLab.get(getActivity()).addMeeting(meeting);
                Intent intent = AddMeetingActivity.newIntent(getActivity(),meeting.getUuidMeeting());
                startActivity(intent);
            }
        });

        updateUI();
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
    public void updateUI() {
        ColdCallingLab coldCallingLab = ColdCallingLab.get(getActivity());
        List<Meeting>meetings = coldCallingLab.getMeeting();

        if (mMeetingAdapter == null){
            mMeetingAdapter = new MeetingAdapter(meetings);
            mRecyclerViewMeeting.setAdapter(mMeetingAdapter);
        }else {
            mMeetingAdapter.setMeeting(meetings);
            mMeetingAdapter.notifyDataSetChanged();
        }

    }
    private class MeetingHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
private Meeting mMeeting;
private TextView nameMeeting;
private TextView placeMeeting;
private Button showMap;
private TextView meetingDate;

        public MeetingHolder(@NonNull final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

nameMeeting = (TextView)itemView.findViewById(R.id.name_meeting);
showMap = (Button)itemView.findViewById(R.id.show_map);
meetingDate = (TextView)itemView.findViewById(R.id.meeting_through);

showMap.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity() , MapsActivity.class);
        startActivity(intent);
    }
});
placeMeeting = (TextView)itemView.findViewById(R.id.place_meeting);
            Button deleteButton = (Button) itemView.findViewById(R.id.delete_meeting);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
mMeetingAdapter.delete(getAdapterPosition());
updateUI();

                }
            });

        }


        @Override
        public void onClick(View v) {
        }
        public void bindMeet (Meeting meeting){
            mMeeting = meeting;
            nameMeeting.setText(meeting.getNameCompanyMeeting());
            placeMeeting.setText(meeting.getPlaceMeeting());
            meetingDate.setText(meeting.getmDate().toString());

        }
    }
    private class MeetingAdapter extends RecyclerView.Adapter<MeetingHolder>{
        private List<Meeting>mMeetings;

        public MeetingAdapter (List<Meeting>meetings){
            mMeetings = meetings;
        }

        @NonNull
        @Override
        public MeetingHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.item_meeting , viewGroup , false);
            return new MeetingHolder(view);
        }

public void setMeeting (List<Meeting>meeting){
            mMeetings = meeting;
}
        @Override
        public void onBindViewHolder(@NonNull MeetingHolder meetingHolder, int i) {
        Meeting meeting = mMeetings.get(i);
        meetingHolder.bindMeet(meeting);
        }

        @Override
        public int getItemCount() {
            return mMeetings.size();
        }

        public void delete(int position){
            ColdCallingLab coldCallingLab = ColdCallingLab.get(getActivity());
            Meeting meeting = mMeetings.get(position);
            coldCallingLab.deleteMeeting(meeting);
            mMeetingAdapter.notifyItemRemoved(position);
            mMeetingAdapter.notifyItemRangeChanged(position , coldCallingLab.getMeeting().size());
        }

    }
}