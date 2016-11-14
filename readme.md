#Synopsis
This is a custom implementation of a RelativeLayout that you can use along with a ViewPager to navigate between fragments. You can use this library if you want 3 tabs(icon + text).

![alt text](/readmeSource/gif.gif "Logo Title Text 1")


#Code usage

You can use this properties in xml for convenience
```xml
        app:tab_color="@color/colorPrimary"
        app:button_color="@color/colorAccent"
        app:text_color="#61FFFFFF"
        
        app:left_icon="@drawable/ic_keyboard"
        app:center_icon="@drawable/ic_fingerprint"
        app:right_icon="@drawable/ic_action"
        
        app:left_text="Left action"
        app:center_text="Center action"
        app:right_text="Right action"
```


You can also use the fallowing methods to customize its appearance and behavior:

| Method                                                    | Description                                                                                                 |
| ----------------------------------------------------------|-------------------------------------------------------------------------------------------------------------|
| getTabLayout();                                           | Gives you the base RelativeLayout.                                                                          |
| setTabColor(@ColorInt int backgroundColor);               | Change the color of the Tab.                                                                                |
| getCurrentPosition();                                     | Gives you the current tab position.                                                                         |
| getButton();                                              | Give you the Floating Action Button View.                                                                   |
| setButtonColor(@ColorInt int backgroundColor);            | Change the color of the Floating Button.                                                                    |
| setOnClickListener(View.OnClickListener l);               | Set the same OnClickListener to all positions.                                                              |
| setLeftActionOnClickListener(View.OnClickListener ll);    | Set the OnClickListener for the left position.                                                              |
| setCenterActionOnClickListener(View.OnClickListener cl);  | Set the OnClickListener for the center position.                                                            |
| setRightActionOnClickListener(View.OnClickListener rl);   | Set the OnClickListener for the right position.                                                             |
| getLeftView();                                            | Get you the left base LinearLayout that contains an ImageView or an ImageView and a TextView.               |
| setLeftView(View leftView);                               | You can set the base layout for the left position.                                                          |
| getCenterView();                                          | Get you the center base LinearLayout that contains an ImageView or an ImageView and a TextView.             |
| setCenterView(View centerView);                           | You can set the base layout for the center position.                                                        |
| getRightView();                                           | Get you the right base LinearLayout that contains an ImageView or an ImageView and a TextView.              |
| setRightView(View rightView);                             | You can set the base layout for the right position.                                                         | 
| setLeftIcon(@DrawableRes int leftButtonIcon);             | Set the icon for both the left tab and the Action Button when in the left position.                         |
| setCenterIcon(@DrawableRes int centerButtonIcon);         | Set the icon for both the center tab and the Action Button when in the center position.                     |
| setRightIcon(@DrawableRes int rightButtonIcon);           | Set the icon for both the right tab and the Action Button when in the right position.                       |
| setLeftText(String leftText);                             | Set the text of the left position.                                                                          |
| setCenterText(String centerText);                         | Set the text of the center position.                                                                        |
| setRightText(String rightText);                           | Set the text of the right position.                                                                         |
| setLeftTextColor(@ColorInt int leftText);                 | Set the text color of the left position.                                                                    |
| setCenterTextColor(@ColorInt int centerText);             | Set the text color of the center position.                                                                  |
| setRightTextColor(@ColorInt int rightText);               | Set the text color of the riht position.                                                                    |


#Motivation
I get the inspiration from the Google Space app that uses a center Floating Action Button. I liked the idea so much that I though that it would be so nice if we could have this like a TabLayout.

You can see in the image:

![alt text](/readmeSource/motivation.png "Logo Title Text 1")

#Installation
###Declaring dependencies
Add this to dependencies brackets in the gradle.build file:
```
compile 'eu.long1:spacetablayout:1.0.2'
```

###XML implementation
In your layout just include this. You can customize the Tab here with text, icons, colors...
```xml
 <eu.long1.spacetablayout.SpaceTabLayout
        android:id="@+id/spaceTabLayout"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" 
        
        app:tab_color="@color/colorPrimary"
        app:button_color="@color/colorAccent"
        app:text_color="#61FFFFFF"
        app:left_icon="@drawable/ic_keyboard"
        app:center_icon="@drawable/ic_fingerprint"
        app:right_icon="@drawable/ic_action"
        app:left_text="Left action"
        app:center_text="Center action"
        app:right_text="Right action"/>
```
####SnackBar behavior
If you are using a CoordinatorLayout as the root of your layout you can add this line to proper handle the behavior when a SnackBar is visible. 
```xml
 app:layout_behavior="eu.long1.spacetablayout.SpaceTabLayoutBehavior" 
```
####XML Example
```xml
<android.support.design.widget.CoordinatorLayout 
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/activity_main"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <android.support.v4.view.ViewPager
        android:id="@+id/viewPager"
        android:layout_width="match_parent"
        android:layout_height="460dp" />

    <eu.long1.spacetablayout.SpaceTabLayout
        android:id="@+id/spaceTabLayout"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:layout_behavior="eu.long1.spacetablayout.SpaceTabLayoutBehavior" />
    
</android.support.design.widget.CoordinatorLayout>

```
###Code implementation
In your MainActivity.java you need to initialize the SpaceTabLayout like this:

```java
    SpaceTabLayout tabLayout;
    
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
    
            //add the fragments you want to display in a List
            List<Fragment> fragmentList = new ArrayList<>();
            fragmentList.add(new FragmentA());
            fragmentList.add(new FragmentB());
            fragmentList.add(new FragmentC());
            
            ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
            tabLayout = (SpaceTabLayout) findViewById(R.id.spaceTabLayout);
            
            //we need the savedInstanceState to get the position
            tabLayout.initialize(viewPager, getSupportFragmentManager(), 
                            fragmentList, savedInstanceState);
        }
    
    
        //we need the outState to save the position
        @Override
        protected void onSaveInstanceState(Bundle outState) {
            tabLayout.saveState(outState);
            super.onSaveInstanceState(outState);
        }
```
#Issues

If you have an issues with this library, please open a issue here: https://github.com/thelong1EU/SpaceTabLayout/issues and provide enough information to reproduce it. The following information needs to be provided:

1. Which version of the SDK are you using?
* What device are you using?
* What steps will reproduce the problem?
* Relevant logcat output.
* Optional: Any screenshot(s) that demonstrate the issue.


#License
    Copyright (c) 2016 Lung Răzvan

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.