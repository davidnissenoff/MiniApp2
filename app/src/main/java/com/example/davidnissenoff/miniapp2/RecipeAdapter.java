package com.example.davidnissenoff.miniapp2;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.SyncStateContract;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by David Nissenoff on 3/25/2018.
 */
// adapter is needed when you want to do any sort of list or table view
// gets data and decides where to display in the activity

public class RecipeAdapter extends BaseAdapter{
    // adapter takes the app itself and a list of data to display
    private Context mContext;
    private ArrayList<Recipe> mRecipeList;
    private LayoutInflater mInflater;

    // constructor
    public RecipeAdapter(Context mContext, ArrayList<Recipe> mRecipeList){
        // initialize instance variables
        this.mContext = mContext;
        this.mRecipeList = mRecipeList;
        mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    // methods
    // a list of methods we need to override
    // gives number of reecipes in the data source
    @Override
    public int getCount(){
        return mRecipeList.size();
    }
    // returns the item at specific position in the data source
    @Override
    public Object getItem(int position){
        return  mRecipeList.get(position);
    }
    // returns the row id associated with the specigic position in the list
    @Override
    public long getItemId(int position){
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // check if the view already exists
        // if yes, you don't need to inglate and findViewbyID again
        if (convertView == null) {
            // inflate
            convertView = mInflater.inflate(R.layout.activtity_result, parent, false);
            // add the views to the holder
            holder = new ViewHolder();
            // views
            holder.titleTextView = convertView.findViewById(R.id.recipe_title);
            holder.servingTextView = convertView.findViewById(R.id.recipe_servings);
            holder.thumbnailImageView = convertView.findViewById(R.id.recipe_pic);
            holder.prepTextView = convertView.findViewById(R.id.recipe_cooking_time);
            holder.startCooking = convertView.findViewById(R.id.start_cooking_image_button);
            // add the holder to the view
            // for future use
            convertView.setTag(holder);
        }
        // get the view golder from convertView
        else{
            holder = (ViewHolder)convertView.getTag();
        }
        // get rlevant subview of the row view
        TextView titleTextView = holder.titleTextView;
        TextView servingTextView = holder.servingTextView;
        ImageView thumbnailImageView = holder.thumbnailImageView;
        TextView prepTextView = holder.prepTextView;
        ImageButton startCooking = holder.startCooking;
        // get corresponding recipe for each row
        final Recipe recipe = (Recipe)getItem(position);
        // update the row view;s textviews and imageview to display the information
        // titleTextView
        titleTextView.setText(recipe.title);
        titleTextView.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        titleTextView.setTextSize(20);
        // servingTextView
        servingTextView.setText(recipe.servings + " servings");
        servingTextView.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
        servingTextView.setTextSize(16);
        // prepTextView
        prepTextView.setText(recipe.prepTime);
        prepTextView.setTextColor(0xff000000);
        prepTextView.setTextSize(16);

        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setData(Uri.parse(recipe.instructionURL));
        final PendingIntent pendingIntent = PendingIntent.getActivity(mContext,0, intent, 0);
        startCooking.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String instruction = "Instructions to make " + recipe.title;
                NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, "channel_ID");
                builder.setSmallIcon(R.drawable.skillet);
                builder.setContentTitle("Instructions");
                builder.setContentText(instruction);
                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);
                Toast.makeText(mContext, "See notifications to view " + instruction, Toast.LENGTH_LONG).show();
                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(mContext);
                notificationManagerCompat.notify((int) System.currentTimeMillis(), builder.build());
            }
        });
        Picasso.with(mContext).load(recipe.image).into(thumbnailImageView);
        return convertView;}
        // imageView
        // use Picasso library to load image from the image url


    // viewHolder
    // is used to customize what you want to put into the view
    // it depends on the layout design of your row
    // this will be a private static class you have to define
    private static class ViewHolder{
        public TextView titleTextView;
        public TextView servingTextView;
        public ImageView thumbnailImageView;
        public TextView prepTextView;
        public ImageButton startCooking;
    }
    // intent is used to pass information between activities
    // intent --> package
    // sender, receiver

}