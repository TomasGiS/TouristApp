package cat.tomasgis.apps.formacio.android.initialapp;

/**
 * Created by TomasGiS on 15/7/16.
 */


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import cat.tomasgis.apps.formacio.android.initialapp.model.TouristPlaceModelTest;
import cat.tomasgis.apps.formacio.android.initialapp.provider.DataProviderTest;

// Runs all unit tests.
@RunWith(Suite.class)
@Suite.SuiteClasses({TouristPlaceModelTest.class, DataProviderTest.class})
public class UnitTestSuite {}
