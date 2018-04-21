package in.cdac.newsfeedapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by Dell1 on 26/01/2018.
 */


public class NewsFeedAdapter extends ArrayAdapter<NewsPojo> {

    List<NewsPojo> pojoList;
    Context context;
    Bitmap bitmap;
    private static String TAG = NewsFeedAdapter.class.getName();


    public NewsFeedAdapter(@NonNull Context context, @NonNull List<NewsPojo> pojoList, Bitmap bitmap) {
        super(context, 0, pojoList);

        this.context = context;
        this.pojoList = pojoList;
        this.bitmap = bitmap;

    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Nullable
    @Override
    public NewsPojo getItem(int position) {

        return super.getItem(position);

    }


    @Override
    public int getPosition(@Nullable NewsPojo item) {
        return super.getPosition(item);
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View itemView = convertView;
        Context context = parent.getContext();

        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.news_list_item_layout, parent, false);

        }


        TextView titleText, sectionText, webPublicationDateText, authorText, webUrlText;
        ImageView imageViewAuthor;

        NewsPojo itemPojo = getItem(position);

        try {
            titleText = (TextView) itemView.findViewById(R.id.textViewTitle);
            String title = itemPojo.getWebTitle();
            titleText.setText(title);
        } catch (NullPointerException n) {
            Log.e(TAG, "" + n.getMessage());
        }

        sectionText = (TextView) itemView.findViewById(R.id.textView2SectionName);
        String sectionName = itemPojo.getSectionName();
        sectionText.setText(sectionName);

        webPublicationDateText = (TextView) itemView.findViewById(R.id.textView3WebPublication);
        String dt = itemPojo.getWebPublicationDate();

        //  String date=DateFormat.getDateTimeInstance().format(dt);
        webPublicationDateText.setText(dt);

        webUrlText = (TextView) itemView.findViewById(R.id.textView4WebUrl);
        String webUrl = itemPojo.getWebUrl();
        webUrlText.setText(webUrl);

        imageViewAuthor = (ImageView) itemView.findViewById(R.id.imgAuthor);
        Bitmap bitmap = itemPojo.getBylineImageUrl();

        if (bitmap != null) {
            imageViewAuthor.setImageBitmap(bitmap);
            imageViewAuthor.setScaleX((float) 0.8);
            imageViewAuthor.setScaleY((float) 0.8);
            imageViewAuthor.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        } else {
            imageViewAuthor.setVisibility(View.GONE);
        }


        authorText = (TextView) itemView.findViewById(R.id.textView5Author);
        String authorNameFirst = itemPojo.getFirstName();
        String authorNameLast = itemPojo.getLastName();
        String authorName = authorNameFirst + " " + authorNameLast;


        if ((authorNameFirst != null) || (authorNameLast != null)) {

            authorText.setText(authorName);
        } else authorText.setVisibility(View.GONE);


        return itemView;
    }

}
