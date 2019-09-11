package com.example.evente;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.evente.helper.Sesija;

import java.util.List;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        MainPageFragment.OnFragmentInteractionListener, EventDetailsFragment.OnFragmentInteractionListener,
        ZainteresovanFragment.OnFragmentInteractionListener, IdemFragment.OnFragmentInteractionListener,
        PopularniFragment.OnFragmentInteractionListener, NoviFragment.OnFragmentInteractionListener,
        IzdvojeniFragment.OnFragmentInteractionListener, MuzickiFragment.OnFragmentInteractionListener,
        SportskiFragment.OnFragmentInteractionListener, KulturniFragment.OnFragmentInteractionListener {
    public static int navigationItemId;
    private Menu menu;
    private SearchView searchView;
    boolean firstSearch = true;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        SetStartFragment();
        setSupportActionBar(toolbar);
       /* fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        this.menu=menu;

        /*Search button implementation*/
        MenuItem searhMenuItem =  menu.findItem(R.id.action_search);

        searchView = (SearchView) searhMenuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                onSearchRequested();
                //custom
                firstSearch=false;
                searchItems(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("") && !firstSearch) {
                    searchItems(newText);
                }
                return false;
            }
        });


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //postavljanje imena i prezimena u drawer
        View headerView = navigationView.getHeaderView(0);
        TextView imePrezimeHeader = (TextView) headerView.findViewById(R.id.imePrezimeHeader);
        imePrezimeHeader.setText(Sesija.getLogiraniKorisnik().Ime+" "+Sesija.getLogiraniKorisnik().Prezime);

        //postavljanje emaila u drawer
        TextView emailHeader = (TextView) headerView.findViewById(R.id.emailHeader);
        emailHeader.setText(Sesija.getLogiraniKorisnik().Email);

        return true;
    }

    private void searchItems(String query) {
        Fragment fragment=null;
        try {
            fragment = (Fragment) MainPageFragment.newInstance(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(fragment!=null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main2Content,fragment).commit();
        }    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    private void SetStartFragment() {
        android.support.v4.app.Fragment fragment=null;
        Class fragmentClass = MainPageFragment.class;

        if(fragmentClass!=null)
        {
            try {
                fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main2Content,fragment).commit();
        }
        navigationItemId=R.id.mainPageFragment;
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        if(menu!=null)
        {
            menu.findItem(R.id.action_search).setVisible(true);
        }
        toolbar.setTitle("Početna");
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Zainteresovan) {
            menu.findItem(R.id.action_search).setVisible(false);
            toolbar.setTitle("Zainteresovan");

            android.support.v4.app.Fragment fragment=null;
            Class fragmentClass = ZainteresovanFragment.class;

            if(fragmentClass!=null)
            {
                try {
                    fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main2Content,fragment).commit();
            }
        }
        else if(id == R.id.nav_Idem)
        {
            menu.findItem(R.id.action_search).setVisible(false);
            toolbar.setTitle("Idem");

            android.support.v4.app.Fragment fragment=null;
            Class fragmentClass = IdemFragment.class;

            if(fragmentClass!=null)
            {
                try {
                    fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main2Content,fragment).commit();
            }
        }
        else if (id == R.id.nav_pocetna) {
            menu.findItem(R.id.action_search).setVisible(true);
            toolbar.setTitle("Pocetna");

            android.support.v4.app.Fragment fragment=null;
            Class fragmentClass = MainPageFragment.class;

            if(fragmentClass!=null)
            {
                try {
                    fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main2Content,fragment).commit();
            }

        } else if (id == R.id.nav_popularni) {
            menu.findItem(R.id.action_search).setVisible(false);
            toolbar.setTitle("Popularni");

            android.support.v4.app.Fragment fragment=null;
            Class fragmentClass = PopularniFragment.class;

            if(fragmentClass!=null)
            {
                try {
                    fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main2Content,fragment).commit();
            }
        } else if (id == R.id.nav_novi) {
            menu.findItem(R.id.action_search).setVisible(false);
            toolbar.setTitle("Novi");

            android.support.v4.app.Fragment fragment=null;
            Class fragmentClass = NoviFragment.class;

            if(fragmentClass!=null)
            {
                try {
                    fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main2Content,fragment).commit();
            }
        } else if (id == R.id.nav_izdvojeni) {
            menu.findItem(R.id.action_search).setVisible(false);
            toolbar.setTitle("Izdvojeni");

            android.support.v4.app.Fragment fragment=null;
            Class fragmentClass = IzdvojeniFragment.class;
            if(fragmentClass!=null)
            {
                try {
                    fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main2Content,fragment).commit();
            }

        } else if (id == R.id.nav_muzicki) {
            menu.findItem(R.id.action_search).setVisible(false);
            toolbar.setTitle("Muzicki");

            android.support.v4.app.Fragment fragment=null;
            Class fragmentClass = MuzickiFragment.class;
            if(fragmentClass!=null)
            {
                try {
                    fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main2Content,fragment).commit();
            }
        } else if (id == R.id.nav_sportski) {
            menu.findItem(R.id.action_search).setVisible(false);
            toolbar.setTitle("Sportski");

            android.support.v4.app.Fragment fragment=null;
            Class fragmentClass = SportskiFragment.class;
            if(fragmentClass!=null)
            {
                try {
                    fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main2Content,fragment).commit();
            }
        }else if (id == R.id.nav_kulturni) {
            menu.findItem(R.id.action_search).setVisible(false);
            toolbar.setTitle("Kulturni");

            android.support.v4.app.Fragment fragment=null;
            Class fragmentClass = KulturniFragment.class;
            if(fragmentClass!=null)
            {
                try {
                    fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main2Content,fragment).commit();
            }
        }
        else if(id ==R.id.nav_odjava)
        {
            Sesija.setLogiraniKorisnik(null);
            final Intent intent = new Intent(MyApp.getContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(String imageURL, String naziv, String objekatOdrzavanja, String datumOdrzavanja,  String opis, int EventId) {
        EventDetailsFragment newFragment = new EventDetailsFragment();
                Bundle args = new Bundle();
                args.putString("imageURL", imageURL);
                args.putString("naziv", naziv);
                args.putString("objekatOdrzavanja", objekatOdrzavanja);
                args.putString("datumOdrzavanja", datumOdrzavanja);
                args.putString("opis", opis);
                args.putInt("EventId", EventId);
                newFragment.setArguments(args);
                menu.findItem(R.id.action_search).setVisible(false);
                toolbar.setTitle(naziv);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.main2Content, newFragment);
                transaction.addToBackStack(null);

// Commit the transaction
                transaction.commit();
    }

    @Override
    public void onFragmentInteraction() {

    }

    @Override
    public void onBackPressed() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();

        navigationItemId = fragments.get(0).getView().getId();

        if(navigationItemId==R.id.mainPageFragment){
            finish();
            System.exit(0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (fragments.size()>0){
            SetStartFragment();
        }
        else {
            super.onBackPressed();
        }
    }
}
