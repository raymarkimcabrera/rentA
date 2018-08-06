package com.skuld.user.rent_a.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.skuld.user.rent_a.BaseActivity;
import com.skuld.user.rent_a.R;

public class DashboardActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialize();

        initNavigationMenu();
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_dashboard;
    }


    private void initialize() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setTitle(R.string.app_name);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initNavigationMenu() {
        mNavigationView.getMenu().clear();
        mNavigationView.inflateMenu(R.menu.drawe_logged_in);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                mDrawerLayout.closeDrawer(GravityCompat.START);

                switch (menuItem.getItemId()) {
                    case R.id.menuItemHome:
                        break;

                    case R.id.menuItemRewards:
                        if (FigaroCoffeeBase.isNetworkAvailable(mContext))
                            startActivity(TutorialPageActivity.newIntent(mContext, TutorialPageActivity.FALLBACK_REWARD));
                        break;

                    case R.id.menuItemStores:
                        if (FigaroCoffeeBase.isNetworkAvailable(mContext))
                            startActivity(StoresListActivity.newIntent(mContext));
                        break;

                    case R.id.menuItemGift:
                        if (FigaroCoffeeBase.isNetworkAvailable(mContext))
                            startActivity(TutorialPageActivity.newIntent(mContext, TutorialPageActivity.FALLBACK_GIFT));
                        break;

                    case R.id.menuItemOrderNow:
                        if (FigaroCoffeeBase.isNetworkAvailable(mContext))
                            startActivity(StoreBranchToOrderActivity.newIntent(mContext, StoreBranchToOrderActivity.NO_PROD_ID));
                        break;

                    case R.id.menuItemPromos:
                        if (FigaroCoffeeBase.isNetworkAvailable(mContext))
                            startActivity(PromosListActivity.newIntent(mContext));
                        break;

                    case R.id.menuItemTransactionHistory:
                        if (FigaroCoffeeBase.isNetworkAvailable(mContext))
                            startActivity(UserFunction.isLoggedIn(mContext) ? TransactionHistoryActivity.newIntent(mContext) : LoginActivity.newIntent(mContext));
                        break;
                    case R.id.menuItemInformation:
                        if (FigaroCoffeeBase.isNetworkAvailable(mContext))
                            startActivity(SplashScreenActivity.newIntent(mContext));
                        break;

                    case R.id.menuItemSettings:
                        if (FigaroCoffeeBase.isNetworkAvailable(mContext))
                            startActivity((UserFunction.isLoggedIn(mContext)) ? SettingsLoggedInActivity.newIntent(mContext) : SettingsNotLoggedInActivity.newIntent(mContext));
                        break;

                    case R.id.menuItemLogout:
                        UserFunction.logout(mContext);
                        break;

                    case R.id.menuItemLogin:
                        if (FigaroCoffeeBase.isNetworkAvailable(mContext))
                            startActivity(LoginActivity.newIntent(mContext));
                        break;

                    case R.id.menuItemRegister:
                        if (FigaroCoffeeBase.isNetworkAvailable(mContext))
                            startActivity(RegisterActivity.newIntent(mContext));
                        break;
                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
        Menu menu = mNavigationView.getMenu();
        MenuItem appVersion = menu.findItem(R.id.menuAppVersion);
        appVersion.setTitle("Version " + BuildConfig.VERSION_NAME);

    }
}
