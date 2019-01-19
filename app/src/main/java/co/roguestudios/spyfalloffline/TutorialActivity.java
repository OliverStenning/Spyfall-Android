package co.roguestudios.spyfalloffline;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TutorialActivity extends AppCompatActivity {

    private ViewPager tutorialPager;
    private ViewPagerAdapter tutorialPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button nextButton;
    private Button prevButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        tutorialPager = findViewById(R.id.tutorialPager);
        dotsLayout = findViewById(R.id.dotsLayout);
        nextButton = findViewById(R.id.nextButton);
        prevButton = findViewById(R.id.prevButton);

        layouts = new int[] { R.layout.tutorial_slide1, R.layout.tutorial_slide2, R.layout.tutorial_slide3 };

        addBottomDots(0);

        tutorialPagerAdapter = new ViewPagerAdapter();
        tutorialPager.setAdapter(tutorialPagerAdapter);
        tutorialPager.addOnPageChangeListener(tutorialPagerPageChangeListener);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextPos = tutorialPager.getCurrentItem() + 1;
                if (nextPos < layouts.length)
                    tutorialPager.setCurrentItem(nextPos, true);
                else
                    exit();
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int prevPos = tutorialPager.getCurrentItem() - 1;
                if (!(prevPos < 0))
                    tutorialPager.setCurrentItem(prevPos, true);
            }
        });

    }



    @SuppressWarnings("deprecation")
    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                dots[i].setText(Html.fromHtml("&#8226;",Html.FROM_HTML_MODE_LEGACY));
            } else {
                dots[i].setText(Html.fromHtml("&#8226;"));
            }
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.inactive_dot));
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(getResources().getColor(R.color.active_dot));
    }

    private void exit() {

    }

    ViewPager.OnPageChangeListener tutorialPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            if (position == layouts.length - 1) {
                nextButton.setText(getString(R.string.done));
                exitButton.setVisibility(View.INVISIBLE);
            } else {
                nextButton.setText(getString(R.string.next));
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };

    public class ViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View slide = layoutInflater.inflate(layouts[position], container, false);
            container.addView(slide);
            return slide;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) { return view == obj; }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

    }

}
