package com.fnp.spotifystreamerstage1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fnp.spotifystreamerstage1.R;
import com.squareup.picasso.Picasso;

import kaaes.spotify.webapi.android.models.Artist;

public class ArtistArrayAdapter extends ArrayAdapter<Artist> {

    private int resId;

    public ArtistArrayAdapter(Context context, int resId) {
        super(context, resId);
        this.resId = resId;
    }

    public static class ViewHolder {
        public final ImageView artistImageView;
        public final TextView artistTextView;

        public ViewHolder(View view) {
            artistImageView = (ImageView) view.findViewById(R.id.artist_imageview);
            artistTextView = (TextView) view.findViewById(R.id.artist_textview);
        }
    }

    public View getView(int position, View convertView, final ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resId, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Artist artist = getItem(position);

        if (artist.images.size() > 0) {
            String imageUrl;
            //Try to load medium image if not, the first available
            imageUrl = (artist.images.size() > 1) ? artist.images.get(1).url
                    : artist.images.get(0).url;

            Picasso.with(getContext())
                    .load(imageUrl)
                    .resizeDimen(R.dimen.cover_size, R.dimen.cover_size)
                    .into(viewHolder.artistImageView);
        }
        viewHolder.artistTextView.setText(artist.name);

        return convertView;
    }
}
