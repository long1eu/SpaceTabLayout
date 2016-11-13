#Synopsis
This is a custom implementation of a RelativeLayout that you can use along with a ViewPager to navigate between fragments. You can use this library if you have 3 tab(icon + text) or you can use it with 4 or 5 tab(icon only).

![alt text](/readmeSource/gif.gif "Logo Title Text 1")


#Code Example
You can use the fallowing methods to customize its apearance and behavior:

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

You can see in the first image: 
![alt text](/readmeSource/motivation.png "Logo Title Text 1")

#Installation
Add this to your gradle.build file:
```
repositories {
    maven {
        url  "http://dl.bintray.com/thelong1eu/spacetablayout"
    }
}
```
Copy this under dependencies brackets in the same file:
```
compile 'eu.long1:spacetablayout:1.0.0'
```
#License
-------

    Copyright 2016 Lung Razvan

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
---