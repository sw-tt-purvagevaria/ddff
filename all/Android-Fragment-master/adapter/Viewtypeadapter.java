package com.wes.bidguru.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wes.bidguru.R;
import com.wes.bidguru.model.ProductBean;
import com.wes.bidguru.tools.Extras;
import com.wes.bidguru.ui.ProductDetailActivity;
import com.wes.bidguru.utils.Utils;

import java.util.ArrayList;

import static com.wes.bidguru.utils.Utils.KEY_FROM_ACTIVE_AUCTION;

/**
 * class name :: ActiveAuctionRecycleAdapter
 * display list of active auctions
 */
public class ActiveAuctionRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_ITEM = 0;
    private final int VIEW_ALL = 1;
    String TAG = "ActiveAuction";
    private Context mContext;
    private ArrayList<ProductBean> mArrayListAuction;

    private boolean isViewMore = false;
    private View.OnClickListener onClickListener;

    public ActiveAuctionRecycleAdapter(Context context, ArrayList<ProductBean> arrayListAuction, boolean isViewMore, View.OnClickListener onClickListener) {
        mContext = context;
        mArrayListAuction = arrayListAuction;
        this.isViewMore = isViewMore;
        this.onClickListener = onClickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            View itemLayoutView = LayoutInflater.from(mContext).inflate(R.layout.list_item_active_auction, parent, false);
            return new AuctionViewHolder(itemLayoutView);
        } else if (viewType == VIEW_ALL) {
            View viewAll = LayoutInflater.from(mContext).inflate(R.layout.view_all, parent, false);
            return new ViewAll(viewAll);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        if (holder instanceof AuctionViewHolder) {//list item

            //((AuctionViewHolder) holder).textViewBidPrice.setText(mContext.getResources().getString(R.string.Rs) + " " + mArrayListAuction.get(position).getBidPrice());
            ((AuctionViewHolder) holder).textViewBidPrice.setText("Per Bid " + String.format("%.0f", mArrayListAuction.get(position).getBidPrice()) + " Mudras");
            ((AuctionViewHolder) holder).textViewProductTitle.setText(mArrayListAuction.get(position).getTitle());
            ((AuctionViewHolder) holder).textViewMrp.setText(mContext.getResources().getString(R.string.Rs) + " " + String.format("%.2f", mArrayListAuction.get(position).getMrp()));
            ((AuctionViewHolder) holder).textViewMrp.setPaintFlags(((AuctionViewHolder) holder).textViewMrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            String maxBidWinner = mArrayListAuction.get(position).getMaxBidWinnerName();
            String lowBidWinne = mArrayListAuction.get(position).getLowestBidWinnweName();

            String lowWinnerLocation = mArrayListAuction.get(position).getLowestWinnerLocation();
            String maxWinnerLocation = mArrayListAuction.get(position).getMaxWinnerLocation();

            //lowest bid
            if (isStrNotNull(lowBidWinne)) {
                ((AuctionViewHolder) holder).textLuWinnerName.setText(lowBidWinne);
                ((AuctionViewHolder) holder).lblLowesBid.setVisibility(View.VISIBLE);
                ((AuctionViewHolder) holder).textLuWinnerName.setVisibility(View.VISIBLE);

                //set lowest winner location
                if (isStrNotNull(lowWinnerLocation)) {
                    ((AuctionViewHolder) holder).textLuWinnerName.append(", " + mArrayListAuction.get(position).getLowestWinnerLocation());
                }


            } else {
                ((AuctionViewHolder) holder).lblLowesBid.setVisibility(View.GONE);
                ((AuctionViewHolder) holder).textLuWinnerName.setVisibility(View.GONE);
            }

            //max bid
            if (isStrNotNull(maxBidWinner)) {
                ((AuctionViewHolder) holder).textMaxWinnerName.setText(maxBidWinner);
                ((AuctionViewHolder) holder).lblMaxBid.setVisibility(View.VISIBLE);
                ((AuctionViewHolder) holder).textMaxWinnerName.setVisibility(View.VISIBLE);

                //set max winner location
                if (isStrNotNull(maxWinnerLocation)) {
                    ((AuctionViewHolder) holder).textMaxWinnerName.append(", " + maxWinnerLocation);
                }
            } else {
                ((AuctionViewHolder) holder).lblMaxBid.setVisibility(View.GONE);
                ((AuctionViewHolder) holder).textMaxWinnerName.setVisibility(View.GONE);
            }

            Log.d(TAG, "holder.imageViewproduct :: " + mArrayListAuction.get(position).getIconPath());
            Utils utils = new Utils();
            //Picasso.with(mContext).load(mArrayListAuction.get(position).getIconPath()).into(holder.imageViewproduct);

            utils.setImageFromUrl(((AuctionViewHolder) holder).imageViewproduct, mArrayListAuction.get(position).getIconPath(), 0);//set image

            //set count down timer
            utils.setCountDownTimer(mArrayListAuction.get(position).getEndDate(), ((AuctionViewHolder) holder).textViewTimeD,
                    ((AuctionViewHolder) holder).textViewTimeHr, ((AuctionViewHolder) holder).textViewTimeMi, ((AuctionViewHolder) holder).textViewTimeSec);

            ((AuctionViewHolder) holder).btnStartBid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startBiddingActivity(mArrayListAuction.get(position).getProductId(), mArrayListAuction.get(position).getBidPrice());
                }
            });

            ((AuctionViewHolder) holder).imageViewproduct.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    startBiddingActivity(mArrayListAuction.get(position).getProductId(), mArrayListAuction.get(position).getBidPrice());
                }
            });

            ((AuctionViewHolder) holder).textViewProductTitle.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    startBiddingActivity(mArrayListAuction.get(position).getProductId(), mArrayListAuction.get(position).getBidPrice());
                }
            });

            //set height full
            if (mArrayListAuction.size() > 1) {

            } else {
                RelativeLayout.LayoutParams rel_btn = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ((AuctionViewHolder) holder).auctionMainLayout.setLayoutParams(rel_btn);
            }
        } else {
            ((ViewAll) holder).txtViewMore.setOnClickListener(onClickListener);
            ((ViewAll) holder).txtViewMore.setTag("active_auction");
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isViewMore) {
            if (isPositionItem(position))
                return VIEW_ITEM;
            return VIEW_ALL;
        } else {
            return VIEW_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        if (isViewMore) {
            return mArrayListAuction.size() + 1; //last item is view more so +1
        } else {
            return mArrayListAuction.size();
        }
    }

    //return true if it is last item of List
    private boolean isPositionItem(int position) {
        //return position == 0;//for headet
        return (position != getItemCount() - 1); // last position for footer
    }


    private void startBiddingActivity(int productId, double bidPrice) {
        //Intent i = new Intent(mContext, ProductBidStartActivity.class);

        Intent i = new Intent(mContext, ProductDetailActivity.class);
        Bundle b = new Bundle();
        b.putInt(Extras.PRODUCT_ID, productId);
        b.putDouble(Extras.BID_PRICE, bidPrice);
        b.putBoolean(KEY_FROM_ACTIVE_AUCTION, true);
        i.putExtras(b);
        mContext.startActivity(i);
    }

    //holder class AuctionViewHolder
    public class AuctionViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTimeD;
        TextView textViewTimeHr;
        TextView textViewTimeMi;
        TextView textViewTimeSec;
        TextView textViewBidPrice;
        TextView textViewMrp;
        TextView textViewProductTitle;
        //NiketChanges
        TextView textLuWinnerName;
        TextView textMaxWinnerName;
        ImageView imageViewproduct;
        Button btnStartBid;

        TextView lblMaxBid, lblLowesBid;

        RelativeLayout auctionMainLayout;

        public AuctionViewHolder(View v) {
            super(v);
            this.textViewTimeD = (TextView) v.findViewById(R.id.textViewDay);
            this.textViewTimeHr = (TextView) v.findViewById(R.id.textViewHr);
            this.textViewTimeMi = (TextView) v.findViewById(R.id.textViewMi);
            this.textViewTimeSec = (TextView) v.findViewById(R.id.textViewSec);
            this.textViewBidPrice = (TextView) v.findViewById(R.id.textViewBidPrice);
            this.textViewMrp = (TextView) v.findViewById(R.id.textViewMrp);
            this.textViewProductTitle = (TextView) v.findViewById(R.id.productTitle);
            this.imageViewproduct = (ImageView) v.findViewById(R.id.productImageView);
            this.btnStartBid = (Button) v.findViewById(R.id.btnStartBid);
            //NiketChanges
            this.textLuWinnerName = (TextView) v.findViewById(R.id.textLUWName);
            this.textMaxWinnerName = (TextView) v.findViewById(R.id.textMBName);

            lblMaxBid = (TextView) v.findViewById(R.id.lblMaxBid);
            lblLowesBid = (TextView) v.findViewById(R.id.lblLowesBid);

            auctionMainLayout = (RelativeLayout) v.findViewById(R.id.auctionMainLayout);

        }
    }//end of AuctionViewHolder()


    public class ViewAll extends RecyclerView.ViewHolder {
        TextView txtViewMore;

        public ViewAll(View itemLayoutView) {
            super(itemLayoutView);
            txtViewMore = (TextView) itemLayoutView.findViewById(R.id.txtViewMore);
        }//end of DateHolder(view)
    }//end of DateHolder class

    protected boolean isStrNotNull(String string) {
        return string != null && !string.isEmpty();
    }//end of isStrNotNull()
}//end of ActiveAuctionRecycleAdapter()
