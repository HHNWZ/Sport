package kelvin.tablayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class SectionsPagerAdapter extends FragmentPagerAdapter{

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:

                FriendsFragment friendsFragment = new FriendsFragment();
                return friendsFragment;

            case 1:
                ChatsFragment chatsFragment = new ChatsFragment();
                return  chatsFragment;

            case 2:
                RequestsFragment requestsFragment = new RequestsFragment();
                return requestsFragment;

            default:
                return  null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position){

        switch (position) {
            case 0:
                return "朋友";

            case 1:
                return "聊天";

            case 2:
                return "全部使用者";

            default:
                return null;
        }

    }
}
