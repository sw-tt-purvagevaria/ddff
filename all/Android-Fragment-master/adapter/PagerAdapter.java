package com.css.bcg.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.css.bcg.R;
import com.css.bcg.model.TestimonialModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/***************************************************************************************************
 * Class name :: TestimonialAdapter
 * Functionality :: Adapter will set data of images of rooms/hotels
 ****************************************************************************************************/
public class TestimonialAdapter extends PagerAdapter {
    String TAG = "TestimonialAdapter";

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<TestimonialModel> testimonialModelArrayList;

    public TestimonialAdapter(Context context, ArrayList<TestimonialModel> testimonialModelArrayList) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.testimonialModelArrayList = testimonialModelArrayList;
    }


    @Override
    public int getCount() {
        return testimonialModelArrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @SuppressWarnings("unused")
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        Holder holder = null;
        View itemView = null;

        if (itemView == null) {
            holder = new Holder();
            itemView = inflater.inflate(R.layout.list_testimonial, container, false);
            holder.txtTestimonials = (TextView) itemView.findViewById(R.id.txtTestimonials);
            holder.txtTestimonialsDescription = (TextView) itemView.findViewById(R.id.txtTestimonialsDescription);
            holder.txtTestimonialsText = (TextView) itemView.findViewById(R.id.txtTestimonialsText);
            holder.imgTestimonialAfter = (ImageView) itemView.findViewById(R.id.imgTestimonialAfter);
            holder.imgTestimonialBefore = (ImageView) itemView.findViewById(R.id.imgTestimonialBefore);
            itemView.setTag(holder);
        } else {
            holder = (Holder) itemView.getTag();
        }

        TestimonialModel testimonialModel = testimonialModelArrayList.get(position);
        holder.txtTestimonials.setText(testimonialModel.getTestimonialText());
        holder.txtTestimonialsDescription.setText(testimonialModel.getDescription());
        holder.txtTestimonialsText.setText(testimonialModel.getTitle());


        if (testimonialModel.getBeforeImage() != null || !testimonialModel.getBeforeImage().isEmpty()) {
            Picasso.with(context)
                    .load(testimonialModel.getBeforeImage())
                    .placeholder(R.drawable.trainer_profile)
                    .error(R.drawable.trainer_profile)
                    .fit()
                    .into(holder.imgTestimonialBefore);

            Picasso.with(context)
                    .load(testimonialModel.getAfterImage())
                    .placeholder(R.drawable.trainer_profile)
                    .error(R.drawable.trainer_profile)
                    .fit()
                    .into(holder.imgTestimonialAfter);
        }

        // Add viewpager_item.xml to ViewPager
        ((ViewPager) container).addView(itemView);

        return itemView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        /*// Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);*/
    }


    static class Holder {

        TextView txtTestimonials, txtTestimonialsDescription, txtTestimonialsText;
        ImageView imgTestimonialAfter, imgTestimonialBefore;

    }
}
