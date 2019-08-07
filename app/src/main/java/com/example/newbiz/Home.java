package com.example.newbiz;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment  {

TextView tv;
private float initialX;
private ViewFlipper viewFlipper;
private GestureDetector mGestureDetector;

private RecyclerView recyclerView;
private RecyclerView.Adapter adapter;
private RecyclerView.LayoutManager layoutManager;
ArrayList<Single_Card> list=new ArrayList<>();

    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv=view.findViewById(R.id.tv);
        recyclerView=view.findViewById(R.id.cardsList);

        Single_Card single_card=new Single_Card();
        single_card.setImageUrl("data:image/jpeg;base64,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");
        single_card.setFoodName("Paneer ");
        single_card.setFoodPrice("50");
        single_card.setAddress("Chembur");
        single_card.setAnyOtherInfo("Get it Now!");

        list.add(single_card);
        list.add(single_card);
        list.add(single_card);
        list.add(single_card);

        CardsAdapter myadapter=new CardsAdapter(list,getContext());
        layoutManager=new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myadapter);

//        viewFlipper=view.findViewById(R.id.viewFlipper);
//
//
//
//        int[] resources = {
//                R.drawable.lotus,
//                R.drawable.sunset,
//                R.drawable.rose_blue,
//                R.drawable.beach
//        };
//
//
//
//        for (int i = 0; i < resources.length; i++) {
//            ImageView ivIcon = new ImageView(getContext());
//            ivIcon.setImageResource(resources[i]);
//            viewFlipper.addView(ivIcon);
//        }
//
//        viewFlipper.setAutoStart(true);
//        viewFlipper.setFlipInterval(2000);
//        viewFlipper.startFlipping();



        CustomGestureDetector customGestureDetector = new CustomGestureDetector();
        mGestureDetector = new GestureDetector(getContext(), customGestureDetector);


         String[] imageUrls = new String[]{
                "https://cdn.pixabay.com/photo/2016/11/11/23/34/cat-1817970_960_720.jpg",
                "https://cdn.pixabay.com/photo/2017/12/21/12/26/glowworm-3031704_960_720.jpg",
                "https://cdn.pixabay.com/photo/2017/12/24/09/09/road-3036620_960_720.jpg",
                "https://cdn.pixabay.com/photo/2017/11/07/00/07/fantasy-2925250_960_720.jpg",
                "https://cdn.pixabay.com/photo/2017/10/10/15/28/butterfly-2837589_960_720.jpg"
        };


        ViewPager viewPager=view.findViewById(R.id.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getContext(), imageUrls);
        viewPager.setAdapter(adapter);



    }



    class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            // Swipe left (next)
            if (e1.getX() > e2.getX()) {
                viewFlipper.showNext();
            }

            // Swipe right (previous)
            if (e1.getX() < e2.getX()) {
                viewFlipper.showPrevious();
            }

            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }




/*

    public boolean onTouchEvent(MotionEvent touchevent) {
        switch (touchevent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialX = touchevent.getX();
                break;

            case MotionEvent.ACTION_UP:
                float finalX = touchevent.getX();
                if (initialX > finalX) {
                    if (viewFlipper.getDisplayedChild() == 1)
                        break;

                    viewFlipper.setInAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.in_from_left));
                    viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.out_from_left));

                    viewFlipper.showPrevious();
                } else {
                    if (viewFlipper.getDisplayedChild() == 0)
                        break;

                    viewFlipper.setInAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.in_from_right));
                    viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.out_from_right));

                    viewFlipper.showNext();
                }
                break;
        }
        return false;
    }
    */

}
