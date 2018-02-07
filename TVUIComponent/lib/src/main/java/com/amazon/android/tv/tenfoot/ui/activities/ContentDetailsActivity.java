/**
 * This file was modified by Amazon:
 * Copyright 2015-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *     http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.amazon.android.tv.tenfoot.ui.activities;

import com.amazon.android.contentbrowser.ContentBrowser;
import com.amazon.android.model.event.ActionUpdateEvent;
import com.amazon.android.tv.tenfoot.R;
import com.amazon.android.tv.tenfoot.base.BaseActivity;
import com.amazon.android.tv.tenfoot.ui.fragments.ContentDetailsFragment;
import com.amazon.android.ui.constants.PreferencesConstants;
import com.amazon.android.utils.Preferences;
import com.amazon.utils.DateAndTimeHelper;
import com.zype.fire.auth.ZypeAuthentication;
import com.zype.fire.auth.ZypeLoginActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import android.os.Bundle;
import android.util.Log;

/**
 * Details activity class that loads the LeanbackDetailsFragment class.
 */
public class ContentDetailsActivity extends BaseActivity implements ZypeAuthentication.IAuthenticationActivityParameters {

    private static final String TAG = ContentDetailsActivity.class.getSimpleName();

    public static final String SHARED_ELEMENT_NAME = "hero";

    public ContentDetailsFragment mContentDetailsFragment;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        Log.v(TAG, "onCreate called.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_details_activity_layout);

        mContentDetailsFragment = (ContentDetailsFragment) getFragmentManager().findFragmentById
                (R.id.content_details_fragment);
    }

    @Override
    public void setRestoreActivityValues() {

        Preferences.setString(PreferencesConstants.LAST_ACTIVITY,
                              ContentBrowser.CONTENT_DETAILS_SCREEN);
        Preferences.setLong(PreferencesConstants.TIME_LAST_SAVED,
                            DateAndTimeHelper.getCurrentDate().getTime());
    }

    @Subscribe
    public void onActionListUpdateRequired(ActionUpdateEvent actionUpdateEvent) {

        mContentDetailsFragment.updateActions();
    }

    @Override
    protected void onStart() {

        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {

        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public Bundle getAuthenticationActivityParameters() {
        Bundle parameters = new Bundle();
        parameters.putString(ZypeLoginActivity.PARAMETERS_MESSAGE, getString(R.string.login_message));
        return parameters;
    }

}
