package com.fnp.spotifystreamerstage1;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fnp.spotifystreamerstage1.adapters.TopTracksAdapter;

import java.util.List;

import kaaes.spotify.webapi.android.models.Track;

public class TopTracksFragment extends Fragment{

    private TopTracksAdapter topTracksAdapter;
    private LinearLayout mWarningView;
    private TextView mWarningTitle, mWarningMessage;

    public TopTracksFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_top_tracks, container, false);
        ListView mListView = (ListView) v.findViewById(R.id.listview);
        mWarningView = (LinearLayout) v.findViewById(R.id.warning_view);
        mWarningTitle = (TextView) v.findViewById(R.id.warning_title);
        mWarningMessage = (TextView) v.findViewById(R.id.warning_message);

        topTracksAdapter = new TopTracksAdapter(getActivity(), R.layout.track_item);
        if(savedInstanceState != null){
            addTopTracks(); //We might have already (in case Activity got destroyed)
        }
        mListView.setAdapter(topTracksAdapter);

        return v;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState == null) { //Only needed the first time
            MainActivity.getNetworkFragment().searchTopTracks
                    (((TopTracksActivity) getActivity()).getArtistId());
        }
    }

    private void addTopTracks(){
        List<Track> trackList = ((TopTracksActivity) getActivity()).getTopTracksList();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
            topTracksAdapter.addAll(trackList);
        } else {
            for (Track track : trackList) {
                topTracksAdapter.add(track);
            }
        }
    }

    public void onNetworkSuccess(){

        List<Track> trackList = ((TopTracksActivity) getActivity()).getTopTracksList();
        if(trackList.size() == 0){
            mWarningTitle.setText(String.format(getString(R.string.no_results_found),
                    ((TopTracksActivity) getActivity()).getArtistName()));
            mWarningView.setVisibility(View.VISIBLE);
            mWarningMessage.setVisibility(View.GONE); //No need to redefine the search here
        }
        else {
            addTopTracks();
        }
    }

    public void onNetworkError(String message){
        mWarningTitle.setText(String.format(getString(R.string.no_results_found),
                ((TopTracksActivity) getActivity()).getArtistName()));
        mWarningMessage.setText(message);

        mWarningView.setVisibility(View.VISIBLE);
    }

}
