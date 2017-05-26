package edu.ucsb.ece.ece150.demos.demosuite;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Samuel on 4/20/2017.
 */

public abstract class SavableFragment extends Fragment {
    public static final String NameExtra = "Name";
    public static final String BundleExtra = "Bundle";

    public abstract void saveState(Bundle bundle);

    public abstract void restoreState(Bundle bundle);
}